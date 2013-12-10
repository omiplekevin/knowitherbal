<?php echo Form::open(array("class"=>"form-horizontal")); ?>

	<fieldset>
		<div class="form-group">
			<?php echo Form::label('Comment', 'comment', array('class'=>'control-label')); ?>

				<?php echo Form::textarea('comment', Input::post('comment', isset($publish) ? $publish->comment : ''), array('class' => 'col-md-8 form-control', 'rows' => 8, 'placeholder'=>'Comment')); ?>

		</div>
		<div class="form-group">
			<label class='control-label'>&nbsp;</label>
			<?php echo Form::submit('submit', 'Save', array('class' => 'btn btn-primary')); ?>		</div>
	</fieldset>
<?php echo Form::close(); ?>