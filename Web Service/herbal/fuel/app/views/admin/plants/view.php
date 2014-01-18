<h2><?php echo $plants->name; ?></h2>
<p>
	<strong>Scientific names:</strong>
	<?php echo $plants->scientific_names; ?></p>
<p>
	<strong>Common names:</strong>
	<?php echo $plants->common_names; ?></p>
<p>
	<strong>Vernacular names:</strong>
	<?php echo $plants->vernacular_names; ?></p>
<p>
	<strong>Properties:</strong>
	<?php echo $plants->properties; ?></p>
<p>
	<strong>Usage:</strong>
	<?php echo $plants->usage; ?></p>
<p>
	<strong>Filename:</strong>
	<?php echo $plants->filename; ?></p>
<p>
	<strong>Images:</strong>
	<?php foreach ($plants->images as $images):?>
	<?php //echo Html::img('herbals_photos/thumbs/'.$images->plant->id.'/'.$images->url);
    //echo $images->id;
	 ?>
	<?php endforeach ?> </p>

<?php echo Html::anchor('admin/plants/edit/'.$plants->id, 'Edit'); ?> |
<?php echo Html::anchor('admin/plants', 'Back'); ?>