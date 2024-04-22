package com.yongjin.util;

import com.yongjin.common.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchDatabase extends JpaRepository<Customer, Long> {
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findByCustomerId(String id, Pageable pageable);
}
