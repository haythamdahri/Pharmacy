<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ajout : Stock</title>
	<link rel="icon" href="static/images/logo.png" />
	<link rel="stylesheet" type="text/css"  href="static/css/materialize.css"/>
	<link rel="stylesheet" type="text/css"  href="static/css/style.css" />
	<link rel="stylesheet" type="text/css"  href="static/css/all.min.css" />
	<link rel="stylesheet" type="text/css" href="static/css/sweetalert2.min.css" />
	<link rel="stylesheet" type="text/css" href="static/css/material_icons.css" />

</head>
<body>
<%@ include file="parts/navbar.jsp" %>

	
	<div class="container">
	
	
	<%@ include file="parts/product_add_form.jsp" %>
	
	
	</div>
	
	
	
	
	
	<script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="static/js/materialize.min.js"></script>
	<script type="text/javascript" src="static/js/sweetalert2.all.min.js"></script>
	<script type="text/javascript" src="static/js/custom.js"></script>
	
</body>
</html>