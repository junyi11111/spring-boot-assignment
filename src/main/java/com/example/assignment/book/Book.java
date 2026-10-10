package com.example.assignment.book;

public class Book {
    private  Long id;
    private String title;
    private String author;
    private int price;
    private  int stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Book(String  title, String author, int price, int stock){
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }
}
