package functions;

import models.Room;

import java.sql.SQLOutput;
import java.util.Scanner;

import static models.Room.rooms;

public class AvaibilityCheck {
    public static void AvaibilityCheck(Scanner sc) {
        if (rooms.isEmpty()) {
            System.out.println();
            System.out.print("No rooms registered.");
        } else {
            System.out.println("Rooms available: ");
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }
}
