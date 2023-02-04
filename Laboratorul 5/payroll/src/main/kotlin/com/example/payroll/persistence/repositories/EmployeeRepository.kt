/**
 * JpaRepository - class to extend => CRUD operations
 *
 * JpaRepository<Employee?, Long?>
 * Domain type: Employee
 * Id type: Long
 *
 *
 */

package com.example.payroll.persistence.repositories

import com.example.payroll.models.Employee
import org.springframework.data.jpa.repository.JpaRepository


interface EmployeeRepository : JpaRepository<Employee, Long>{

}