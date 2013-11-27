<h2>Viewing #<?php echo $image->id; ?></h2>

<p>
	<strong>Plant id:</strong>
	<?php echo $image->plant->name; ?></p>
<p>
	<strong>Url:</strong>
	<?php echo $image->url; ?></p>

<?php echo Html::anchor('admin/images/edit/'.$image->id, 'Edit'); ?> |
<?php echo Html::anchor('admin/images', 'Back'); ?>