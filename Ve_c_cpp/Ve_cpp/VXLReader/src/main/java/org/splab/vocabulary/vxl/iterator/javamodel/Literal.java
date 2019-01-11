package org.splab.vocabulary.vxl.iterator.javamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Literal {

    @XmlAttribute(name = "cntt")
    protected String cntt;
    @XmlAttribute(name = "count")
    protected String count;

    /**
     * Obt�m o valor da propriedade cntt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCntt() {
        return cntt;
    }

    /**
     * Define o valor da propriedade cntt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCntt(String value) {
        this.cntt = value;
    }

    /**
     * Obt�m o valor da propriedade count.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getCount() {
        return count;
    }

    /**
     * Define o valor da propriedade count.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(String value) {
        this.count = value;
    }
}