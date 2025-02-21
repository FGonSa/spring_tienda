package com.f5antos.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	
	@NotNull
	@NotBlank(message = "No se puede dejar el nombre del producto en blanco.")
	private String productName;
	private String description;
	private String image;
	
	@NotNull
	@Min(1)
	private Integer quantity;
	
	@NotNull
	@Min(0)
	private double price; //precio que tiene siempre
	
	private double discount;
	private double specialPrice; //precio en oferta
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
}
