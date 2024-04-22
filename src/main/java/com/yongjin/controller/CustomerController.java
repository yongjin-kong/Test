package com.yongjin.controller;

import com.yongjin.common.Customer;
import com.yongjin.util.DeleteDatabase;
import com.yongjin.util.InsertDatabase;
import com.yongjin.util.SearchDatabase;
import com.yongjin.util.UpdateDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/example")
public class CustomerController {
    @Autowired
    SearchDatabase searchDatabase;
    @Autowired
    UpdateDatabase updateDatabase;
    @Autowired
    DeleteDatabase deleteDatabase;
    @Autowired
    InsertDatabase insertDatabase;

    @GetMapping("/customer/getalldetails")
    public ResponseEntity<Map<String, Object>> getAllDetails(
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<Customer> customer = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<Customer> customerPage;

            if(id == null)
                customerPage = searchDatabase.findAll(paging);
            else
                customerPage = searchDatabase.findByCustomerId(id, paging);

            customer = customerPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("customer", customer);
            response.put("currentPage", customerPage.getNumber());
            response.put("totalItems", customerPage.getTotalElements());
            response.put("totalPages", customerPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/getdetailById")
    public ResponseEntity<Map<String, Object>> getDetailById(
            @RequestParam(required = true) String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<Customer> customer = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<Customer> customerPage;

            customerPage = searchDatabase.findByCustomerId(id, paging);

            customer = customerPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("customer", customer);
            response.put("currentPage", customerPage.getNumber());
            response.put("totalItems", customerPage.getTotalElements());
            response.put("totalPages", customerPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/customer/update")
    public ResponseEntity<Map<String, Object>> updateEmail(
            @RequestParam String id,
            @RequestParam String email) {

        var success = updateDatabase.updateEmail(email, id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/customer/delete")
    public ResponseEntity<Map<String, Object>> deleteById(
            @RequestParam String id) {

        var success = deleteDatabase.deleteById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/customer/create")
    public ResponseEntity<Map<String, Object>> createNewCustomer(
            @RequestBody Customer customer) {

        insertDatabase.insertNewCustomer(customer);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
