package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This creates the Product Class. */
public class Product {
    private SimpleStringProperty ProductPart;
    private SimpleIntegerProperty ProductInStock;
    private SimpleStringProperty ProductMin;
    private SimpleStringProperty ProductMax;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public String productID;
    public String productName;
    public Double productPrice = 0.0;
    public int productInStock;
    public int min;
    public int max;


    /**This is the product constructor. */


    public Product(int productID, String productName, Double productPrice, int productInStock, int min, int max) {
        this.productID = String.valueOf(productID);
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInStock = productInStock;
        this.min = min;
        this.max = max;
    }

    /** This is the Product ID getter.
     * @return the id
     */
    public int getProductID() {
        return Integer.parseInt(productID);
    }

    /** This is the Product ID setter.
     * @param productID the id to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /** This is the product name getter.
     * @return the name
     */
    public String getProductName() {
        return productName;
    }

    /** This is the product name setter.
     * @param productName the name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /** This is the product price getter.
     * @return the price
     */
    public Double getProductPrice() {
        return productPrice;
    }

    /**This is the product price setter.
     * @param productPrice the price to set
     */
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    /**This is the product in stock getter.
     * @return the number of product
     */
    public int getProductInStock() {
        return productInStock;
    }

    /** This is the product in stock setter.
     * @param productInStock the number of product in stock to set
     */
    public void setProductInStock(int productInStock) {
        this.productInStock = productInStock;
    }

    /** This is the minimum getter.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /** this is the minimum setter.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**This is the maximum getter.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /** This is the maximum setter.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }




    /**This method adds an associated part
     * @param part the part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);

    }
    /** This method deletes an associated part.
     * @param part the part to delete
     */
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }
    /** This is the associated parts getter.
     @return the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;}
}

