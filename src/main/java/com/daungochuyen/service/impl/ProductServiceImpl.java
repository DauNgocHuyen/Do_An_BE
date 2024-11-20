package com.daungochuyen.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.daungochuyen.dto.ProductDTO;
import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Product;
import com.daungochuyen.mapper.ProductMapper;
import com.daungochuyen.payload.ProductCriteria;
import com.daungochuyen.repository.CategoryRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.ProductService;
import com.daungochuyen.utils.Constants;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * Get all products
	 */
	@Override
	public Page<ProductDTO> getAll(ProductCriteria criteria, Pageable pageable) {
		Specification<Product> specification = createProductCriteria(criteria);
		Page<Product> page = productRepository.findAll(specification, pageable);
		
		return page.map(product -> ProductMapper.toDTO(product, categoryRepository));
	}
	
	/**
	 * Build criteria
	 * @param criteria
	 * @return
	 */
	private Specification<Product> createProductCriteria(ProductCriteria criteria) {
		Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (criteria.getSearch() != null) {
                predicates.add(cb.like(root.get("name"), "%" + criteria.getSearch() + "%"));
            }
            
            if(criteria.getCategoryId() != null) {
            	predicates.add(cb.equal(root.get("categoryId"), criteria.getCategoryId()));
            }
            
            if(criteria.getIsDiscount() != null) {
            	predicates.add(cb.greaterThan(root.get("discount"), 0));
            }
            
            predicates.add(cb.notEqual(root.get("deleted"), 1));
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return spec;
	}
	
	/**
	 * Get all product discounts
	 */
	@Override
	public List<ProductDTO> getAllProductDiscount() {
		List<ProductDTO> result = new ArrayList<>();
		List<Product> products = productRepository.findAll()
				.stream().filter(product -> product.getDeleted() != Constants.DELETED).toList();
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			result.add(dto);
		}
		
		result = result.stream().filter(product -> product.getDiscount() != 0).toList();
		
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
		productDTO.setDeleted(product.getDeleted());
		productDTO.setCategory(category);
		return productDTO;
	}


	/**
	 * Filter by category
	 */
	@Override
	public List<ProductDTO> filterByCategory(Long id) {
		List<Product> products = productRepository.findAllByCategoryIds(id)
				.stream().filter(product -> product.getDeleted() != Constants.DELETED).toList();
		List<ProductDTO> result = new ArrayList<>();
		
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			result.add(dto);
		}
		
		return result;
	}


	/**
	 * Search by name
	 */
	@Override
	public List<ProductDTO> searchByName(String name) {
		List<Product> products = productRepository.findByName(name)
				.stream().filter(product -> product.getDeleted() != Constants.DELETED).toList();
		List<ProductDTO> result = new ArrayList<>();
		
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			result.add(dto);
		}
		return result;
	}


	/**
	 * Update product info
	 */
	@Override
	public void updateProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
		product.setCategoryId(productDTO.getCategory().getId());
		product.setName(productDTO.getName());
		product.setPhotos(product.getPhotos());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getQuantity());
		product.setDiscount(productDTO.getDiscount());
		
		productRepository.save(product);
	}

	/**
	 * Delete product
	 */
	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
		product.setDeleted(Constants.DELETED);
		productRepository.save(product);
	}

	/**
	 * Filter discount by category
	 */
	@Override
	public List<ProductDTO> filterDiscountByCategory(Long id) {
		List<Product> products = productRepository.findAllByCategoryIds(id)
				.stream().filter(product -> product.getDeleted() != Constants.DELETED).toList();
		List<ProductDTO> result = new ArrayList<>();
		
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			result.add(dto);
		}
		
		result = result.stream().filter(product -> product.getDiscount() != 0).toList();
		
		return result;
	}

	/**
	 * Search product discount by name
	 */
	@Override
	public List<ProductDTO> searchDiscountByName(String name) {
		List<Product> products = productRepository.findByName(name)
				.stream().filter(product -> product.getDeleted() != Constants.DELETED).toList();
		List<ProductDTO> result = new ArrayList<>();
		
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			result.add(dto);
		}
		
		result = result.stream().filter(product -> product.getDiscount() != 0).toList();
		
		return result;
	}

	/**
	 * Get new product
	 */
	@Override
	public List<ProductDTO> findNewProducts() {
		List<ProductDTO> result = new ArrayList<>();
		List<Product> products = productRepository.findNewProducts()
				.stream().filter(product -> product.getDeleted() != Constants.DELETED).toList();
		for(Product product : products) {
			Category category = categoryRepository.findById(product.getCategoryId()).get();
			ProductDTO dto = buildProductDto(product, category);
			result.add(dto);
		}
		
		return result;
	}
	

}
