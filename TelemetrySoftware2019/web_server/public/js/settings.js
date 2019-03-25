//************* SETTARE INDIRIZZO E PORTA DEL SERVER
myHost = "ws://127.0.0.1:8080";

//************* SETTARE MAPPING CHANNEL-DATASETNUMB (n.b. non ripetere valori tra CHANNEL, settare DATASETNUMB da 0 a N, settare defaultMapping a 0)
var mapping = { 
"1": "0", "2": "1", "3": "3", "4": "5", "6": "2", "7": "4", "10": "6", "11": "7", "12": "8", "13": "9"
};
var defaultMapping = 80; //0 -> disabilitato (conta quello sopra specificato), >0 -> numero di canali

//************* SETTARE CHANNEL DEL LAP TIMER
var ltChannel = -1;

/************** SETTARE I VARI CANALI SECONDO LO SCHEMA D'ESEMPIO (* = opzionale)

page [name, graphs[name*, unit*, numbData*, max*, min*, minInterval*, maxInterval*, dataset[name, mean, color, datasetNumb]]]

page.name: String, nome della scheda
graphs.name: String, nome del grafico
graphs.unit: String, unità di misura ordinata grafico
graphs.numbData: Int, numero di valori inizialmente visualizzati nel grafico
graphs.max: Number, soglia per errore massima
graphs.min: Number, soglia per errore minima
graphs.minInterval: Number, valore iniziale asse verticale grafico minimo
graphs.maxInterval: Number, valore iniziale asse verticale grafico massimo
dataset.name: String, nome del dataset
dataset.mean: Boolean, specificare se vuoi rappresentare la media o meno
dataset.color: Color, colore del dataset
dataset.datasetNumb: Int, numero del dataset con riferimento al mapping sopra specificato
*/

var page = [
   {
   name: "Temperature",
   graphs: [
            {
            name: "Temperature",
            minInterval: 0, maxInterval: 100, unit: "C",
            dataset: [
                      {
                      name: "T H20 SX IN", mean: false, color: $(":root").css("--colorA"), datasetNumb: 5
                      },
                      {
                      name: "T H20 SX OUT", mean: false, color: $(":root").css("--colorB"), datasetNumb: 6
                      },
                      {
                      name: "T H20 DX IN", mean: false, color: $(":root").css("--colorD"), datasetNumb: 7
                      },
                      {
                      name: "T H20 DX OUT", mean: false, color: $(":root").css("--colorE"), datasetNumb: 8
                      }
                     ]
            },
            {
            name: "Temperature Engine",
            minInterval: 0, maxInterval: 100, unit: "C",
            dataset: [
                      {
                      name: "T H20 ENGINE", mean: false, color: $(":root").css("--colorA"), datasetNumb: 11
                      }
                     ]
            },
            {
            name: "Temperature Oil",
            minInterval: 0, maxInterval: 100, unit: "C",
            dataset: [
                      {
                      name: "T OIL IN", mean: false, color: $(":root").css("--colorA"), datasetNumb: 9
                      },
                      {
                      name: "T OIL OUT", mean: false, color: $(":root").css("--colorB"), datasetNumb: 10
                      }
                     ]
            }
           ]
   },
   {
   name: "Pressure",
   graphs: [
            {
            minInterval: 0, maxInterval: 10000, unit: "mBar",
            dataset: [
                      {
                      name: "FUEL PRESSURE", mean: false, color: $(":root").css("--colorA"), datasetNumb: 24
                      },
                      {
                      name: "OIL PRESSURE", mean: false, color: $(":root").css("--colorB"), datasetNumb: 25
                      }
                     ]
            }
           ]
   },
   {
   name: "Dynamic",
   graphs: [
            {
            name: "Rpm",
            minInterval: 0, maxInterval: 15000, unit: "rpm",
            dataset: [
                      {
                      name: "RPM", mean: false, color: $(":root").css("--colorA"), datasetNumb: 14
                      }
                     ]
            }
           ]
   }
]

//************* ALTRI SETTAGGI
var options = [5,20,100,1000]; //opzioni disponibili per la selezione del numero di dati (n.b. deve comprendere quelli sopra specificati)
var defaultOption = 100; //opzione di default se non specificata