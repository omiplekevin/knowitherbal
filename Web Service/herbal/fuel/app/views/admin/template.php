<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title><?php echo "knoWITHerbal - ".$title; ?></title>
	<?php echo Asset::css('bootstrap.css'); ?>
	<style>
		body { margin: 50px; }
	</style>
	<?php echo Asset::js(array(
		'jquery.min.js',
		'bootstrap.js'
	)); ?>
	<script>
		$(function(){ $('.topbar').dropdown(); });
	</script>
	<link rel="shortcut icon" href="http://omiplekevin:9980/herbal/public/assets/img/fav_icon.ico" />
</head>
<body>

	<?php if ($current_user): ?>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index" style="position: relative; padding: 0;"><?php echo Html::img('assets/img/brand.png')?></a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="<?php echo Uri::segment(2) == '' ? 'active' : '' ?>">
						<?php echo Html::anchor('admin', 'Dashboard') ?>
					</li>

					
				</ul>
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"><?php echo $current_user->username ?> <b class="caret"></b></a>
						<ul class="dropdown-menu">

							<?php
						$files = new GlobIterator(APPPATH.'classes/controller/admin/*.php');
						foreach($files as $file)
						{
							$section_segment = $file->getBasename('.php');
							$section_title = Inflector::humanize($section_segment);
							?>
							<li class="<?php echo Uri::segment(2) == $section_segment ? 'active' : '' ?>">
								<?php echo Html::anchor('admin/'.$section_segment, $section_title) ?>
							</li>
							<?php
						}
					?>
							
							<li><?php echo Html::anchor('admin/logout', 'Logout') ?></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<?php endif; ?>

	<div class="container" style="margin-bottom: 100px;">
		<div class="row">
			<div class="col-md-12">
				<h1><?php echo $title; ?></h1>
				<hr>
<?php if (Session::get_flash('success')): ?>
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					<p>
					<?php echo implode('</p><p>', (array) Session::get_flash('success')); ?>
					</p>
				</div>
<?php endif; ?>
<?php if (Session::get_flash('error')): ?>
				<div class="alert alert-error alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					<p>
					<?php echo implode('</p><p>', (array) Session::get_flash('error')); ?>
					</p>
				</div>
<?php endif; ?>
			</div>
			<div class="col-md-12">
<?php echo $content; ?>
			</div>
		</div>		
		<hr/>
		<footer class="navbar-fixed-bottom" style="background-color:#6FE26F; height: 60px; opacity: .5;">
			<p style="text-align:right; z-index: -100;"><?php echo Html::img('assets/img/web_footer_bg.png') ?></p>
			<p style="text-align:center; position: relative; top: -50px">
				<a href="http://fuelphp.com">FuelPHP</a> is released under the MIT license.
				<small>Version: <?php echo e(Fuel::VERSION); ?></small>
			</p>
		</footer>
	</div>
</body>
</html>
