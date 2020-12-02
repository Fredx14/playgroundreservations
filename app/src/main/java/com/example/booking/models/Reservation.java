package com.example.booking.models;

import com.google.gson.annotations.SerializedName;
public class Reservation {


        @SerializedName("date")
        private String date;
        @SerializedName("05:00")
        private String firstTime;
        @SerializedName("07:00")
        private String secondTime;
    @SerializedName("09:00")
    private String thirdTime;

        public Reservation(String date, String firstTime, String secondTime,String thirdTime) {

            this.date = date;
            this.firstTime = firstTime;
            this.secondTime = secondTime;
            this.thirdTime = thirdTime;
        }

        public String getDate() {
            return this.date; }
        public void setDate(String date) {
            this.date = date; }


        public String getFirstTime() {
            return this.firstTime; }
        public void setFirstTimer(String firstTime) {
            this.firstTime = firstTime; }

        public String getSecondTime() {
            return this.secondTime; }
        public void setSecondTime(String secondTime) {
            this.secondTime = secondTime; }

    public String getThirdTime() {
        return this.thirdTime; }
    public void setThirdTime(String thirdTime) {
        this.thirdTime = thirdTime; }

}
