package com.example.booking.models;

import com.google.gson.annotations.SerializedName;

public class Reserve {


        @SerializedName("date")
        private String date;
        @SerializedName("time")
        private String time;
        @SerializedName("user_id")
        private String user_id;

        public Reserve(String date, String time, String user_id) {

            this.date = date;
            this.time = time;
            this.user_id = user_id;
        }

        public String getDate() {
            return this.date; }
        public void setDate(String date) {
            this.date = date; }


        public String getTime() {
            return this.time; }
        public void setTime(String time) {
            this.time = time; }

        public String getUserId() {
            return this.user_id; }
        public void setUserId(String user_id) {
            this.user_id = user_id; }

}
