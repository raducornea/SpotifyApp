/**
 * Se converteste Employee intr-un Model Based Object (EntityModel<Employee>)
 * @Component - assembler-ul e creat automat cand aplicatia porneste
 *
 * Clasa EmployeeModelAssembler e folosita pentru crea link-urile
 */

package com.example.payroll.business.assemblers

import com.example.payroll.models.Employee
import com.example.payroll.presentation.controllers.EmployeeController
import org.springframework.hateoas.EntityModel

import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component


@Component
class EmployeeModelAssembler : RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    override fun toModel(employee: Employee): EntityModel<Employee> {
        return EntityModel.of(
            employee,
            linkTo(methodOn(EmployeeController::class.java).one(employee.getId())).withSelfRel(),
            linkTo(methodOn(EmployeeController::class.java).all()).withRel("employees")
        )
    }
}