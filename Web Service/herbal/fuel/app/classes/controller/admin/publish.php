<?php
class Controller_Admin_Publish extends Controller_Admin{

	public function action_index()
	{
		$data['publishes'] = Model_Publish::find('all');
		$this->template->title = "Publishes";
		$this->template->content = View::forge('admin\publish/index', $data);

	}

	public function action_view($id = null)
	{
		$data['publish'] = Model_Publish::find($id);

		$this->template->title = "Publish";
		$this->template->content = View::forge('admin\publish/view', $data);

	}

	public function action_create()
	{
		if (Input::method() == 'POST')
		{
			$val = Model_Publish::validate('create');

			if ($val->run())
			{
				$publish = Model_Publish::forge(array(
					'comment' => Input::post('comment'),
				));

				if ($publish and $publish->save())
				{
					Session::set_flash('success', e('Added publish #'.$publish->id.'.'));

					Response::redirect('admin/publish');
				}

				else
				{
					Session::set_flash('error', e('Could not save publish.'));
				}
			}
			else
			{
				Session::set_flash('error', $val->error());
			}
		}

		$this->template->title = "Publishes";
		$this->template->content = View::forge('admin\publish/create');

	}

	public function action_edit($id = null)
	{
		$publish = Model_Publish::find($id);
		$val = Model_Publish::validate('edit');

		if ($val->run())
		{
			$publish->comment = Input::post('comment');

			if ($publish->save())
			{
				Session::set_flash('success', e('Updated publish #' . $id));

				Response::redirect('admin/publish');
			}

			else
			{
				Session::set_flash('error', e('Could not update publish #' . $id));
			}
		}

		else
		{
			if (Input::method() == 'POST')
			{
				$publish->comment = $val->validated('comment');

				Session::set_flash('error', $val->error());
			}

			$this->template->set_global('publish', $publish, false);
		}

		$this->template->title = "Publishes";
		$this->template->content = View::forge('admin\publish/edit');

	}

	public function action_delete($id = null)
	{
		if ($publish = Model_Publish::find($id))
		{
			$publish->delete();

			Session::set_flash('success', e('Deleted publish #'.$id));
		}

		else
		{
			Session::set_flash('error', e('Could not delete publish #'.$id));
		}

		Response::redirect('admin/publish');

	}


}