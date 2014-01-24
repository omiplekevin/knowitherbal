<!DOCTYPE html>
<html>
<head>
	<link rel="icon" 
      type="image/png" 
      href="http://localhost:9980/herbal/public/assets/img/fav_icon.ico">
	<meta charset="utf-8">
	<title><?php echo "knowITherbal - ".$title; ?></title>
	<?php echo Asset::css('bootstrap.css'); ?>
	<?php echo Asset::css('paginate.css'); ?>
	<?php /*echo Asset::css('generic.css');*/ ?>
	<?php echo Asset::css('js-image-slider.css'); ?>
	<style>
		body { margin: 50px; }
	</style>
	<?php echo Asset::js(array(
		'jquery.min.js',
		'bootstrap.js',
		'seemore.js',
		'custom.js',
		'jquery.paginate.js',
		'jquery.paginate.min.js',
		'js-image-slider.js'
	)); ?>
	
	<script>
		$(function(){ $('.topbar').dropdown(); });
	</script>

	
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
				<?php echo Html::img('assets/img/brand.png')?>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="<?php echo Uri::segment(2) == '' ? 'active' : '' ?>">
						<?php echo Html::anchor('admin', 'Dashboard') ?>
					</li>

					
				</ul>
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"><?php echo 'Howdy '.$current_user->username.'!' ?> <b class="caret"></b></a>
						<ul class="dropdown-menu">

							<?php
						$files = new GlobIterator(APPPATH.'classes/controller/admin/*.php');
						$i = 0;
						$icons = array("images.png", "plants.png", "publish.png", "users.png");
								foreach($files as $file)
								{

									$section_segment = $file->getBasename('.php');
									$section_title = Inflector::humanize($section_segment);
									?>
									<li class="<?php echo Uri::segment(2) == $section_segment ? 'active' : '' ?>">
										<?php echo Html::anchor('admin/'.$section_segment, Html::img('assets/img/'.$icons[$i]).'&nbsp;&nbsp;&nbsp;&nbsp;'.$section_title) ?>
									</li>
									<?php
									$i++;
								}
							?>
							
							<li><?php echo Html::anchor('admin/logout', Html::img('assets/img/logout.png').'&nbsp;&nbsp;&nbsp;&nbsp;Logout') ?></li>
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
				<h1><?php if($title == "Login") echo Html::img('assets/img/login_large.png').$title; ?>
				</h1>
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
		<footer class="navbar-fixed-bottom" style="background-color:#222222; height: 30px; opacity: .9;">
			<p style="text-align:center; position: relative; top: -3px; color: #FFF;">Developed under FuelPHP</p>
			<p style="text-align:center; position: relative; top: -20px; color: #FFF;">
				<a href="http://fuelphp.com">FuelPHP</a> is released under the MIT license.
				<small>Version: <?php echo e(Fuel::VERSION); ?></small>
			</p>
		</footer>
	</div>
</body>
</html>
