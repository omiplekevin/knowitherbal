<h2>Listing Plants</h2>
<br>
<?php if ($plants): ?>

<?php echo Html::anchor('admin/plants/create', 'Add new plant detail', array('class' => 'btn btn-success')); ?>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Scientific names</th>
			<th>Common names</th>
			<th>Vernacular names</th>
			<th>Properties</th>
			<th>Usage</th>
			<th>Filename</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($plants as $plant): ?>		<tr>
			
			<td><?php echo $plant->name; ?></td>
			<td><?php echo $plant->scientific_names; ?></td>
			<td><?php echo $plant->common_names; ?></td>
			<td><?php echo $plant->vernacular_names; ?></td>
			<td><?php echo $plant->properties; ?></td>
			<td><?php echo $plant->usage; ?></td>
			<td><?php echo $plant->filename; ?></td>
			<td>
				<?php echo Html::anchor('admin/plants/view/'.$plant->id, 'View'); ?> |
				<?php echo Html::anchor('admin/plants/edit/'.$plant->id, 'Edit'); ?> |
				<?php echo Html::anchor('admin/plants/delete/'.$plant->id, 'Delete', array('onclick' => "return confirm('Are you sure?')")); ?>

			</td>
		</tr>

<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Plants.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/plants/create', 'Add new plant detail', array('class' => 'btn btn-success')); ?>

	

</p>
