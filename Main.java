import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vehicle[] carSlots = new Vehicle[8];
        Vehicle[] bikeSlots = new Vehicle[7];

        while (true) {
            int totalLeft = getFree(carSlots) + getFree(bikeSlots);

            System.out.println("\n===== SMART PARKING SYSTEM =====");
            System.out.println("TOTAL PARKING SPACES: 15");
            System.out.println("AVAILABLE SPACES: " + totalLeft);
            System.out.println("1. PARK VEHICLE");
            System.out.println("2. REMOVE VEHICLE & GENERATE BILL");
            System.out.println("3. VIEW PARKING STATUS");
            System.out.println("4. EXIT");
            System.out.print("ENTER CHOICE: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("1. CAR 2. BIKE");
                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("ENTER VEHICLE NUMBER: ");
                    String number = sc.nextLine();

                    if (type == 1) {
                        parkVehicle(sc, carSlots, new Car(number), "Car");
                    } else if (type == 2) {
                        parkVehicle(sc, bikeSlots, new Bike(number), "Bike");
                    } else {
                        System.out.println("INVALID TYPE!");
                    }
                    break;

                case 2:
                    System.out.println("1. CAR 2. BIKE");
                    int removeType = sc.nextInt();

                    if (removeType == 1) {
                        removeVehicle(sc, carSlots, "Car");
                    } else if (removeType == 2) {
                        removeVehicle(sc, bikeSlots, "Bike");
                    } else {
                        System.out.println("INVALID TYPE!");
                    }
                    break;

                case 3:
                    showStatus(carSlots, bikeSlots);
                    break;

                case 4:
                    System.out.println("EXITING...");
                    return;

                default:
                    System.out.println("INVALID CHOICE!");
            }
        }
    }

    static int getFree(Vehicle[] arr) {
        int count = 0;
        for (Vehicle v : arr) {
            if (v == null) count++;
        }
        return count;
    }

    static void parkVehicle(Scanner sc, Vehicle[] slots, Vehicle v, String type) {
        System.out.println(type.toUpperCase() + " SLOTS:");
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null)
                System.out.println("SLOT " + (i + 1) + " → AVAILABLE");
        }

        System.out.print("CHOOSE SLOT: ");
        int slot = sc.nextInt();

        if (slot < 1 || slot > slots.length || slots[slot - 1] != null) {
            System.out.println("INVALID OR OCCUPIED SLOT!");
        } else {
            slots[slot - 1] = v;
            System.out.println("VEHICLE PARKED AT SLOT " + slot);
        }
    }

    static void removeVehicle(Scanner sc, Vehicle[] slots, String type) {
        System.out.print("ENTER SLOT NUMBER: ");
        int slot = sc.nextInt();

        if (slot < 1 || slot > slots.length || slots[slot - 1] == null) {
            System.out.println("INVALID SLOT!");
            return;
        }

        Vehicle v = slots[slot - 1];
        LocalDateTime exitTime = LocalDateTime.now();

        long minutes = Duration.between(v.entryTime, exitTime).toMinutes();
        long hours = (minutes / 60) + 1;

        int rate = v.getRate();
        long bill = hours * rate;
        double tax = bill * 0.05;
        double finalAmount = bill + tax;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("\n=======================================");
        System.out.println("           PARKING RECEIPT             ");
        System.out.println("=======================================");
        System.out.println("BILL NO: " + slot + " | SLOT NO: " + slot);
        System.out.println("---------------------------------------");
        System.out.println("VEHICLE NUMBER : " + v.number.toUpperCase());
        System.out.println("VEHICLE TYPE   : " + v.type.toUpperCase());
        System.out.println("---------------------------------------");
        System.out.println("ENTRY TIME     : " + v.entryTime.format(format));
        System.out.println("EXIT TIME      : " + exitTime.format(format));
        System.out.println("DURATION       : " + hours + " HOURS");
        System.out.println("---------------------------------------");
        System.out.println("RATE / HOUR    : RS. " + rate);
        System.out.println("TOTAL BILL     : ₹" + bill);
        System.out.println("TAX (5%)       : ₹" + tax);
        System.out.println("FINAL AMOUNT   : ₹" + finalAmount);
        System.out.println("=======================================");

        slots[slot - 1] = null;

        System.out.print("\nAVAILABLE " + type.toUpperCase() + " SLOTS: ");
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                System.out.print("(" + String.format("%02d", i + 1) + "), ");
            }
        }
        System.out.println();
    }

    static void showStatus(Vehicle[] carSlots, Vehicle[] bikeSlots) {
        System.out.println("\n--- CAR SLOTS ---");
        for (int i = 0; i < carSlots.length; i++) {
            System.out.println("SLOT " + (i + 1) + ": " + (carSlots[i] == null ? "EMPTY" : carSlots[i].number));
        }

        System.out.println("\n--- BIKE SLOTS ---");
        for (int i = 0; i < bikeSlots.length; i++) {
            System.out.println("SLOT " + (i + 1) + ": " + (bikeSlots[i] == null ? "EMPTY" : bikeSlots[i].number));
        }
    }
}