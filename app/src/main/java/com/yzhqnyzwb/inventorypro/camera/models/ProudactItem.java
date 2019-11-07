package com.yzhqnyzwb.inventorypro.camera.models;

public class ProudactItem {
    private String userId;
    private String barcode;
    private String name;
    private String description;
    private String count;
    private String price;
    private String listId;
    private String imageUrl;
    private String vat;
    private String profit;
    private String spoilerPrice;


    public ProudactItem() {
    }

    public ProudactItem(String userId, String barcode, String name, String description, String count, String price,String listId,String imageUrl,String vat,String profit,String spoilerPrice) {
        this.userId = userId;
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.count = count;
        this.price = price;
        this.listId=listId;
        this.imageUrl = imageUrl;
        this.vat=vat;
        this.profit=profit;
        this.spoilerPrice=spoilerPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getListId() {
        return listId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getSpoilerPrice() {
        return spoilerPrice;
    }

    public void setSpoilerPrice(String spoilerPrice) {
        this.spoilerPrice = spoilerPrice;
    }

    @Override
    public String toString() {
        return "ProudactItem{" +
                "userId='" + userId + '\'' +
                ", barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                ", listId='" + listId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", vat='" + vat + '\'' +
                ", profit='" + profit + '\'' +
                ", spoilerPrice='" + spoilerPrice + '\'' +
                '}';
    }
}
