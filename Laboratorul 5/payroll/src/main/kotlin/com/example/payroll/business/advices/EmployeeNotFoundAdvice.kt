/**
 * Aici se randeaza pe pagina erorile de tip not found
 * IT RENDERS: HTTP 404
 *
 * @ResponseBody - advice is rendered straight into the response body
 * @ExceptionHandler configures the advice to only respond if an EmployeeNotFoundException is thrown
 * @ResponseStatus says to issue an HttpStatus.NOT_FOUND, i.e. an HTTP 404.
 */

package com.example.payroll.business.advices

import com.example.payroll.business.exceptions.EmployeeNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class EmployeeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun employeeNotFoundHandler(ex: EmployeeNotFoundException): String? {
        return ex.message
    }
}