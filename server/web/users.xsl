<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<body>
			<header>

			</header>
			<html>
				<table>
					<thead>
						<th>
							<td>ID</td>
							<td>Imie i nazwisko</td>
							<td>email</td>
							<td></td>
						</th>

					</thead>
					<tbody>
						<xsl:apply-templates select="root/users/row"/>
					</tbody>
				</table>
				<form method="POST" action="/servtest/do">
					<input type="hidden" name="action" value="user.add" />
					<input type="hidden" name="name" value="dupa"/>
					<label>Imie</label>
					<input type="text" name="login" />
					<label>Email</label>
					<input type="text" name="email" />
					<button type="submit" >Dodaj</button>
				</form>


			</html>

		</body>
	</xsl:template>
	<xsl:template match="row">
		<tr><td><xsl:value-of select="id_user"/></td>
			<td><xsl:value-of select="login"/></td>
			<td><xsl:value-of select="email"/></td>
			<td><button onClick="delete({id_user})">X</button></td>
		</tr>
		
	</xsl:template>
</xsl:stylesheet>