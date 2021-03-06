<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="cl.tds.controlvales.servlets.IngresarPlanillaServlet" %>
<%@page import="cl.tds.controlvales.beans.Usuario"%>
<%@page import="cl.tds.controlvales.beans.Perfil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>Ingreso planilla</title>
<link rel="stylesheet" type="text/css" href="./css/ivory.css" />
</head>
<body>
<%
if( session.getAttribute("login") != null ){
	Usuario login = (Usuario ) session.getAttribute("login");
	if( login.getPerfil().equals(Perfil.administrador) ){
%>
<div class="row">
	<h1 class="text-center">Ingresar planilla</h1>
</div>
<div class="content">
	<div class="grid">
		<hr>
		<div class="row space-bot">
			<div class="c4 first"></div>
			<div class="c4">
				<form  class="hform" action="IngresarPlanillaServlet" method="post" enctype="multipart/form-data">
				<p class="note text-center">Por favor seleccione un archivo</p>
				<p class="text-center"><input type="file" name="file" style="width:350px"/></p> 
				<span>el nombre del archivo debe ser 'mes-a�o', para indicar el periodo de los vales a cargar</span>
				<br /> 
				<p class="text-center"><button type="submit">Seleccionar</button></p>
				</form>
			</div>
			<div class="c4 last"></div>
		</div>
		<hr>
		<div class="row">
			<p class="note text-center">
				<a href="index.jsp">Inicio</a>
			</p>
		</div>
	</div>
</div>
<%
	}
}
%>
</body>
</html>