package functions;

import java.util.Scanner;

public class HotelManagementSystem {
        public static void main(Scanner args) {
            Scanner sc = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n--- Hotel Management System ---");
                System.out.println("1. Check-in");
                System.out.println("2. Check-out");
                System.out.println("3. Exit");
                System.out.print("\nSelect one of the options above: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        CheckIn.checkIn(sc);
                        break;
                    case 2:
                        CheckOut.checkOut(sc);
                        break;
                    case 3:
                        System.out.println("Return to options.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
                return;
            }
            sc.close();
        }
}
