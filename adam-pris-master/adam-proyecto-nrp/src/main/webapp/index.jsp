
<!-- IMPORTANTE PARA UTF-8 -->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

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

<title>Login</title>

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
	<main role="main" style="min-height: 67vh;">

		<section class="text-center" style="margin-top: 120px">
			<div class="container" style="width: 100%">
				<div class="abs-center">
					<h1>PROYECTO: Problema de la siguiente versión</h1>
					<%--<h1 style = "margin-bottom: 1em"></h1> --%>
					<div class="panel panel-login"
						style="max-width: 40%; margin-right: 30%; margin-left: 30%; margin-top: 50px">
						<div class="abs-center">
							<%
							String error = (String) request.getAttribute("error");
							if (error != null) {
							%>
							<li style="color: red; margin-bottom: 25px;"><%=error%></li>
							<%
							}
							%>
							<div class="col-lg-12">
								<form id="login-form"
									action="ServletUsuario?action=iniciar_sesion" method="post"
									role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="username" id="username" tabindex="1"
											class="form-control" placeholder="Usuario" value=""
											style="border-radius: 2rem">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password"
											tabindex="2" class="form-control" placeholder="Contraseña"
											style="border-radius: 2rem">
									</div>
									<%--<div class="form-group text-center">
										<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
										<label for="remember"> Recordarme</label>
									</div>--%>
									<div class="form-group">
										<div class="abs-center">
											<input type="submit" name="login-submit" id="login-submit"
												tabindex="4" class="btn btn-dark" value="Iniciar sesión"
												style="border-radius: 2rem">
										</div>
									</div>
							</div>
						</div>
						</form>
						<form id="register-form"
							action="http://phpoll.com/register/process" method="post"
							role="form" style="display: none;">
							<div class="form-group">
								<input type="text" name="username" id="username" tabindex="1"
									class="form-control" placeholder="Usuario" value="">
							</div>
							<div class="form-group">
								<input type="email" name="email" id="email" tabindex="1"
									class="form-control" placeholder="Correo electronico" value="">
							</div>
							<div class="form-group">
								<input type="password" name="password" id="password"
									tabindex="2" class="form-control" placeholder="Contraseña">
							</div>
							<div class="form-group">
								<input type="password" name="confirm-password"
									id="confirm-password" tabindex="2" class="form-control"
									placeholder="Confirmar contraseña">
							</div>
							<div class="form-group">
								<div class="row">
									<div class="col-sm-6 col-sm-offset-3">
										<input type="submit" name="register-submit"
											id="register-submit" tabindex="4"
											class="form-control btn btn-register" value="Crear cuenta">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>

	</main>

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