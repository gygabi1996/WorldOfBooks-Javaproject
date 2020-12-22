package main.java.entity;

import java.util.Date;
import java.util.UUID;

public class Listing {
    private UUID id;
    private String title;
    private String description;
    private UUID inventory_item_location_id;
    private double listing_price;
    private String currency;
    private int quantity;
    private ListingStatus listing_status;
    private Marketplace marketplace;
    private Date upload_time;
    private String owner_email_address;

    public Listing(UUID id, String title, String description, UUID inventory_item_location_id, double listing_price, String currency, int quantity, ListingStatus listing_status, Marketplace marketplace, Date upload_time, String owner_email_address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.inventory_item_location_id = inventory_item_location_id;
        this.listing_price = listing_price;
        this.currency = currency;
        this.quantity = quantity;
        this.listing_status = listing_status;
        this.marketplace = marketplace;
        this.upload_time = upload_time;
        this.owner_email_address = owner_email_address;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getInventory_item_location_id() {
        return inventory_item_location_id;
    }

    public double getListing_price() {
        return listing_price;
    }

    public String getCurrency() {
        return currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public ListingStatus getListing_status() {
        return listing_status;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public String getOwner_email_address() {
        return owner_email_address;
    }
}
