<?php echo Form::open(array("class"=>"form-horizontal",'enctype' => 'multipart/form-data', 'method' => 'post', 'action' => 'admin/images/create')); ?>

	<fieldset>
		<div class="form-group">
			<?php echo Form::label('Plant id', 'plant_id', array('class'=>'control-label')); ?>

				<?php echo Form::select('plant_id', Input::post('plant_id', isset($image) ? $image->plant_id : ''), $plant, array('placeholder'=>'Plant id')); ?>

		</div>
		<div class="form-group">
			<?php echo Form::input('fileselect[]', '',array('type' => 'file', 'multiple' => 'true')); ?>
			
		</div>
		<div class="form-group">
			<label class='control-label'>&nbsp;</label>
			<?php echo Form::submit('submit', 'Save', array('class' => 'btn btn-primary')); ?>		</div>
	</fieldset>
<?php echo Form::close(); ?>