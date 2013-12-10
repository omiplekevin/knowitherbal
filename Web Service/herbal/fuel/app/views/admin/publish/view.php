<h2>Viewing #<?php echo $publish->id; ?></h2>

<p>
	<strong>Comment:</strong>
	<?php echo $publish->comment; ?></p>

<?php echo Html::anchor('admin/publish/edit/'.$publish->id, 'Edit'); ?> |
<?php echo Html::anchor('admin/publish', 'Back'); ?>