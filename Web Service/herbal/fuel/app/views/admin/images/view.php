<h2>Viewing #<?php echo $image->id; ?></h2>

<p>
	<strong>Plant Name:</strong>
	<?php echo $image->plant->name; ?></p>
<p>
	<strong>Original:</strong>
	<?php echo Html::img('herbals_photos/'.$image->plant->id.'/'.$image->url); ?></p><br/>

<p>
	<strong>Thumbnail:</strong>
	<?php echo Html::img('herbals_photos/thumbs/'.$image->plant->id.'/'.$image->url); ?></p>

<?php echo Html::anchor('admin/images/edit/'.$image->id, 'Edit'); ?> |
<?php echo Html::anchor('admin/images', 'Back'); ?>