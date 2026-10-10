package com.example.assignment.book;

import java.util.List;
import com.example.assignment.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDetailResponse>> create(@RequestBody BookCreateRequest request) {
        BookDetailResponse response = bookService.create(
                request.getTitle(), request.getAuthor(), request.getPrice(), request.getStock());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<BookDetailResponse>>> createBulk(@RequestBody List<BookCreateRequest> items) {
        List<BookDetailResponse> responses = bookService.createBulk(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responses));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookSummaryResponse>>> findAll() {
        List<BookSummaryResponse> books = bookService.findAll();
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDetailResponse>> findById(@PathVariable Long id) {
        BookDetailResponse book = bookService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<BookDetailResponse>> updateStock(@PathVariable Long id,
                                                                       @RequestBody StockUpdateRequest request) {
        BookDetailResponse book = bookService.updateStock(id, request.getStock());
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<ApiResponse<BookDetailResponse>> updatePrice(@PathVariable Long id,
                                                                       @RequestBody PriceUpdateRequest request) {
        BookDetailResponse book = bookService.updatePrice(id, request.getPrice());
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAll() {
        bookService.deleteAll();
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}