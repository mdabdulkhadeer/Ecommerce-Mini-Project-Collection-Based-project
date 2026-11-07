package com.kodewala.ecommerce.model;
/*
 * Customer represents one registered customer.
 */
public class Customer {

    private final int customerId;
    private String name;
    private String email;
    private String mobile;
    private String address;

    public Customer(int customerId, String name, String email,
                    String mobile, String address) {

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", name='" + name + '\'' + ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' + ", address='" + address + '\'' +'}';
    }
}