package org.pursuit.dogbreeds.model;

//created just for the database table
public class DogImage {

    private String breed;
    private String imageUrl;

    public DogImage(String breed, String imageUrl) {
        this.breed = breed;
        this.imageUrl = imageUrl;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBreed() {
        return breed;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
