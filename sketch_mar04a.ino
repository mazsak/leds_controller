#include <FastLED.h>

#define DATA_PIN 13
#define LED_TYPE WS2812
#define NUM_LEDS 96
#define LENGTH 10

CRGB leds[NUM_LEDS];
void setup() {
  Serial.begin(9600);
  FastLED.addLeds<LED_TYPE, DATA_PIN, GRB>(leds, NUM_LEDS);
}

void loop() {
  if (Serial.available()) {
    char buf[LENGTH];
    int len = Serial.readBytes(buf, LENGTH);
    String hexs = buf;
//    char c = Serial.read();
//    hexs += c;
//    hexs += Serial.read();
//    hexs += Serial.read();
//    hexs += Serial.read();
//    hexs += Serial.read();
//    hexs += Serial.read();

//    Serial.read();

    char* pch = strtok(hexs.c_str(), " ");
    long number = strtol(&pch[0], NULL, 16);
    pch = strtok(NULL, " ");
    int indexLed = atoi(&pch[0]);

    long r = number >> 16 & 0xFF;
    long g = number >> 8 & 0xFF;
    long b = number & 0xFF;

    leds[indexLed] = CRGB(r, g, b);


    FastLED.show();
    Serial.read();
  }
  Serial.flush();
}

//      Serial.print("HEX:");
//      Serial.println(pch);
//      Serial.print("Index lED:");
//      Serial.println(indexled, DEC);
//      Serial.print("Red:");
//      Serial.println(r, DEC);
//      Serial.print("Green:");
//      Serial.println(g, DEC);
//      Serial.print("Blue:");
//      Serial.println(b, DEC);
