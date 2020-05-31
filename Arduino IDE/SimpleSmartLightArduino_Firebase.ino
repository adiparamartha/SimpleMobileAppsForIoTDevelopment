/*Simple Arduino Smart Light, LDR Sensor and Relay Module + Firebase Database
 * Made Adi Paramartha Putra,
 * STMIK Primakara
 * April 2020
 */
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define WIFI_SSID "YOUR_SSID_NAME" 
#define WIFI_PASSWORD "YOUR_SSID_PASSWORD" 
#define FIREBASE_HOST "YOUR_FIREBASE_HOST" 
#define FIREBASE_AUTH "YOUR_FIREBASE_AUTH" 


//Variable Declaration
int relayPin = D0;  // Pin for Relay
boolean ledStatus;

//Setup (Running Once)
void setup() {
  pinMode(relayPin, OUTPUT); //Set Relay Module as an OUPUT
  Serial.begin(115200); //Serial Port Comm

  //Start Wi-Fi Connection
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD); 
  Serial.print("connecting"); 
  while (WiFi.status() != WL_CONNECTED) { 
    Serial.print("."); 
    delay(500); 
  } 
  Serial.println(); 
  Serial.print("Connected: "); 
  Serial.println(WiFi.localIP()); 
   
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
 }

//Loop (Forever)
void loop() {
  ledStatus = Firebase.getBool("devCondition/ledStatusOn");
    if(ledStatus == true){
    digitalWrite(relayPin,LOW);  //Turn on Relay Module
    Serial.println("Turning On Light");
  }else{
    digitalWrite(relayPin,HIGH); //Turn off Relay Module
    Serial.println("Turning Off Light");
  }
}
