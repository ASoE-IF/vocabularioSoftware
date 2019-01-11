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
 *         &lt;element name="param" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="comm" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="gvar" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="access" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="storage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="lvar" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="access" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="storage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="lit" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="prttp" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="access" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="storage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="call" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="enum" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="comm" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="const" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="sloc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="union" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="comm" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="field" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="sloc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="strt" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="comm" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="cntt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="field" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="sloc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="func">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="call">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="access" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="storage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="inn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="sloc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="access" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="storage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="inn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sloc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "param", "comm", "gvar", "lvar", "lit", "prttp", "call", "_enum", "union", "strt",
		"func" })
public class Func {

	@XmlElement(required = true)
	protected List<Parameter> param;
	@XmlElement(required = true)
	protected List<Comm> comm;
	@XmlElement(required = true)
	protected List<Gvar> gvar;
	@XmlElement(required = true)
	protected List<Lvar> lvar;
	@XmlElement(required = true)
	protected List<Literal> lit;
	@XmlElement(required = true)
	protected List<Prototype> prttp;
	@XmlElement(required = true)
	protected List<Call> call;
	@XmlElement(name = "enum", required = true)
	protected List<Enum> _enum;
	@XmlElement(required = true)
	protected List<Union> union;
	@XmlElement(required = true)
	protected List<Struct> strt;
	@XmlElement(required = true)
	protected List<Func> func;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "access")
	protected String access;
	@XmlAttribute(name = "storage")
	protected String storage;
	@XmlAttribute(name = "inn")
	protected String inn;
	@XmlAttribute(name = "sloc")
	protected Integer sloc;

	/**
	 * Gets the value of the param property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the param property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getParam().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Parameter }
	 * 
	 * 
	 */
	public List<Parameter> getParam() {
		if (this.param == null) {
			this.param = new ArrayList<Parameter>();
		}
		return this.param;
	}

	public void addParam(Parameter param) {
		if (this.param == null) {
			this.param = new ArrayList<Parameter>();
		}
		this.param.add(param);
	}

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
	 * {@link CProject.CFile.Func.Comm }
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
	 * Gets the value of the gvar property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the gvar property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getGvar().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Gvar }
	 * 
	 * 
	 */
	public List<Gvar> getGvar() {
		if (this.gvar == null) {
			this.gvar = new ArrayList<Gvar>();
		}
		return this.gvar;
	}

	public void addGvar(Gvar gvar) {
		if (this.gvar == null) {
			this.gvar = new ArrayList<Gvar>();
		}
		this.gvar.add(gvar);
	}

	/**
	 * Gets the value of the lvar property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the lvar property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLvar().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Lvar }
	 * 
	 * 
	 */
	public List<Lvar> getLvar() {
		if (this.lvar == null) {
			this.lvar = new ArrayList<Lvar>();
		}
		return this.lvar;
	}

	public void addLvar(Lvar lvar) {
		if (this.lvar == null) {
			this.lvar = new ArrayList<Lvar>();
		}
		this.lvar.add(lvar);
	}

	/**
	 * Gets the value of the lit property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the lit property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLit().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Literal }
	 * 
	 * 
	 */
	public List<Literal> getLit() {
		if (this.lit == null) {
			this.lit = new ArrayList<Literal>();
		}
		return this.lit;
	}

	public void addLit(Literal lit) {
		if (this.lit == null) {
			this.lit = new ArrayList<Literal>();
		}
		this.lit.add(lit);
	}

	/**
	 * Gets the value of the prttp property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the prttp property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPrttp().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Prototype }
	 * 
	 * 
	 */
	public List<Prototype> getPrttp() {
		if (this.prttp == null) {
			this.prttp = new ArrayList<Prototype>();
		}
		return this.prttp;
	}

	public void addPrttp(Prototype prttp) {
		if (this.prttp == null) {
			this.prttp = new ArrayList<Prototype>();
		}
		this.prttp.add(prttp);
	}

	/**
	 * Gets the value of the call property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the call property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getCall().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Call }
	 * 
	 * 
	 */
	public List<Call> getCall() {
		if (this.call == null) {
			this.call = new ArrayList<Call>();
		}
		return this.call;
	}

	public void addCall(Call call) {
		if (this.call == null) {
			this.call = new ArrayList<Call>();
		}
		this.call.add(call);
	}

	/**
	 * Gets the value of the enum property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the enum property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getEnum().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Enum }
	 * 
	 * 
	 */
	public List<Enum> getEnum() {
		if (this._enum == null) {
			this._enum = new ArrayList<Enum>();
		}
		return this._enum;
	}

	public void addEnum(Enum _enum) {
		if (this._enum == null) {
			this._enum = new ArrayList<Enum>();
		}
		this._enum.add(_enum);
	}

	/**
	 * Gets the value of the union property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the union property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getUnion().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Union }
	 * 
	 * 
	 */
	public List<Union> getUnion() {
		if (this.union == null) {
			this.union = new ArrayList<Union>();
		}
		return this.union;
	}

	public void addUnion(Union union) {
		if (this.union == null) {
			this.union = new ArrayList<Union>();
		}
		this.union.add(union);
	}

	/**
	 * Gets the value of the strt property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the strt property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getStrt().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CProject.CFile.Func.Strt }
	 * 
	 * 
	 */
	public List<Struct> getStrt() {
		if (this.strt == null) {
			this.strt = new ArrayList<Struct>();
		}
		return this.strt;
	}

	public void addStrt(Struct strt) {
		if (this.strt == null) {
			this.strt = new ArrayList<Struct>();
		}
		this.strt.add(strt);
	}

	/**
	 * Obt�m o valor da propriedade func.
	 * 
	 * @return possible object is {@link CProject.CFile.Func.Func }
	 * 
	 */
	public List<Func> getFunc() {
		if (this.func == null) {
			this.func = new ArrayList<Func>();
		}
		return this.func;
	}

	public void addFunc(Func func) {
		if (this.func == null) {
			this.func = new ArrayList<Func>();
		}
		this.func.add(func);
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
	 * Obt�m o valor da propriedade inn.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInn() {
		return inn;
	}

	/**
	 * Define o valor da propriedade inn.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setInn(String value) {
		this.inn = value;
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
