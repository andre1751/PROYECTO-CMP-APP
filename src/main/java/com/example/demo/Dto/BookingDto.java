package com.example.demo.Dto;
public  class BookingDto {
    public static class BookingRequest {
    private String documentID;
    private String roomNumber;
    private String guestName; 
    private java.time.LocalDate enterDate;
    private java.time.LocalDate exitDate;
    private String bookingState; 

    public String getID() { return documentID; }
    public void setID(String documentID) { this.documentID = documentID; }
    public String getNumHab() { return roomNumber; }
    public void setNumHab(String roomNumber) { this.roomNumber = roomNumber; }
    public String getName(){return guestName;}
    public void setName(String guestName){this.guestName=guestName; }
    public java.time.LocalDate getFechaEntrada() { return enterDate; }
    public void setFechaEntrada(java.time.LocalDate enterDate) { this.enterDate = enterDate; }
    public java.time.LocalDate getFechaSalida() { return exitDate; }
    public void setFechaSalida(java.time.LocalDate exitDate) { this.exitDate = exitDate; }
    public String getbookingState(){ return bookingState; }
    public void setbookingState(String bookingState){this.bookingState=bookingState;}
}

}
