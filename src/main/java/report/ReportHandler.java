package main.java.report;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.entity.ReportEntity;
import org.json.JSONObject;

import java.io.*;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ReportHandler {
    public static final String filepath = "json/report.JSON";

    public static void createReport(ReportEntity reportEntity){
        // Generate report.JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(reportEntity, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
