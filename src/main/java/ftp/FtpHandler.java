package main.java.ftp;

import main.java.report.ReportHandler;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Properties;

public class FtpHandler {

    public static final String FTP_SERVER = "ftp.server";
    public static final String FTP_PORT = "ftp.port";
    public static final String FTP_USERNAME = "ftp.username";
    public static final String FTP_PASSWORD = "ftp.password";

    private static Properties properties = null;

    public static void uploadReportToFtp() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config/config.properties"));


        // upload report.json to the FTP server
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(properties.getProperty(FTP_SERVER), Integer.parseInt(properties.getProperty(FTP_PORT)));
            ftpClient.login(properties.getProperty(FTP_USERNAME), properties.getProperty(FTP_PASSWORD));

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File firstLocalFile = new File(ReportHandler.filepath);

            String firstRemoteFile = "report.JSON";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading report.JSON");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The report.JSON is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
