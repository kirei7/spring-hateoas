
package org.springframework.hateoas.examples;

class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(Long id) {
		super("Order " + id + " not found!");
	}
}
