package com.dmytrobilokha.viaproxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Standard {

    public static void main(String[] args) {
        ProxySelectorWrapper.setup();
        URLConnection connection;
        try {
             URL targetUrl = new URL(Constants.WEB_TARGET);
             connection = targetUrl.openConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        connection.addRequestProperty("User-Agent", "Mozilla/4.76");
        try (InputStream inputStream = connection.getInputStream();
             BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));) {
            streamReader
                    .lines()
                    .forEach(System.out::println);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

}
