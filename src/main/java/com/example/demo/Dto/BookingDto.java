package com.example.demo.Dto;

import java.time.LocalDate;

import com.example.demo.Entity.Habitacion;
import com.example.demo.Entity.Huesped;
import com.example.demo.Entity.Reserva;

public class BookingDto {

    public static class BookingRequest {
        private Integer documentID;
        private String roomNumber;
        private String guestName;
        private LocalDate enterDate;
        private LocalDate exitDate;
        private String bookingState;

        public Integer getDocumentID() {
            return documentID;
        }

        public void setDocumentID(Integer documentID) {
            this.documentID = documentID;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public LocalDate getEnterDate() {
            return enterDate;
        }

        public void setEnterDate(LocalDate enterDate) {
            this.enterDate = enterDate;
        }

        public LocalDate getExitDate() {
            return exitDate;
        }

        public void setExitDate(LocalDate exitDate) {
            this.exitDate = exitDate;
        }

        public String getBookingState() {
            return bookingState;
        }

        public void setBookingState(String bookingState) {
            this.bookingState = bookingState;
        }
    }

    public static class BookingSummary {
        private Integer id;
        private Integer documentID;
        private String guestName;
        private String roomNumber;
        private LocalDate enterDate;
        private LocalDate exitDate;
        private String bookingState;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDocumentID() {
            return documentID;
        }

        public void setDocumentID(Integer documentID) {
            this.documentID = documentID;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public LocalDate getEnterDate() {
            return enterDate;
        }

        public void setEnterDate(LocalDate enterDate) {
            this.enterDate = enterDate;
        }

        public LocalDate getExitDate() {
            return exitDate;
        }

        public void setExitDate(LocalDate exitDate) {
            this.exitDate = exitDate;
        }

        public String getBookingState() {
            return bookingState;
        }

        public void setBookingState(String bookingState) {
            this.bookingState = bookingState;
        }
    }

    public BookingSummary mapear(Reserva reserva) {
        BookingSummary resumen = new BookingSummary();
        resumen.setId(reserva.getId());
        resumen.setDocumentID(reserva.getHuesped().getDocumentoId());
        resumen.setGuestName(reserva.getHuesped().getNombre());
        resumen.setRoomNumber(reserva.getHabitacion().getNumero());
        resumen.setEnterDate(reserva.getFechaEntrada());
        resumen.setExitDate(reserva.getFechaSalida());
        resumen.setBookingState(reserva.getEstadoReserva());
        return resumen;
    }

    public Reserva toReserva(BookingRequest request, Huesped huesped, Habitacion habitacion) {
        Reserva reserva = new Reserva();
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setFechaEntrada(request.getEnterDate());
        reserva.setFechaSalida(request.getExitDate());
        reserva.setEstadoReserva(request.getBookingState() == null || request.getBookingState().isBlank()
                ? "Confirmada"
                : request.getBookingState());
        return reserva;
    }
}
