<h2>Listing Images</h2>
<br>
<?php if ($images): ?>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Plant id</th>
			<th>Url</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($images as $item): ?>		<tr>

			<td><?php echo $item->plant->name; ?></td>
			<td><?php echo $item->url; ?></td>
			<td>
				<?php echo Html::anchor('admin/images/view/'.$item->id, 'View'); ?> |
				<?php echo Html::anchor('admin/images/edit/'.$item->id, 'Edit'); ?> |
				<?php echo Html::anchor('admin/images/delete/'.$item->id, 'Delete', array('onclick' => "return confirm('Are you sure?')")); ?>

			</td>
		</tr>
<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Images.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/images/create', 'Add new Image', array('class' => 'btn btn-success')); ?>

</p>
