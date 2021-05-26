package com.example.coronameter;

public class Corona implements Comparable<Corona>{

    int totalConfirmed,newConfirmed,totalDeaths,newDeaths,totalRecovered,newRecovered;
    String country;


    public Corona(String country, int totalConfirmed, int newConfirmed, int totalDeaths, int newDeaths, int totalRecovered, int newRecovered) {
        this.totalConfirmed = totalConfirmed;
        this.newConfirmed = newConfirmed;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalRecovered = totalRecovered;
        this.newRecovered = newRecovered;
        this.country = country;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int compareTo(Corona o) {
        return this.totalConfirmed - o.getTotalConfirmed();
    }
}
