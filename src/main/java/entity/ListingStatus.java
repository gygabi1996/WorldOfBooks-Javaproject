package main.java.entity;

public class ListingStatus {
    private Integer id;
    private String statusName;

    public ListingStatus() {
    }

    public ListingStatus(Integer id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
