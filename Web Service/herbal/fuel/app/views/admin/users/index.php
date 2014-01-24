<h2><?php echo Html::img('assets/img/users_large.png'); ?>User <span class='muted'>List</span></h2>
<br>
<?php if ($users): ?>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Username</th>
			<th>Login Hash</th>
			<th>Email</th>
			<th>Last login</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
<?php foreach ($users as $item): ?>		<tr>

			<td><?php echo $item->username; ?></td>
			<td><?php echo $item->login_hash; ?></td>
			<td><?php echo $item->email; ?></td>
			<td><?php echo date('F jS, Y - l g:ia',$item->last_login); ?></td>
			<td>
				<div class="btn-toolbar">
					<div class="btn-group">
						<?php echo Html::anchor('admin/users/view/'.$item->id, Html::img('assets/img/open.png'), array('class' => 'btn btn-small')); ?>
						<?php echo Html::anchor('admin/users/edit/'.$item->id, Html::img('assets/img/edit.png'), array('class' => 'btn btn-small')); ?>
						<?php echo Html::anchor('admin/users/delete/'.$item->id, Html::img('assets/img/delete.png'), array('class' => 'btn btn-small', 'onclick' => "return confirm('Are you sure?')")); ?>
					</div>
				</div>

			</td>
		</tr>
<?php endforeach; ?>	</tbody>
</table>

<?php else: ?>
<p>No Users.</p>

<?php endif; ?><p>
	<?php echo Html::anchor('admin/users/create', 'Add new User', array('class' => 'btn btn-success')); ?>

</p>
