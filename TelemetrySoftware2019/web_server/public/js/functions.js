//funzione per creare un grafico, serve l'indicatore e il puntatore di riferimento
function createChart(indicator, ctx){
   //dataset principale
   var myDatasets = [];
   for(i = 0; i<page[currentPage].graphs[indicator].dataset.length; i++){
       myDatasets.push({
                    type: 'scatter',
                    label: page[currentPage].graphs[indicator].dataset[i].name,
                    data: data[page[currentPage].graphs[indicator].dataset[i].datasetNumb],
                    borderColor: page[currentPage].graphs[indicator].dataset[i].color,
                    borderWidth: 2,
                    pointRadius: 2,
                    backgroundColor: 'transparent',
                });
   }
   for(i = 0; i<page[currentPage].graphs[indicator].dataset.length; i++){
       //dataset media
       if(page[currentPage].graphs[indicator].dataset[i].mean){
           myDatasets.push({
               type: 'scatter',
               label: page[currentPage].graphs[indicator].dataset[i].name + ' mean',
               data: mean[page[currentPage].graphs[indicator].dataset[i].datasetNumb],
               borderColor: page[currentPage].graphs[indicator].dataset[i].color,
               borderWidth: 0.5,
               pointRadius: 0,
               backgroundColor: 'transparent',
               borderDash: [10, 5],
           });
       }
   }
   //dataset limiti inferiori
   if(page[currentPage].graphs[indicator].min){
       myDatasets.push({
           type: 'scatter',
           label: 'Min',
           data: minBound[page[currentPage].graphs[indicator].dataset[0].datasetNumb],
           borderColor: $(":root").css("--colorSpecial"),
           borderWidth: 1,
           pointRadius: 0,
           backgroundColor: 'transparent',
           borderDash: [5, 5],
       });
   }
   //dataset limiti superiori
   if(page[currentPage].graphs[indicator].max){
       myDatasets.push({
           type: 'scatter',
           label: 'Max',
           data: maxBound[page[currentPage].graphs[indicator].dataset[0].datasetNumb],
           borderColor: $(":root").css("--colorSpecial"),
           borderWidth: 1,
           pointRadius: 0,
           backgroundColor: 'transparent',
           borderDash: [5, 5],
       });
   }
   var graphName = page[currentPage].name;
   if(page[currentPage].graphs[indicator].name) graphName = page[currentPage].graphs[indicator].name;
   var graphUnit = "";
   if(page[currentPage].graphs[indicator].unit) graphUnit = '(' + page[currentPage].graphs[indicator].unit + ')';
   //caratteristiche principali grafico
   var myChart = {
        type: 'scatter',
        data: { datasets: myDatasets },
        options: {
            title: {
                display: true,
                text: graphName + " " + graphUnit,
                fontSize: 16
            },
            scales: {
                xAxes: [{
                    type: 'time',
                    time: {
                        displayFormats: {
                            quarter: 'h:mm:ss.SSS a'
                        }
                    },
                    distribution: 'linear',
                    position: 'bottom',
                    bounds: 'ticks',
                    ticks: {
                        beginAtZero: false,
                        autoSkip: true,
                        stepSize: 1,
                        source: 'data'
                    }
                }],
                yAxes: [{
                    ticks: {
                        suggestedMin: page[currentPage].graphs[indicator].minInterval,
                        suggestedMax: page[currentPage].graphs[indicator].maxInterval,
                    }
                }]
            },
            responsive:false,
            cubicInterpolationMode: 'monotone',
            animation: {
                duration: 0, 
            },
            hover: {
                animationDuration: 0,
            },
            responsiveAnimationDuration: 0,
        }
    };
    return new Chart(ctx, myChart);
}

//creo opzione num dati nel grafico
// -- N.B. Grafici e numbData sono numerati da 0 al numero di grafici nella pagina,in ordine
function addOption(number){
   var ch = '<p>'
   ch += '<font>#Data </font>';
   ch += '<select id="#Data'+number.toString()+'" onchange="updateNumData('+number.toString()+')"></select>';
   ch += '</p>'
   return ch;
}

//iniziallizzo opzione num dati nel grafico
function initOption(myPage, graphNumber){
   var selectBox = document.getElementById('#Data'+graphNumber.toString());
   for(var i = 0, l = options.length; i < l; i++){
       selectBox.options.add( new Option(options[i]) );
   }
   var opts = selectBox.options;
   for (var opt, j = 0; opt = opts[j]; j++) {
       if (opt.value == page[myPage].graphs[graphNumber].numbData) {
           selectBox.selectedIndex = j;
           break;
        }
   }
}

