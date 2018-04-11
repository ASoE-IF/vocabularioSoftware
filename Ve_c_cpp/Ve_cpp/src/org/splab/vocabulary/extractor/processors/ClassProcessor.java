package org.splab.vocabulary.extractor.processors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPField;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPClassType;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * This class is responsible for extracting informations from the class
 * body. All the informations extract should be Statement type.
 * 
 * StructProcessor extract informations such as: class
 * 
 * @author Israel Gomes de Lima
 */

public class ClassProcessor {
	private StringBuffer vxlFragment;
	private StringBuffer methodVXL;
	private StringBuffer fieldVXL;
	private StringBuffer enumVXL;
	private StringBuffer structVXL;
	private StringBuffer unionVXL;
	private StringBuffer classVXL;
	
	private List<ASTNode> listDeclarations;

	public ClassProcessor(CPPASTCompositeTypeSpecifier compositeType, boolean isInner, EntityType entityType) {

		vxlFragment = new StringBuffer();
		methodVXL = new StringBuffer();
		fieldVXL = new StringBuffer();
		enumVXL = new StringBuffer();
		structVXL = new StringBuffer();
		unionVXL = new StringBuffer();
		classVXL = new StringBuffer();

		CPPClassType classScope = (CPPClassType) compositeType.getScope().getClassType();
		String name = classScope.getName();
		String isInnerType = (isInner) ? "y" : "n";

		//Cria lista de ASTNode
		this.listDeclarations = new LinkedList<ASTNode>();
		
		// Cria lista de membros
		List<IASTDeclaration> members = Arrays.asList(compositeType.getMembers());
		
		HashMap<String, ICPPField> fields = new HashMap<String, ICPPField>();
		for (ICPPField field : classScope.getDeclaredFields())
			fields.put(field.getName(), field);

		ICPPMethod[] methods = compositeType.getScope().getClassType().getMethods();
		int methodCount = 0;

		for (IASTDeclaration declaration : compositeType.getMembers()) {
			if (declaration instanceof IASTSimpleDeclaration) {
				CPPASTSimpleDeclaration simpleDeclaration = (CPPASTSimpleDeclaration) declaration;

				if (simpleDeclaration.getDeclarators().length > 0) {

					for (IASTDeclarator fieldDeclarator : simpleDeclaration.getDeclarators()) {

						if (fields.containsKey(fieldDeclarator.getName().toString())) {

							FieldProcessor aField = new FieldProcessor(fieldDeclarator,
									simpleDeclaration.getDeclSpecifier(),
									fields.get(fieldDeclarator.getName().toString()));

							fieldVXL.append(aField.getVxlFragment());
						}
					}
				} else {
					declarations((CPPASTSimpleDeclaration) declaration);
				}
			}

			if (declaration instanceof ICPPASTFunctionDefinition) {
				CPPASTFunctionDefinition functionDefinition = (CPPASTFunctionDefinition) declaration;
				
				ICPPMethod method = methods[methodCount];
				MethodProcessor functions = new MethodProcessor(functionDefinition, method, false, EntityType.METHOD);
				methodVXL.append(functions.getVxlFragment());
				
				listDeclarations.add((ASTNode) functionDefinition);
				methodCount += 1;

			}
		}

		// Efetua contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode) compositeType, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, false);

		if (LOCManager.locParameters.contains(LOCParameters.LOC))
			LOCManager.appendEntityLOCData(compositeType.getName().toString(), locKeeper, entityType);

		vxlFragment.append(VxlManager.startClass(name, isInnerType, locKeeper.getLOC()));
		vxlFragment.append(new CommentsProcessor(listDeclarations, (ASTNode)compositeType, false));
		
		/*
		 * ;
		 * 
		 * //Extrai os membros das structs for(IASTDeclaration membersSimple :
		 * members) { if(membersSimple instanceof CASTSimpleDeclaration) {
		 * CASTSimpleDeclaration simpleMembers =
		 * (CASTSimpleDeclaration)membersSimple;
		 * 
		 * IASTDeclarator[] structMembers = simpleMembers.getDeclarators();
		 * if(structMembers.length != 0) {
		 * vxlFragment.append(VxlManager.structMembers(structMembers[0].getName(
		 * ).toString(), scope)); } } }
		 * 
		 *
		 */

		vxlFragment.append(fieldVXL);
		vxlFragment.append(enumVXL);
		vxlFragment.append(unionVXL);
		vxlFragment.append(structVXL);
		vxlFragment.append(classVXL);
		vxlFragment.append(methodVXL);
		vxlFragment.append(VxlManager.endClass());
	}

	private DeclarationsType extractCompositeType(CPPASTCompositeTypeSpecifier compositeType) {
		DeclarationsType valor = null;

		switch (compositeType.getKey()) {
		case 1:
			StructProcessor struct = new StructProcessor((CPPASTCompositeTypeSpecifier) compositeType, true);
			structVXL.append(struct.getVxlFragment());
			valor = DeclarationsType.STRUCT;

			break;

		case 2:
			UnionProcessor union = new UnionProcessor((CPPASTCompositeTypeSpecifier) compositeType, true);
			unionVXL.append(union.getVxlFragment());
			valor = DeclarationsType.UNION;

			break;
		case 3:
			ClassProcessor classProcessor = new ClassProcessor(compositeType, true, EntityType.INNER_CLASS);
			classVXL.append(classProcessor.getVxlFragment());
			valor = DeclarationsType.CLASS;
			break;
		}

		return valor;
	}

	public DeclarationsType declarations(CPPASTSimpleDeclaration simpleDeclaration) {

		DeclarationsType declarationType = null;

		// extrai a declaração específica
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		// processa enums
		if (declarations instanceof CPPASTEnumerationSpecifier) {
			CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, true, true, 0);

			vxlFragment.append(enumProcessor.getVxlFragment());

			listDeclarations.add((ASTNode) enumeration);
			declarationType = DeclarationsType.ENUM;
		}

		// processa structs e unions
		if (declarations instanceof CPPASTCompositeTypeSpecifier) {

			CPPASTCompositeTypeSpecifier compositeType = (CPPASTCompositeTypeSpecifier) declarations;

			declarationType = extractCompositeType(compositeType);

			/*
			 * IASTDeclarator[] declarators =
			 * simpleDeclaration.getDeclarators(); if (declarators.length > 0) {
			 * for (IASTDeclarator declarator : declarators) {
			 * extractDeclarator(declarator); } }
			 */
			
			listDeclarations.add((ASTNode) compositeType);
		}

		return declarationType;
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
}
