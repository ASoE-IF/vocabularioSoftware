package org.splab.vocabulary.vxl.iterator.util;

import org.splab.vocabulary.vxl.iterator.javamodel.CError;
import org.splab.vocabulary.vxl.iterator.javamodel.CFile;
import org.splab.vocabulary.vxl.iterator.javamodel.Call;
import org.splab.vocabulary.vxl.iterator.javamodel.Const;
import org.splab.vocabulary.vxl.iterator.javamodel.Enum;
import org.splab.vocabulary.vxl.iterator.javamodel.Field;
import org.splab.vocabulary.vxl.iterator.javamodel.Func;
import org.splab.vocabulary.vxl.iterator.javamodel.Gvar;
import org.splab.vocabulary.vxl.iterator.javamodel.Include;
import org.splab.vocabulary.vxl.iterator.javamodel.Literal;
import org.splab.vocabulary.vxl.iterator.javamodel.Lvar;
import org.splab.vocabulary.vxl.iterator.javamodel.Macro;
import org.splab.vocabulary.vxl.iterator.javamodel.Parameter;
import org.splab.vocabulary.vxl.iterator.javamodel.Pragma;
import org.splab.vocabulary.vxl.iterator.javamodel.Prototype;
import org.splab.vocabulary.vxl.iterator.javamodel.Struct;
import org.splab.vocabulary.vxl.iterator.javamodel.Union;

/**
 * Filter undesired information from an entity, such as parameters and enums
 * 
 * @author gustavojss
 * @author Israel Gomes de Lima
 * 
 *         Modificado por Israel Gomes de Lima a partir da classe original feita
 *         por: gustavojss
 */
public class EntityFilter {

	private boolean includeFunction = false, includeEnum = false, includeUnion = false, includeStruct = false,
			includeParameter = false, includeLocalVar = false, includeGlobalVar = false, includeFuncCall = false,
			includePrototype = false, includeLiterals = false, includeField = false, includeConstant = false,
			includePragma = false, includeDirective = false, includeError = false, includeMacro = false,
			includeComments = false;

	public void setProperties(boolean includeFunction, boolean includeEnum, boolean includeUnion, boolean includeStruct,
			boolean includeParameter, boolean includeLocalVar, boolean includeGlobalVar, boolean includeFuncCall,
			boolean includePrototype, boolean includeLiterals, boolean includeField, boolean includeConstant,
			boolean includePragma, boolean includeDirective, boolean includeError, boolean includeMacro,
			boolean includeComments) {

		this.includeFunction = includeFunction;
		this.includeEnum = includeEnum;
		this.includeUnion = includeUnion;
		this.includeStruct = includeStruct;
		this.includeParameter = includeParameter;
		this.includeLocalVar = includeLocalVar;
		this.includeGlobalVar = includeGlobalVar;
		this.includeFuncCall = includeFuncCall;
		this.includePrototype = includePrototype;
		this.includeLiterals = includeLiterals;
		this.includeField = includeField;
		this.includeConstant = includeConstant;
		this.includePragma = includePragma;
		this.includeDirective = includeDirective;
		this.includeError = includeError;
		this.includeMacro = includeMacro;
		this.includeComments = includeComments;
	}

