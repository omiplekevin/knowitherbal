<h2>Publish Listing</h2>
<br>
<?php if ($publishes): ?>

	<div id = "paginate" style = "float: right"></div><br><br>
	<div id = "status" style = "float: right; margin-right: -150px"></div>
	
<table class="table table-striped">
	<thead>
		<tr>
			<th>Comment</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($publishes as $item): ?>		<tr>

			<td><?php echo $item->comment; ?></td>
			<td>
				<?php echo Html::anchor('admin/publish/view/'.$item->id, Html::img('assets/img/open.png'), array('title' => 'View')); ?></a> |
				<?php echo Html::anchor('admin/publish/edit/'.$item->id, Html::img('assets/img/edit.png'), array('title' => 'Edit')); ?> |
				<?php echo Html::anchor('admin/publish/delete/'.$item->id, Html::img('assets/img/delete.png'), array('title' => 'Delete','onclick' => "return confirm('Confirm delete publish number ".$item->id."?')")); ?>
			</td>
<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Publishes.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/publish/create', 'New Publish', array('class' => 'btn btn-success')); ?>

</p>
