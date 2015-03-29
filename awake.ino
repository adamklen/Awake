
int brewCoffee(String command);

void setup() {
pinMode(D7, OUTPUT);
pinMode(D6, OUTPUT);

Spark.function("brew",brewCoffee);
Spark.function("alarm", alarm);
}

void loop() {

}

int brewCoffee(String command)
{
    if(command == "HIGH")
    {
            digitalWrite(D7, HIGH);
            delay(15000);
            digitalWrite(D7, LOW);
            digitalWrite(D6, LOW);
            return 1;
    }
    else {
    digitalWrite(D7, LOW);
    return 0;
    }
}

int alarm(String command)
{
    if(command == "HIGH")
    {
            digitalWrite(D6, HIGH);
            delay(100);
            digitalWrite(D6, LOW);
            return 1;
    }
    else {
    digitalWrite(D6, LOW);
    return 0;
    }
}