package com.example.dietapp.dtos;

public class SharedId {
    public static SharedId instance;
    private  int sharedData;

    public static synchronized SharedId getInstance() {
        if (instance == null) {
            instance = new SharedId();
        }
        return instance;
    }

    public int getSharedData() {
        return sharedData;
    }

    public void setSharedData(int sharedData) {
        this.sharedData = sharedData;
    }
}
