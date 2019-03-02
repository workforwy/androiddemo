package com.ceshi.ha.update;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wy on 2017/7/31.
 */

public class DownUtils {

    private URL url = null;

    public String downloadBaseFile(String urlStr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            //创建一个url对象
            url = new URL(urlStr);
            //通過url对象，创建一个HttpURLConnection对象（连接）
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //通过HttpURLConnection对象，得到InputStream
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            //使用io流读取文件
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public int downloadAllFile(String urlStr, String path, String fileName) {
        InputStream inputStream = null;
        try {
            FileUtils fileUtils = new FileUtils();
            if (fileUtils.isFileExist(path + fileName)) {
                return 1;
            } else {
                inputStream = getInputStream(urlStr);
                File file = fileUtils.write2SDFromInput(path, fileName, inputStream);
                if (file == null) {
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }
        return 0;
    }

    private InputStream getInputStream(String urlStr) throws MalformedURLException, IOException {
        url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        return inputStream;
    }

    class FileUtils {
        private String SD_PATH;

        public String getSD_PATH() {
            return SD_PATH;
        }

        public FileUtils() {
            //得到当前外部存储设备的目录
            SD_PATH = Environment.getExternalStorageDirectory() + "/";
        }

        public File createSDFile(String fileName) throws IOException {
            File file = new File(SD_PATH + fileName);
            file.createNewFile();
            return file;
        }

        public File createSDDir(String dirName) {
            File dir = new File(SD_PATH + dirName);
            dir.mkdir();
            return dir;
        }

        public boolean isFileExist(String fileName) {
            File file = new File(SD_PATH + fileName);
            return file.exists();
        }

        public File write2SDFromInput(String path, String fileName, InputStream input) {
            File file = null;
            OutputStream output = null;
            try {
                createSDDir(path);
                file = createSDFile(path + fileName);
                output = new FileOutputStream(file);
                byte buffer[] = new byte[4 * 1024];
                while (input.read(buffer) != -1) {
                    output.write(buffer);
                }
                //清掉缓存
                output.flush();
            } catch (Exception e) {
                Log.e("write2SDFromInput", e.getMessage());
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException ioe) {
                    Log.e("write2SDFromInput", ioe.getMessage());
                }
            }
            return file;
        }
    }
}
