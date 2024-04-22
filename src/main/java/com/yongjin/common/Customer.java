package com.yongjin.common;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    String customerId;
    @Column(name = "phone_num")
    String phoneNo;
    @Column(name = "email")
    String email;
    @Column(name = "address")
    String address;
    @OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL)
            @JoinColumn(name = "account_customer_id", referencedColumnName = "id")
    private List<Account> account;

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
