package com.example.zoomimage;

public class Lampada {
    private String name;
    private String description;
    private String imageUri;

    public Lampada() {}

    public Lampada (String name, String description, String imageUri) {
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUri(String imageUrl) {
        this.imageUri = imageUri;
    }
}
