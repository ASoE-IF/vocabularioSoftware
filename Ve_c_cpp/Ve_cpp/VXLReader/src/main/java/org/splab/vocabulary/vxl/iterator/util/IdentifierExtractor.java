package org.splab.vocabulary.vxl.iterator.util;

import java.util.HashMap;
import java.util.Map;

import org.splab.vocabulary.vxl.iterator.javamodel.CPPClass;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPError;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPFile;
import org.splab.vocabulary.vxl.iterator.javamodel.Call;
import org.splab.vocabulary.vxl.iterator.javamodel.Comm;
import org.splab.vocabulary.vxl.iterator.javamodel.Const;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.javamodel.Enum;
import org.splab.vocabulary.vxl.iterator.javamodel.Field;
import org.splab.vocabulary.vxl.iterator.javamodel.Func;
import org.splab.vocabulary.vxl.iterator.javamodel.Gvar;
import org.splab.vocabulary.vxl.iterator.javamodel.Include;
import org.splab.vocabulary.vxl.iterator.javamodel.Literal;
import org.splab.vocabulary.vxl.iterator.javamodel.Lvar;
import org.splab.vocabulary.vxl.iterator.javamodel.Macro;
import org.splab.vocabulary.vxl.iterator.javamodel.Method;
import org.splab.vocabulary.vxl.iterator.javamodel.Parameter;
import org.splab.vocabulary.vxl.iterator.javamodel.Pragma;
import org.splab.vocabulary.vxl.iterator.javamodel.Prototype;
import org.splab.vocabulary.vxl.iterator.javamodel.Struct;
import org.splab.vocabulary.vxl.iterator.javamodel.Union;
import org.splab.vocabulary.vxl.iterator.util.VXLReaderPropertyKeys.ContainerType;

/**
 * A class that generates a ContainerEntity from an entity, extracting all
 * identifiers from containing entities
 * 
 * @author gustavojss
 * @author Israel Gomes de Lima
 * 
 *         Modificado por Israel Gomes de Lima a partir da classe original feita
 *         por: gustavojss
 */
public class IdentifierExtractor {

	/**
	 * Generates a file container, with all containing names
	 * 
	 * @param aFile
	 * @return
	 */
	public static ContainerEntity generateContainer(CPPFile aFile) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		aFile.setName(aFile.getName().replace("\\", "/"));
		String[] nameSplitted = aFile.getName().split("/");
		String fileName = nameSplitted[nameSplitted.length - 1];

		if (identifiers.containsKey(fileName)) {
			identifiers.put(fileName, identifiers.get(fileName) + 1);
		} else {
			identifiers.put(fileName, 1);
		}

