package com.example.dietapp.dtos;

public class UserInformationDto {
    private double length;
    private double weight;
    private double targetWeight;
    private double dailyCalorieRequirement;
    private double dailyProteinRequirement;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getDailyCarbonhydrateRequirement() {
        return dailyCarbonhydrateRequirement;
    }

    public void setDailyCarbonhydrateRequirement(double dailyCarbonhydrateRequirement) {
        this.dailyCarbonhydrateRequirement = dailyCarbonhydrateRequirement;
    }

    public int getDietDay() {
        return dietDay;
    }

    public void setDietDay(int dietDay) {
        this.dietDay = dietDay;
    }

    private double dailyCarbonhydrateRequirement;
    private  int dietDay;
    private double dailyFatRequirement;
    private String gender;
    private String age;
    private int appUserId;

    public double getlength() {
        return length;
    }

    public void setlength(double length) {
        this.length = length;
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

    public double getdailyCarbonhydrateRequirement
() {
        return dailyCarbonhydrateRequirement
;
    }

    public void setdailyCarbonhydrateRequirement
(double dailyCarbonhydrateRequirement
) {
        this.dailyCarbonhydrateRequirement
 = dailyCarbonhydrateRequirement
;
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

    public UserInformationDto(double length, double weight, double targetWeight, double dailyCalorieRequirement, double dailyProteinRequirement, double dailyCarbonhydrateRequirement
, int dietDay, double dailyFatRequirement, String gender, String age, int appUserId) {
        this.length = length;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.dailyCalorieRequirement = dailyCalorieRequirement;
        this.dailyProteinRequirement = dailyProteinRequirement;
        this.dailyCarbonhydrateRequirement
 = dailyCarbonhydrateRequirement
;
        this.dietDay = dietDay;
        this.dailyFatRequirement = dailyFatRequirement;
        this.gender = gender;
        this.age = age;
        this.appUserId = appUserId;
    }
}
