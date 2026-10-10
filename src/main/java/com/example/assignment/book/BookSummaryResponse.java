package com.example.assignment.book;

public class BookSummaryResponse {
    private  Long id;
    private  String  title;
    private int stock;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStock() {
        return stock;
    }

    public BookSummaryResponse(long id, String title, int stock){
        this.id = id;
        this.title = title;
        this.stock = stock;
    }
}
