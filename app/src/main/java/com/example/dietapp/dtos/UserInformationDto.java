package com.example.dietapp.dtos;

public class UserInformationDto {
    public double lengt;
    public double weight;
    public double targetWeight;
    public double dailyCalorieRequirement;
    public double dailyProteinRequirement;
    public double dailyCarbRequirement;
    public double dailyFatRequirement;
    public String gender;
    public String age;
    public int appUserId;

    public double getLengt() {
        return lengt;
    }

    public void setLengt(double lengt) {
        this.lengt = lengt;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public double getDailyCalorieRequirement() {
        return dailyCalorieRequirement;
    }

    public void setDailyCalorieRequirement(double dailyCalorieRequirement) {
        this.dailyCalorieRequirement = dailyCalorieRequirement;
    }

    public double getDailyProteinRequirement() {
        return dailyProteinRequirement;
    }

    public void setDailyProteinRequirement(double dailyProteinRequirement) {
        this.dailyProteinRequirement = dailyProteinRequirement;
    }

    public double getDailyCarbRequirement() {
        return dailyCarbRequirement;
    }

    public void setDailyCarbRequirement(double dailyCarbRequirement) {
        this.dailyCarbRequirement = dailyCarbRequirement;
    }

    public double getDailyFatRequirement() {
        return dailyFatRequirement;
    }

    public void setDailyFatRequirement(double dailyFatRequirement) {
        this.dailyFatRequirement = dailyFatRequirement;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public UserInformationDto(double lengt, double weight, double targetWeight, double dailyCalorieRequirement, double dailyProteinRequirement, double dailyCarbRequirement, double dailyFatRequirement, String gender, String age, int appUserId) {
        this.lengt = lengt;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.dailyCalorieRequirement = dailyCalorieRequirement;
        this.dailyProteinRequirement = dailyProteinRequirement;
        this.dailyCarbRequirement = dailyCarbRequirement;
        this.dailyFatRequirement = dailyFatRequirement;
        this.gender = gender;
        this.age = age;
        this.appUserId = appUserId;
    }
}
