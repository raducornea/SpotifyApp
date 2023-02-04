package com.gateway.spotifyapp.business.services

import org.springframework.stereotype.Service
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource
import java.io.StringReader

@Service
class XMLExtracter {

    fun extractValueFromXMLTag(xmlString: String, element: String): String {
        // Create a DocumentBuilderFactory and set the namespace aware property to true
        val factory = DocumentBuilderFactory.newInstance()
        factory.isNamespaceAware = true

        // Create a DocumentBuilder and parse the XML string
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(InputSource(StringReader(xmlString)))

        // Find the element in the DOM tree
        val nodes = document.getElementsByTagName(element)

        // Extract the value from the element
        return nodes.item(0).textContent
    }
}