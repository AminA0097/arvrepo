package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.AAProductService.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductInterface productInterface;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {

        List<Map<String, Object>> products = new ArrayList<>();

        Map<String, Object> product1 = new HashMap<>();
        product1.put("id", "1");
        product1.put("name", "MacBook Pro M3");
        product1.put("title", "Apple MacBook Pro 14-inch");
        product1.put("price", 2499.99);
        product1.put("isNew", true);
        product1.put("isBestSeller", true);
        product1.put("discount", 200);
        product1.put("stock", 12);
        product1.put("categoryId", "electronics");
        product1.put("views", 1200);
        product1.put("rank", 1);
        product1.put("createdAt", "2026-05-01T10:00:00Z");

        List<Map<String, Object>> tags1 = new ArrayList<>();
        tags1.add(Map.of("id", "t1", "name", "Apple"));
        tags1.add(Map.of("id", "t2", "name", "Laptop"));
        product1.put("tags", tags1);

        List<Map<String, Object>> images1 = new ArrayList<>();
        images1.add(Map.of("id", "g1", "url", "https://picsum.photos/600/400?1"));
        images1.add(Map.of("id", "g2", "url", "https://picsum.photos/600/400?2"));
        product1.put("images", images1);

        products.add(product1);

        // Product 2
        Map<String, Object> product2 = new HashMap<>();
        product2.put("id", "2");
        product2.put("name", "iPhone 15 Pro");
        product2.put("title", "Apple Smartphone");
        product2.put("price", 1299.99);
        product2.put("isNew", true);
        product2.put("isBestSeller", false);
        product2.put("discount", 100);
        product2.put("stock", 25);
        product2.put("categoryId", "electronics");
        product2.put("views", 3400);
        product2.put("rank", 2);
        product2.put("createdAt", "2026-05-01T10:10:00Z");

        product2.put("tags", List.of(
                Map.of("id", "t1", "name", "Apple"),
                Map.of("id", "t3", "name", "Phone")
        ));

        product2.put("images", List.of(
                Map.of("id", "g3", "url", "https://picsum.photos/600/400?3")
        ));

        products.add(product2);
        products.add(product2);
        products.add(product2);
        products.add(product2);
        products.add(product1);
        products.add(product1);
        products.add(product1);
        products.add(product1);
        products.add(product2);
        products.add(product2);
        products.add(product2);
        products.add(product2);
        products.add(product1);
        products.add(product1);
        products.add(product1);
        products.add(product1);

        return ResponseEntity.ok(products);
    }
    @RequestMapping("/new-arrivals")
    public ResponseEntity<?> newArrivals() {
        return null;
    }
}