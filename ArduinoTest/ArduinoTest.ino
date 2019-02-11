#include <Event.h>
#include <Timer.h>

#define STATE_LEN 15
#define DEBUG_LEN 96
#define DATA_LEN 512
#define DATA_INTERVAL 1000
#define DEBUG_INTERVAL 2000
#define STATE_INTERVAL 5000

Timer t;
bool received, started;
int state = 2;
char dataCount = '1';
char debugCount = '1';
int secondDebug = 0;
int randomState = 0;
int pos = 0;
char dataToSend[] = "[A;0000000;000.0;000.0;000.0;000.0;000;000;000;000;000;000;000;00.0;0;00000;000;000;000.0;000.0;0000.0;0;0;000;0;00000;00000;0.000;00000;0;0;000;000;00.00;00000;00.0;00.00;00000;00.0;00000;00.00;00000;00.00;00000;00000;00000;000;000;000;000;000;000;000;000;000;000;000;000;0000.00;0000.00;000.00;0000.00;0000.00;0000.00;000;000;0;0;0;000;000;00000;00000;00000;00000;00000;00000;000;0000;000;0000;000;0000;000;0000;000;0000000000;0000000;0000000;0000000;000000000000000000000000000000000000000000000000000000000000]";
char debugToSend[] = "[X;000;0000;000;0000;000;0000;000;0000;000;000;0000;0000;000;00000;00000;00000;00000;00000;000000]";
char stateToSend[] = "[C;0;0;0;0;0;0;0]";
char strReceived[20];

void SendData();
void SendDebug();
void SendState();

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  t.oscillate(13, 500, HIGH);
  t.every(DATA_INTERVAL, SendData, 0);
  t.every(DEBUG_INTERVAL, SendDebug, 0);
  t.every(STATE_INTERVAL, SendState, 0);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(received==true) {
    randomState++;
    if(randomState<10) {
      switch(strReceived[1])
      {
        case 'M':
          Serial.write("[AM]");
          received=false;
          break;
        case 'N':
          Serial.write("[AN]");
          received=false;
          break;
        case 'B':
          Serial.write("[AB]");
          received=false;
          break; 
        case 'V':
          Serial.write("[AV]");
          received=false;
          break;
        case 'X':
          Serial.write("[AX]");
          received=false;
          break;
        case 'Z':
          Serial.write("[AZ]");
          received=false;
          break;
        case 'J':
          Serial.write("[AJ]");
          received=false;
          break;
        case 'H':
          Serial.write("[AH]");
          received=false;
          break;
        case 'R':
          Serial.write("[AR11:22:33;01:01:2019]");
          received=false;
          break;
      }
    }
    else if(randomState == 10) {
      received=false;
      randomState = 0;
    } 
  }
  t.update();
}

void SendData()
{
  if(dataCount == ('9'+1)) {
    dataCount = '0';
  }
  else {
    Serial.write(dataToSend);
    dataToSend[2] = dataCount;
    dataToSend[10] = dataCount;
    dataToSend[16] = dataCount;
    dataToSend[22] = dataCount;
    dataToSend[28] = dataCount;
    dataToSend[34] = dataCount;
    dataToSend[42] = dataCount;
    dataToSend[46] = dataCount;
    dataToSend[50] = dataCount;
    dataToSend[54] = dataCount;
    dataToSend[58] = dataCount;
    dataToSend[62] = dataCount;
    dataToSend[67] = dataCount;
    dataToSend[69] = dataCount;
    dataToSend[75] = dataCount;
    dataToSend[79] = dataCount;
    dataToSend[83] = dataCount;
    dataToSend[89] = dataCount;
    dataToSend[95] = dataCount;
    dataToSend[102] = dataCount;
    dataToSend[104] = dataCount;
    dataToSend[106] = dataCount;
    dataToSend[110] = dataCount;
    dataToSend[112] = dataCount;
    dataToSend[118] = dataCount;
    dataToSend[124] = dataCount;
    dataToSend[130] = dataCount;
    dataToSend[136] = dataCount;
    dataToSend[138] = dataCount;
    dataToSend[140] = dataCount;
    dataToSend[144] = dataCount;
    dataToSend[148] = dataCount;
    dataToSend[154] = dataCount;
    dataToSend[160] = dataCount;
    dataToSend[165] = dataCount;
    dataToSend[171] = dataCount;
    dataToSend[177] = dataCount;
    dataToSend[182] = dataCount;
    dataToSend[188] = dataCount;
    dataToSend[194] = dataCount;
    dataToSend[200] = dataCount;
    dataToSend[206] = dataCount;
    dataToSend[212] = dataCount;
    dataToSend[218] = dataCount;
    dataToSend[224] = dataCount;
    dataToSend[232] = dataCount;
    dataToSend[236] = dataCount;
    dataToSend[240] = dataCount;
    dataToSend[244] = dataCount;
    dataToSend[248] = dataCount;
    dataToSend[252] = dataCount;
    dataToSend[256] = dataCount;
    dataToSend[260] = dataCount;
    dataToSend[264] = dataCount;
    dataToSend[268] = dataCount;
    dataToSend[272] = dataCount;
    dataToSend[280] = dataCount;
    dataToSend[288] = dataCount;
    dataToSend[295] = dataCount;
    dataToSend[303] = dataCount;
    dataToSend[311] = dataCount;
    dataToSend[319] = dataCount;
    dataToSend[323] = dataCount;
    dataToSend[327] = dataCount;
    dataToSend[329] = dataCount;
    dataToSend[331] = dataCount;
    dataToSend[333] = dataCount;
    dataToSend[337] = dataCount;
    dataToSend[341] = dataCount;
    dataToSend[347] = dataCount;
    dataToSend[353] = dataCount;
    dataToSend[359] = dataCount;
    dataToSend[365] = dataCount;
    dataToSend[371] = dataCount;
    dataToSend[377] = dataCount;
    dataToSend[381] = dataCount;
    dataToSend[386] = dataCount;
    dataToSend[390] = dataCount;
    dataToSend[395] = dataCount;
    dataToSend[399] = dataCount;
    dataToSend[404] = dataCount;
    dataToSend[408] = dataCount;
    dataToSend[413] = dataCount;
    dataToSend[417] = dataCount;
    dataToSend[428] = dataCount;
    dataToSend[436] = dataCount;
    dataToSend[444] = dataCount;
    dataToSend[452] = dataCount;
    dataCount++;
  }
}

