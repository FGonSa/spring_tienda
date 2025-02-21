package com.f5antos.tienda.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String field;
	String fieldName;
	Long fieldId;
	
	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String resourceName, String field, String fieldName, Long fieldId) {
		super(String.format("%s not found with %s: %s", resourceName, field, fieldName, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
	}
	
	public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
		super(String.format("%s not found with %s: %s", resourceName, field, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}
	

}
