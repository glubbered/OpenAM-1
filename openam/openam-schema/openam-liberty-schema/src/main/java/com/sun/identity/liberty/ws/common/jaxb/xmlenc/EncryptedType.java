//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.xmlenc;


/**
 * Java content class for EncryptedType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/xenc-schema.xsd line 40)
 * <p>
 * <pre>
 * &lt;complexType name="EncryptedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EncryptionMethod" type="{http://www.w3.org/2001/04/xmlenc#}EncryptionMethodType" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}KeyInfo" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}CipherData"/>
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}EncryptionProperties" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Encoding" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface EncryptedType {


    /**
     * Gets the value of the keyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.KeyInfoType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.KeyInfoElement}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlsig.KeyInfoType getKeyInfo();

    /**
     * Sets the value of the keyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.KeyInfoType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.KeyInfoElement}
     */
    void setKeyInfo(com.sun.identity.liberty.ws.common.jaxb.xmlsig.KeyInfoType value);

    /**
     * Gets the value of the encryptionMethod property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionMethodType}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionMethodType getEncryptionMethod();

    /**
     * Sets the value of the encryptionMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionMethodType}
     */
    void setEncryptionMethod(com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionMethodType value);

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getEncoding();

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setEncoding(java.lang.String value);

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getType();

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setType(java.lang.String value);

    /**
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getMimeType();

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setMimeType(java.lang.String value);

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setId(java.lang.String value);

    /**
     * Gets the value of the cipherData property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.CipherDataElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.CipherDataType}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlenc.CipherDataType getCipherData();

    /**
     * Sets the value of the cipherData property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.CipherDataElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.CipherDataType}
     */
    void setCipherData(com.sun.identity.liberty.ws.common.jaxb.xmlenc.CipherDataType value);

    /**
     * Gets the value of the encryptionProperties property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionPropertiesType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionPropertiesElement}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionPropertiesType getEncryptionProperties();

    /**
     * Sets the value of the encryptionProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionPropertiesType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionPropertiesElement}
     */
    void setEncryptionProperties(com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptionPropertiesType value);

}
