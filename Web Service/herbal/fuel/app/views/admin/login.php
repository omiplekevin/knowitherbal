<div class="row">
	<div style="position: absolute; width: 100%; height: 306px;" >
		<?php echo Html::img('assets/img/banners/login_banner.png') ?>
	</div>
	<div class="col-md-3">
		<?php echo Form::open(array()); ?>

			<?php if (isset($_GET['destination'])): ?>
				<?php echo Form::hidden('destination',$_GET['destination']); ?>
			<?php endif; ?>

			<?php if (isset($login_error)): ?>
				<div class="error" style="color: #FF0000; text-shadow 0px, 1px, 3px, rgba(255,0,0,1); position: absolute; margin-top: -30px;">
					<?php /*echo $login_error;*/ ?>
					<?php echo 'Incorrect E-mail or Password!'; ?>
				</div>
			<?php endif; ?>

			<div class="form-group <?php echo ! $val->error('email') ?: 'has-error' ?>" style="margin-top: 40px;">
				<!-- <label for="email" style="color: #CCC;">Email or Username:</label> -->
				<?php echo Form::input('email', Input::post('email'), array('class' => 'form-control', 'placeholder' => 'Email or Username', 'autofocus')); ?>

				<?php if ($val->error('email')): ?>
					<span class="control-label"><?php echo $val->error('email')->get_message('You must provide a username or email'); ?></sőan>
				<?php endif; ?>
			</div>

			<div class="form-group <?php echo ! $val->error('password') ?: 'has-error' ?>">
				<!-- <label for="password" style="color: #CCC;">Password:</label> -->
				<?php echo Form::password('password', null, array('class' => 'form-control', 'placeholder' => 'Password')); ?>

				<?php if ($val->error('password')): ?>
					<span class="control-label"><?php echo $val->error('password')->get_message(':label cannot be blank'); ?></span>
				<?php endif; ?>
			</div>

			<div class="actions">
				<?php echo Form::submit(array('value'=>'Login', 'name'=>'submit', 'class' => 'btn btn-lg btn-primary btn-block')); ?>
			</div>

			<div>
				<a id="custom_anchor" href="http://localhost:9980/herbal/public/user" title="Visitor" style="color: #AAED93; text-decoration: none;">
				Proceed to visitors' page</a>
			</div>
		<?php echo Form::close(); ?>
	</div>
</div>