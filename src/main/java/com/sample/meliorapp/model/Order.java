package com.sample.meliorapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "quantity")
    @NotNull
    @Min(value = 1, message = "Quantity must be greater than 0")
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
