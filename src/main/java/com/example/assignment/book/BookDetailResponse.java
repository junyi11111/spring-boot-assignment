package com.example.assignment.book;

public class BookDetailResponse {
    private  Long id;
    private String title;
    private String author;
    private int price;
    private int stock;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public BookDetailResponse(Long id, String title, String author, int price, int stock){
        this.id= id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }
}
