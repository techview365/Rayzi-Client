package com.example.chiky.modelclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiftCategoryRoot {

    @SerializedName("message")
    private String message;

    @SerializedName("category")
    private final List<CategoryItem> category;

    @SerializedName("status")
    private boolean status;

    public GiftCategoryRoot(List<CategoryItem> category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public List<CategoryItem> getCategory() {
        return category;
    }

    public boolean isStatus() {
        return status;
    }

    public static class CategoryItem {

        @SerializedName("image")
        private final String image;

        @SerializedName("createdAt")
        private final String createdAt;

        @SerializedName("name")
        private final String name;

        @SerializedName("giftCount")
        private final int giftCount;

        @SerializedName("_id")
        private final String id;

        public CategoryItem(String image, String createdAt, String name, int giftCount, String id) {
            this.image = image;
            this.createdAt = createdAt;
            this.name = name;
            this.giftCount = giftCount;
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getName() {
            return name;
        }

        public int getGiftCount() {
            return giftCount;
        }

        public String getId() {
            return id;
        }
    }
}