<?php echo Form::open(array("class"=>"form-horizontal",'enctype' => 'multipart/form-data', 'method' => 'post', 'action' => 'admin/plants/create')); ?>

	<div class="form-group">
			<?php echo Form::input('fileselect[]', 'plant', array('type' => 'file', 'multiple' => 'true')); ?>		
	</div>
		
	
	<div class="form-group">
			<label class='control-label'>&nbsp;</label>
		<?php echo Form::submit('submit', 'Save', array('class' => 'btn btn-primary')); ?>		</div>
	</fieldset>
<?php echo Form::close(); ?>