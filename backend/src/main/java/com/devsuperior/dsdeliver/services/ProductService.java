package com.devsuperior.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() throws Exception {
		List<Product> products = productRepository.findAllByOrderByNameAsc();

		if (products.isEmpty()) {
			throw new Exception("A lista de produtos estão vazias.");
		}

		return products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());

	}
}
