#include <Event.h>
#include <Timer.h>

Timer t;
bool received, started;
int state = 0;
int randomState = 0;
int pos = 0;
char dataToSend = "A0000000;000.0;000.0;000.0;000.0;000;000;000;000;000;000;000;00.0;0;00000;000;000;000.0;000.0;0000.0;0;0;000;0;00000;00000;0.000;00000;0;0;000;000;00.00;00000;00.0;00.00;00000;00.0;00000;00.00;00000;00.00;00000;00000;00000;000;000;000;000;000;000;000;000;000;000;000;000;0000.00;0000.00;000.00;0000.00;0000.00;0000.00;000;000;0;0;0;000;000;00000;00000;00000;00000;00000;00000;000;0000;000;0000;000;0000;000;0000;000;0000000000;0000000;0000000;0000000;000000000000000000000000000000000000000000000000000000000000";
char debugToSend = "X000;0000;000;0000;000;0000;000;0000;000;000;0000;0000;000;00000;00000;00000;00000;00000;000000";
char stateToSend = "C0000000";
char strReceived[20];

void SendData();
void SendDebug();
void SendState();

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  t.every(500, SendData, 0);
  t.every(2000, SendDebug, 0);
  t.every(10000, SendState, 0);
}

void loop() {
  // put your main code here, to run repeatedly:
  if((received==true) && (randomState<9)) {
    switch(strReceived[1])
    {
      case 'M':
        Serial.write("[AM]");
        randomState++;
        break;
      case 'N':
        Serial.write("[AN]");
        randomState++;
        break;
      case 'B':
        Serial.write("[AB]");
        randomState++;
        break; 
      case 'V':
        Serial.write("[AV]");
        randomState++;
        break;
      case 'X':
        Serial.write("[AX]");
        randomState++;
        break;
      case 'Z':
        Serial.write("[AZ]");
        randomState++;
        break;
      case 'J':
        Serial.write("[AJ]");
        randomState++;
        break;
      case 'H':
        Serial.write("[AH]");
        randomState++;
        break;
      case 'R':
        Serial.write("[AR11:22:33;01:01:2019]");
        randomState++;
        break;
    }
  }
  else if(randomState == 10) {
    randomState = 0;
  }
}

void SendData()
{
  Serial.write(dataToSend);
}

void SendDebug()
{
  Serial.write(debugToSend);
}

void SendState()
{
  Serial.write(stateToSend);
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
