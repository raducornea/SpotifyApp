//package com.gateway.spotifyapp.business.services
//
//import com.gateway.consumingwebservice.wsdl.Authorize
//import com.gateway.consumingwebservice.wsdl.AuthorizeResponse
//import com.gateway.consumingwebservice.wsdl.ObjectFactory
//
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Component
//import org.springframework.ws.WebServiceMessage
//import org.springframework.ws.client.core.WebServiceMessageCallback
//import org.springframework.ws.client.core.WebServiceTemplate
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport
//import org.springframework.ws.soap.SoapMessage
//import org.springframework.ws.soap.client.core.SoapActionCallback
//import javax.xml.bind.JAXBContext
//import javax.xml.bind.JAXBElement
//
//
//// https://spring.io/guides/gs/consuming-web-service/
////@Service
//class TokenService : WebServiceGatewaySupport() {
//
//    companion object {
//        private val log: Logger = LoggerFactory.getLogger(TokenService::class.java)
//    }
//
//    fun validateToken(token: String): AuthorizeResponse {
//
//        val factory = ObjectFactory()
//        val tokenDescription: JAXBElement<String> = factory.createAuthorizeToken(token)
//
//        val request = Authorize()
//        request.token = tokenDescription
//
//        log.info("Requesting Authorization for token $token")
//
//        val response: AuthorizeResponse = webServiceTemplate
//            .marshalSendAndReceive(
//                "http://localhost:8000/?wsdl", request,
//                SoapActionCallback(
//                    "http://tempuri.org/AuthorizeToken"
//                )
//            ) as AuthorizeResponse
//
//        return response
//    }
//}
