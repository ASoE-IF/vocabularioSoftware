package org.splab.vocabulary.vxl.iterator.javamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence>
 *         &lt;element name="comm" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="const" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sloc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "comm", "_const" })
public class Enum {

	@XmlElement(required = true)
	protected List<Comm> comm;
	@XmlElement(name = "const", required = true)
	protected List<Const> _const;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "sloc")
	protected Integer sloc;

	/**
	 * Gets the value of the comm property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the comm property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getComm().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CPPProject.CPPFile.Func.Enum.Comm }
	 * 
	 * 
	 */
	public List<Comm> getComm() {
		if (this.comm == null) {
			this.comm = new ArrayList<Comm>();
		}
		return this.comm;
	}

	public void addComm(Comm comm) {
		if (this.comm == null) {
			this.comm = new ArrayList<Comm>();
		}

		this.comm.add(comm);
	}
	
	public void setComm(List<Comm> comm) {
		this.comm = comm;
	}

	/**
	 * Gets the value of the const property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the const property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getConst().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CPPProject.CPPFile.Func.Enum.Const }
	 * 
	 * 
	 */
	public List<Const> getConst() {
		if (this._const == null) {
			this._const = new ArrayList<Const>();
		}
		return this._const;
	}

	public void addConst(Const _const) {
		if (this._const == null) {
			this._const = new ArrayList<Const>();
		}
		this._const.add(_const);
	}

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
	 * Obt�m o valor da propriedade sloc.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getSloc() {
		return sloc;
	}

	/**
	 * Define o valor da propriedade sloc.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setSloc(Integer value) {
		this.sloc = value;
	}

}