<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="utf-8" indent="yes" />
	<xsl:template match="/">
	<xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
	<html>
		<head>

		<title>ZPI</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="description" content="" />
		<meta name="author" content="Michał Mikołajczak" />
		<!-- Le styles -->
		<link href="css/bootstrap.css" rel="stylesheet" />
		<link href="css/custom.css" rel="stylesheet" />
		
		<link href="css/bootstrap-responsive.css" rel="stylesheet" />
		<!-- &lt;!-- HTML5 shim, for IE6-8 support of HTML5 elements --&gt;
		&lt;!--[if lt IE 9]&gt;
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		&lt;![endif]--&gt; -->
	</head>
	<body>
		<xsl:call-template name="toolbar" />
		<xsl:call-template name="content" />
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.js"></script>
	</body>
	</html>
	</xsl:template>
	<xsl:template name="toolbar">
	<div class="navbar navbar-inverse navbar-fixed-to">
			<div class="navbar-inner">
				<div class="container">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					</a>
					<a class="brand" href="#">S.A.S.M</a>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li class="active"><a href="users.html"><i class="icon-home"></i>Użytkownicy</a></li>
							<li><a href="timetable.html"><i class="icon-home"></i>Harmonogramy</li>
<!-- 							<li><a href="admin_trans.html"><i class="icon-random"></i>Liczniki i transakcje</a></li>
							<li><a href="admin_msg.html"><i class="icon-envelope"></i>Sprawy lokatorów</a></li>
							<li><a href="admin_ogloszenia.html"><i class="icon-envelope"></i>Ogłoszenia</a></li>	 -->								
						</ul>
						<p class="navbar-text pull-right">
							Witaj <strong><xsl:value-of select="login" /></strong><small>(<i class="icon-eject"></i><a href="#" onClick="logout()" class="navbar-link"> Wyloguj</a></small>)
						</p>
					</div>
					<!--/.nav-collapse -->
				</div>
			</div>
		</div>
	</xsl:template>
	<xsl:template name="content"/>
</xsl:stylesheet>
