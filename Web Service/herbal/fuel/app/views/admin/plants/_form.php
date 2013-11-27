<?php echo Form::open(array("class"=>"form-horizontal",'enctype' => 'multipart/form-data', 'method' => 'post', 'action' => 'admin/plants/create')); ?>

	<div class="form-group">
			<?php echo Form::input('fileselect[]', 'plant', array('type' => 'file', 'multiple' => 'true')); ?>		
	</div>


	<div class="form-group">
		<?php echo Form::input('name', Input::post('name', isset($plant) ? $plant->name : ''), array('class' => 'col-md-4 form-control', 'placeholder'=>'Name')); ?>
	</div>
	
	<div class="form-group">
		<?php echo Form::input('scientific_names', Input::post('scientific_names', isset($plant) ? $plant->scientific_names : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Scientific names')); ?>
	</div>
		
	<div class="form-group">
		<?php echo Form::input('common_names', Input::post('common_names', isset($plant) ? $plant->common_names : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Common names')); ?>
	</div>
		
	<div class="form-group">
		<?php echo Form::input('vernacular_names', Input::post('vernacular_names', isset($plant) ? $plant->vernacular_names : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Vernacular names')); ?>
	</div>
		
	<div class="form-group">
		<?php echo Form::input('properties', Input::post('properties', isset($plant) ? $plant->properties : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Properties')); ?>

	</div>
		
	<div class="form-group">
		<?php echo Form::input('usage', Input::post('usage', isset($plant) ? $plant->usage : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Usage')); ?>
	</div>
		
	
	<div class="form-group">
			<label class='control-label'>&nbsp;</label>
		<?php echo Form::submit('submit', 'Save', array('class' => 'btn btn-primary')); ?>		</div>
	</fieldset>
<?php echo Form::close(); ?>