void SendDebug()
{
  /*
  if((debugCount == '9') && (secondDebug == '0')) {
    secondDebug = 1;
    debugCount = '0';
    Serial.write("pollo");
  }
  else if ((debugCount == '9') && (secondDebug == 1)) {
    debugToSend[4-secondDebug] = '0';
    debugToSend[9-secondDebug] = '0';
    debugToSend[13-secondDebug] = '0';
    debugToSend[18-secondDebug] = '0';
    debugToSend[22-secondDebug] = '0';
    debugToSend[27-secondDebug] = '0';
    debugToSend[31-secondDebug] = '0';
    debugToSend[36-secondDebug] = '0';
    debugToSend[40-secondDebug] = '0';
    debugToSend[44-secondDebug] = '0';
    debugToSend[49-secondDebug] = '0';
    debugToSend[54-secondDebug] = '0';
    debugToSend[58-secondDebug] = '0';
    debugToSend[64-secondDebug] = '0';
    debugToSend[70-secondDebug] = '0';
    debugToSend[76-secondDebug] = '0';
    debugToSend[82-secondDebug] = '0';
    debugToSend[88-secondDebug] = '0';
    debugToSend[95-secondDebug] = '0';
    secondDebug = 0;
    debugCount = '1';
  }
  */
  if(debugCount == ('9'+1)) {
    debugCount = '0';
  }
  else {
    Serial.write(debugToSend);
    debugToSend[4-secondDebug] = debugCount;
    debugToSend[9-secondDebug] = debugCount;
    debugToSend[13-secondDebug] = debugCount;
    debugToSend[18-secondDebug] = debugCount;
    debugToSend[22-secondDebug] = debugCount;
    debugToSend[27-secondDebug] = debugCount;
    debugToSend[31-secondDebug] = debugCount;
    debugToSend[36-secondDebug] = debugCount;
    debugToSend[40-secondDebug] = debugCount;
    debugToSend[44-secondDebug] = debugCount;
    debugToSend[49-secondDebug] = debugCount;
    debugToSend[54-secondDebug] = debugCount;
    debugToSend[58-secondDebug] = debugCount;
    debugToSend[64-secondDebug] = debugCount;
    debugToSend[70-secondDebug] = debugCount;
    debugToSend[76-secondDebug] = debugCount;
    debugToSend[82-secondDebug] = debugCount;
    debugToSend[88-secondDebug] = debugCount;
    debugToSend[95-secondDebug] = debugCount;
    debugCount++;
  }
}

void SendState()
{
  Serial.write(stateToSend);
  if (stateToSend[state] == '0') {
    stateToSend[state] = '1';
  }
  else {
    stateToSend[state] = '0';
  }
  if (state == (STATE_LEN - 1)) {
    state = 2;
  }
  else {
    state = state+2;
  }
}

void serialEvent() {
  while (Serial.available()) {
    char recChar = (char)Serial.read();
    if (recChar == '[') {
      started = true;
      received = false;
      pos = 0;
    }
    else if (recChar == ']') {
      received = true;
    }
    else {
      strReceived[pos] = recChar;
      pos++;
    }
  }
}
