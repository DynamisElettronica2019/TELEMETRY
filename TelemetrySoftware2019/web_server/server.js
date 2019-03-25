//ESEMPIO COMPLETO WS -> https://medium.com/@martin.sikora/node-js-websocket-simple-chat-tutorial-2def3a841b61

//require (installare librerie necessarie)
var WebSocketServer = require('websocket').server;
var http = require('http');
var path = require('path');
var fs = require('fs');

//porta d'ascolto
var port = 8080;

//sender (dove mando i dati relativi al numero di connessioni attive)
var sender = "127.0.0.1";

//estensioni supportate (content type)
extensions = {
	".html" : "text/html",
	".css" : "text/css",
	".js" : "application/javascript",
	".jpg" : "image/jpeg",
    ".png" : "image/png"
};
//cartelle
folders = {
	".html" : "",
	".css" : "css/",
	".js" : "js/",
	".jpg" : "img/",
    ".png" : "img/"
};

//settaggi vari
var localFolder = 'public/';
var mainPage = 'index.html';

//lista di utenti connessi. Quando uno si disconnette lo metto a 'void' per poi riempire il buco alla prima connessione
var clients = [ ];

//numero di client connessi
var numClients = 0;

//creo il server HTTP, quando viene chiamato risponde con test
var server = http.createServer(function(request, response) {
  var fileName = path.basename(request.url) || mainPage;
  var ext = path.extname(fileName);
    
  if(!extensions[ext]){
		response.writeHead(404, {'Content-Type': 'text/html'});
		response.end("&lt;html&gt;&lt;head&gt;&lt;/head&gt;&lt;body&gt;The requested file type is not supported&lt;/body&gt;&lt;/html&gt;");
   }
   else{
       var file = fs.readFileSync(localFolder+folders[ext]+fileName);
       response.writeHead(200, {'Content-Type': extensions[ext] });
       response.end(file,'binary');
   }
});
//Il server HTTP attende sulla porta 8080
server.listen(port, function() { });

//Creo il server WS
wsServer = new WebSocketServer({
  httpServer: server
});

//Configuro funzioni del server WS
wsServer.on('request', function(request) {
  //Creo una nuova 'connection' per ogni richiesta ricevuta
  var ip = request.httpRequest.socket._peername.address.split(":")[3];
  var connection = request.accept(null, request.origin);
  var index;
  var full = true;
  for(var i=0; i<clients.length && full; i++){
      if(clients[i]=='void'){
          index = i;
          clients[i] = { conn:connection, ip:ip }
          full = false;
      }
  }
  if(full) index = clients.push({ conn:connection, ip:ip }) - 1;
  numClients++;
  console.log('utente '+index+' connesso da ip '+clients[index].ip);
  for (var i=0; i < clients.length; i++) { 
    if(clients[i]!='void') if(clients[i].ip == sender) clients[i].conn.send(numClients);   
  }

  //Cosa faccio quando ricevo un messaggio
  connection.on('message', function(message) {
      if (message.type == 'utf8'){
        for (var i=0; i < clients.length; i++) { 
            if(i!=index && clients[i]!='void') clients[i].conn.send(message.utf8Data);
        }
        console.log('message received from client ' + index + ': ' + message.utf8Data);
      }
  });

  //Cosa faccio quando si disconnette un client
  connection.on('close', function(connection) {
    if(clients[index].ip == sender) senderPosition = -1;
    clients.splice(index, 1, 'void'); //rimuovo dalla lista
    numClients--;
    console.log('utente '+index+' disconnesso');
    for (var i=0; i < clients.length; i++) { 
        if(clients[i]!='void') if(clients[i].ip == sender) clients[i].conn.send(numClients);  
    }
  });
});

console.log('-- Server running on port '+port+' --');