<h2><strong><?php echo $plants->name; ?></strong></h2></br>

<h5><strong>Scientific names:</strong></br>
	<p style="text-align:justify; padding-left:2em"></br>
		<?php echo $plants->scientific_names; ?></p></h5>

<h5><strong>Common names:</strong>
	<p style="text-align:justify; padding-left:2em"></br>
		<?php echo $plants->common_names; ?></p></h5>

<h5><strong>Vernacular names:</strong>
	<p style="text-align:justify; padding-left:2em"></br>
		<?php echo $plants->vernacular_names; ?></p></h5>

<h5><strong>Properties:</strong>
	<p style="text-align:justify; padding-left:2em"></br>
		<?php echo $plants->properties; ?></p></h5>

<h5><strong>Usage:</strong>
	<p style="text-align:justify; padding-left:2em"></br>
		<?php echo $plants->usage; ?></p></h5>

<h5><strong>Filename:</strong>
	<p style="padding-left:2em"></br>
		<?php echo $plants->filename; ?></p></h5>
<p>
	<strong>Images:</strong>
	<?php foreach ($plants->images as $image):?>
	<?php //echo Html::img('herbals_photos/'.$image->plant->id.'/thumbs/'.$image->url);
          echo $plants->id;
	 ?>
	<?php endforeach ?> </p>

<?php echo Html::anchor('admin/plants/edit/'.$plants->id, 'Edit'); ?> |
<?php echo Html::anchor('admin/plants', 'Back'); ?>