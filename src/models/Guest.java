package models;

import java.util.ArrayList;
import java.util.List;

public class Guest {
    private String name;
    private String cpf;
    private String phone;
    private String email;
    private int option;
    private List<Reservation> reservations;

    public Guest(String name, String cpf, String phone, String email){
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.option = option;
        this.reservations = new ArrayList<Reservation>();
    }

    public String getCpf(){
        return cpf;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public static List<Guest> guests = new ArrayList<>();

    @Override
    public String toString() {
        return "Guest: " + name + " | CPF: " + cpf + " | Telefone: " + phone + " | Email: " + email;
    }

public void addGuest (Reservation guest) {
        reservations.add(guest);
}

}
