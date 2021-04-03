package com.example.vesithacks;

public class EventData {
    String eventName, organiser, reg, eventDate, details = "";

    public EventData(){
        //no argument constructor required for firebase
    }
    public EventData(String eventName, String organiser,
                     String reg, String eventDate, String details) {
        this.eventName = eventName;
        this.organiser = organiser;
        this.reg = reg;
        this.eventDate = eventDate;
        this.details = details;
    }

    public EventData(String eventName, String organiser,
                     String reg, String eventDate) {
        this.eventName = eventName;
        this.organiser = organiser;
        this.reg = reg;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getOrganiser() {
        return organiser;
    }

    public String getReg() {
        return reg;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventDetails() {
        return details;
    }
}
