package main.java.entity;

import java.util.Map;

public class ReportEntity {
    private final Integer total_listing_count;
    private final Integer total_ebay_listing_count;
    private final Integer total_ebay_listing_price;
    private final Integer average_ebay_listing_price;
    private final Integer total_amazon_listing_count;
    private final Integer total_amazon_listing_price;
    private final Integer average_amazon_listing_price;
    private final String best_lister_email_address;
    private final Map<String,Map<String,Object>> monthly_reports;

    public ReportEntity(Integer total_listing_count, Integer total_ebay_listing_count, Integer total_ebay_listing_price, Integer average_ebay_listing_price, Integer total_amazon_listing_count, Integer total_amazon_listing_price, Integer average_amazon_listing_price, String best_lister_email_address, Map<String,Map<String,Object>> monthly_reports) {
        this.total_listing_count = total_listing_count;
        this.total_ebay_listing_count = total_ebay_listing_count;
        this.total_ebay_listing_price = total_ebay_listing_price;
        this.average_ebay_listing_price = average_ebay_listing_price;
        this.total_amazon_listing_count = total_amazon_listing_count;
        this.total_amazon_listing_price = total_amazon_listing_price;
        this.average_amazon_listing_price = average_amazon_listing_price;
        this.best_lister_email_address = best_lister_email_address;
        this.monthly_reports = monthly_reports;
    }

    public Integer getTotal_listing_count() {
        return total_listing_count;
    }

    public Integer getTotal_ebay_listing_count() {
        return total_ebay_listing_count;
    }

    public Integer getTotal_ebay_listing_price() {
        return total_ebay_listing_price;
    }

    public Integer getAverage_ebay_listing_price() {
        return average_ebay_listing_price;
    }

    public Integer getTotal_amazon_listing_count() {
        return total_amazon_listing_count;
    }

    public Integer getTotal_amazon_listing_price() {
        return total_amazon_listing_price;
    }

    public Integer getAverage_amazon_listing_price() {
        return average_amazon_listing_price;
    }

    public String getBest_lister_email_address() {
        return best_lister_email_address;
    }

    public Map<String,Map<String,Object>> getMonthly_reports() {
        return monthly_reports;
    }
}
