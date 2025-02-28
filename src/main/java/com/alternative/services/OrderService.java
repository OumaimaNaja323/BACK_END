package com.alternative.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alternative.dto.request.OrderDTO;
import com.alternative.dto.request.OrderItemDTO;
import com.alternative.entities.Order;
import com.alternative.entities.OrderItem;
import com.alternative.entities.Product;
import com.alternative.entities.User;
import com.alternative.mapper.OrderItemMapper;
import com.alternative.mapper.OrderMapper;
import com.alternative.repositories.OrderItemRepository;
import com.alternative.repositories.OrderRepository;
import com.alternative.repositories.ProductRepository;
import com.alternative.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
  @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO, Long clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Order order = new Order();
        order.setClient(client);
        order.setStatus(orderDTO.getStatus());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setOrderDate(new Date()); // Définir la date actuelle de la commande

        List<OrderItem> orderItems = new ArrayList<>();

        if (orderDTO.getOrderItems() != null && !orderDTO.getOrderItems().isEmpty()) {
            for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
                Product product = productRepository.findById(itemDTO.getProduct().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                if (product.getQuantityInStock() < itemDTO.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }

                // Déduire la quantité en stock
                product.setQuantityInStock(product.getQuantityInStock() - itemDTO.getQuantity());
                productRepository.save(product);

                // Création de l'OrderItem
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            }
        }

        // Enregistrer la commande
        order = orderRepository.save(order);

        // Associer et enregistrer les OrderItems
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        orderItemRepository.saveAll(orderItems);

        return orderMapper.toDTO(order);
    }


    public List<OrderDTO> getClientOrders(Long clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        return orderRepository.findByClient(client).stream()
                .map(orderMapper::toDTOonlyOrderItems)
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public OrderDTO updateOrderStatus(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(orderDTO.getStatus());
        return orderMapper.toDTOonlyOrderItemsAndClient(orderRepository.save(order));
    }
    
    public List<OrderItemDTO> getOrderItemsByProducer(Long producerId) {
        User producer = userRepository.findById(producerId)
                .orElseThrow(() -> new EntityNotFoundException("Producer not found"));

        // Trouver les produits associés au producteur
        List<Product> products = productRepository.findByUser(producer);

        // Trouver les OrderItems associés à ces produits
        List<OrderItem> orderItems = orderItemRepository.findByProductIn(products);

        // Convertir les OrderItems en OrderItemDTO
        return orderItems.stream()
                .map(orderItemMapper::toDTO)  // Utilisation de la méthode toDTO
                .collect(Collectors.toList());
    }


}
