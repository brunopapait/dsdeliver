package com.devsuperior.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() throws Exception {
		List<Order> orders = orderRepository.findAllOrdersWithProducts();

		if (orders.isEmpty()) {
			throw new Exception("A lista de pedidos está vazia.");
		}
		return orders.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO insert(OrderDTO orderDto) {

		Order order = new Order(null, orderDto.getAddress(), orderDto.getLatitude(), orderDto.getLongitude(),
				Instant.now(), OrderStatus.PENDING);
		
		for (ProductDTO product : orderDto.getProducts()) {
			Product product2 = productRepository.getOne(product.getId());
			order.getProducts().add(product2);
		}

		return new OrderDTO(orderRepository.save(order));
	}

}
