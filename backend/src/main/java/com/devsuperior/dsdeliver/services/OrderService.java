package com.devsuperior.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() throws Exception {
		List<Order> orders = orderRepository.findAllOrdersWithProducts();

		if (orders.isEmpty()) {
			throw new Exception("A lista de pedidos está vazia.");
		}

		return orders.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());

	}

}
