package com.hadjshell.ecom.controller;

import com.hadjshell.ecom.model.Product;
import com.hadjshell.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@SuppressWarnings("unused")
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    public ProductController() {
    }

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return service.findAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable int id) {
        Product product = service.findProductById(id);

        return product != null ? new ResponseEntity<>(product, HttpStatus.OK) :
                                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> findProductImageById(@PathVariable int id) {
        Product product = service.findProductById(id);

        return product != null ? new ResponseEntity<>(product.getImageData(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.findProductByKeyword(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart(name = "imageFile") MultipartFile image) {
        try {
            Product savedProduct = service.addOrUpdateProduct(product, image);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart(name = "imageFile") MultipartFile image) {
        try {
            service.addOrUpdateProduct(product, image);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = service.findProductById(id);
        if (product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
