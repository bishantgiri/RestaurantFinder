package com.bishant.restaurantfinder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceDetailsModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("formatted_address")
        @Expose
        private String formattedAddress;
        @SerializedName("formatted_phone_number")
        @Expose
        private String formattedPhoneNumber;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("place_id")
        @Expose
        private String placeId;
        @SerializedName("rating")
        @Expose
        private Float rating;
        @SerializedName("vicinity")
        @Expose
        private String vicinity;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;
        @SerializedName("opening_hours")
        @Expose
        private OpeningHours openingHours;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos = null;
        @SerializedName("reviews")
        @Expose
        private List<Review> reviews = null;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public String getFormattedPhoneNumber() {
            return formattedPhoneNumber;
        }

        public void setFormattedPhoneNumber(String formattedPhoneNumber) {
            this.formattedPhoneNumber = formattedPhoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public Float getRating() {
            return rating;
        }

        public void setRating(Float rating) {
            this.rating = rating;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public OpeningHours getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public class Geometry {

            @SerializedName("location")
            @Expose
            private Location location;

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public class Location {

                @SerializedName("lat")
                @Expose
                private Double lat;
                @SerializedName("lng")
                @Expose
                private Double lng;

                public Double getLat() {
                    return lat;
                }

                public void setLat(Double lat) {
                    this.lat = lat;
                }

                public Double getLng() {
                    return lng;
                }

                public void setLng(Double lng) {
                    this.lng = lng;
                }

            }

        }

        public class OpeningHours {

            @SerializedName("open_now")
            @Expose
            private Boolean openNow;
            @SerializedName("weekday_text")
            @Expose
            private List<String> weekdayText = null;

            public Boolean getOpenNow() {
                return openNow;
            }

            public void setOpenNow(Boolean openNow) {
                this.openNow = openNow;
            }

            public List<String> getWeekdayText() {
                return weekdayText;
            }

            public void setWeekdayText(List<String> weekdayText) {
                this.weekdayText = weekdayText;
            }

        }

        public class Photo {

            @SerializedName("photo_reference")
            @Expose
            private String photoReference;

            public String getPhotoReference() {
                return photoReference;
            }

            public void setPhotoReference(String photoReference) {
                this.photoReference = photoReference;
            }

        }

        public class Review {

            @SerializedName("author_name")
            @Expose
            private String authorName;
            @SerializedName("profile_photo_url")
            @Expose
            private String profilePhotoUrl;
            @SerializedName("rating")
            @Expose
            private Float rating;
            @SerializedName("relative_time_description")
            @Expose
            private String relativeTimeDescription;
            @SerializedName("text")
            @Expose
            private String text;

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            public String getProfilePhotoUrl() {
                return profilePhotoUrl;
            }

            public void setProfilePhotoUrl(String profilePhotoUrl) {
                this.profilePhotoUrl = profilePhotoUrl;
            }

            public Float getRating() {
                return rating;
            }

            public void setRating(Float rating) {
                this.rating = rating;
            }

            public String getRelativeTimeDescription() {
                return relativeTimeDescription;
            }

            public void setRelativeTimeDescription(String relativeTimeDescription) {
                this.relativeTimeDescription = relativeTimeDescription;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

        }

    }

}