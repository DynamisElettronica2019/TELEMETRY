//cosa faccio quando ricevo un messaggio
ws.onmessage = function (evt) { 

   //CONTROLLARE CHI MANDA I DATI E CHE MANDI JSON (autenticazione della ground) https://www.npmjs.com/package/json-validation
   /*
   Formato richiesto:
   {"ch":"7","ts":"45","val":"345"}
   */

   //Parser json
   try { var json = JSON.parse(evt.data) } catch (e) { return; }
   if(json.hasOwnProperty('ch') && json.hasOwnProperty('ts') && json.hasOwnProperty('val')){
       if(json.ch.toString() in mapping){ //LT richiede ch=-1
           //Aggiornamento scheda
           var g = parseInt(mapping[json.ch]); //numero dataset mappato dal canale
           var v = parseFloat(json.val.replace(',', '.')); //valore interessato

           //calcolo media
           var newMean = 0;
           if(mean[g] != null){ 
               newMean = arrotondaNumero((((mean[g][0].y)*countData[g])+v)/(countData[g]+1),3);
               countData[g]++;
           }

           //aggiungi in coda dato
           //FORMATO: 2013-2-08 09:30:26.123 AM 
           var myDate = new Date();
           var timestamp = moment(myDate.getFullYear().toString()+'-'+(myDate.getMonth()+1).toString()+'-'+myDate.getDate().toString()+' '+json.ts.toString());

           data[g].push({ x: timestamp, y:v }); 
           if(minBound[g] != null){ minBound[g][1].x = timestamp; }
           if(maxBound[g] != null){ maxBound[g][1].x = timestamp; }
           if(mean[g] != null){ mean[g][1].x = timestamp; mean[g][1].y = newMean;}

           //rimuovi primi
           while(data[g].length>page[mappingDP[g]].graphs[mappingDG[g]].numbData){ data[g].shift(); }
           if(minBound[g] != null){ minBound[g][0].x = data[g][0].x; }
           if(maxBound[g] != null){ maxBound[g][0].x = data[g][0].x; }
           if(mean[g] != null){ mean[g][0].x = data[g][0].x; mean[g][0].y = newMean;}

           if(currentPage==-1){
               //aggiornamento tabella
               var minore = false; var maggiore = false;
               if(minBound[g] != null){ if(v<minBound[g][0].y){ minore = true } }
               if(maxBound[g] != null){ if(v>maxBound[g][0].y){ maggiore = true } }
               document.getElementById("TD"+g.toString()).innerHTML = '<h2>'+json.val+'</h2>';
               document.getElementById("TT"+g.toString()).innerHTML = '<h2>'+timestamp._i.substring(9,21)+'</h2>';
               if(mean[g] != null) document.getElementById("TM"+g.toString()).innerHTML = '<h2>'+newMean+'</h2>';
               if(minore || maggiore) $('#T'+g.toString()).attr('class', 'grid-itemErr');
               else $('#T'+g.toString()).attr('class', 'grid-item');
           }
           else {
               //aggiornamento grafico
               if(mappingDP[g]==currentPage){
                   var minore = false; var maggiore = false;
                   for (var j = 0; j < page[mappingDP[g]].graphs[mappingDG[g]].dataset.length; j++){
                        var tempDatasetNumb = page[currentPage].graphs[mappingDG[g]].dataset[j].datasetNumb;
                        if(data[tempDatasetNumb].length>0){
                           if(page[currentPage].graphs[mappingDG[g]].min){ if(data[tempDatasetNumb][data[tempDatasetNumb].length-1].y<page[currentPage].graphs[mappingDG[g]].min){ minore = true } }
                           if(page[currentPage].graphs[mappingDG[g]].max){ if(data[tempDatasetNumb][data[tempDatasetNumb].length-1].y>page[currentPage].graphs[mappingDG[g]].max){ maggiore = true } }
                        }
                   }
                   if(minore || maggiore) $('#'+mappingDG[g].toString()).attr('class', 'canvasError');
                   else $('#'+mappingDG[g].toString()).attr('class', 'canvasOk');
                   currentGraphs[mappingDG[g]].update();
               }
           }
       }
       else if (json.ch.toString()==ltChannel){
          //Aggiornamento lapTime
          lapTime = json.val.toString()
          if(currentPage==-1) document.getElementById('lapTime').innerHTML = '<h2>'+lapTime+'</h2>';
       }
   } 
}