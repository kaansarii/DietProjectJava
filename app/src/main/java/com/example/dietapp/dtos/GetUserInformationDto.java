package com.example.dietapp.dtos;

public class GetUserInformationDto {
    private double dailyCalorieRequirement;
    private double dailyProteinRequirement;
    private double dailyCarbonhydrateRequirement;
    private double dailyFatRequirement;

    public GetUserInformationDto(double dailyCalorieRequirement, double dailyProteinRequirement, double dailyCarbonhydrateRequirement, double dailyFatRequirement) {
        this.dailyCalorieRequirement = dailyCalorieRequirement;
        this.dailyProteinRequirement = dailyProteinRequirement;
        this.dailyCarbonhydrateRequirement = dailyCarbonhydrateRequirement;
        this.dailyFatRequirement = dailyFatRequirement;
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

    public double getDailyCarbonhydrateRequirement() {
        return dailyCarbonhydrateRequirement;
    }

    public void setDailyCarbonhydrateRequirement(double dailyCarbonhydrateRequirement) {
        this.dailyCarbonhydrateRequirement = dailyCarbonhydrateRequirement;
    }

    public double getDailyFatRequirement() {
        return dailyFatRequirement;
    }

    public void setDailyFatRequirement(double dailyFatRequirement) {
        this.dailyFatRequirement = dailyFatRequirement;
    }
}
