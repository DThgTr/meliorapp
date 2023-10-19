package com.sample.meliorapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

import java.util.*;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column(name = "first_name")
    @NotEmpty
    protected String firstName;

    @Column(name = "last_name")
    @NotEmpty
    protected String lastName;

    @Column(name = "address")
    @NotEmpty
    private String address;

    @Column(name = "city")
    @NotEmpty
    private String city;

    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Order> orders;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //----------------------------ORDER-RELATED----------------------------
    private Set<Order> getOrdersInternal() {
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        return this.orders;
    }
    public List<Order> getOrders() {
        List<Order> ordersList = new ArrayList<>(getOrdersInternal());
        return Collections.unmodifiableList(ordersList);
    }

    public void setOrders(List<Order> orders) {
        this.orders = new HashSet<>(orders);
    }

    public void addOrder(Order order) {
        order.setCustomer(this);
        this.orders.add(order);
    }
}
