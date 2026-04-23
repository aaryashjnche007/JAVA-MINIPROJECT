class Car extends Vehicle {
    Car(String number) {
        super(number, "Car");
    }

    @Override
    int getRate() {
        return 120;
    }
}