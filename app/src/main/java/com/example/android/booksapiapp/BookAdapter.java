package com.example.android.booksapiapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * CONSTRUCTOR
     * <p>
     * Constructs {@link BookAdapter} object with
     *
     * @param context is the context of the adapter
     * @param books   is the list of book objects
     */
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;

        // If there is no recyclable view, create a new one
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the current book object
        Book currentBook = getItem(position);

        /* Find the TextView that represent the title in the list_item.xml
         *  by its ID */
        TextView titleView = (TextView) listView.findViewById(R.id.title_view);
        // And set the title name of the current book
        titleView.setText(currentBook.getBookTitle());

        /* Find the TextView that represent the author in the list_item.xml
         *  by its ID  */
        TextView authorView = (TextView) listView.findViewById(R.id.author_view);
        // And set the author name of the current book
        authorView.setText(currentBook.getBookAuthor());

        // Return the list view
        return listView;
    }
}
