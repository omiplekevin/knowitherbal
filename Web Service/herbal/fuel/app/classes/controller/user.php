<?php

class Controller_User extends Controller_Base
{
	public $template = 'user/template';

	public function action_index()
	{
		$this->template->title = 'Dashboard';
		$this->template->content = View::forge('user/dashboard');
	}

}

/* End of file admin.php */