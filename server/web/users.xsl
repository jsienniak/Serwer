<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
		<xsl:import href="common.xsl"/>
	<xsl:template name="content">
	<script>
	function logout(){
		alert('logout');
	}
	function clearForm(){
		alert('clearForm');
	}
	function edit(id){
		alert('edit '+id);
	}
	function del(idd){
		$('#id_delete').val(idd);
		$('form[name="delete_form"]').submit();
	}
	</script>
	<form action="/do" name="delete_form">
	<input type="hidden" name="action" value="user.delete" />
	<input type="hidden" name="id" id="id_delete" value="" />
	</form>
	
		<div class="container">
			<div class="hero-unit">
				<h2>Zarządzanie</h2>
				<h4>Lokatorzy</h4>
				<form class="form-horizontal" id="input_form" action="/do">
					<input type="hidden" name="action" value="user.add" />
					<input type="hidden" name="id_users" value="{id_users}" />
					<div class="control-group">
						<label class="control-label" for="inputEmail">login</label>
						<div class="controls">
							<input type="text" id="inputEmail" name="login" placeholder="login" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Hasło</label>
						<div class="controls">
							<input type="text" id="inputEmail" name="password" placeholder="Hasło" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Email</label>
						<div class="controls">
							<input type="text" id="inputEmail" name="email" placeholder="Email" />
						</div>
					</div>
					<button type="submit" class="btn btn-primary">Dodaj</button>
					<button class="btn btn-inverse" onClick="clearForm()">Wyczyść</button>

		</form>
		</div>
			<!-- Main hero unit for a primary marketing message or call to action -->
			<div class="hero-unit">
				<h2>Lokatorzy</h2>
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>ID</th>
							<th>Login</th>
							<th>Email</th>
							<th>E</th>
							<th>D</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="root/users/row" />
						
					</tbody>
				</table>
			</div>
			
		<footer>
			<p> Company 2012</p>
		</footer>
		</div> <!-- /container -->

	</xsl:template>
	<xsl:template match="row">
		<tr>
			<td><xsl:value-of select="id_users"/></td>
			<td><xsl:value-of select="login"/></td>
			<td><xsl:value-of select="email"/></td>
			<td><button class="btn btn-danger btn-mini" onClick="edit({id_users})">E</button></td>
			<td><button class="btn btn-danger btn-mini" onClick="del({id_users})">X</button></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>