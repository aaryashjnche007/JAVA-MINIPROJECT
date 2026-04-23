class Bike extends Vehicle {
    Bike(String number) {
        super(number, "Bike");
    }

    @Override
    int getRate() {
        return 60;
    }
}