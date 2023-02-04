/**
 * Controllerele au business logic
 *
 * @RestController - datele returnate de metode ajung direct in raspunsul body-ului in loc sa randeze template-ul
 * to wrap repository with web layer, we use Spring MVC (easy -> RestController)
 * not much to code, but now we can focus more on infrastructure
 * EmployeeRepository e injectat in constructor de EmployeeController
 *
 * @RequestBody mapeaza HttpRequest body incat sa TRANSFERE un DO (Domain Object) (DTO?) (automatic serialization)
 */
package com.example.payroll.presentation.controllers

import com.example.payroll.models.Employee
import com.example.payroll.business.assemblers.EmployeeModelAssembler
import com.example.payroll.business.exceptions.EmployeeNotFoundException
import com.example.payroll.persistence.repositories.EmployeeRepository
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors


/** asa ii dai wrap, la propriu doar l-ai pus intr-o clasa */
@RestController
class EmployeeController(private val repository: EmployeeRepository, private val assembler: EmployeeModelAssembler) {

    /** obtin toti employees */
    @GetMapping("/employees")
    fun all(): CollectionModel<EntityModel<Employee>> {
        val employees = repository.findAll().stream()
            .map { employee: Employee -> assembler.toModel(employee) }
            .collect(Collectors.toList())
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController::class.java).all()).withSelfRel())
    }
    /** Not 100% RESTful */
//    @GetMapping("/employees")
//    fun all(): List<Employee> {
//        return repository.findAll()
//    }

    /** post cu un nou employee */
    /** POST that handles "old" and "new" client requests */
    @PostMapping("/employees")
    fun newEmployee(@RequestBody newEmployee: Employee): ResponseEntity<*> {
        val entityModel = assembler.toModel(repository.save(newEmployee))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }
    /** Not 100% RESTful */
//    @PostMapping("/employees")
//    fun newEmployee(@RequestBody newEmployee: Employee): Employee {
//        return repository.save(newEmployee)
//    }

    /** get unui employee dupa id (daca exista) */
    @GetMapping("/employees/{id}")
    fun one(@PathVariable id: Long): EntityModel<Employee> {
        val employee = repository.findById(id)
            .orElseThrow { EmployeeNotFoundException(id) }
        return assembler.toModel(employee)
    }
    /** Not 100% RESTful */
//    @GetMapping("/employees/{id}")
//    fun one(@PathVariable id: Long): Employee {
//        return repository.findById(id)
//            .orElseThrow { EmployeeNotFoundException(id) }
//    }



    /** replace cu un nou employee, sau creaza unul cu acel id */
    /** Handling a PUT for different clients */
    @PutMapping("/employees/{id}")
    fun replaceEmployee(@RequestBody newEmployee: Employee, @PathVariable id: Long): ResponseEntity<*> {
        val updatedEmployee = repository.findById(id)
            .map { employee: Employee ->
                employee.setName(newEmployee.getName())
                employee.setRole(newEmployee.getRole())
                repository.save(employee)
            }
            .orElseGet {
                newEmployee.setId(id)
                repository.save(newEmployee)
            }
        val entityModel = assembler.toModel(updatedEmployee)

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }
    /** Not 100% RESTful */
//    @PutMapping("/employees/{id}")
//    fun replaceEmployee(@RequestBody newEmployee: Employee, @PathVariable id: Long): Employee {
//        return repository.findById(id)
//            .map { employee: Employee ->
//                employee.setName(newEmployee.getName())
//                employee.setRole(newEmployee.getRole())
//                repository.save(employee)
//            }
//            .orElseGet {
//                newEmployee.setId(id)
//                repository.save(newEmployee)
//            }
//    }

    /** sterg employee dupa id */
    @DeleteMapping("/employees/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<*> {
        try {
            repository.deleteById(id)
            return ResponseEntity.noContent().build<Any>()
        }
        catch (ex: Exception){
            throw EmployeeNotFoundException(id)
        }
    }
    /** Not 100% RESTful */
//    @DeleteMapping("/employees/{id}")
//    fun deleteEmployee(@PathVariable id: Long) {
//        // da eroare daca nu exista cineva cu acel id, lol
//        try{
//            repository.deleteById(id)
//        }
//        catch (ex: Exception){
//            throw EmployeeNotFoundException(id)
//        }
//    }
}