package com.yongjin.util;

import com.yongjin.common.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InsertDatabase {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertNewCustomer(Customer customer) {
        entityManager.createNativeQuery("INSERT INTO customer (id, phone_num, email, address) VALUES (?,?,?,?)")
                .setParameter(1, customer.getCustomerId())
                .setParameter(2, customer.getPhoneNo())
                .setParameter(3, customer.getEmail())
                .setParameter(4, customer.getAddress())
                .executeUpdate();
    }
}
