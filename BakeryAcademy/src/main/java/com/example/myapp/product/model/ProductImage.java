package com.example.myapp.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
	private int productImageId;
	private int productId;
	private String directoryName;
	private String imageName;
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
