<?php

namespace Fuel\Migrations;

class Create_publishes
{
	public function up()
	{
		\DBUtil::create_table('publishes', array(
			'id' => array('constraint' => 11, 'type' => 'int', 'auto_increment' => true, 'unsigned' => true),
			'comment' => array('type' => 'text'),
			'modified' => array('type' => 'date'),
			'created_at' => array('constraint' => 11, 'type' => 'int', 'null' => true),
			'updated_at' => array('constraint' => 11, 'type' => 'int', 'null' => true),

		), array('id'));
	}

	public function down()
	{
		\DBUtil::drop_table('publishes');
	}
}