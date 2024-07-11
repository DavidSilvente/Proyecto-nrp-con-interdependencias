<!-- IMPORTANTE PARA UTF-8 -->
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<!-- saved from url=(0050)https://getbootstrap.com/docs/4.0/examples/album/# -->
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon"
	href="https://getbootstrap.com/docs/4.0/assets/img/favicons/favicon.ico">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">

<title>Seleccion de proyecto</title>

<!-- Bootstrap core CSS -->
<link href="./css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">

<!-- Custom styles for this template -->
<link href="./css/album.css" rel="stylesheet">
<style type="text/css">
@font-face {
	font-family: Roboto;
	src:
		url("chrome-extension://mcgbeeipkmelnpldkobichboakdfaeon/css/Roboto-Regular.ttf");
}
</style>
<script
	src="chrome-extension://mooikfkahbdckldjjndioackbalphokd/assets/prompt.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com/"
	crossorigin="true">
<link rel="preconnect" href="https://fonts.gstatic.com/">
<link rel="stylesheet" href="./css/css2">
</head>

<body>

	<header>
		<div class="navbar navbar-dark bg-dark box-shadow">
			<div class="container d-flex justify-content-between"
				style="text-align: center">
				<div class="navbar-brand d-flex align-items-center"
					style="margin: 0px auto">
					<strong>PROYECTO: Problema de la siguiente versión</strong>
				</div>
				<a href="./ServletUsuario?action=cerrar_sesion"
					style="justify-content: center"> <i
					class="fa-solid fa-right-from-bracket" style="color: white;"></i>
				</a>
			</div>
		</div>
	</header>

	<main role="main" style="min-height: 67vh;">

		<section class="text-center" style="margin-top: 30px">
			<div class="container-xl">
				<div class="table" style="max-width: 65%; margin-left: 17.5%">
					<div class="table-wrapper">
						<div class="table-title">
							<div class="row">
								<div class="col-sm-12"
									style="display: inline-flex; justify-content: center">
									<h2 style="margin-right: 20px">
										Proyectos
										<c:if test="${not admin}"> asignados</c:if>
									</h2>
									<c:if test="${admin}">
										<a href="./ServletProyecto?action=mostrar_crear_proyecto"
											class="bi bi-plus-circle" data-toggle="modal"
											style="align-items: center; display: flex; justify-content: center"></a>
									</c:if>
								</div>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered">
							<thead>
								<tr>
									<th>ID<i ></i></th>
									<th>Nombre Proyecto<i ></i></th>
									<c:if test="${admin}">
										<th>Opciones</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="proyecto" items="${listaProyectos}">
									<tr>
										<td><c:out value="${proyecto.id}" /></td>
										<td><a
											href="ServletProyecto?action=mostrar_inicio&id=<c:out value="${proyecto.id}"/>"><c:out
													value="${proyecto.nombre}" /></a></td>
										<c:if test="${admin}">
											<td><a
												href="ServletProyecto?action=eliminar_proyecto&id=<c:out value="${proyecto.id}"/>&nombre=<c:out value="${proyecto.nombre}"/>"
												onclick="return confirm('¿Estás seguro de que quieres eliminar el proyecto <c:out value="${proyecto.nombre}" />?')"
												class="bi bi-x-circle" title="Delete" data-toggle="tooltip"></a>
												<a
												href="ServletProyecto?action=mostrar_editar_proyecto&id=<c:out value="${proyecto.id}"/>&nombre=<c:out value="${proyecto.nombre}"/>"
												class="bi bi-pencil-square" title="Edit"
												data-toggle="tooltip"></a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<c:if test="${admin}">
					<div class="table" style="max-width: 65%; margin-left: 17.5%">
						<div class="table-wrapper">
							<div class="table-title">
								<div class="row">
									<div class="col-sm-12"
										style="display: inline-flex; justify-content: center">
										<h2 style="margin-right: 20px">Usuarios</h2>
										<a href="crearUsuario.jsp" class="bi bi-plus-circle"
											data-toggle="modal"
											style="align-items: center; display: flex; justify-content: center"></a>
									</div>
								</div>
							</div>
							<table class="table table-striped table-hover table-bordered">
								<thead>
									<tr>
										<th>ID<i ></i></th>
										<th>Login Usuario<i ></i></th>
										<th>Opciones<i ></i></th>

									</tr>
								</thead>
								<tbody>
									<c:forEach var="usuario" items="${listaUsuarios}">
										<tr>
											<td><c:out value="${usuario.id}" /></td>
											<td><c:out value="${usuario.login}" /></td>
											<td><a
												href="ServletUsuario?action=eliminar_usuario&id=<c:out value="${usuario.id}"/>&login=<c:out value="${usuario.login}"/>"
												onclick="return confirm('¿Estás seguro de que quieres eliminar el usuario <c:out value="${usuario.login}" />?')"
												class="bi bi-x-circle" title="Delete" data-toggle="tooltip"></a>
												<a
												href="ServletUsuario?action=mostrar_editar_usuario&id=<c:out value="${usuario.id}"/>&login=<c:out value="${usuario.login}"/>"
												class="bi bi-pencil-square" title="Edit"
												data-toggle="tooltip"></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
			</div>
		</section>
	</main>

	<footer class="text-muted"
		style="background: black; position: relative; bottom: 0; width: 100%">
		<div class="container">
			<p style="text-align: center; color: white">David
				Silvente</p>
			<p style="text-align: center; color: white"">Universidad de
				Almería</p>
		</div>
	</footer>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./js/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')
	</script>
	<script src="./js/popper.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/holder.min.js"></script>


	<svg xmlns="http://www.w3.org/2000/svg" width="348" height="225"
		viewBox="0 0 348 225" preserveAspectRatio="none"
		style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;">
		<defs>
		<style type="text/css"></style></defs>
		<text x="0" y="17"
			style="font-weight:bold;font-size:17pt;font-family:Arial, Helvetica, Open Sans, sans-serif">Thumbnail</text></svg>
</body>
</html>