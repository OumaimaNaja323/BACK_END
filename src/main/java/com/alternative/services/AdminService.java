package com.alternative.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alternative.dto.UserReq;
import com.alternative.dto.request.ProductDTO;
import com.alternative.dto.request.UserDTO;
import com.alternative.entities.Product;
import com.alternative.entities.User;
import com.alternative.mapper.OrderMapper;
import com.alternative.mapper.ProductMapper;
import com.alternative.mapper.UserMapper;
import com.alternative.repositories.ProductRepository;
import com.alternative.repositories.UserRepository;



@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ProductMapper productMapper;
	
	
    public List<UserDTO> getClients() {

    	List<User> clients = userRepository.findByRole("USER");
    	return clients.stream().
    			map(c->userMapper.toDTOOnly(c))
    			.collect(Collectors.toList());
    }
    
    public List<UserDTO> getProducers() {
    	List<User> producers = userRepository.findByRole("PRODUCER");
    	return producers.stream().
    			map(c->userMapper.toDTOOnly(c))
    			.collect(Collectors.toList());
    }
    
    public List<UserDTO> getAll() {
    	return userRepository.findAll().stream().
		map(c->userMapper.toDTOOnly(c))
		.collect(Collectors.toList());
    }
  
	
   
    
    public UserDTO getUser(Long id) {
   
    	User  user = userRepository.findById(id).orElse(null);
    
    	return userMapper.toDTO(user);
    	
    }
    
    
    
    public List<ProductDTO> getProducts() {
    	return productRepository.findAll().stream()
    			.map(a->productMapper.toDTO(a))
    			.collect(Collectors.toList());
    }
    

    public UserDTO updateUser(@RequestBody UserDTO userDTO ) {
    	
       
       User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getName()));
        	
       user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        	
        User ourUser = userRepository.save(user);
        	
        
       return userMapper.toDTOOnly(ourUser);

    } 
    
    public void deleteUser(Long id) {
    	
    	userRepository.deleteById(id);
  
    	
    }
    
    
 
	
}
