/**
 * @Table - JPA table annotation -> CUSTOMER_ORDER table (ORDER NOT A VALID NAME OHOHOHOH)
 * description + status fields
 */

package com.example.payroll.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "CUSTOMER_ORDER")
class Order() {
    @Id
    @GeneratedValue
    var id: Long = 0
    var description: String? = null
    private var status: Status? = null

    constructor(description: String?, status: Status?) : this() {
        this.description = description
        this.status = status
    }

    fun getStatus(): Status? {
        return status
    }

    fun setStatus(status: Status?) {
        this.status = status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Order) return false
        return Objects.equals(id, other.id) && Objects.equals(description, other.description) && status === other.status
    }

    override fun hashCode(): Int {
        return Objects.hash(id, description, status)
    }

    override fun toString(): String {
        return "Order{id=$id, description='$description', status=$status}"
    }
}