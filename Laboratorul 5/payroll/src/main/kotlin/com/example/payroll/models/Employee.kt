/**
 * Entity -> DO (DTO - pare a fi asta, DAO - contine logica pentru salvare, retrieving si update la baza de date)
 *
 * JPA = Java Persistance API, pentru management al datelor relationale in aplicatii Java EE
 *
 * @Entity - JPA @ to make Employee object ready for storage in JPA datastore - assumed Employee table exists
 * id, name, role - attributes of Employee DOMAIN OBJECT
 * id - more annotations => primary key + automatically poplulated by JPA provider
 *
 * @Id - object's id
 * @GeneratedValue - the id should be generated automatically
 */

package com.example.payroll.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class Employee() {
    @Id
    @GeneratedValue
    private var id: Long = 0
//    private var name: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var role: String? = null

//    constructor(name: String?, role: String?) : this() {
    constructor(firstName: String?, lastName: String?, role: String?) : this() {
//        this.name = name
        this.firstName = firstName
        this.lastName = lastName
        this.role = role
    }

    fun getId(): Long = id
//    fun getName(): String? = name
    fun getName(): String = "$firstName $lastName"
    fun getFirstName(): String? = firstName
    fun getLastName(): String? = lastName
    fun getRole(): String? = role

    fun setId(id: Long) {
        this.id = id
    }

//    fun setName(name: String?) {
//        this.name = name
//    }
    fun setName(name: String) {
        val parts = name.split(" ")
        firstName = parts[0]
        lastName = parts[1]
    }
    fun setFirstName(firstName: String?) {
        this.firstName = firstName
    }
    fun setLastName(lastName: String?) {
        this.lastName = lastName
    }

    fun setRole(role: String?) {
        this.role = role
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false
        return (Objects.equals(id, other.id) && Objects.equals(firstName, other.firstName) // Objects.equals(name, other.name)
                && Objects.equals(lastName, other.lastName) && Objects.equals(role, other.role))
    }

    override fun hashCode(): Int {
//        return Objects.hash(id, name, role)
        return Objects.hash(id, firstName, lastName, role)
    }

    override fun toString(): String {
//        return "Employee{id=$id, name='$name', role='$role'}"
        return "Employee{id=$id, firstName='$firstName', lastName='$lastName' role='$role'}"
    }
}