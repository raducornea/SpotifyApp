package com.example.payroll.business.exceptions

class EmployeeNotFoundException(id: Long) : RuntimeException("Could not find employee $id")