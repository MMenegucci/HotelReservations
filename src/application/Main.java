package application;

import functions.RegisterGuest;
import functions.RegisterRoom;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("=== System Activated ===");

        int option = -1;

        while (option != 0) {
            System.out.println();
            System.out.println("Select one of the options below:");
            System.out.println("1 - Room Registration");
            System.out.println("2 - Guest Registration");
            System.out.println("3 - Make Reservation");
            System.out.println("4 - Reservation Cancellation");
            System.out.println("5 - Check-in and Check-out");
            System.out.println("6 - Stay Cost Calculation");
            System.out.println("7 - Reservation");
            System.out.println("0 - Exit");

            boolean validEntry = false;

            while (!validEntry) {
                try {
                    System.out.println();
                    System.out.print("Press the key according to the expected command: ");
                    option = sc.nextInt();
                    sc.nextLine();
                    validEntry = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid entry. Enter a number.");
                    sc.nextLine();
                }
            }
            switch (option) {
                case 1:
                    RegisterRoom.registerRoom(sc);
                    break;
                case 2:
                    RegisterGuest.registerGuest(sc);
                    break;
                case 3:
                    makeReservation(sc);
                    break;
                case 4:
                    cancelReservation(sc);
                    break;
                case 5:
                    checkIn(sc);
                    break;
                case 6:
                    checkOut(sc);
                    break;
                case 7:
                    calculateTotalPrice(sc);
                    break;
                case 0:
                    System.out.println("Exiting system..");
                    break;
                default:
                    System.out.println("Option invalid, try again.");
            }
        }
        sc.close();
    }

    private static void registerRoom(Scanner sc) {
        System.out.println("Room registration logic here.");
    }

    private static void registerGuest(Scanner sc) {
        System.out.println("Guest registration logic here.");
    }

    private static void makeReservation(Scanner sc) {
        System.out.println("Room reservation logic here.");
    }

    private static void cancelReservation(Scanner sc) {
        System.out.println("Reservation cancellation logic here.");
    }

    private static void checkAvailability(Scanner sc) {
        System.out.println("Availability check logic here.");
    }

    private static void checkIn(Scanner sc) {
        System.out.println("Check-in logic here.");
    }

    private static void checkOut(Scanner sc) {
        System.out.println("Check-out logic here.");
    }

    private static void calculateTotalPrice(Scanner sc) {
        System.out.println("Stay cost calculation logic here.");
    }
}