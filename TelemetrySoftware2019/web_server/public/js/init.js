//init default mapping
if (defaultMapping != 0){
    mapping = {};
    for(i = 1; i<=defaultMapping; i++){
        mapping[i.toString()]=i.toString();
    }
}

//init variabili
var currentPage = -1; //riferimento alla schermata iniziale
var currentGraphs = [];
var lapTime = "..";

//creo una websocket connessa al mio server sulla porta su cui ascolta    
var ws = new WebSocket(myHost);

//Creo strutture dati sui vari dataset ordinati
var data = []; //array di dati dei grafici
var countData = new Array(); //array per il conteggio dei dati ricevuti sui canali
for (i = 0; i < Object.keys(mapping).length; i++) {
    var tempData = [];
    data.push(tempData);
    countData.push(0);
}
var mean = new Array(); //array per le medie dei canali
for (i = 0; i < Object.keys(mapping).length; i++) {
    for (a = 0; a < page.length; a++){
        for (b = 0; b < page[a].graphs.length; b++){
            for (c = 0; c < page[a].graphs[b].dataset.length; c++){
                if(page[a].graphs[b].dataset[c].datasetNumb == i){
                    if(page[a].graphs[b].dataset[c].mean){
                        mean.push([{x: new Date(), y: 0},{x: new Date(), y: 0}]);
                    }
                    else mean.push(null);
                }
            }
        }
    }
}
var minBound = []; //array di limiti inferiori
for (i = 0; i < Object.keys(mapping).length; i++) {
    for (a = 0; a < page.length; a++){
        for (b = 0; b < page[a].graphs.length; b++){
            for (c = 0; c < page[a].graphs[b].dataset.length; c++){
                if(page[a].graphs[b].dataset[c].datasetNumb == i){
                    if(page[a].graphs[b].min){
                        minBound.push([{x: new Date(), y: page[a].graphs[b].min},{x: new Date(), y: page[a].graphs[b].min}]);
                    }
                    else minBound.push(null);
                }
            }
        }
    }
}
var maxBound = []; //array di limiti inferiori
for (i = 0; i < Object.keys(mapping).length; i++) {
    for (a = 0; a < page.length; a++){
        for (b = 0; b < page[a].graphs.length; b++){
            for (c = 0; c < page[a].graphs[b].dataset.length; c++){
                if(page[a].graphs[b].dataset[c].datasetNumb == i){
                    if(page[a].graphs[b].max){
                        maxBound.push([{x: new Date(), y: page[a].graphs[b].max},{x: new Date(), y: page[a].graphs[b].max}]);
                    }
                    else maxBound.push(null);
                }
            }
        }
    }
}
//opzionalità di alcune opzioni
for (a = 0; a < page.length; a++){
    for (b = 0; b < page[a].graphs.length; b++){
        if(!page[a].graphs[b].numbData) page[a].graphs[b].numbData = defaultOption;
        if(!page[a].graphs[b].minInterval) page[a].graphs[b].minInterval = -1;
        if(!page[a].graphs[b].maxInterval) page[a].graphs[b].maxInterval = 1;
    }
}
//creo mapping DATASETNUMB-GRAPHINPAGE e DATASETNUMB-PAGE per essere più veloce nel controllo errore
var mappingDG = []; var mappingDP = [];
for (i = 0; i < Object.keys(mapping).length; i++) {
   for (a = 0; a < page.length; a++){
        for (b = 0; b < page[a].graphs.length; b++){
            for (c = 0; c < page[a].graphs[b].dataset.length; c++){
                if(page[a].graphs[b].dataset[c].datasetNumb == i){
                    mappingDG.push(b);
                    mappingDP.push(a);
                }
            }
        }
    }
}