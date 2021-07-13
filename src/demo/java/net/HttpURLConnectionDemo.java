package demo.java.net;

import demo.util.IoUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionDemo {

    public static void main(String[] args) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://www.example.com/");
            connection = (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                result.append(line);
            }
            //System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.closeQuietly(reader);
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

}
