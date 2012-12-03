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
	function harm_set(){
	
		$.ajax({
  			url: "/do",
  			method: 'POST',
  			data: $('form[name="harm"]').serialize(),
  			success: function(result){
				$('div#result').html(result);  
				alert(result);
				return false;			
  			}
			
			});
	}
	</script>
	
	
		<div class="container">
				<h4>Harmonogramy</h4>
				<form class="well span4" name="harm" method="POST" action="/do">
					<input type="hidden" name="action" value="harmonogram.set"/>
					<label>Dni</label>
					<input type="text" name="dni" />
					<label>Godzina start</label>
					<input type="text" name="g_start" />
					<label>Godzina stop</label>
					<input type="text" name="g_stop" />
					<label>Id moduł</label>
					<input type="text" name="m_id" />
					<label>Wartość start</label>
					<input type="text" name="w_start" />
					<label>Wartość stop</label>
					<input type="text" name="w_stop" />
					<input type="hidden" name="active" value="1" />
					<button onClick="harm_set()" >SEND</button>
				</form>
				<div id="result" ></div>
		</div> <!-- /container -->

	</xsl:template>
	
</xsl:stylesheet>