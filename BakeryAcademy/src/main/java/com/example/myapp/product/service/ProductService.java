package com.example.myapp.product.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.product.dao.IProductRepository;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements IProductService {
	@Autowired
	IProductRepository productRepository;

	@Override
	@Transactional
	public void insertProduct(Product product) {
		try {
			productRepository.insertProduct(product);

			MultipartFile mfile = product.getImage();
			ProductImage productImage = new ProductImage();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productImage.setProductImageSize(String.valueOf(mfile.getSize()));
				productImage.setProductImageType(mfile.getContentType());
				productRepository.insertProductImage(productImage);
			}

			mfile = product.getImage2();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productImage.setProductImageSize(String.valueOf(mfile.getSize()));
				productImage.setProductImageType(mfile.getContentType());
				productRepository.insertProductImage(productImage);
			}

			mfile = product.getImage3();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productImage.setProductImageSize(String.valueOf(mfile.getSize()));
				productImage.setProductImageType(mfile.getContentType());
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
			ProductImage productImage = new ProductImage();
			productImage.setProductId(product.getProductId());
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productImage.setProductImageSize(String.valueOf(mfile.getSize()));
				productImage.setProductImageType(mfile.getContentType());
				productRepository.updateProductImage(productImage);
			}

			mfile = product.getImage2();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productImage.setProductImageSize(String.valueOf(mfile.getSize()));
				productImage.setProductImageType(mfile.getContentType());
				productRepository.updateProductImage(productImage);
			}

			mfile = product.getImage3();
			if (mfile != null && !mfile.isEmpty()) {
				productImage.setImageName(mfile.getOriginalFilename());
				productImage.setProductImage(mfile.getBytes());
				productImage.setProductImageSize(String.valueOf(mfile.getSize()));
				productImage.setProductImageType(mfile.getContentType());
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

	@Override
	public List<Product> selectSearchKeywordProduct(String keyword, int start, int end) {
		return productRepository.selectKeywordProductList(keyword, start, end);
	}

	@Override
	public List<Product> selectProductAtModal(int start, int end) {
		return productRepository.selectProductListAtModal(start, end);
	}

	@Override
	public int countProductList() {
		return productRepository.countProductList();
	}

	@Override
	public int countKeyWordProductList(String keyword) {
		return productRepository.countKeywordProductList(keyword);
	}

	@Override
	public List<Product> selectProductListInBaord(List<Integer> list) {
		return productRepository.selectProductListInBoard(list);
	}

	@Override
	public List<Product> selectProductListByCategory(int categoryId, int page) {
		int start = (page - 1) * 10 + 1;
		return productRepository.getProductListByCategory(categoryId, start, start + 9);
	}

	@Override
	public int selectTotalProductCountByCategory(int categoryId) {
		return productRepository.selectTotalProductCountByCategoryId(categoryId);
	}

	@Override
	public ProductImage getProductThumbnail(int productId) {
		return productRepository.getProductImageMinId(productId);
	}

	@Override
	public List<Integer> getProductImageList(int productId) {
		return productRepository.getProductImageList(productId);
	}

	@Override
	public ProductImage getProductImageByImageId(int productImageId) {
		return productRepository.getProductImageByImageId(productImageId);
	}
}
