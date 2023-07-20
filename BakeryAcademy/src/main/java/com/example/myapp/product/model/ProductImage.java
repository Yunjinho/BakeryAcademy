package com.example.myapp.product.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductImage {
	private int productImageId;
	private int productId;
	private byte[] productImage;
	private String productImageName;
	private String productImageSize;
	private String productImageType;
	
	@Override
	public String toString() {
		return "ProductImage [productImageId=" + productImageId + ", productId=" + productId + ", productImageName="
				+ productImageName + ", productImageSize=" + productImageSize + ", productImageType=" + productImageType
				+ "]";
	}
}
