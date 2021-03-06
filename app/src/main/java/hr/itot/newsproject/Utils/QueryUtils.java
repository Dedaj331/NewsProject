package hr.itot.newsproject.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.itot.newsproject.Fragments.FactoryNews;
import hr.itot.newsproject.Helper.News;

/**
 * Created by IgorTot on 23.7.2017.
 */

public final class QueryUtils {
    private static final String LOG_TAG = FactoryNews.class.getName();

    private QueryUtils() {

    }


    public static List<News> fetchNewsData(String requestedURL) {

        Log.d("Loader", "Query utils Fetch News Data method called");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        URL url = createUrl(requestedURL);

        String jsonString = null;
        try {
            jsonString = makeHttpRequest(url);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<News> news = extractFromResponse(jsonString);

        return news;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return null;
        }
        HttpURLConnection conn = null;
        InputStream inputStream = null;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + conn.getResponseCode());
            }

        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(LOG_TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    private static URL createUrl(String requestedURL) {
        URL url = null;
        try {
            url = new URL(requestedURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    private static ArrayList<News> extractFromResponse(String jsonString) {
        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObject = jsonArray.getJSONObject(i);

                String title = currentObject.getString("title");
                String description = currentObject.getString("description");
                String urlToImage = currentObject.getString("urlToImage");
                String postingDate = currentObject.getString("publishedAt");
                String url = currentObject.getString("url");
                News newsData = new News(title, description, urlToImage, postingDate, url);
                news.add(newsData);
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }


        return news;
    }

}
