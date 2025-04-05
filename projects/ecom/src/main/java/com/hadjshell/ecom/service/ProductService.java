package com.hadjshell.ecom.service;

import com.hadjshell.ecom.model.Product;
import com.hadjshell.ecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private ProductRepo repo;

    @Autowired
    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public ProductService() {
    }

    public List<Product> findAllProducts() {
        return repo.findAll();
    }

    public Product findProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> findProductByKeyword(String keyword) {
        return repo.findByKeyword(keyword);
    }
}
