package main.java.report;



import com.google.gson.Gson;
import main.java.entity.ReportEntity;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportHandler {
    public static void createReport(ReportEntity reportEntity){
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("json/report.JSON")) {
            gson.toJson(reportEntity, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
