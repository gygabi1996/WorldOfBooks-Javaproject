package main.java.report;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.entity.ReportEntity;

import java.io.*;

public class ReportHandler {
    public static final String filepath = "json/report.JSON";

    public static void createReport(ReportEntity reportEntity){
        // Generate report.JSON
        System.out.println("Generating report.JSON");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(reportEntity, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
