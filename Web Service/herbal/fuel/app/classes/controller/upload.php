<?php

class Controller_Upload extends Controller
{

	public function action_index()
	{
		return Response::forge(View::forge('upload'));
	}

	public function action_upload()
	{
		// Custom configuration for this upload
		$config = array(
		    'path' => DOCROOT.'files',
		    'randomize' => true,
		    'ext_whitelist' => array('img', 'jpg', 'jpeg', 'gif', 'png'),
		);

		// process the uploaded files in $_FILES
		Upload::process($config);

		// if there are any valid files
		if (Upload::is_valid())
		{
		    // save them according to the config
		    Upload::save();

		    // call a model method to update the database
		    //Model_Uploads::add(Upload::get_files());
		}

		
	}

	
}
