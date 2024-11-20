package com.daungochuyen.dto;

import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
	private Long id;
	
	private String name;
	
	private Long price;
	
	private Long quantity;
	
	private long quantitySelected;
	
	private String photos;
	
	private Category category;
	
	private int discount;
	
	private int deleted;
	
	public ProductDTO(Product product, Category category) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		this.photos = product.getPhotos();
		this.discount = product.getDiscount();
		this.deleted = product.getDeleted();
		this.category = category;
	}

}
