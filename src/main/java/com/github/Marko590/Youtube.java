package com.github.Marko590;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Youtube {
    private static String jsonFetchUrl="https://www.youtube.com/oembed?url=";
    String url;
    YoutubeEnum format;

    public Youtube(){
        this.url=null;
    }
    public Youtube(String url){
        format=YoutubeEnum.fromInt(0);
        if(url.startsWith("https://www.youtube.com/watch?v="))
        {
            format = YoutubeEnum.HTTPS;
        }
        else if(url.startsWith("youtube.com/watch?v="))
        {
            format = YoutubeEnum.REGULAR;
        }
        this.jsonFetchUrl="https://www.youtube.com/oembed?url=";
        this.url=url;
    }

    public void setUrl(String url) {

        format=YoutubeEnum.fromInt(0);
        if(url.startsWith("https://www.youtube.com/watch?v="))
        {
            format = YoutubeEnum.HTTPS;
        }
        else if(url.startsWith("youtube.com/watch?v="))
        {
            format = YoutubeEnum.REGULAR;
        }
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        if (format == YoutubeEnum.HTTPS)
            return url.subSequence(32, 43).toString();

        else if (format == YoutubeEnum.REGULAR)
            return url.subSequence(20, 31).toString();


        else
        {
            return null;
        }

    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public static String getJson(String urlString) {
        String json = null;
        urlString=jsonFetchUrl+urlString+"&format=json";

        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
