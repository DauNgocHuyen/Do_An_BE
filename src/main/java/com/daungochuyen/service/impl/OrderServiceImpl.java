package com.daungochuyen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daungochuyen.dto.OrderDetailDTO;
import com.daungochuyen.dto.ProductDTO;
import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Order;
import com.daungochuyen.entity.Product;
import com.daungochuyen.entity.ProductOrder;
import com.daungochuyen.repository.CategoryRepository;
import com.daungochuyen.repository.OrderRepository;
import com.daungochuyen.repository.ProductOrderRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderDetailRepository;
	
	@Autowired
	private ProductOrderRepository productOrderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Get order detail by id
	 */
	@Override
	public OrderDetailDTO getOrderDetails(Long id) {
		OrderDetailDTO result = new OrderDetailDTO();
		
		// Information
		Order orderDetail = orderDetailRepository.findById(id).get();
		result.setCity(orderDetail.getCity());
		result.setCode(orderDetail.getCode());
		result.setCompany(orderDetail.getCompany());
		result.setDetailAddress(orderDetail.getDetailAddress());
		result.setDistrict(orderDetail.getDistrict());
		result.setEmail(orderDetail.getEmail());
		result.setFullName(orderDetail.getFullName());
		result.setId(orderDetail.getId());
		result.setOrderDate(orderDetail.getOrderDate());
		result.setPhone(orderDetail.getPhone());
		result.setStatus(orderDetail.getStatus());
		result.setTotal(orderDetail.getTotal());
		result.setZip(orderDetail.getZip());
		
		// List product
		List<ProductDTO> productsInOrder = new ArrayList<>();
		List<ProductOrder> productOrders = productOrderRepository.findAllByOrderId(id);
		List<Product> products = productRepository.findAllById(productOrders.stream().map(ProductOrder::getProductId).toList());
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			
			productOrders.forEach(productOrder -> {
				if(product.getId() == productOrder.getProductId()) {
					dto.setQuantitySelected(productOrder.getQuantitySelected());
				}
			});
			
			productsInOrder.add(dto);
		}
		
		result.setProducts(productsInOrder);
		
		return result;
	}
	
	/**
	 * Build productDTO
	 * @param product product data
	 * @param category category data
	 * @return productDTO
	 */
	private ProductDTO buildProductDto(Product product, Category category) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPhotos(product.getPhotos());
		productDTO.setPrice(product.getPrice());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setDiscount(product.getDiscount());
		productDTO.setCategory(category);
		
		return productDTO;
	}

}
