<h2>Editing Publish</h2>
<br>

<?php echo render('admin\publish/_form'); ?>
<p>
	<?php echo Html::anchor('admin/publish/view/'.$publish->id, 'View'); ?> |
	<?php echo Html::anchor('admin/publish', 'Back'); ?></p>
