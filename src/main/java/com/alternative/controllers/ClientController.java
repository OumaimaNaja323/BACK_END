package com.alternative.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.alternative.dto.request.CategoryDTO;
import com.alternative.dto.request.OrderDTO;
import com.alternative.dto.request.PaimentDTO;
import com.alternative.dto.request.ProductDTO;
import com.alternative.dto.request.ReviewDTO;
import com.alternative.services.CategoryService;

import com.alternative.services.OrderService;
import com.alternative.services.PaymentService;
import com.alternative.services.ProductService;
import com.alternative.services.ReviewService;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ReviewService reviewService;

    // Récupérer tous les produits
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Récupérer les produits d'une catégorie
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    // Récupérer toutes les catégories
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Créer une commande pour un client donné
    @PostMapping("/orders/{clientId}")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long clientId) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Récupérer les commandes d'un client donné
    @GetMapping("/orders/{clientId}")
    public ResponseEntity<List<OrderDTO>> getMyOrders(@PathVariable Long clientId) {
        List<OrderDTO> orders = orderService.getClientOrders(clientId);
        return ResponseEntity.ok(orders);
    }

    // Effectuer un paiement
    @PostMapping("/payment")
    public ResponseEntity<PaimentDTO> processPayment(@RequestBody PaimentDTO paimentDTO) {
        PaimentDTO processedPayment = paymentService.payer(paimentDTO);
        return ResponseEntity.ok(processedPayment);
    }

    // Supprimer une commande par son ID
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // Ajouter un commentaire
    @PostMapping("/review")
    public ResponseEntity<ReviewDTO> addComment(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.ajouterCommentaire(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    // Supprimer un commentaire
    @DeleteMapping("/review")
    public ResponseEntity<Void> deleteComment(@RequestBody ReviewDTO reviewDTO) {
        reviewService.supprimmerCommantaire(reviewDTO);
        return ResponseEntity.noContent().build();
    }
}
