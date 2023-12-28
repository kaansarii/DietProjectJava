package com.example.dietapp.dtos;

public class AddFoodDto {
    private  String name, type;
    private  double calorie, fat, carbonhydrate, protein;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbonhydrate() {
        return carbonhydrate;
    }

    public void setCarbonhydrate(double carbonhydrate) {
        this.carbonhydrate = carbonhydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public AddFoodDto(String name, String type, double calorie, double fat, double carbonhydrate, double protein) {
        this.name = name;
        this.type = type;
        this.calorie = calorie;
        this.fat = fat;
        this.carbonhydrate = carbonhydrate;
        this.protein = protein;
    }
}