//costruisco e passo alla tabella di indicatori
function changeToAllIndicator(){
   var ch = '<div id="lapGrid" class="grid-container-lap"></div><br>';
   ch += '<div id="grid" class="grid-container"></div>';
   document.getElementById("charts").innerHTML = ch;
   var ch = '<div class="grid-item"><h2>Lap time</h2></div><div id="lapTime" class="grid-item"><h2>'+lapTime+'</h2></div>';
   document.getElementById("lapGrid").innerHTML = ch;
   var ch = '<div class="grid-itemB"></div>';
   ch += '<div class="grid-item"><h2>'+'TS'+'</h2></div>';
   ch += '<div class="grid-item"><h2>'+'DATA'+'</h2></div>';
   ch += '<div class="grid-item"><h2>'+'MEAN'+'</h2></div>';
   for (i = 0; i < Object.keys(mapping).length; i++) {
        for (a = 0; a < page.length; a++){
            for (b = 0; b < page[a].graphs.length; b++){
                for (c = 0; c < page[a].graphs[b].dataset.length; c++){
                    if(page[a].graphs[b].dataset[c].datasetNumb == i){
                        var graphUnit = "";
                        if(page[a].graphs[b].unit) graphUnit = '(' + page[a].graphs[b].unit + ')';
                        ch += '<div class="grid-item" id="T'+ i.toString() +'"><h2>'+page[a].graphs[b].dataset[c].name+' '+graphUnit+'</h2></div>';
                        ch += '<div class="grid-item" id="TT'+ i.toString() +'"><h2>'+'..'+'</h2></div>';
                        ch += '<div class="grid-item" id="TD'+ i.toString() +'"><h2>'+'..'+'</h2></div>';
                        ch += '<div class="grid-item" id="TM'+ i.toString() +'"><h2>'+'&#10008;'+'</h2></div>';
                    }
                }
            }
        }
   }
   document.getElementById("grid").innerHTML = ch;

   for (i = 0; i < page.length; i++) {
       document.getElementById("B"+i.toString()).classList.remove('active');
   }
   document.getElementById("BAll").classList.add('active')
   currentPage = -1;

   for (i = 0; i < Object.keys(mapping).length; i++) {
        for (a = 0; a < page.length; a++){
            for (b = 0; b < page[a].graphs.length; b++){
                for (c = 0; c < page[a].graphs[b].dataset.length; c++){
                    if(page[a].graphs[b].dataset[c].datasetNumb == i){
                        if(data[i].length>0){
                            document.getElementById("TT"+i.toString()).innerHTML = "<h2>"+data[i][data[i].length-1].x._i.substring(11,23)+"</h2>";
                           document.getElementById("TD"+i.toString()).innerHTML = "<h2>"+data[i][data[i].length-1].y+"</h2>";
                           var minore = false; var maggiore = false;
                           if(page[a].graphs[b].min){ if(data[i][data[i].length-1].y<page[a].graphs[b].min){ minore = true } }
                           if(page[a].graphs[b].max){ if(data[i][data[i].length-1].y>page[a].graphs[b].max){ maggiore = true } }
                           if(minore || maggiore) $('#T'+i.toString()).attr('class', 'grid-itemErr');
                           else $('#T'+i.toString()).attr('class', 'grid-item');
                       }
                    }
                }
            }
        }
   }
}

//costruisco e passo alla scheda corrente 
function changeIndicator(newPage){
   currentPage = newPage;
   ch = '';
   for (var i = 0; i < page[newPage].graphs.length; i++){
       ch += '<canvas id="'+ i.toString() +'" width="500" height="500" class="canvasOk"></canvas>';
       ch += addOption(i);
   }
   document.getElementById("charts").innerHTML = ch;
   for (var i = 0; i < page[newPage].graphs.length; i++){
       initOption(newPage, i);
   }
   for (var i = 0; i < page[newPage].graphs.length; i++){
       var tempCtx = document.getElementById(i.toString()).getContext('2d');
       currentGraphs[i] = createChart(i, tempCtx);
   }
   for (i = 0; i < page.length; i++) {
       document.getElementById("B"+i.toString()).classList.remove('active');
   }
   document.getElementById("BAll").classList.remove('active');
   document.getElementById("B"+newPage.toString()).classList.add('active');

   for (var i = 1; i < page[newPage].graphs.length; i++){
       var minore = false; var maggiore = false;
       for (var j = 0; j < page[newPage].graphs[i].dataset.length; j++){
            var tempDatasetNumb = page[newPage].graphs[i].dataset[j].datasetNumb;
            if(data[tempDatasetNumb].length>0){
               if(page[newPage].graphs[i].min){ if(data[tempDatasetNumb][data[tempDatasetNumb].length-1].y<page[newPage].graphs[i].min){ minore = true } }
               if(page[newPage].graphs[i].max){ if(data[tempDatasetNumb][data[tempDatasetNumb].length-1].y>page[newPage].graphs[i].max){ maggiore = true } }
            }
       }
       if(minore || maggiore) $('#'+i.toString()).attr('class', 'canvasError');
       else $('#'+i.toString()).attr('class', 'canvasOk');
   }
}

//aggiorno tendina opzioni
function updateNumData(number){
   page[currentPage].graphs[number].numbData = document.getElementById("#Data"+number.toString()).options[document.getElementById("#Data"+number.toString()).selectedIndex].value;
}

//arrotonda numeri decimali
function arrotondaNumero(numero,nDecimali){
  return Math.round(numero*Math.pow(10,nDecimali))/Math.pow(10,nDecimali);
}