<h2>Editing Plant</h2>
<br>

<?php echo Form::open(array("class"=>"form-horizontal")); ?>

	<fieldset>
		<div class="form-group">
			<?php echo Form::label('Name', 'name', array('class'=>'control-label')); ?>

				<?php echo Form::input('name', Input::post('name', isset($plant) ? $plant->name : ''), array('class' => 'col-md-4 form-control', 'placeholder'=>'Name')); ?>

		</div>
		<div class="form-group">
			<?php echo Form::label('Scientific names', 'scientific_names', array('class'=>'control-label')); ?>

				<?php echo Form::textarea('scientific_names', Input::post('scientific_names', isset($plant) ? $plant->scientific_names : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Scientific names')); ?>

		</div>
		<div class="form-group">
			<?php echo Form::label('Common names', 'common_names', array('class'=>'control-label')); ?>

				<?php echo Form::textarea('common_names', Input::post('common_names', isset($plant) ? $plant->common_names : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Common names')); ?>

		</div>
		<div class="form-group">
			<?php echo Form::label('Vernacular names', 'vernacular_names', array('class'=>'control-label')); ?>

				<?php echo Form::textarea('vernacular_names', Input::post('vernacular_names', isset($plant) ? $plant->vernacular_names : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Vernacular names')); ?>

		</div>
		<div class="form-group">
			<?php echo Form::label('Properties', 'properties', array('class'=>'control-label')); ?>

				<?php echo Form::textarea('properties', Input::post('properties', isset($plant) ? $plant->properties : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Properties')); ?>

		</div>
		<div class="form-group">
			<?php echo Form::label('Usage', 'usage', array('class'=>'control-label')); ?>

				<?php echo Form::textarea('usage', Input::post('usage', isset($plant) ? $plant->usage : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Usage')); ?>

		</div>
		<div class="form-group">
			<label class='control-label'>&nbsp;</label>
			<?php echo Form::submit('submit', 'Save', array('class' => 'btn btn-primary')); ?>		</div>
	</fieldset>
<?php echo Form::close(); ?>

<p>
	<?php echo Html::anchor('admin/plants/view/'.$plant->id, 'View'); ?> |
	<?php echo Html::anchor('admin/plants', 'Back'); ?></p>
