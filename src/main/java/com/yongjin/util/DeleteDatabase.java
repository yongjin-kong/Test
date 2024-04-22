package com.yongjin.util;

import com.yongjin.common.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeleteDatabase extends JpaRepository<Customer, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.id = :id")
    int deleteById(@Param("id") String id);
}
