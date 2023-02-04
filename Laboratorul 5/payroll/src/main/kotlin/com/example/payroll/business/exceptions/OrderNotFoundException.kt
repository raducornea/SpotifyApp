package com.example.payroll.business.exceptions

class OrderNotFoundException(id: Long) : RuntimeException("Could not find order $id")