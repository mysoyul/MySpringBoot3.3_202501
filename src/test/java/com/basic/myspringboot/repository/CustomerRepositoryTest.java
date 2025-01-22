package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @Test @Rollback(value = false)
    void insert_select() {
        //Entity 객체생성
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링");

        //CrudRepository 의 save() 호출
        Customer savedCustomer = repository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerName()).isEqualTo("스프링");
        assertEquals("A001",savedCustomer.getCustomerId());

        //PK로 조회하기
        Optional<Customer> byId = repository.findById(1L);
        if(byId.isPresent()){
            Customer existCustomer = byId.get();
            assertThat(existCustomer.getCustomerName()).isEqualTo("스프링");
        }
        //Optional orElseGet
        Customer notFoundCust =
                repository.findByCustomerId("B001").orElseGet(() -> new Customer());
        assertThat(notFoundCust.getCustomerId()).isNullOrEmpty();

        //Optional orElseThrow
        Customer customer1 = repository.findByCustomerId("A001")
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        assertThat(customer1.getCustomerName()).isEqualTo("스프링");

        //setter 메서드 호출 - update query가 실행되는 것임
        customer1.setCustomerName("스프링즈");
        assertThat(customer1.getCustomerName()).isEqualTo("스프링즈");        
    }
}