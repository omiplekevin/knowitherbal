<h2><?php echo $plants->name; ?>
	<div style="font-size: 14px; position: relative; display: inline-block;">
		<?php echo Html::anchor('admin/plants/edit/'.$plants->id, 'Edit'); ?> |
		<?php echo Html::anchor('admin/plants', 'Back'); ?>
	</div>
</h2>
<p>
	<strong>Scientific names:</br>• </strong>
	<?php echo str_replace('||', ' • ', $plants->scientific_names); ?></p>
<p>
	<strong>Common names:</br></strong>
	<?php echo str_replace('||', ' • ', $plants->common_names); ?></p>
<p>
	<strong>Vernacular names:</br></strong>
	<?php echo str_replace('||', ' • ', $plants->vernacular_names); ?></p>
<p>
	<strong>Properties:</br></strong>
	<?php echo str_replace('||', ' • ', $plants->properties); ?></p>
<p>
	<strong>Usage:</br></strong>
	<?php echo str_replace('||', ' • ', $plants->usage); ?></p>
<!-- <p>
	<strong>Filename:</strong>
	<?php /*echo str_replace('||', ' • ', $plants->filename);*/ ?></p> -->
<p>
	<strong>Images:</strong>
	<div style="width: 50%; margin: 0 auto; display: block;">
		<?php
			$urls = DB::query("SELECT `url` FROM `images` WHERE `plant_id` = ".$plants->id)->execute()->as_array();
			foreach($urls as $url)
			{
				echo "<div style='margin: 5px; position: relative; float: left; box-shadow: 0px 2px 5px #000;'>"
				.Html::img('herbals_photos/'.$plants->id.'/thumbs'.'/'.$url['url'])."</div>";
			}
		?>
	</div>
</p>