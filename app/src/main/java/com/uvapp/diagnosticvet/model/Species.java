package com.uvapp.diagnosticvet.model;

public class Species {

    private int id;
    private String name;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getImageUrl() {
        return "http://104.236.28.76/images/species/"+this.id+"."+this.photo;
    }
}
