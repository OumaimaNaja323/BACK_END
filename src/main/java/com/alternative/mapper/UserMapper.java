package com.alternative.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alternative.dto.request.UserDTO;
import com.alternative.entities.User;

@Component
public class UserMapper {
	@Autowired
    private   ProductMapper productMapper ;
	@Autowired
	@Lazy
    private   OrderMapper orderMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    
	public UserDTO toDTOOnly(User user) {
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setCity(user.getCity());
        dto.setRole(user.getRole());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
	}
	
    public UserDTO toDTO(User user) {
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setCity(user.getCity());
        dto.setRole(user.getRole());
        dto.setPhoneNumber(user.getPhoneNumber());
        
        // Handle products
        if (user.getProducts() != null) {
            dto.setProducts(user.getProducts().stream()
                .map(product -> productMapper.toDTOWithoutCategory(product))
                .collect(Collectors.toList()));
        }
        
        // Handle orders
        if (user.getOrders() != null) {
            dto.setOrders(user.getOrders().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public UserDTO toDTOWithoutProducts(User user) {
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setCity(user.getCity());
        dto.setRole(user.getRole());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }
    
    public UserDTO toDTOWithoutOrders(User user) {
        if (user == null) return null;
        
        UserDTO dto = toDTOWithoutProducts(user);
        if (user.getProducts() != null) {
            dto.setProducts(user.getProducts().stream()
                .map(product -> productMapper.toDTOWithoutCategory(product))
                .collect(Collectors.toList()));
        }
        return dto;
    }
    
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setCity(dto.getCity());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setPhoneNumber(dto.getPhoneNumber());
        
        if (dto.getProducts() != null) {
            user.setProducts(dto.getProducts().stream()
                .map(productDTO -> productMapper.toEntityWithoutCategory(productDTO))
                .collect(Collectors.toList()));
        }
        
        if (dto.getOrders() != null) {
            user.setOrders(dto.getOrders().stream()
                .map(orderMapper::toEntity)
                .collect(Collectors.toList()));
        }
        
        return user;
    }
    
    public User toEntityWithoutProducts(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setCity(userDTO.getCity());
        user.setRole(userDTO.getRole());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        // Ne pas hacher le mot de passe si il est null
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return user;
    }
    
    public User toEntityWithoutOrders(UserDTO dto) {
        if (dto == null) return null;
        
        User user = toEntityWithoutProducts(dto);
        if (dto.getProducts() != null) {
            user.setProducts(dto.getProducts().stream()
                .map(productDTO -> productMapper.toEntityWithoutCategory(productDTO))
                .collect(Collectors.toList()));
        }
        return user;
    }
}