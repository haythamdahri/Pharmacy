<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acceuil : Pharmacy HAYTHAM</title>
<link rel="icon" href="static/images/logo.png" />
<link rel="stylesheet" type="text/css" href="static/css/materialize.css" />
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<link rel="stylesheet" type="text/css" href="static/css/all.min.css" />
<link rel="stylesheet" type="text/css"
	href="static/css/material_icons.css" />

</head>
<body>


	<main> <%@ include file="parts/navbar.jsp"%>


	<div class="container">


		<h4 class="center-align">Messagerie Instantanée</h4>
		<ul class="collection" id="messages-container"
			style="max-height: 400px; overflow-y: scroll;">

			<table class="striped highlight centered">
				<tbody id="messages">

					<!-- Messages go here -->

					<tr>
						<td id="no-message">
							<div class="card-panel lime lighten-1 center-align flow-text">
								<span class="blue-text text-darken-2">
									<i class="material-icons left">info</i> Aucun message!
								</span>
							</div>
						</td>
					</tr>

				</tbody>
			</table>

		</ul>


		<!-- Chat message -->
		<form id="form" id="chat_form">
			<div class="row">
				<div class="input-field col s12">
					<i class="material-icons prefix">textsms</i> <input id="chat_input"
						type="text" name="code" /> <label for="chat_input">Message</label>
					<span class="helper-text hide" id="someone-typing">SOMEONE
						IS TYPING...</span>
				</div>
			</div>
		</form>

	</div>


	</main>

	<%@ include file="parts/footer.jsp"%>


	<script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="static/js/materialize.min.js"></script>
	<script type="text/javascript" src="static/js/sweetalert2.all.min.js"></script>
	<script defer type="text/javascript" src="static/js/socket.io.slim.js"></script>
	<script defer type="text/javascript" src="static/js/script.js"></script>
	<script type="text/javascript" src="static/js/custom.js"></script>



</body>
</html>