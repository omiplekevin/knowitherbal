<?php
class Controller_Admin_Plants extends Controller_Admin{

	public function action_index()
	{
		$data['plants'] = Model_Plant::find('all');
		$this->template->title = "Plants";
		$this->template->content = View::forge('admin\plants/index', $data);

	}

	public function action_view($id = null)
	{
		$data['plant'] = Model_Plant::find($id);

		$this->template->title = "Plant";
		$this->template->content = View::forge('admin\plants/view', $data);

	}

	public function action_create()
	{
		$data['plants'] = Model_Plant::find('all');

		if (Input::method() == 'POST')
		{

			$val = Model_Plant::validate('create');

			if ($val->run())
			{
				$plant = Model_Plant::forge(array(
					'name' => Input::post('name'),
					'scientific_names' => Input::post('scientific_names'),
					'common_names' => Input::post('common_names'),
					'vernacular_names' => Input::post('vernacular_names'),
					'properties' => Input::post('properties'),
					'usage' => Input::post('usage'),
				));

				$config = array(
					'path' => DOCROOT.'herbals/',
					'ext_whitelist' => array('xml'),
				);

				Upload::process($config);

				if (Upload::is_valid()) {

					Upload::save();

					$value = Upload::get_files();

					foreach($value as $files) {
						
						if (input::post('submit')){
						$plant->filename = $value[0]['saved_as'];

									//$parse = simplexml_load_file('http://herbal.dev/herbals/'.$plant->filename);
									//$parse = simplexml_load_file('http://localhost/herbal/public/herbals/'.$plant->filename);
									$parse = simplexml_load_file('http://192.168.180.1:9980/herbal/public/herbals/'.$plant->filename);
									foreach ($parse as $input){
									$name = $input->name;
									$sciname = $input->sci_name;
									$commonname = $input->common_name;
									$vername =  $input->vernacular;
									$properties = $input->properties;
									$usage = $input->usage;
								}
						$query = DB::query("INSERT INTO `plants` (`name`,`scientific_names`, `common_names`, `vernacular_names`, `properties`, `usage`,`filename`) VALUES('$name','$sciname','$commonname','$vername','$properties','$usage','$plant->filename')")->execute();
									 
						}

					}

				

				}

				if ($plant)
				{
					Session::set_flash('success', e('Added plant '.$name.'.'));
				
					Response::redirect('admin/plants');
				}

				else
				{
					Session::set_flash('error', e('Could not save plant.'));
				}
			}
			else
			{
				Session::set_flash('error', $val->error());
			}
		}


		$this->template->title = "Plants";
		$this->template->content = View::forge('admin\plants/create',$data);

	}

	public function action_edit($id = null)
	{
		$plant = Model_Plant::find($id);
		$val = Model_Plant::validate('edit');

		if ($val->run())
		{
			$plant->name = Input::post('name');
			$plant->scientific_name = Input::post('scientific_names');
			$plant->common_names = Input::post('common_names');
			$plant->vernacular_names = Input::post('vernacular_names');
			$plant->properties = Input::post('properties');
			$plant->usage = Input::post('usage');
			$plant->filename = Input::post('filename');

			if ($plant->save())
			{
				Session::set_flash('success', e('Updated plant #' . $id));

				Response::redirect('admin/plants');
			}

			else
			{
				Session::set_flash('error', e('Could not update plant #' . $id));
			}
		}

		else
		{
			if (Input::method() == 'POST')
			{
				$plant->name = $val->validated('name');
				$plant->scientific_name = $val->validated('scientific_names');
				$plant->common_names = $val->validated('common_names');
				$plant->vernacular_names = $val->validated('vernacular_names');
				$plant->properties = $val->validated('properties');
				$plant->usage = $val->validated('usage');
				$plant->filename = $val->validated('filename');

				Session::set_flash('error', $val->error());
			}

			$this->template->set_global('plant', $plant, false);
		}

		$this->template->title = "Plants";
		$this->template->content = View::forge('admin\plants/edit');

	}

	public function action_delete($id = null)
	{
		if ($plant = Model_Plant::find($id))
		{
			$plant->delete();

			Session::set_flash('success', e('Deleted plant #'.$id));
		}

		else
		{
			Session::set_flash('error', e('Could not delete plant #'.$id));
		}

		Response::redirect('admin/plants');

	}


}