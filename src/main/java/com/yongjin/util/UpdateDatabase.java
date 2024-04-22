package com.yongjin.util;

import com.yongjin.common.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UpdateDatabase extends JpaRepository<Customer, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.email = :email WHERE c.id = :id")
    int updateEmail(@Param("email") String email, @Param("id") String id);
}