		// extracting comments
		for (Comm c : aFile.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		// adding file identifier
		String fileIdentifier = "";
		if (aFile.getName() != null || !aFile.getName().isEmpty())
			fileIdentifier = aFile.getName();

		// extracting global variables' identifiers
		for (Gvar aGlobalVar : aFile.getGvar()) {
			if (aGlobalVar.getName() != null || !aGlobalVar.getName().isEmpty()) {

				if (identifiers.containsKey(aGlobalVar.getName())) {
					identifiers.put(aGlobalVar.getName(),
							identifiers.get(aGlobalVar.getName()) + toInteger(aGlobalVar.getCount()));
				} else {
					identifiers.put(aGlobalVar.getName(), toInteger(aGlobalVar.getCount()));
				}
			}
		}

		// extracting functions call's identifiers
		for (Call aCall : aFile.getCall()) {
			if (aCall.getName() != null || !aCall.getName().isEmpty()) {

				if (identifiers.containsKey(aCall.getName())) {
					identifiers.put(aCall.getName(), identifiers.get(aCall.getName()) + toInteger(aCall.getCount()));
				} else {
					identifiers.put(aCall.getName(), toInteger(aCall.getCount()));
				}
			}
		}

		// extracting function prototype's identifiers
		for (Prototype aPrttp : aFile.getPrttp()) {
			if (aPrttp.getName() != null || !aPrttp.getName().isEmpty()) {

				if (identifiers.containsKey(aPrttp.getName())) {
					identifiers.put(aPrttp.getName(), identifiers.get(aPrttp.getName()) + 1);
				} else {
					identifiers.put(aPrttp.getName(), 1);
				}
			}
		}

		// extracting literals identifiers
		for (Literal lit : aFile.getLit()) {
			if (lit.getCntt() != null || !lit.getCntt().isEmpty()) {

				String cntt = reconvertLiteral(lit.getCntt());

				if (identifiers.containsKey(cntt)) {
					identifiers.put(cntt, identifiers.get(cntt) + toInteger(lit.getCount()));
				} else {
					identifiers.put(cntt, toInteger(lit.getCount()));
				}
			}
		}

		// extracting pragma identifiers
		for (Pragma aPragma : aFile.getPragma()) {
			if (aPragma.getMessage() != null || !aPragma.getMessage().isEmpty()) {

				if (identifiers.containsKey(aPragma.getMessage())) {
					identifiers.put(aPragma.getMessage(), identifiers.get(aPragma.getMessage()) + 1);
				} else {
					identifiers.put(aPragma.getMessage(), 1);
				}
			}
		}

		// extracting directives identifiers
		for (Include aDirective : aFile.getInclude()) {
			if (aDirective.getName() != null || !aDirective.getName().isEmpty()) {

				if (identifiers.containsKey(aDirective.getName())) {
					identifiers.put(aDirective.getName(), identifiers.get(aDirective.getName()) + 1);
				} else {
					identifiers.put(aDirective.getName(), 1);
				}
			}
		}

		// extracting error's identifiers
		for (CPPError aError : aFile.getError()) {
			if (aError.getMessage() != null || !aError.getMessage().isEmpty()) {

				if (identifiers.containsKey(aError.getMessage())) {
					identifiers.put(aError.getMessage(), identifiers.get(aError.getMessage()) + 1);
				} else {
					identifiers.put(aError.getMessage(), 1);
				}
			}
		}

		for (Macro aMacro : aFile.getMacro()) {
			/*
			 * Em essência, macros não são entidades, cuntudo, podem se
			 * comportar como tal se forem function style macro (macros
			 * semelhantes a funções)
			 */
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aMacro));
		}

		// extracting enums' identifiers
		for (Enum anEnum : aFile.getEnum()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(anEnum));
		}

		// extracting unions' identifiers
		for (Union aUnion : aFile.getUnion()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aUnion));
		}

		// extracting structs' identifiers
		for (Struct aStruct : aFile.getStrt()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aStruct));
		}

		// extracting functions identifiers
		for (Func aFunc : aFile.getFunc()) {
			// Como uma Função é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da Função)
			ContainerEntity methodContainer = generateContainer(aFunc);
			identifiers = mergeMaps(identifiers, methodContainer.getIdentifiers());
		}

		// extracting class identifiers
		for (CPPClass aClass : aFile.getClazz()) {
			// Como uma Classe é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da classe)
			ContainerEntity methodContainer = generateContainer(aClass);
			identifiers = mergeMaps(identifiers, methodContainer.getIdentifiers());
		}

		return new ContainerEntity(fileIdentifier, identifiers, aFile, ContainerType.FILE);
	}

	/**
	 * Gera map de identificadores macro
	 * 
	 * @param anMacro
	 * @return
	 */
	private static Map<String, Integer> generateMapIdentifiers(Macro anMacro) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		// adding enum's identifier
		if (anMacro.getName() != null || !anMacro.getName().isEmpty()) {
			if (identifiers.containsKey(anMacro.getName())) {
				identifiers.put(anMacro.getName(), identifiers.get(anMacro.getName()) + 1);
			} else {
				identifiers.put(anMacro.getName(), 1);
			}
		}

		// extracting constants' identifiers
		for (Parameter aParam : anMacro.getParam()) {
			if (aParam.getName() != null || !aParam.getName().isEmpty()) {

				if (identifiers.containsKey(aParam.getName())) {
					identifiers.put(aParam.getName(), identifiers.get(aParam.getName()) + 1);
				} else {
					identifiers.put(aParam.getName(), 1);
				}
			}
		}

		return identifiers;
	}

	/**
	 * Gera map de identificadores enum
	 * 
	 * @param anEnum
	 * @return
	 */
	private static Map<String, Integer> generateMapIdentifiers(Enum anEnum) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		// adding enum's identifier
		if (anEnum.getName() != null || !anEnum.getName().isEmpty()) {
			if (identifiers.containsKey(anEnum.getName())) {
				identifiers.put(anEnum.getName(), identifiers.get(anEnum.getName()) + 1);
			} else {
				identifiers.put(anEnum.getName(), 1);
			}
		}

		// extracting constants' identifiers
		for (Const aConstant : anEnum.getConst()) {
			if (aConstant.getName() != null || !aConstant.getName().isEmpty()) {

				if (identifiers.containsKey(aConstant.getName())) {
					identifiers.put(aConstant.getName(), identifiers.get(aConstant.getName()) + 1);
				} else {
					identifiers.put(aConstant.getName(), 1);
				}
			}
		}

		// extrating comments
		for (Comm c : anEnum.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		return identifiers;
	}

	/**
	 * Gera map de identificadores union
	 * 
	 * @param anUnion
	 * @return
	 */
	private static Map<String, Integer> generateMapIdentifiers(Union anUnion) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		// adding union's identifier
		if (anUnion.getName() != null || !anUnion.getName().isEmpty()) {
			if (identifiers.containsKey(anUnion.getName())) {
				identifiers.put(anUnion.getName(), identifiers.get(anUnion.getName()) + 1);
			} else {
				identifiers.put(anUnion.getName(), 1);
			}
		}

		// extracting field's identifiers
		for (Field aField : anUnion.getField()) {
			if (aField.getName() != null || !aField.getName().isEmpty()) {

				if (identifiers.containsKey(aField.getName())) {
					identifiers.put(aField.getName(), identifiers.get(aField.getName()) + 1);
				} else {
					identifiers.put(aField.getName(), 1);
				}
			}
		}

		// extrating comments
		for (Comm c : anUnion.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		return identifiers;
	}

	/**
	 * Gera map de identificadores union
	 * 
	 * @param anStruct
	 * @return
	 */
	private static Map<String, Integer> generateMapIdentifiers(Struct anStruct) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		// adding struct's identifier
		if (anStruct.getName() != null || !anStruct.getName().isEmpty()) {
			if (identifiers.containsKey(anStruct.getName())) {
				identifiers.put(anStruct.getName(), identifiers.get(anStruct.getName()) + 1);
			} else {
				identifiers.put(anStruct.getName(), 1);
			}
		}

		// extracting field's identifiers
		for (Field aField : anStruct.getField()) {
			if (aField.getName() != null || !aField.getName().isEmpty()) {

				if (identifiers.containsKey(aField.getName())) {
					identifiers.put(aField.getName(), identifiers.get(aField.getName()) + 1);
				} else {
					identifiers.put(aField.getName(), 1);
				}
			}
		}

		// extrating comments
		for (Comm c : anStruct.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		return identifiers;
	}

	/**
	 * Gera mapa de identificadores de funções
	 * 
	 * @param aFunc
	 * @return
	 */
	public static ContainerEntity generateContainer(Func aFunc) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		String funcIdentifier = "";
		if (aFunc.getName() != null || !aFunc.getName().isEmpty()) {
			funcIdentifier = aFunc.getName();
		}

		if (identifiers.containsKey(funcIdentifier)) {
			identifiers.put(funcIdentifier, identifiers.get(funcIdentifier) + 1);
		} else {
			identifiers.put(funcIdentifier, 1);
		}

		// extrating comments
		for (Comm c : aFunc.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		// extracting parameters' identifiers
		for (Parameter aParam : aFunc.getParam()) {
			if (aParam.getName() != null || !aParam.getName().isEmpty()) {

				if (identifiers.containsKey(aParam.getName())) {
					identifiers.put(aParam.getName(), identifiers.get(aParam.getName()) + 1);
				} else {
					identifiers.put(aParam.getName(), 1);
				}
			}
		}

		// extracting global variables' identifiers
		for (Gvar aGlobalVar : aFunc.getGvar()) {
			if (aGlobalVar.getName() != null || !aGlobalVar.getName().isEmpty()) {

				if (identifiers.containsKey(aGlobalVar.getName())) {
					identifiers.put(aGlobalVar.getName(),
							identifiers.get(aGlobalVar.getName()) + toInteger(aGlobalVar.getCount()));
				} else {
					identifiers.put(aGlobalVar.getName(), toInteger(aGlobalVar.getCount()));
				}
			}
		}

		// extracting local variables' identifiers
		for (Lvar aLocalVar : aFunc.getLvar()) {
			if (aLocalVar.getName() != null || !aLocalVar.getName().isEmpty()) {

				if (identifiers.containsKey(aLocalVar.getName())) {
					identifiers.put(aLocalVar.getName(),
							identifiers.get(aLocalVar.getName()) + toInteger(aLocalVar.getCount()));
				} else {
					identifiers.put(aLocalVar.getName(), toInteger(aLocalVar.getCount()));
				}
			}
		}

		// extracting fields' identifiers
		for (Field aField : aFunc.getField()) {
			if (aField.getName() != null || !aField.getName().isEmpty()) {

				if (identifiers.containsKey(aField.getName())) {
					identifiers.put(aField.getName(), identifiers.get(aField.getName()) + toInteger(aField.getCount()));
				} else {
					identifiers.put(aField.getName(), toInteger(aField.getCount()));
				}
			}
		}

		// extracting local variables' identifiers
		for (Call aCall : aFunc.getCall()) {
			if (aCall.getName() != null || !aCall.getName().isEmpty()) {

				if (identifiers.containsKey(aCall.getName())) {
					identifiers.put(aCall.getName(), identifiers.get(aCall.getName()) + toInteger(aCall.getCount()));
				} else {
					identifiers.put(aCall.getName(), toInteger(aCall.getCount()));
				}
			}
		}

		// extracting parameters' identifiers
		for (Prototype aPrototype : aFunc.getPrttp()) {
			if (aPrototype.getName() != null || !aPrototype.getName().isEmpty()) {

				if (identifiers.containsKey(aPrototype.getName())) {
					identifiers.put(aPrototype.getName(), identifiers.get(aPrototype.getName()) + 1);
				} else {
					identifiers.put(aPrototype.getName(), 1);
				}
			}
		}

		// extracting literals
		for (Literal lit : aFunc.getLit()) {
			if (lit.getCntt() != null || !lit.getCntt().isEmpty()) {

				String cntt = reconvertLiteral(lit.getCntt());

				if (identifiers.containsKey(cntt)) {
					identifiers.put(cntt, identifiers.get(cntt) + toInteger(lit.getCount()));
				} else {
					identifiers.put(cntt, toInteger(lit.getCount()));
				}
			}
		}

		// extracting enums' identifiers
		for (Enum anEnum : aFunc.getEnum()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(anEnum));
		}

		// extracting unions' identifiers
		for (Union aUnion : aFunc.getUnion()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aUnion));
		}

		// extracting structs' identifiers
		for (Struct aStruct : aFunc.getStrt()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aStruct));
		}

		// extracting functions identifiers
		for (Func anFunc : aFunc.getFunc()) {
			// Como uma Função é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da Função)
			ContainerEntity functionContainer = generateContainer(anFunc);
			identifiers = mergeMaps(identifiers, functionContainer.getIdentifiers());
		}

		// extracting class identifiers
		for (CPPClass aClass : aFunc.getClazz()) {
			// Como uma Classe é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da classe)
			ContainerEntity methodContainer = generateContainer(aClass);
			identifiers = mergeMaps(identifiers, methodContainer.getIdentifiers());
		}

		return new ContainerEntity(funcIdentifier, identifiers, aFunc, ContainerType.FUNCTION);
	}

	/**
	 * Gera mapa de identificadores de classes
	 * 
	 * @param aClass
	 * @return
	 */
	public static ContainerEntity generateContainer(CPPClass aClass) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		String funcIdentifier = "";
		if (aClass.getName() != null || !aClass.getName().isEmpty()) {
			funcIdentifier = aClass.getName();
		}

		if (identifiers.containsKey(funcIdentifier)) {
			identifiers.put(funcIdentifier, identifiers.get(funcIdentifier) + 1);
		} else {
			identifiers.put(funcIdentifier, 1);
		}

		// extrating comments
		for (Comm c : aClass.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		// extracting global variables' identifiers
		for (Gvar aGlobalVar : aClass.getGvar()) {
			if (aGlobalVar.getName() != null || !aGlobalVar.getName().isEmpty()) {

				if (identifiers.containsKey(aGlobalVar.getName())) {
					identifiers.put(aGlobalVar.getName(),
							identifiers.get(aGlobalVar.getName()) + toInteger(aGlobalVar.getCount()));
				} else {
					identifiers.put(aGlobalVar.getName(), toInteger(aGlobalVar.getCount()));
				}
			}
		}

		// extracting fields' identifiers
		for (Field aField : aClass.getField()) {
			if (aField.getName() != null || !aField.getName().isEmpty()) {

				if (identifiers.containsKey(aField.getName())) {
					identifiers.put(aField.getName(), identifiers.get(aField.getName()) + toInteger(aField.getCount()));
				} else {
					identifiers.put(aField.getName(), toInteger(aField.getCount()));
				}
			}
		}

		// extracting local variables' identifiers
		for (Call aCall : aClass.getCall()) {
			if (aCall.getName() != null || !aCall.getName().isEmpty()) {

				if (identifiers.containsKey(aCall.getName())) {
					identifiers.put(aCall.getName(), identifiers.get(aCall.getName()) + toInteger(aCall.getCount()));
				} else {
					identifiers.put(aCall.getName(), toInteger(aCall.getCount()));
				}
			}
		}

		// extracting parameters' identifiers
		for (Prototype aPrototype : aClass.getPrttp()) {
			if (aPrototype.getName() != null || !aPrototype.getName().isEmpty()) {

				if (identifiers.containsKey(aPrototype.getName())) {
					identifiers.put(aPrototype.getName(), identifiers.get(aPrototype.getName()) + 1);
				} else {
					identifiers.put(aPrototype.getName(), 1);
				}
			}
		}

		// extracting literals
		for (Literal lit : aClass.getLit()) {
			if (lit.getCntt() != null || !lit.getCntt().isEmpty()) {

				String cntt = reconvertLiteral(lit.getCntt());

				if (identifiers.containsKey(cntt)) {
					identifiers.put(cntt, identifiers.get(cntt) + toInteger(lit.getCount()));
				} else {
					identifiers.put(cntt, toInteger(lit.getCount()));
				}
			}
		}

		// extracting enums' identifiers
		for (Enum anEnum : aClass.getEnum()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(anEnum));
		}

		// extracting unions' identifiers
		for (Union aUnion : aClass.getUnion()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aUnion));
		}

		// extracting structs' identifiers
		for (Struct aStruct : aClass.getStrt()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aStruct));
		}

		// extracting functions identifiers
		for (Func anFunc : aClass.getFunc()) {
			// Como uma Função é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da Função)
			ContainerEntity functionContainer = generateContainer(anFunc);
			identifiers = mergeMaps(identifiers, functionContainer.getIdentifiers());
		}

		// extracting methods identifiers
		for (Method anMethod : aClass.getMth()) {
			// Como uma Função é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da Função)
			ContainerEntity methodContainer = generateContainer(anMethod);
			identifiers = mergeMaps(identifiers, methodContainer.getIdentifiers());
		}

		// extracting class identifiers
		for (CPPClass anClass : aClass.getClazz()) {
			// Como uma Classe é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da classe)
			ContainerEntity classContainer = generateContainer(anClass);
			identifiers = mergeMaps(identifiers, classContainer.getIdentifiers());
		}

		return new ContainerEntity(funcIdentifier, identifiers, aClass, ContainerType.CLASS);
	}

	/**
	 * Gera mapa de identificadores de metodos
	 * 
	 * @param aMethod
	 * @return
	 */
	public static ContainerEntity generateContainer(Method aMethod) {
		Map<String, Integer> identifiers = new HashMap<String, Integer>();

		String funcIdentifier = "";
		if (aMethod.getName() != null || !aMethod.getName().isEmpty()) {
			funcIdentifier = aMethod.getName();
		}

		if (identifiers.containsKey(funcIdentifier)) {
			identifiers.put(funcIdentifier, identifiers.get(funcIdentifier) + 1);
		} else {
			identifiers.put(funcIdentifier, 1);
		}

		// extrating comments
		for (Comm c : aMethod.getComm()) {
			for (String part : c.getCntt().split(" ")) {
				if (!part.equals("")) {
					if (identifiers.containsKey(part)) {
						identifiers.put(part, identifiers.get(part) + 1);
					} else {
						identifiers.put(part, 1);
					}
				}
			}
		}

		// extracting parameters' identifiers
		for (Parameter aParam : aMethod.getParam()) {
			if (aParam.getName() != null || !aParam.getName().isEmpty()) {

				if (identifiers.containsKey(aParam.getName())) {
					identifiers.put(aParam.getName(), identifiers.get(aParam.getName()) + 1);
				} else {
					identifiers.put(aParam.getName(), 1);
				}
			}
		}

		// extracting global variables' identifiers
		for (Gvar aGlobalVar : aMethod.getGvar()) {
			if (aGlobalVar.getName() != null || !aGlobalVar.getName().isEmpty()) {

				if (identifiers.containsKey(aGlobalVar.getName())) {
					identifiers.put(aGlobalVar.getName(),
							identifiers.get(aGlobalVar.getName()) + toInteger(aGlobalVar.getCount()));
				} else {
					identifiers.put(aGlobalVar.getName(), toInteger(aGlobalVar.getCount()));
				}
			}
		}

		// extracting local variables' identifiers
		for (Lvar aLocalVar : aMethod.getLvar()) {
			if (aLocalVar.getName() != null || !aLocalVar.getName().isEmpty()) {

				if (identifiers.containsKey(aLocalVar.getName())) {
					identifiers.put(aLocalVar.getName(),
							identifiers.get(aLocalVar.getName()) + toInteger(aLocalVar.getCount()));
				} else {
					identifiers.put(aLocalVar.getName(), toInteger(aLocalVar.getCount()));
				}
			}
		}

		// extracting fields' identifiers
		for (Field aField : aMethod.getField()) {
			if (aField.getName() != null || !aField.getName().isEmpty()) {

				if (identifiers.containsKey(aField.getName())) {
					identifiers.put(aField.getName(), identifiers.get(aField.getName()) + toInteger(aField.getCount()));
				} else {
					identifiers.put(aField.getName(), toInteger(aField.getCount()));
				}
			}
		}

		// extracting local variables' identifiers
		for (Call aCall : aMethod.getCall()) {
			if (aCall.getName() != null || !aCall.getName().isEmpty()) {

				if (identifiers.containsKey(aCall.getName())) {
					identifiers.put(aCall.getName(), identifiers.get(aCall.getName()) + toInteger(aCall.getCount()));
				} else {
					identifiers.put(aCall.getName(), toInteger(aCall.getCount()));
				}
			}
		}

		// extracting parameters' identifiers
		for (Prototype aPrototype : aMethod.getPrttp()) {
			if (aPrototype.getName() != null || !aPrototype.getName().isEmpty()) {

				if (identifiers.containsKey(aPrototype.getName())) {
					identifiers.put(aPrototype.getName(), identifiers.get(aPrototype.getName()) + 1);
				} else {
					identifiers.put(aPrototype.getName(), 1);
				}
			}
		}

		// extracting literals
		for (Literal lit : aMethod.getLit()) {
			if (lit.getCntt() != null || !lit.getCntt().isEmpty()) {

				String cntt = reconvertLiteral(lit.getCntt());

				if (identifiers.containsKey(cntt)) {
					identifiers.put(cntt, identifiers.get(cntt) + toInteger(lit.getCount()));
				} else {
					identifiers.put(cntt, toInteger(lit.getCount()));
				}
			}
		}

		// extracting enums' identifiers
		for (Enum anEnum : aMethod.getEnum()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(anEnum));
		}

		// extracting unions' identifiers
		for (Union aUnion : aMethod.getUnion()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aUnion));
		}

		// extracting structs' identifiers
		for (Struct aStruct : aMethod.getStrt()) {
			identifiers = mergeMaps(identifiers, generateMapIdentifiers(aStruct));
		}

		// extracting functions identifiers
		for (Func anFunc : aMethod.getFunc()) {
			// Como uma Função é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da Função)
			ContainerEntity functionContainer = generateContainer(anFunc);
			identifiers = mergeMaps(identifiers, functionContainer.getIdentifiers());
		}

		// extracting class identifiers
		for (CPPClass anClass : aMethod.getClazz()) {
			// Como uma Classe é uma entidade, na sua lista de identificadores
			// já se encontra também o seu identificador (o nome da classe)
			ContainerEntity classContainer = generateContainer(anClass);
			identifiers = mergeMaps(identifiers, classContainer.getIdentifiers());
		}

		return new ContainerEntity(funcIdentifier, identifiers, aMethod, ContainerType.METHOD);
	}

	private static String reconvertLiteral(String literal) {
		literal = literal.replace("&amp;", "&");
		literal = literal.replace("&lt;", "<");
		literal = literal.replace("&gt;", ">");
		literal = literal.replace("&quot;", "\"");
		literal = literal.replace("&apos;", "'");

		return literal;
	}

	private static Integer toInteger(String number) {
		return Integer.parseInt(number);
	}

	private static Map<String, Integer> mergeMaps(Map<String, Integer> mapOne, Map<String, Integer> mapTwo) {
		for (String key : mapTwo.keySet()) {
			if (mapOne.containsKey(key)) {
				mapOne.put(key, mapOne.get(key) + mapTwo.get(key));
			} else {
				mapOne.put(key, mapTwo.get(key));
			}
		}
		return mapOne;
	}
}