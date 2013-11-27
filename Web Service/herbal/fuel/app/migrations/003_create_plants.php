<?php

namespace Fuel\Migrations;

class Create_plants
{
	public function up()
	{
		\DBUtil::create_table('plants', array(
			'id' => array('constraint' => 11, 'type' => 'int', 'auto_increment' => true, 'unsigned' => true),
			'name' => array('constraint' => 255, 'type' => 'varchar'),
			'scientific_name' => array('type' => 'text'),
			'common_names' => array('type' => 'text'),
			'vernacular_names' => array('type' => 'text'),
			'properties' => array('type' => 'text'),
			'usage' => array('type' => 'text'),
			'filename' => array('type' => 'text'),
			'created_at' => array('constraint' => 11, 'type' => 'int', 'null' => true),
			'updated_at' => array('constraint' => 11, 'type' => 'int', 'null' => true),

		), array('id'));
	}

	public function down()
	{
		\DBUtil::drop_table('plants');
	}
}