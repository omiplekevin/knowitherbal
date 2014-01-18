<h2>Listing Plants</h2>
<br>

<?php if ($plants): ?>

	<div id = "paginate" style = "float: right"></div><br><br>
	<div id = "status" style = "float: right; margin-right: -150px"></div>
	<?php echo Html::anchor('admin/plants/create', 'Add new plant detail', array('class' => 'btn btn-success')); ?>

<table class="table table-striped">
	<thead>
		<tr>
			<th><p>Name</p></th>
			<th><p>Scientific names</p></th>
			<th><p>Common names</p></th>
			<th><p>Vernacular names</p></th>
			<th style="margin-right:150px;"><p style="margin-right:150px">Properties</p></th>
			<th><p>Usage</p></th>
			<!-- <th><p>Filename</p></th> -->
			<th><p style="margin-right: 70px; margin-left: 70px;"> </p></th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($plants as $plant): ?>		<tr>
			
			<td><b><?php echo $plant->name; ?></b></td>
			<td><p class = "seemore"><?php echo $plant->scientific_names; ?></p></td>
			<td><p class = "seemore"><?php echo $plant->common_names; ?></p></td>
			<td><p class = "seemore"><?php echo $plant->vernacular_names; ?></p></td>
			<td><p class = "seemore" ><?php echo $plant->properties; ?></p></td>
			<td><p class = "seemore"><?php echo $plant->usage; ?></p></td>
			<!-- <td><?php echo $plant->filename; ?></td> -->
			<td>
				<?php echo Html::anchor('admin/plants/view/'.$plant->id, Html::img('assets/img/open.png')); ?> |
				<?php echo Html::anchor('admin/plants/edit/'.$plant->id, Html::img('assets/img/edit.png')); ?> |
				<?php echo Html::anchor('admin/plants/delete/'.$plant->id, Html::img('assets/img/delete.png'), array('onclick' => "return confirm('Are you sure?')")); ?>

			</td>
		</tr>

<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Plants.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/plants/create', 'Add new plant detail', array('class' => 'btn btn-success')); ?>

	

</p>
