// Import socket package
const app = require('express')();
const server = require('http').createServer(app);
const io = require('socket.io')(server)

// Create an array of future users
const users = {}

// Handle different event which will be emitted by users
io.on('connection', socket => {
  socket.on('new-user', name => {
    users[socket.id] = name
    socket.broadcast.emit('user-connected', name)
  })
  socket.on('send-chat-message', message => {
    console.log(`New message from ${users[socket.id]}: ${message}`);
    socket.broadcast.emit('chat-message', { message: message, name: users[socket.id] })
  })
  socket.on('typing-message', () => {
    console.log(`${users[socket.id]} is typing`);
    socket.broadcast.emit('user-is-typing', users[socket.id]);
  })
  socket.on('stop-typing-message', () => {
    console.log(`${users[socket.id]} stopped typing`);
    socket.broadcast.emit('user-stopped-typing', users[socket.id]);
  })
  socket.on('disconnect', () => {
    socket.broadcast.emit('user-disconnected', users[socket.id]);
    delete users[socket.id]
  })
});

server.listen(3000, () => {
  console.log('Server listening...');
});