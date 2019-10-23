package org.springframework.hateoas.examples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Greg Turnquist
 */
@Entity
@Table(name = "ORDERS")
class Order {

    @Id @GeneratedValue
    private Long id;

    private OrderStatus orderStatus;

    private String description;

    private Order() {
        this.id = null;
        this.orderStatus = OrderStatus.BEING_CREATED;
        this.description = "";
    }
    
    public Order(String description) {
        this();
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
            orderStatus == order.orderStatus &&
            Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderStatus, description);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderStatus=" + orderStatus +
            ", description='" + description + '\'' +
            '}';
    }
}
