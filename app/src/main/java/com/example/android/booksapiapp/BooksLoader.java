package com.example.android.booksapiapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of books by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BooksLoader extends AsyncTaskLoader<List<Book>> {

    // Tag for log messages
    private static final String LOG_TAG = BooksLoader.class.getName();

    // Query URL
    private String mUrl;

    /**
     * CONSTRUCTOR
     * <p>
     * Constructs a new {@link BooksLoader}
     *
     * @param context is the context of the activity
     * @param url     is from where to load data
     */
    public BooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> books = BookUtils.fetchBookData(mUrl);
        return books;
    }


}
