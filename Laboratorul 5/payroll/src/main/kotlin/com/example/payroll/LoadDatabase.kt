/**
 * @Configuration
 * @Bean - TOATE bean-urile sunt rulate cand application context e loaded
 *
 * CommandLineRunner - cere o copie la EmployeeRepository creata
 * apoi creaza 2 entitati pe care le stocheaza
 */

package com.example.payroll

import com.example.payroll.models.Employee
import com.example.payroll.persistence.repositories.EmployeeRepository
import com.example.payroll.models.Order
import com.example.payroll.persistence.repositories.OrderRepository
import com.example.payroll.models.Status
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadDatabase {
    private val log: Logger = LoggerFactory.getLogger(LoadDatabase::class.java)

//    @Bean
//    fun initDatabase(repository: EmployeeRepository): CommandLineRunner? {
//        return CommandLineRunner {
//            log.info("Preloading " + repository.save(Employee("Bilbo", "Baggins", "burglar")))
//            log.info("Preloading " + repository.save(Employee("Frodo", "Baggins", "thief")))
//        }
//    }

    @Bean
    fun initDatabase(employeeRepository: EmployeeRepository, orderRepository: OrderRepository): CommandLineRunner? {
        return CommandLineRunner {
            employeeRepository.save(Employee("Bilbo", "Baggins", "burglar"))
            employeeRepository.save(Employee("Frodo", "Baggins", "thief"))
            employeeRepository.findAll().forEach { employee: Employee -> log.info("Preloaded $employee") }

            orderRepository.save(Order("MacBook Pro", Status.COMPLETED))
            orderRepository.save(Order("iPhone", Status.IN_PROGRESS))
            orderRepository.findAll().forEach { order: Order -> log.info("Preloaded $order") }
        }
    }
}