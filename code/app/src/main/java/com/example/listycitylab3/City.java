package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String province;
    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }
    public String getName() {
        return name;
    }
    public String getProvince() {
        return province;
    }

    // Hint #1: Add setters to your City class so that you can modify its nam and province
    public void setName(String name) { this.name = name; }
    public void setProvince(String province) { this.province = province; }
}
