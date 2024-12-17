package com.daungochuyen.service.admin.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.daungochuyen.entity.Product;
import com.daungochuyen.iofile.IOFile;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.admin.CSVService;

/**
 * Admin import list product service implements
 * @author ADMIN
 *
 */
@Service
public class CSVServiceImpl implements CSVService {
	
	@Autowired
	ProductRepository productRepository;

	/*
	 * Save list product into DB
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@Override
	public void save(MultipartFile file) {
		try {
			List<Product> products = IOFile.csvProducts(file.getInputStream());
			productRepository.saveAll(products);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
	
}
