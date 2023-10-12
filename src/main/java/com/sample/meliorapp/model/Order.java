package com.sample.meliorapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "quantity")
    @NotEmpty
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "fragrance_type_id")
    private FragranceType fragranceType;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public FragranceType getFragranceType() {
        return fragranceType;
    }

    public void setFragranceType(FragranceType fragranceType) {
        this.fragranceType = fragranceType;
    }
}
