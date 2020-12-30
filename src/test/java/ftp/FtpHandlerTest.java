package test.java.ftp;

import main.java.ftp.FtpHandler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FtpHandlerTest {

    @Test
    public static void Test() throws  IOException {
        /**
         * Connect to FTP and upload the report.JSON
         */
        FtpHandler.uploadReportToFtp();
    }
}
