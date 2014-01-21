<h2>Image File Listing</h2>
<br>

<?php if ($images): ?>

	<div id = "paginate" style = "float: right"></div><br><br>
	<div id = "status" style = "float: right; margin-right: -150px"></div>
	<?php echo Html::anchor('admin/images/create', 'New Image', array('class' => 'btn btn-success')); ?>

<table class="table table-striped">
	<thead>
		<tr>
			<th>Plant Name</th>
			<th>Image</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($images as $item): ?>		<tr>

			<td><?php echo $item->plant->name; ?></td>
			<td><?php echo Html::img('herbals_photos/'.$item->plant->id.'/thumbs/'.$item->url); ?></td>

			<td>
				<?php echo Html::anchor('admin/images/view/'.$item->id, Html::img('assets/img/open.png'), array('title' => 'View')); ?> |
				<?php echo Html::anchor('admin/images/edit/'.$item->id, Html::img('assets/img/edit.png'), array('title' => 'Edit')); ?> |
				<?php echo Html::anchor('admin/images/delete/'.$item->id, Html::img('assets/img/delete.png'), array('title' => 'Delete','onclick' => "return confirm('Are you sure?')")); ?>

			</td>
		</tr>
<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Images.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/images/create', 'New Image', array('class' => 'btn btn-success')); ?>

</p>
