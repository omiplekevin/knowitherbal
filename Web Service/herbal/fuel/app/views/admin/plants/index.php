<h2>Listing Plants</h2>
<br>

<?php if ($plants): ?>

	<div id = "paginate" style = "float: right"></div><br><br>
	<div id = "status" style = "float: right; margin-right: -150px"></div>
	<?php echo Html::anchor('admin/plants/create', 'Add new plant detail', array('class' => 'btn btn-success')); ?>

<table class="table table-striped">
	<thead>
		<tr>
			<th style="margin-right:80px"><p style="margin-right:80px; text-align:center">Name</p></th>
			<th><p style="text-align:center">Scientific names</p></th>
			<th><p style="text-align:center">Common names</p></th>
			<th><p style="text-align:center">Vernacular names</p></th>
			<th style="margin-right:80px;"><p style="margin-right:80px; text-align:center">Properties</p></th>
			<th><p style="text-align:center">Usage</p></th>
			<th><p style="text-align:center">Filename</p></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($plants as $plant): ?>		<tr>
			
			<td><font size="4"><strong><?php echo $plant->name; ?></strong></font></td>

			<td><p class = "seemore" style = "text-align:justify">
				<?php echo $plant->scientific_names; ?></p></td>

			<td><p class = "seemore" style = "text-align:justify">
				<?php echo $plant->common_names; ?></p></td>

			<td><p class = "seemore" style = "text-align:justify">
				<?php echo $plant->vernacular_names; ?></p></td>

			<td><p class = "seemore" style = "text-align:justify">
				<?php echo $plant->properties; ?></p></td>

			<td><p class = "seemore" style = "text-align:justify">
				<?php echo $plant->usage; ?></p></td>

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
