package com.example.apphandmade.views.home.model;

public class CartItem {
    private int id;
    private byte[] image;
    private String productName;
    private double price;
    private int quantity;

    public CartItem(int id, byte[] image, String productName, double price, int quantity) {
        this.id = id;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter and Setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
