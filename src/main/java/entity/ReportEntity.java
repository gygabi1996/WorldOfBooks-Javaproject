package main.java.entity;

import java.util.Map;

public class ReportEntity {
    private final Integer total_listing_count;
    private final Integer total_ebay_listing_count;
    private final Double total_ebay_listing_price;
    private final Double average_ebay_listing_price;
    private final Integer total_amazon_listing_count;
    private final Double total_amazon_listing_price;
    private final Double average_amazon_listing_price;
    private final String best_lister_email_address;
    private final Map<String,Map<String,Object>> monthly_reports;

    public ReportEntity(Number total_listing_count, Number total_ebay_listing_count, Number total_ebay_listing_price, Number average_ebay_listing_price, Number total_amazon_listing_count, Number total_amazon_listing_price, Number average_amazon_listing_price, String best_lister_email_address, Map<String,Map<String,Object>> monthly_reports) {
        this.total_listing_count = (Integer) total_listing_count;
        this.total_ebay_listing_count = (Integer) total_ebay_listing_count;
        this.total_ebay_listing_price = (Double) total_ebay_listing_price;
        this.average_ebay_listing_price = (Double) average_ebay_listing_price;
        this.total_amazon_listing_count = (Integer) total_amazon_listing_count;
        this.total_amazon_listing_price = (Double) total_amazon_listing_price;
        this.average_amazon_listing_price = (Double) average_amazon_listing_price;
        this.best_lister_email_address = best_lister_email_address;
        this.monthly_reports = monthly_reports;
    }

    public Integer getTotal_listing_count() {
        return total_listing_count;
    }

    public Integer getTotal_ebay_listing_count() {
        return total_ebay_listing_count;
    }

    public Double getTotal_ebay_listing_price() {
        return total_ebay_listing_price;
    }

    public Double getAverage_ebay_listing_price() {
        return average_ebay_listing_price;
    }

    public Integer getTotal_amazon_listing_count() {
        return total_amazon_listing_count;
    }

    public Double getTotal_amazon_listing_price() {
        return total_amazon_listing_price;
    }

    public Double getAverage_amazon_listing_price() {
        return average_amazon_listing_price;
    }

    public String getBest_lister_email_address() {
        return best_lister_email_address;
    }

    public Map<String,Map<String,Object>> getMonthly_reports() {
        return monthly_reports;
    }
}
