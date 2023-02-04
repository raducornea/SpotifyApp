/**
 * Spring Data repository -> To support interacting with orders in the database
 *
 * Then we can go in OrderController
 */

package com.example.payroll.persistence.repositories

import com.example.payroll.models.Order
import org.springframework.data.jpa.repository.JpaRepository


interface OrderRepository : JpaRepository<Order, Long>{

}