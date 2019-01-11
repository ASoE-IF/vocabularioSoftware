//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2018.09.03 �s 05:48:58 PM BRT 
//

package org.splab.vocabulary.vxl.iterator.javamodel;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the VXL.CLASS package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: VXL.CLASS
	 * 
	 */
	public ObjectFactory() {
	}

	public Call createCall() {
		return new Call();
	}

	public CError createError() {
		return new CError();
	}

	public CFile createFile() {
		return new CFile();
	}

	public Comm createComm() {
		return new Comm();
	}

	public Const createConst() {
		return new Const();
	}

	public CProject createCProject() {
		return new CProject();
	}

	public Enum createEnum() {
		return new Enum();
	}

	public Field createField() {
		return new Field();
	}

	public Gvar createGvar() {
		return new Gvar();
	}

	public Include createDirective() {
		return new Include();
	}

	public Literal createLiteral() {
		return new Literal();
	}

	public Lvar createLvar() {
		return new Lvar();
	}

	public Macro createMacro() {
		return new Macro();
	}

	public Parameter createParameter() {
		return new Parameter();
	}

	public Pragma createPragma() {
		return new Pragma();
	}

	public Prototype createPrototype() {
		return new Prototype();
	}

	public Struct createStruct() {
		return new Struct();
	}

	public Union createUnion() {
		return new Union();
	}
}
