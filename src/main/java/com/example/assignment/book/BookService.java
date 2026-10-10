package com.example.assignment.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    private void validate(String title, int price){
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("title must not be blank");
        }
        if(price<0){
            throw new IllegalArgumentException("price must not be negative");
        }
    }

    private BookDetailResponse toDetailResponse(Book book){
        return new BookDetailResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock()
        );
    }

    private BookSummaryResponse toSummaryResponse(Book book) {
        return new BookSummaryResponse(book.getId(), book.getTitle(), book.getStock());
    }

    private Book getBookOrThrow(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    public BookDetailResponse create(String title, String author, int price, int stock){
        validate(title, price);

        long id = idCounter.incrementAndGet();
        Book book=new Book(title, author, price, stock);
        book.setId(id);

        books.put(id, book);

        return toDetailResponse(book);
    }

    public List<BookDetailResponse> createBulk(List<BookCreateRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }

        List<BookDetailResponse> results = new ArrayList<>();
        for (BookCreateRequest item : items) {
            results.add(create(item.getTitle(), item.getAuthor(), item.getPrice(), item.getStock()));
        }
        return results;
    }

    public List<BookSummaryResponse> findAll() {
        List<BookSummaryResponse> result = new ArrayList<>();
        for (Book book : books.values()) {
            result.add(toSummaryResponse(book));
        }
        return result;
    }

    public BookDetailResponse findById(Long id) {
        return toDetailResponse(getBookOrThrow(id));
    }

    public BookDetailResponse updateStock(Long id, int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("stock must not be negative");
        }
        Book book = getBookOrThrow(id);
        book.setStock(stock);
        return toDetailResponse(book);
    }

    public BookDetailResponse updatePrice(Long id, int price) {
        if (price < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
        Book book = getBookOrThrow(id);
        book.setPrice(price);
        return toDetailResponse(book);
    }

    public void deleteById(Long id) {
        getBookOrThrow(id);
        books.remove(id);
    }

    public void deleteAll() {
        books.clear();
    }
}
