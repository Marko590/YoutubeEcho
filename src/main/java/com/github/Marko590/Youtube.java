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
    private static String regularFormat="youtube.com/watch?v=";
    private static String shortFormat="youtu.be/";
    private static String httpsPrefix="https://";
    public Youtube(){
        this.url=null;
    }
    public Youtube(String url){
        format=YoutubeEnum.fromInt(0);

        if(url.startsWith(regularFormat)||url.startsWith(httpsPrefix+regularFormat))
        {
            format = YoutubeEnum.REGULAR;
        }
        else if(url.startsWith(shortFormat)||url.startsWith(httpsPrefix+shortFormat)){
            format=YoutubeEnum.SHORT;
        }

        this.url=url;
    }

    public void setUrl(String url) {

        format=YoutubeEnum.fromInt(0);
        if(url.startsWith(httpsPrefix))
        {
            if (url.startsWith(regularFormat))
                format = YoutubeEnum.HTTPS_REGULAR;

            else if (url.startsWith(shortFormat))
                format = YoutubeEnum.HTTPS_SHORT;
        }
        else
            {
            if (url.startsWith(regularFormat))
                format = YoutubeEnum.REGULAR;

            else if (url.startsWith(shortFormat))
                format = YoutubeEnum.SHORT;
        }
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        if (format == YoutubeEnum.HTTPS_REGULAR)
            return url.subSequence(32, 43).toString();
        else if (format == YoutubeEnum.REGULAR)
            return url.subSequence(20, 31).toString();
        else if(format == YoutubeEnum.SHORT)
            return url.subSequence(9,url.length()).toString();
        else if(format ==YoutubeEnum.HTTPS_SHORT)
            return url.subSequence(17,url.length()).toString();
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
