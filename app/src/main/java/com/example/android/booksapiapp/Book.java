package com.example.android.booksapiapp;


public class Book {

    //Create a variable for the author of the book
    private String bookAuthor;

    //Create a variable for the title of the book
    private String bookTitle;

    //Create a variable for the info link of the book
    private String bookLink;

    /**
     * CONSTRUCTOR
     * <p>
     * Construct a book object with
     *
     * @param author is the author of the book object
     * @param title  is the title of the book object
     * @param link   is the web link for the book
     */
    public Book(String author, String title, String link) {
        bookAuthor = author;
        bookTitle = title;
        bookLink = link;
    }

    // Getter method that returns the author of the book
    public String getBookAuthor() {
        return bookAuthor;
    }

    // Getter method that returns the web link of the book
    public String getBookLink() {
        return bookLink;
    }

    // Getter method that returns the book title
    public String getBookTitle() {
        return bookTitle;
    }
}
