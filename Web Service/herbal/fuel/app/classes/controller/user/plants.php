<?php
class Controller_User_Plants extends Controller_User{

	public function action_index()
	{
		$plant = Model_Plant::find('all', array('related' => array('images')));
		
		$data['plants'] = $plant;

		$this->template->title = "Plants";
		$this->template->content = View::forge('user/plants/index', $data, array('plant' => $plant));

	}

	public function action_view($id = null)
	{

		$plant = Model_Plant::find($id, array('related' => array('images')));
		
		$data['plants'] = $plant;

		$this->template->title = "Plant";
		$this->template->content = View::forge('user\plants/view', $data, array('plant' => $plant));

	}

}