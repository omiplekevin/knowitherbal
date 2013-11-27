<?php
class Model_Plant extends \Orm\Model
{
	protected static $_properties = array(
		'id',
		'name',
		'scientific_names',
		'common_names',
		'vernacular_names',
		'properties',
		'usage',
		'filename',
		'created_at',
		'updated_at',
	);

	protected static $_observers = array(
		'Orm\Observer_CreatedAt' => array(
			'events' => array('before_insert'),
			'mysql_timestamp' => false,
		),
		'Orm\Observer_UpdatedAt' => array(
			'events' => array('before_save'),
			'mysql_timestamp' => false,
		),
	);

	public static function validate($factory)
	{
		$val = Validation::forge($factory);
		$val->add_field('name', 'Name', '|max_length[255]');
		$val->add_field('scientific_names', 'Scientific Names', '');
		$val->add_field('common_names', 'Common Names', '');
		$val->add_field('vernacular_names', 'Vernacular Names', '');
		$val->add_field('properties', 'Properties', '');
		$val->add_field('usage', 'Usage', '');
		$val->add_field('filename', 'Filename', '');

		return $val;
	}

}
