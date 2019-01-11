package org.splab.vocabulary.vxl.iterator.javamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java de anonymous complex type.
 * 
 * <p>
 * O seguinte fragmento do esquema especifica o conte�do esperado contido dentro
 * desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="access" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="storage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="visib" type="{http://www.w3.org/2001/XMLSchema}string" />
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
public class Field {

	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "access")
	protected String access;
	@XmlAttribute(name = "storage")
	protected String storage;
	@XmlAttribute(name = "visib")
	protected String visib;
	@XmlAttribute(name = "count")
	protected String count;

	/**
	 * Obt�m o valor da propriedade name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define o valor da propriedade name.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Obt�m o valor da propriedade access.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAccess() {
		return access;
	}

	/**
	 * Define o valor da propriedade access.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAccess(String value) {
		this.access = value;
	}

	/**
	 * Obt�m o valor da propriedade storage.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStorage() {
		return storage;
	}

	/**
	 * Define o valor da propriedade storage.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStorage(String value) {
		this.storage = value;
	}

	/**
	 * Obt�m o valor da propriedade visib.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVisib() {
		return visib;
	}

	/**
	 * Define o valor da propriedade visib.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVisib(String value) {
		this.visib = value;
	}

	/**
	 * Obt�m o valor da propriedade count.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public String getCount() {
		return count;
	}

	/**
	 * Define o valor da propriedade count.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setCount(String value) {
		this.count = value;
	}
}