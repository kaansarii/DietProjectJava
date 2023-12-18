package com.example.dietapp.interfaces;




//CalorieEntry sınıfı; girdiğimiz verileri yani, kullanıcının girdiği bir tarih, yaktığı kalori ve aldığı kalori bilgilerini saklamaya yarıyor
public class CalorieEntry {
    private String date;
    private int burnedCalories;
    private int consumedCalories;

    //yapıcı metot(constructor)
    public CalorieEntry(String date, int burnedCalories, int consumedCalories) {
        this.date = date;
        this.burnedCalories = burnedCalories;
        this.consumedCalories = consumedCalories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(int burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public int getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(int consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    //farkı veriyor
    public int getCalorieDifference() {
        return consumedCalories - burnedCalories;
    }
}
