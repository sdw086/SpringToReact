package com.example.reactboot.common.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FtpUtil {

    private FTPClient ftpClient;

    public FtpUtil() {
        this.ftpClient = new FTPClient();
    }

    // ftp 연결 & 설정
    public void connect(String ip, int port, String id, String pw, String dir) throws Exception {
        try {
            boolean result                  = false;
            ftpClient.connect(ip, port);
            ftpClient.setControlEncoding("UTF-8");
            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("connection failure");
            }
            if (!ftpClient.login(id, pw)) {
                ftpClient.logout();
                throw new Exception("login failure");
            }

            ftpClient.setSoTimeout(1000 * 10);                                          // time setting
            ftpClient.login(id, pw);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);                                // file type setting
            ftpClient.enterLocalPassiveMode();                                          // active mode
            result = ftpClient.changeWorkingDirectory(dir);                             // 저장 파일 경로

            if (!result) {
                boolean makeDirResult = false;
                String[] directory = dir.split("/");

                String newdir = "";
                for(int i=0, l=directory.length; i<l; i++) {
                    newdir += ("/" + directory[i]);
                    try {
                        makeDirResult = ftpClient.changeWorkingDirectory(newdir);
                        if(!makeDirResult) {
                            ftpClient.makeDirectory(newdir);
                            ftpClient.changeWorkingDirectory(newdir);
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                }
            }

        } catch (Exception e) {
            if (e.getMessage().indexOf("refused") != -1) {
                throw new Exception("connection error");
            }
            throw e;
        }
    }

    // ftp 연결 해제
    public void disconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            int a = 0;
            int b = 0;
            a = b;
            b = a;
        }
    }

    // file upload
    public void uploadFile(String saveFileName, InputStream inputStream) throws Exception {
        try {
            if(!ftpClient.storeFile(saveFileName, inputStream)) {
                throw new Exception("upload error");
            }
        } catch (Exception e) {
            if(e.getMessage().indexOf("not open") != -1) {
                throw new Exception("connnection error");
            }
            throw e;
        }
    }

    // dir > all file upload
    public void uploadDirFile(String dirName) throws Exception {
        try {
            FileInputStream fis;
            // dir내에 파일 읽기
            File dir            = new File(dirName);
            String[] filenames  = dir.list();
            File[] files        = dir.listFiles();
            for (int i=0; i<filenames.length; i++) {
                fis             = new FileInputStream(files[i]);
                if (!ftpClient.storeFile(filenames[i], fis)) {
                    throw new Exception("upload error");
                }
            }
        } catch (Exception e) {
            if(e.getMessage().indexOf("not open") != -1) {
                throw new Exception("connnection error");
            }
            throw e;
        }
    }

    // file down
    public boolean downloadFile(String savedFileName, OutputStream local) throws IOException {
        boolean isSuccess = ftpClient.retrieveFile(savedFileName, local);

        return isSuccess;
    }
}
