package com.bishant.restaurantfinder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * Created by Bishant on 27-Nov-19.
 * Copyright (c) 2019 softwarica. All rights reserved.
 *
 */
public class MenuModel {
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private double price;
        @SerializedName("rating")
        @Expose
        private float rating;
        @SerializedName("quantity")
        @Expose
        private int quantity;
        @SerializedName("image_url")
        @Expose
        private String image_url;

        public Result() {
        }

        public Result(int id, String name, String description, double price, float rating, int quantity, String image_url) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.rating = rating;
            this.quantity = quantity;
            this.image_url = image_url;
        }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
