package com.example.demo.dto;

import java.time.LocalDate;

public class BookingDto {

    private Integer documentID;
    private String roomNumber;
    private String guestName; 
    private LocalDate enterDate;
    private LocalDate exitDate;
    private String bookingState; 

    public BookingDto() {}

    //===Clase interna: Builder===//
    public static class Builder {
        private Integer documentID;
        private String roomNumber;
        private String guestName; 
        private LocalDate enterDate;
        private LocalDate exitDate;
        private String bookingState;        
        
        public Builder() {}

        public Builder documentID(Integer documentID) {
            this.documentID = documentID;
            return this;
        }
        
        public Builder roomNumber(String val) {
            this.roomNumber = val;
            return this;
        }

        public Builder guestName(String val) {
            this.guestName = val;
            return this;
        }
        
        public Builder bookingState(String val) {
            this.bookingState = val;
            return this;
        }

        public Builder enterDate(LocalDate val) {
            this.enterDate = val;
            return this;
        }

        public Builder exitDate(LocalDate val) {
            this.exitDate = val;
            return this;
        }

        public BookingDto build() {
            return new BookingDto(this);
        }
    }
    //===Fin de clase interna===//

    public BookingDto(Builder builder) {
        this.documentID = builder.documentID;
        this.roomNumber = builder.roomNumber;
        this.guestName = builder.guestName;
        this.enterDate = builder.enterDate;
        this.exitDate = builder.exitDate;
        this.bookingState = builder.bookingState;
    }

    //getters

    public Integer getDocumentID() {
        return documentID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getEnterDate() {
        return enterDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public String getBookingState() {
        return bookingState;
    }

    //setters

    public void setID(Integer documentID) {
        this.documentID = documentID;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setEnterDate(LocalDate enterDate) {
        this.enterDate = enterDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public void setBookingState(String bookingState) {
        this.bookingState = bookingState;
    }
}