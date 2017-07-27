package com.example.android.booksapiapp;

import android.text.TextUtils;
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

/**
 * Helper methods related to requesting and receiving book data from Google Books API.
 */
public final class BookUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = BookUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link BookUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name BookUtils (and an object instance of QueryUtils is not needed).
     */
    private BookUtils() {
    }

    public static List<Book> fetchBookData(String query) {

        // Create a URL with createUrl() method
        URL url = createUrl(query);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> books = extractFromJson(jsonResponse);

        // Return the list of {@link Book}s
        return books;
    }

    /**
     * Create a {@link URL} from the query inserted by the user
     *
     * @param query is the query that was inserted by the user
     * @return the new URL created or null if failed
     */
    private static URL createUrl(String query) {

        // Create a variable to use for URL
        URL url = null;

        try {
            url = new URL(query);
        } catch (MalformedURLException e) {

            //If it fails, log it
            Log.e(LOG_TAG, "URL creation failed", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000 /* milliseconds */);
            urlConnection.setConnectTimeout(1500 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Book> extractFromJson(String responseJson) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(responseJson)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding books to
        List<Book> booksList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(responseJson);

            // Extract the JSONArray associated with the key called "volumes",
            // which represents a list of features (or earthquakes).
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");


            /** For each book in the bookArray, create an {@link Book} object */
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the list of books
                JSONObject currentBookObject = bookArray.getJSONObject(i);

                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that book.
                JSONObject volumeInfo = currentBookObject.getJSONObject("volumeInfo");

                // Extract the value for the key called title
                String title = volumeInfo.getString("title");

                // Extract the JSONArray associated with the key called "authors"
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                // Extract the value at the position 0
                String author = authorsArray.getString(0);

                // Extract the value for the key called infoLink
                String webLink = volumeInfo.getString("infoLink");

                /** Create a new {@link Book } object with the title, author and web link url from
                 * the JSON response.*/
                Book newBook = new Book(title, author, webLink);

                /** Add the new {@link Book} to the list of books */
                booksList.add(newBook);

            }

        } catch (JSONException e) {

            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("BookUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of books
        return booksList;
    }


}
