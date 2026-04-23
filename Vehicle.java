import java.time.LocalDateTime;

class Vehicle {
    String number;
    String type;
    LocalDateTime entryTime;

    Vehicle(String number, String type) {
        this.number = number;
        this.type = type;
        this.entryTime = LocalDateTime.now();
    }

    int getRate() {
        return 0;
    }
}