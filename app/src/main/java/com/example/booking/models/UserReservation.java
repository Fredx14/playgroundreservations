package com.example.booking.models;

import com.google.gson.annotations.SerializedName;
public class UserReservation {



    @SerializedName("id")
    private String id;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;


    public UserReservation(String id, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }
    public String getId() {
        return this.id; }
    public void setId(String id) {
        this.id = id; }

    public String getDate() {
        return this.date; }
    public void setDate(String date) {
        this.date = date; }


    public String getTime() {
        return this.time; }
    public void setTime(String firstTime) {
        this.time = firstTime; }


}
