package ca.gbc.productservice.controller;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Create a new product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {

        ProductResponse createdProduct = productService.createProduct(productRequest);

        // Set the headers (e.g., Location header if you want to indicate the URL of the
        //created resource)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/product/" + createdProduct.id());

        // Return the ResponseEntity with the 201 Created status, response body, and headers.

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdProduct);
    }


    // Get all products
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // Update a product
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String productId,
                                           @RequestBody ProductRequest productRequest) {
        String updateProductId = productService.updateProduct(productId, productRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/product/" + updateProductId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    // Delete a product
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


