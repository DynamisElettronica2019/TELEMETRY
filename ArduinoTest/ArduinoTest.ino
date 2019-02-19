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
int state = 3;
char dataCount = '1';
char debugCount = '1';
int secondDebug = 0;
int randomState = 0;
int pos = 0;
char dataToSend[] = "[C;0000000;000.0;000.0;000.0;000.0;000;000;000;000;000;000;000;00.0;0;00000;000;000;000.0;000.0;0000.0;0;0;000;0;00000;00000;0.000;00000;0;0;000;000;00.00;00000;00.0;00.00;00000;00.0;00000;00.00;00000;00.00;00000;00000;00000;000;000;000;000;000;000;000;000;000;000;000;000;0000.00;0000.00;000.00;0000.00;0000.00;0000.00;000;000;0;0;0;000;000;00000;00000;00000;00000;00000;00000;000;0000;000;0000;000;0000;000;0000;000;0000000000;0000000;0000000;0000000;000000000000000000000000000000000000000000000000000000000000]";
char debugToSend[] = "[D;000;0000;000;0000;000;0000;000;0000;000;000;0000;0000;000;00000;00000;00000;00000;00000;000000]";
char stateToSend[] = "[S;0;0;0;0;0;0;0]";
char strReceived[20];

void SendData();
void SendDebug();
void SendState();

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  t.oscillate(13, 500, HIGH);
  //t.every(DATA_INTERVAL, (void (*) (void*))SendData, 0);
  //t.every(DEBUG_INTERVAL, (void (*) (void*))SendDebug, 0);
  //t.every(STATE_INTERVAL, (void (*) (void*))SendState, 0);
}

void loop() {
  // put your main code here, to run repeatedly:
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
    dataToSend[3] = dataCount;
    dataToSend[11] = dataCount;
    dataToSend[17] = dataCount;
    dataToSend[23] = dataCount;
    dataToSend[29] = dataCount;
    dataToSend[35] = dataCount;
    dataToSend[43] = dataCount;
    dataToSend[47] = dataCount;
    dataToSend[51] = dataCount;
    dataToSend[55] = dataCount;
    dataToSend[59] = dataCount;
    dataToSend[53] = dataCount;
    dataToSend[68] = dataCount;
    dataToSend[70] = dataCount;
    dataToSend[76] = dataCount;
    dataToSend[80] = dataCount;
    dataToSend[84] = dataCount;
    dataToSend[90] = dataCount;
    dataToSend[96] = dataCount;
    dataToSend[103] = dataCount;
    dataToSend[105] = dataCount;
    dataToSend[107] = dataCount;
    dataToSend[111] = dataCount;
    dataToSend[113] = dataCount;
    dataToSend[119] = dataCount;
    dataToSend[125] = dataCount;
    dataToSend[131] = dataCount;
    dataToSend[137] = dataCount;
    dataToSend[139] = dataCount;
    dataToSend[141] = dataCount;
    dataToSend[145] = dataCount;
    dataToSend[149] = dataCount;
    dataToSend[155] = dataCount;
    dataToSend[161] = dataCount;
    dataToSend[166] = dataCount;
    dataToSend[172] = dataCount;
    dataToSend[178] = dataCount;
    dataToSend[183] = dataCount;
    dataToSend[189] = dataCount;
    dataToSend[195] = dataCount;
    dataToSend[201] = dataCount;
    dataToSend[207] = dataCount;
    dataToSend[213] = dataCount;
    dataToSend[219] = dataCount;
    dataToSend[225] = dataCount;
    dataToSend[233] = dataCount;
    dataToSend[237] = dataCount;
    dataToSend[241] = dataCount;
    dataToSend[245] = dataCount;
    dataToSend[249] = dataCount;
    dataToSend[253] = dataCount;
    dataToSend[257] = dataCount;
    dataToSend[261] = dataCount;
    dataToSend[265] = dataCount;
    dataToSend[269] = dataCount;
    dataToSend[273] = dataCount;
    dataToSend[281] = dataCount;
    dataToSend[289] = dataCount;
    dataToSend[296] = dataCount;
    dataToSend[304] = dataCount;
    dataToSend[312] = dataCount;
    dataToSend[320] = dataCount;
    dataToSend[324] = dataCount;
    dataToSend[328] = dataCount;
    dataToSend[330] = dataCount;
    dataToSend[332] = dataCount;
    dataToSend[334] = dataCount;
    dataToSend[338] = dataCount;
    dataToSend[342] = dataCount;
    dataToSend[348] = dataCount;
    dataToSend[354] = dataCount;
    dataToSend[360] = dataCount;
    dataToSend[366] = dataCount;
    dataToSend[372] = dataCount;
    dataToSend[378] = dataCount;
    dataToSend[382] = dataCount;
    dataToSend[387] = dataCount;
    dataToSend[391] = dataCount;
    dataToSend[396] = dataCount;
    dataToSend[400] = dataCount;
    dataToSend[405] = dataCount;
    dataToSend[409] = dataCount;
    dataToSend[414] = dataCount;
    dataToSend[418] = dataCount;
    dataToSend[429] = dataCount;
    dataToSend[437] = dataCount;
    dataToSend[445] = dataCount;
    dataToSend[453] = dataCount;
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
  if (state == (STATE_LEN)) {
    state = 3;
  }
  else {
    state = state+2;
  }
}
/*
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
*/
