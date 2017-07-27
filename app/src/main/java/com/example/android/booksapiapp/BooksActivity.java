package com.example.android.booksapiapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = BooksActivity.class.getName();

    /*
     * Constant value for the Book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * URL for books data from Google Books API
     */
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=40&projection=full&q=:";

    public String searchQuery = "";

    /* Variable for the {@link SearchView} */
    public SearchView searchView;

    /* Variable for the ListView */
    private ListView bookListView;

    /**
     * Variable for the {@link BookAdapter} object that holds
     * the list of {@link Book}s objects
     */
    private BookAdapter bookAdapter;

    /**
     * ProgressBar that is displayed when the data is loaded
     */
    private ProgressBar progressBar;

    /* Variable for the user's query */
    private String userQuery;

    /* TextViews that are displayed when the list is empty */
    private TextView emptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        /* Find the reference to the {@link SearchView} in the layout */
        searchView = (SearchView) findViewById(R.id.search);

        /* Find the reference to the {@link ListView} in the layout  */
        bookListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of books as input
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());

        /* Set the adapter on the {@link ListView}
         * so the list can be populated in the user interface */
        bookListView.setAdapter(bookAdapter);

        /* Set an item click listener on the {@link ListView}, which sends an intent to a web
         * browser to open a website with more information about the selected book */
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current book that was clicked on
                Book clickedBook = bookAdapter.getItem(position);

                // Convert the String URL into URI object ( to pass into the Intent constructor)
                Uri bookUri = Uri.parse(clickedBook.getBookLink());

                // Create a new intent to view the book URI
                Intent webIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                //If there is an App to handle the Intent, start it
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    // Send the intent to launch a new activity
                    startActivity(webIntent);
                }
            }
        });

        // Empty state text view for no item found
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyStateTextView);

        // Find the reference to the progress bar in a layout
        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        // If there is a network connection, fetch data
        if (isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();
            /*
             * Initialize the loader. Pass in the int Id constant defined above and pass in
             * null for the bundle. Pass in this activity for the LoaderCallbacks parameter ( which
             * is valid because this activity implements the LoaderCallbacks interface).
             */
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            /*
             *  Otherwise, display error
             *  First, hide loading indicator so error message will be visible
             */
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet_connection);
        }

        /*
         * Set an OnQueryTextListener to the button so if there is a network
         * connection to update the search
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If there is a network connection
                if (isConnected()) {
                    bookListView.setVisibility(View.INVISIBLE);
                    emptyStateTextView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    userQuery = searchView.getQuery().toString();
                    userQuery = userQuery.replace(" ", "");
                    Log.v(LOG_TAG, userQuery);

                    // Restart the Loader and execute the new search
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, BooksActivity.this);
                    searchView.clearFocus();
                }
                // If there is NO network connection
                else {
                    bookListView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.GONE);
                    // Set the text of the Empty View
                    emptyStateTextView.setVisibility(View.VISIBLE);
                    emptyStateTextView.setText(R.string.no_internet_connection);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Helper method to check network connection
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        String requestUrl = "";

        if (userQuery != null && userQuery != "") {
            requestUrl = BOOK_REQUEST_URL + userQuery;
        } else {
            String defaultQuery = "google";
            requestUrl = BOOK_REQUEST_URL + defaultQuery;
        }
        return new BooksLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> booksData) {

        // Hide loading indicator because the data has been loaded
        progressBar.setVisibility(View.GONE);

        // If the list is empty
        emptyStateTextView.setText(R.string.no_item_found);
        // Clear the adapter of previous books data
        bookAdapter.clear();

        /* If there is a valid list of {@link Book}s , then add them to the
         * adapter's data set. This will trigger the {@link  ListView} to update. */
        if (booksData != null && !booksData.isEmpty()) {
            bookAdapter.addAll(booksData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        bookAdapter.clear();
    }
}
