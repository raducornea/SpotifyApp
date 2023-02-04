/**
 * @SpringBootApplication - meta @ that pulls component scanning + autoconfig + property support
 * launches ===> servlet container to serve our service
 * entry point of application + psvm
 */

package com.example.payroll

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PayrollApplication

/** Tutorial: https://spring.io/guides/tutorials/rest/ */
fun main(args: Array<String>) {
	runApplication<PayrollApplication>(*args)
}
