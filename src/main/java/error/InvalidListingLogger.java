package main.java.error;

import main.java.entity.list.EntityList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvalidListingLogger {

    private static final String CSV_DIRECTORY_NAME = "log\\";
    private static final String CSV_FILE_NAME = "importLog.csv";

    public static void saveInvalidListingsToCSV(EntityList entityList) throws FileNotFoundException {
        Set<String> csvLines = new HashSet<>();
        csvLines.add("ListingId;MarketplaceName;InvalidField");
        entityList.listingList.forEach((listing, v) -> {
            v.forEach((invalidFieldName,isValid) -> {
                if(!isValid){
                    csvLines.add(listing.getId()+ ";" + listing.getMarketplace().getMarketplace_name() + ";" + invalidFieldName);
                }
            });
        });

        File csvOutputFile = new File(CSV_DIRECTORY_NAME + CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            csvLines.stream()
                    .forEach(pw::println);
        }
    }
}
