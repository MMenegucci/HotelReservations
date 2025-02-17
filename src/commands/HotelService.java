package commands;

import models.Guest;
import models.Reservation;
import models.Room;
import models.RoomStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotelService {
    private List<Guest> guests = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void registerGuest(Guest guest) {
        guests.add(guest);
        System.out.println("Registered guest: " + guest.getName());
    }

    public void registerRoom(Room room) {
        rooms.add(room);
        System.out.println("Registered room: " + room);
    }
    public Reservation makeReservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            System.out.println("Error: Room not available.");
            return null;
        }

        Reservation reservation = new Reservation(guest, room, checkIn, checkOut);
        reservations.add(reservation);
        guest.addReservation(reservation);
        room.setStatus(RoomStatus.OCCUPIED);

        System.out.println("Reservation made: " + reservation);
        return reservation;
    }

    public void cancelReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
        System.out.println("Reservation canceled" + reservation);
    }

    public void checkIn(Reservation reservation) {
        System.out.println("Check-in carried out for: " + reservation);
    }

    public void checkOut(Reservation reservation) {
        reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
        System.out.println("Check-out carried. Room " + reservation.getRoom().getRoomNumber() + "is available again");
    }
}