	/**
	 * Filters a file
	 * 
	 * @param anFile
	 * @return
	 */
	public CFile filterFile(CFile anFile) {

		CFile result = new CFile();

		result.setName(anFile.getName());
		result.setSloc(anFile.getSloc());

		if (this.includeComments) {
			result.setComm(anFile.getComm());
		}

		if (this.includeFunction) {
			for (Func anFunc : anFile.getFunc()) {

				Func filteredFunc = filterFunc(anFunc);
				result.addFunc(filteredFunc);
			}
		}

		if (this.includeEnum) {
			for (Enum anEnum : anFile.getEnum()) {

				Enum filteredEnum = filterEnum(anEnum);
				result.addEnum(filteredEnum);
			}
		}

		if (this.includeUnion) {
			for (Union anUnion : anFile.getUnion()) {

				Union filteredUnion = filterUnion(anUnion);
				result.addUnion(filteredUnion);
			}
		}

		if (this.includeStruct) {
			for (Struct anStruct : anFile.getStrt()) {

				Struct filteredStruct = filterStruct(anStruct);
				result.addStrt(filteredStruct);
			}
		}

		if (this.includeGlobalVar) {
			for (Gvar gvar : anFile.getGvar()) {

				Gvar filteredGvar = new Gvar();
				filteredGvar.setName(gvar.getName());
				filteredGvar.setStorage(gvar.getStorage());
				filteredGvar.setAccess(gvar.getAccess());
				filteredGvar.setCount(gvar.getCount());

				result.addGvar(filteredGvar);
			}
		}

		if (this.includeFuncCall) {
			for (Call call : anFile.getCall()) {

				Call filteredCall = new Call();
				filteredCall.setName(call.getName());
				filteredCall.setCount(call.getCount());

				result.addCall(filteredCall);
			}
		}

		if (this.includePrototype) {
			for (Prototype prttp : anFile.getPrttp()) {

				Prototype filteredPrttp = new Prototype();
				filteredPrttp.setName(prttp.getName());
				filteredPrttp.setAccess(prttp.getAccess());
				filteredPrttp.setStorage(prttp.getStorage());

				result.addPrttp(filteredPrttp);
			}
		}

		if (this.includeLiterals) {
			for (Literal literal : anFile.getLit()) {

				Literal filteredLiteral = new Literal();
				filteredLiteral.setCntt(literal.getCntt());
				filteredLiteral.setCount(literal.getCount());

				result.addLit(filteredLiteral);
			}
		}

		if (this.includePragma) {
			for (Pragma pragma : anFile.getPragma()) {

				Pragma filteredPragma = new Pragma();
				filteredPragma.setMessage(pragma.getMessage());

				result.addPragma(filteredPragma);
			}
		}

		if (this.includeDirective) {
			for (Include directive : anFile.getInclude()) {

				Include filteredDirective = new Include();
				filteredDirective.setName(directive.getName());

				result.addInclude(filteredDirective);
			}
		}

		if (this.includeError) {
			for (CError error : anFile.getError()) {

				CError filteredError = new CError();
				filteredError.setMessage(error.getMessage());

				result.addError(filteredError);
			}
		}

		if (this.includeMacro) {
			for (Macro macro : anFile.getMacro()) {
				Macro filteredMacro = filterMacro(macro);
				result.addMacro(filteredMacro);
			}
		}

		return result;
	}

	/**
	 * Filters an enum
	 * 
	 * @param anEnum
	 * @return
	 */
	public Enum filterEnum(Enum anEnum) {
		Enum result = new Enum();
		result.setName(anEnum.getName());
		result.setSloc(anEnum.getSloc());

		if (this.includeComments) {
			result.setComm(anEnum.getComm());
		}

		if (this.includeConstant)
			for (Const aConstant : anEnum.getConst()) {

				Const filteredConstant = new Const();
				filteredConstant.setName(aConstant.getName());

				result.addConst(filteredConstant);
			}

		return result;
	}

	/**
	 * Filters an union
	 * 
	 * @param anUnion
	 * @return
	 */
	public Union filterUnion(Union anUnion) {
		Union result = new Union();
		result.setName(anUnion.getName());
		result.setSloc(anUnion.getSloc());

		if (this.includeComments) {
			result.setComm(anUnion.getComm());
		}

		if (this.includeField)
			for (Field aField : anUnion.getField()) {

				Field filteredField = new Field();
				filteredField.setName(aField.getName());

				result.addField(filteredField);
			}

		return result;
	}

	/**
	 * Filters an union
	 * 
	 * @param anStruct
	 * @return
	 */
	public Struct filterStruct(Struct anStruct) {
		Struct result = new Struct();
		result.setName(anStruct.getName());
		result.setSloc(anStruct.getSloc());

		if (this.includeComments) {
			result.setComm(anStruct.getComm());
		}

		if (this.includeField)
			for (Field aField : anStruct.getField()) {

				Field filteredField = new Field();
				filteredField.setName(aField.getName());

				result.addField(filteredField);
			}

		return result;
	}

