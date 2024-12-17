package com.example.apphandmade.views.home.model;

public class Product {
    private int id;
    private byte[] image;
    private String productName;
    private double price;
    private float rating;
    private String productDetail;
    private int quantity;
    private boolean isFavorite = false;

    public Product(int id, byte[] image, String productName, double price, float rating, String productDetail, int quantity, boolean isFavorite) {
        this.id = id;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.rating = rating;
        this.productDetail = productDetail;
        this.quantity = quantity;
        this.isFavorite = isFavorite;
    }

    // Getter methods
    public int getId() { return id; }
    public byte[] getImage() { return image; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public float getRating() { return rating; }
    public String getProductDetail() { return productDetail; }
    public int getQuantity() { return quantity; }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", productName='" + productName + "', price=" + price +
                ", rating=" + rating + ", productDetail='" + productDetail + "', quantity=" + quantity + "}";
    }
}
