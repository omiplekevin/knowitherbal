<html>
<head></head>
<body>
<?php echo Form::open(array('enctype' => 'multipart/form-data', 'method' => 'post', 'action' => 'upload/upload'));?>
		
		<fieldset>

		<?php echo Form::input('fileselect[]', '',array('type' => 'file', 'multiple' => 'true')); ?>
		<div class="actions">
			<?php echo Form::submit('submit', 'Save', array('class' => 'btn btn-primary')); ?>

		</div>
	</fieldset>

<?php echo Form::close();?>

</body>
</html>
