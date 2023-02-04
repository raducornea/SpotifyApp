//package com.gateway.spotifyapp.presentation.configurations
//
//import com.gateway.spotifyapp.business.services.TokenService
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.oxm.jaxb.Jaxb2Marshaller
//
//
//@Configuration
//class TokenConfiguration {
//
//    @Bean
//    fun marshaller(): Jaxb2Marshaller {
//        val marshaller = Jaxb2Marshaller()
//        // this package must match the package in the <generatePackage> specified in
//        // pom.xml
//        marshaller.contextPath = "com.gateway.consumingwebservice.wsdl"
//        return marshaller
//    }
//
//    @Bean
//    fun tokenClient(marshaller: Jaxb2Marshaller?): TokenService {
//        val client = TokenService()
//        client.defaultUri = "http://localhost:8000/?wsdl"
//        client.marshaller = marshaller
//        client.unmarshaller = marshaller
//        return client
//    }
//}