var socket = io.connect('http://localhost:3000');

// Get username
let username = null;
do{
	username = prompt("Votre nom: ");
	if( username == null || username === '' ){
		alert("Veuillez saisir un nom d'utilisateur correct!");
	}
}while( username == null);

console.log(`${username}`);

// Connect the user for first time
socket.emit('new-user', username);

// Listen to received messages from the server
socket.on('user-connected', (name) => {
	var user = name != username ? `${name} a rejoint!` : 'Bienvenue!'
	Swal.fire('Connection établie', user, 'success');
});

// Send message
$('form').submit((e) => {
	e.preventDefault();
	const message = $('#chat_input').val();
	socket.emit('send-chat-message', message);
	$("#no-message").remove();
	$('#messages').append(`<tr>
						<td>
							<blockquote style="border-right: 5px solid #7cb342; border-left: none; padding-right: 1.5rem;">
								<h6 class='right-align'>${message}</h6>
							</blockquote>
						</td>
					</tr>`);
	$('#messages-container').scrollTop($('#messages-container')[0].scrollHeight);
	$('#chat_input').val('');
});

// on user message typing
$('#chat_input').on('keypress', (event) => {
	socket.emit('typing-message');
});

// on user stop message typing
$('#chat_input').on('focusout', (event) => {
	socket.emit('stop-typing-message');
});

// On user stopped typing
socket.on('user-disconnected', name => {
	Swal.fire('Un utilisateur est parti', `${name} à quitter le chat!`, 'info');
})

// On someone message typing
socket.on('user-is-typing', name => {
	$('#someone-typing').removeClass('hide');
	console.log(name)
	$("#someone-typing").html(`${name} is typing...`);
});

// On someone message typing
socket.on('user-stopped-typing', name => {
	$('#someone-typing').addClass('hide');
});

// On message broadcast from the server
socket.on('chat-message', (data) => {
	const message = data.message;
	const name = data.name;
	$("#no-message").remove();
	$('#messages').append(`<tr>
			<td>
				<blockquote>
					<h6 class='left-align'>${message}</h6>
				</blockquote>
			</td>
		</tr>`);
	$('#messages-container').scrollTop($('#messages-container')[0].scrollHeight);
});