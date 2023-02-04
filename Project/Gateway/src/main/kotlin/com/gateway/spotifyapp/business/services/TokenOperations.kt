package com.gateway.spotifyapp.business.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TokenOperations {

    @Autowired
    private lateinit var soapClient: SOAPClient

    @Autowired
    private lateinit var xmlExtracter: XMLExtracter

    fun verifyToken(bearerJws: String, allowedRoles: List<Int>): String {

        val authorizationResponseXML = soapClient.makeSOAPRequest { _ -> soapClient.authorizeRequest(bearerJws) }
        val authorizationResponse = xmlExtracter.extractValueFromXMLTag(authorizationResponseXML, "tns:authorizeResult")

        val roleIntegrityResponseXML = soapClient.makeSOAPRequest { _ -> soapClient.roleIntegrityRequest(bearerJws, allowedRoles) }
        val roleIntegrityResponse =  xmlExtracter.extractValueFromXMLTag(roleIntegrityResponseXML, "tns:verify_role_integrityResult")

        if (authorizationResponse != "Valid Token" || roleIntegrityResponse != "Authorized")
            return "TOKEN FAIL"

        return "TOKEN SUCCESS"
    }
}