package com.ssp.higher.base.pojos;

import java.util.List;

/**
 * @Author:zouyu
 * @Date:2019/8/22 10:39
 */
public class User {

    private int id;
    private String name;
    private List<String> cityTravels;

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

    public List<String> getCityTravels() {
        return cityTravels;
    }

    public void setCityTravels(List<String> cityTravels) {
        this.cityTravels = cityTravels;
    }
}
