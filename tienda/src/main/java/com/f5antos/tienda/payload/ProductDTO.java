package com.f5antos.tienda.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private Long productID;
	private String productName;
	private String description;
	private String image;
	private Integer quantity;
	private double price;
	private double discount;
	private double specialPrice;
	private Long categoryID;
	private String categoryName;
}
