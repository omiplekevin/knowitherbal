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
				<?php echo Html::anchor('admin/publish/view/'.$item->id, 'View'); ?> |
				<?php echo Html::anchor('admin/publish/edit/'.$item->id, 'Edit'); ?> |
				<?php echo Html::anchor('admin/publish/delete/'.$item->id, 'Delete', array('onclick' => "return confirm('Are you sure?')")); ?>

			</td>
		</tr>
<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Publishes.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/publish/create', 'Add new Publish', array('class' => 'btn btn-success')); ?>

</p>
