#include <Event.h>
#include <Timer.h>

#define LAP_INTERVAL 1000
#define INT_INTERVAL 2000
//Mode ACC or END
#define MODE "ACC"

Timer t;
char accInt[]= "[K;I;00;0:00:000]";
char accLap[] = "[K;E;00;0:00:000]";
char endInt[] = "[G;I;00;0:00:000]";
char endLap[] = "[G;E;00;0:00:000]";
int LapState = '0';
int IntState = '0';

void SendLap();
void SendInt();

void setup() {
  // Start all the timers for running periodically
  Serial.begin(115200);
  t.oscillate(13, 500, HIGH);
  t.every(LAP_INTERVAL, (void (*) (void*))SendLap, 0);
  t.every(INT_INTERVAL, (void (*) (void*))SendInt, 0);
}

void loop() {
  t.update(); //Update the timer
}

//Send Lap Message
void SendLap()
{
  if (MODE == "ACC") {
    Serial.write(accLap);
    if(LapState == '9') {
      LapState = '0';
    }
    accLap[6] = LapState;
    accLap[8] = LapState;
    accLap[11] = LapState;
    accLap[15] = LapState;
    LapState++;
  }
  else if (MODE == "END") {
    Serial.write(endLap);
    if(LapState == '9') {
      LapState = '0';
    }
    endLap[6] = LapState;
    endLap[8] = LapState;
    endLap[11] = LapState;
    endLap[15] = LapState;
    LapState++;
  }
}

//Send Interval Message
void SendInt()
{
  if (MODE == "ACC") {
    Serial.write(accInt);
    if(IntState == '9'+1) {
      IntState = '0';
    }
    accInt[6] = IntState;
    accInt[8] = IntState;
    accInt[11] = IntState;
    accInt[15] = IntState;
    IntState++;
  }
  else if (MODE == "END") {
    Serial.write(endInt);
    if(IntState == '9'+1) {
      IntState = '0';
    }
    endInt[6] = IntState;
    endInt[8] = IntState;
    endInt[11] = IntState;
    endInt[15] = IntState;
    IntState++;
  }
}


