package main.java.entity;

public class ListingStatus {
    private Integer id;
    private String status_name;

    public ListingStatus(Integer id, String status_name) {
        this.id = id;
        this.status_name = status_name;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus_name() {
        return status_name;
    }
}
