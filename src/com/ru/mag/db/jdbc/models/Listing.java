package com.ru.mag.db.jdbc.models;

public class Listing {
    private int listingId;
    private String typeOfListing;
    private String description;
    private String notes;

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }
    public int getListingId() {
        return listingId;
    }

    public void setTypeOfListing(String typeOfListing) {
        this.typeOfListing = typeOfListing;
    }

    public String getTypeOfListing() {
        return typeOfListing;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes() {
        return notes;
    }

}
