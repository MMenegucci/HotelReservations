package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;

    public Reservation(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getPricePerNight();
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Reservation: " + guest.getName() + " | Room: " + room.getRoomNumber() + " | Check-in: " + checkInDate +
                " | Check-out: " + checkOutDate + " | Total: R$" + totalPrice;
    }
}
