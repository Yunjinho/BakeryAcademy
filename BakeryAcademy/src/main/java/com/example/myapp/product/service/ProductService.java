package com.example.myapp.product.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.product.dao.IProductRepository;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements IProductService{
	@Autowired
	IProductRepository productRepository;
	
	@Override
	@Transactional
	public void insertProduct(Product product) {
		try {
			productRepository.insertProduct(product);
			
			MultipartFile mfile = product.getImage();
			ProductImage productImage=new ProductImage();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productRepository.insertProductImage(productImage);
			}
			
			mfile = product.getImage2();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productRepository.insertProductImage(productImage);
			}
			
			mfile = product.getImage3();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productRepository.insertProductImage(productImage);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void updateProduct(Product product) {
		try {
			productRepository.updateProduct(product);
			productRepository.deleteProductImage(product.getProductId());
			
			MultipartFile mfile = product.getImage();
			ProductImage productImage=new ProductImage();
			productImage.setProductId(product.getProductId());
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productRepository.updateProductImage(productImage);
			}
			
			mfile = product.getImage2();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productRepository.updateProductImage(productImage);
			}
			
			mfile = product.getImage3();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productRepository.updateProductImage(productImage);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Product selectProduct(int productId) {
		return productRepository.selectProduct(productId);
	}
}
