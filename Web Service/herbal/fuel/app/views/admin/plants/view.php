<h2>Viewing #<?php echo $plant->id; ?></h2>

<p>
	<strong>Name:</strong>
	<?php echo $plant->name; ?></p>
<p>
	<strong>Scientific names:</strong>
	<?php echo $plant->scientific_names; ?></p>
<p>
	<strong>Common names:</strong>
	<?php echo $plant->common_names; ?></p>
<p>
	<strong>Vernacular names:</strong>
	<?php echo $plant->vernacular_names; ?></p>
<p>
	<strong>Properties:</strong>
	<?php echo $plant->properties; ?></p>
<p>
	<strong>Usage:</strong>
	<?php echo $plant->usage; ?></p>
<p>
	<strong>Filename:</strong>
	<?php echo $plant->filename; ?></p>

<?php echo Html::anchor('admin/plants/edit/'.$plant->id, 'Edit'); ?> |
<?php echo Html::anchor('admin/plants', 'Back'); ?>