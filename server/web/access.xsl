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
		return false;
	}
	</script>
	<style>
		body td{text-align:center;}
	</style>
	<form action="/zpi_server/do" name="delete_form">
	<input type="hidden" name="action" value="user.delete" />
	<input type="hidden" name="id" id="id_delete" value="" />
	</form>
	
		<div class="container">
			<div class="hero-unit">
				<h2>Zarządzanie</h2>
				<h4>Prawa dostępu</h4>
				<form class="form-horizontal" id="input_form" action="/zpi_server/do">
				<input type="hidden" name="action" value="user.edit" />
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>ID</th>
							<th>Email</th>
							<th>Alarm</th>
							<th>Brama</th>
							<th>Ogród</th>
							<th>Roleta</th>
							<th>Woda</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="root/users/users" />
						
					</tbody>
				</table>
				<button type="submit">Zapisz</button>
				</form>
			</div>
		</div> <!-- /container -->

	</xsl:template>
	<xsl:template match="users">
		<tr>
			<input type="hidden" name="id_{position()}" value="{id_users}" />
			<td><xsl:value-of select="id_users"/></td>
			<td><xsl:value-of select="email"/></td>
			<td><input type="checkbox" name="{position()}_1" >
				<xsl:if test='contains(access_rights,"*1*")'>
					<xsl:attribute name="checked">true</xsl:attribute>
				</xsl:if> 
			</input>
			<input type="checkbox" name="{position()}_2" >
			<xsl:if test='contains(access_rights,"*2*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			</td>
			<td><input type="checkbox" name="{position()}_3" >
			<xsl:if test='contains(access_rights,"*3*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			<input type="checkbox" name="{position()}_4" >
			<xsl:if test='contains(access_rights,"*4*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			</td>
			<td><input type="checkbox" name="{position()}_5" >
			<xsl:if test='contains(access_rights,"*5*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			<input type="checkbox" name="{position()}_6" >
			<xsl:if test='contains(access_rights,"*6*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			</td>
			<td><input type="checkbox" name="{position()}_7" >
			<xsl:if test='contains(access_rights,"*7*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			<input type="checkbox" name="{position()}_8" >
			<xsl:if test='contains(access_rights,"*8*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			</td>
			<td><input type="checkbox" name="{position()}_9" >
			<xsl:if test='contains(access_rights,"*9*")'>
					<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			<input type="checkbox" name="{position()}_10" >
			<xsl:if test='contains(access_rights,"*10*")'>
				<xsl:attribute name="checked">true</xsl:attribute>
			</xsl:if> 
			</input>
			</td>
			<td><button class="btn btn-danger btn-mini" onClick="del({id_users})">X</button></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
