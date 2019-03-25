//CREAZIONE SCHERMATA INIZIALE
//Creo Bar
ch = '<a id="BAll" href="#home" class="active" onclick="changeToAllIndicator()">All</a>';
for (i = 0; i < page.length; i++) {
    ch += '<a id="B'+ i.toString() +'" href="#home" onclick="changeIndicator('+i.toString()+')">'+page[i].name+'</a>';
}
document.getElementById("myTopnav").innerHTML = ch;
//Prima finestra da visualizzare
changeToAllIndicator();