	/**
	 * Filters an macro or function style macro
	 * 
	 * @param anMacro
	 * @return
	 */
	public Macro filterMacro(Macro anMacro) {
		Macro result = new Macro();
		result.setName(anMacro.getName());

		if (this.includeParameter)
			for (Parameter aParameter : anMacro.getParam()) {

				Parameter filteredParam = new Parameter();
				filteredParam.setName(aParameter.getName());

				result.addParam(filteredParam);
			}

		return result;
	}

	/**
	 * Filters an function
	 * 
	 * @param anFunc
	 * @return
	 */
	public Func filterFunc(Func anFunc) {

		Func result = new Func();

		result.setName(anFunc.getName());
		result.setAccess(anFunc.getAccess());
		result.setStorage(anFunc.getStorage());
		result.setInn(anFunc.getInn());
		result.setSloc(anFunc.getSloc());

		if (this.includeComments) {
			result.setComm(anFunc.getComm());
		}

		if (this.includeFunction) {
			for (Func func : anFunc.getFunc()) {

				Func filteredFunc = filterFunc(func);
				result.addFunc(filteredFunc);
			}
		}

		if (this.includeEnum) {
			for (Enum anEnum : anFunc.getEnum()) {

				Enum filteredEnum = filterEnum(anEnum);
				result.addEnum(filteredEnum);
			}
		}

		if (this.includeUnion) {
			for (Union anUnion : anFunc.getUnion()) {

				Union filteredUnion = filterUnion(anUnion);
				result.addUnion(filteredUnion);
			}
		}

		if (this.includeStruct) {
			for (Struct anStruct : anFunc.getStrt()) {

				Struct filteredStruct = filterStruct(anStruct);
				result.addStrt(filteredStruct);
			}
		}

		if (this.includeParameter) {
			for (Parameter aParameter : anFunc.getParam()) {
				Parameter filteredParam = new Parameter();
				filteredParam.setName(aParameter.getName());

				result.addParam(filteredParam);
			}
		}
		if (this.includeGlobalVar) {
			for (Gvar gvar : anFunc.getGvar()) {

				Gvar filteredGvar = new Gvar();
				filteredGvar.setName(gvar.getName());
				filteredGvar.setStorage(gvar.getStorage());
				filteredGvar.setAccess(gvar.getAccess());
				filteredGvar.setCount(gvar.getCount());

				result.addGvar(filteredGvar);
			}
		}

		if (this.includeLocalVar) {
			for (Lvar lvar : anFunc.getLvar()) {

				Lvar filteredLvar = new Lvar();
				filteredLvar.setName(lvar.getName());
				filteredLvar.setStorage(lvar.getStorage());
				filteredLvar.setAccess(lvar.getAccess());
				filteredLvar.setCount(lvar.getCount());

				result.addLvar(filteredLvar);
			}
		}

		if (this.includeFuncCall) {
			for (Call call : anFunc.getCall()) {

				Call filteredCall = new Call();
				filteredCall.setName(call.getName());
				filteredCall.setCount(call.getCount());

				result.addCall(filteredCall);
			}
		}

		if (this.includePrototype) {
			for (Prototype prttp : anFunc.getPrttp()) {

				Prototype filteredPrttp = new Prototype();
				filteredPrttp.setName(prttp.getName());
				filteredPrttp.setAccess(prttp.getAccess());
				filteredPrttp.setStorage(prttp.getStorage());

				result.addPrttp(filteredPrttp);
			}
		}

		if (this.includeLiterals) {
			for (Literal literal : anFunc.getLit()) {

				Literal filteredLiteral = new Literal();
				filteredLiteral.setCntt(literal.getCntt());
				filteredLiteral.setCount(literal.getCount());

				result.addLit(filteredLiteral);
			}
		}

		return result;
	}
}