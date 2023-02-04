package com.gateway.spotifyapp.business.services

import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import javax.xml.soap.*

typealias FunctionToCall = (Array<Any>) -> SOAPMessage


@Service
class SOAPClient {

    fun makeSOAPRequest(functionToCall: FunctionToCall): String {

        // Create a SOAP connection
        val soapConnectionFactory = SOAPConnectionFactory.newInstance()
        val soapConnection = soapConnectionFactory.createConnection()

        // Send SOAP message to SOAP server
        val soapResponse = soapConnection.call(functionToCall(arrayOf()), "http://localhost:8000")

        // Retrieve the SOAP Message with its schema
        val responseByteArrayOutputStream = ByteArrayOutputStream()
        soapResponse.writeTo(responseByteArrayOutputStream)
        soapConnection.close()

        return responseByteArrayOutputStream.toString()
    }

    @Throws(Exception::class)
    fun roleIntegrityRequest(bearerJws: String, roles: List<Int>): SOAPMessage {

        val messageFactory: MessageFactory = MessageFactory.newInstance()
        val soapMessage: SOAPMessage = messageFactory.createMessage()

        // Create the "authorize" request
        val soapPart = soapMessage.soapPart
        val serverURI = "services.spotify.soap"
        val envelope = soapPart.envelope
        envelope.addNamespaceDeclaration("sample", serverURI)

        val soapBody = envelope.body
        val roleIntegrityRequest = soapBody.addChildElement("verify_role_integrity", "sample")
        val token = roleIntegrityRequest.addChildElement("token", "sample")
        token.addTextNode(bearerJws)
        val allowedRoles = roleIntegrityRequest.addChildElement("allowed_roles", "sample")

        // <sample:integer>1</sample:integer>
        // <sample:integer>2</sample:integer>
        roles.forEach {
            val role = allowedRoles.addChildElement("integer", "sample")
            role.addTextNode(it.toString())
        }

        soapMessage.saveChanges()
        return soapMessage
    }

    @Throws(Exception::class)
    fun authorizeRequest(bearerJws: String): SOAPMessage {

        val messageFactory: MessageFactory = MessageFactory.newInstance()
        val soapMessage: SOAPMessage = messageFactory.createMessage()

        // Create the "authorize" request
        val soapPart = soapMessage.soapPart
        val serverURI = "services.spotify.soap"
        val envelope = soapPart.envelope
        envelope.addNamespaceDeclaration("sample", serverURI)

        val soapBody = envelope.body
        val authorizeRequest = soapBody.addChildElement("authorize", "sample")
        val token = authorizeRequest.addChildElement("token", "sample")

        token.addTextNode(bearerJws)

        soapMessage.saveChanges()
        return soapMessage
    }
}