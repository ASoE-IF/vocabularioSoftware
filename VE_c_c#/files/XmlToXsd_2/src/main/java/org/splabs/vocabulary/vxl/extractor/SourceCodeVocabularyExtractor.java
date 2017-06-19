package org.splabs.vocabulary.vxl.extractor;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.splabs.vocabulary.vxl.vloccount.EntityLOCKeeper;
import org.splabs.vocabulary.vxl.vloccount.LOCCountPerEntity;
import org.splabs.vocabulary.vxl.vloccount.LOCParameters;
import org.splabs.vocabulary.vxl.vloccount.PhysicalLOCCount;

/**
 * An abstract implementation of term extraction of Java Source Code
 * @author gustavojss / Tercio de Melo
 */
public abstract class SourceCodeVocabularyExtractor extends ProjectVocabularyExtractor {

	protected ASTNode astNode;
	
	protected List<CommentUnit> sourceCodeComments;
	protected String sourceCode;
	protected String packageName;
	protected List<Comment> commentList;
	/**
	 * Creates an abstraction for term extraction in java source code
	 * @param projectName the project's name
	 * @param projectResivion the project's revision name
	 */
	public SourceCodeVocabularyExtractor(String projectName, String projectResivion, List<LOCParameters> locParameters) {
		super(projectName, projectResivion, locParameters);
	}
	
	/**
	 * Extracts the AST tree from the source code of a .java file
	 */
	@SuppressWarnings("unchecked")
	public void getASTTreeFromSourceCode(final char[] sourceCode) throws InvocationTargetException, InterruptedException {
		
		this.sourceCode = new String(sourceCode);
		
		
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceCode);
		
		// setting java compilationUnit
		Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(options);
		
		astNode = parser.createAST(new NullProgressMonitor());
		
		extractCommentsFromCompilationUnit((CompilationUnit) astNode, new String(sourceCode));
	}
	
	/**
	 * Stores all the Comments from the source code in a LinkedList<CommentUnit>
	 * @param compilationUnit
	 * @param sourceCode
	 */
	@SuppressWarnings("unchecked")
	protected void extractCommentsFromCompilationUnit(CompilationUnit compilationUnit, String sourceCode) {
		
		sourceCodeComments = new LinkedList<CommentUnit>();
		
		for(Comment element : (List<Comment>) compilationUnit.getCommentList())
			if(element instanceof BlockComment || element instanceof LineComment) {
				sourceCodeComments.add( new CommentUnit(element, sourceCode) );
			}
	}
	
	/**
	 * Checks if the given comment start point is not in a inner class
	 * @param commentStartPoint
	 * @param type
	 * @return
	 */
	protected boolean notInInnerTypes(int commentStartPoint, TypeDeclaration type) {
		for(TypeDeclaration t : type.getTypes())
			if(commentStartPoint > t.getStartPosition() && commentStartPoint < (t.getStartPosition() + t.getLength()))
				return false;
		return true;
	}
	
	/**
	 * Extracts every comment that belong to a given class
	 * @param type
	 * @return
	 */
	protected List<String> getTypeDeclarationComments(TypeDeclaration type) {
		MethodDeclaration[] allMethods = type.getMethods();
		
		List<String> classComments = new LinkedList<String>();
		
		// Seleciona os comentarios localizados antes de qualquer entidade de encapsulamento
		for(CommentUnit c : sourceCodeComments)
			if(notInInnerTypes(c.getStartPosition(), type) && allMethods.length != 0)
				if(c.getStartPosition() > type.getStartPosition()  && c.getStartPosition() < allMethods[0].getStartPosition())
					classComments.add(c.getComment());
		
		// Seleciona os comentarios localizados entre entidades de encapsulamento
		for (int i = 1; i < allMethods.length; i++)
			for(CommentUnit c : sourceCodeComments)
				if(notInInnerTypes(c.getStartPosition(), type)) {
					int endOfPreviousMethod = allMethods[i-1].getStartPosition() + allMethods[i-1].getLength();
					int beginOfCurrentMethod = allMethods[i].getStartPosition();
						
					if(c.getStartPosition() > endOfPreviousMethod && c.getStartPosition() < beginOfCurrentMethod)
						classComments.add(c.getComment());
				}
		
		// Seleciona os comentarios localizados depois de todas as entidades de encapsulamento mas dentro do corpo da classe
		for(CommentUnit c : sourceCodeComments)
			if(notInInnerTypes(c.getStartPosition(), type) && allMethods.length != 0) {
				int endOfPreviousMethod = allMethods[allMethods.length-1].getStartPosition() + allMethods[allMethods.length-1].getLength();
				int endOfClass = type.getStartPosition() + type.getLength();
			
				if(c.getStartPosition() > endOfPreviousMethod && c.getStartPosition() < endOfClass)
					classComments.add(c.getComment());
			}
					
		return classComments;
	}
	
	/**
	 * Extracts every comment that belongs to a given enum
	 * @param enumDeclaration
	 * @return
	 */
	protected List<String> getEnumDeclararionComments(EnumDeclaration enumDeclaration) {
		List<String> enumComments = new LinkedList<String>();
		
		for(CommentUnit c : sourceCodeComments)
			if(c.getStartPosition() > enumDeclaration.getStartPosition()
					&& c.getStartPosition() < (enumDeclaration.getStartPosition() + enumDeclaration.getLength()))
				enumComments.add(c.getComment());
				
		return enumComments;
	}
	
	/**
	 * Extracts every comment that belongs to a given method
	 * @param method
	 * @return
	 */
	protected List<String> getMethodDeclarationComments(MethodDeclaration method) {
		List<String> methodComments = new LinkedList<String>();
		
		for(CommentUnit c : sourceCodeComments)
			if(c.getStartPosition() > method.getStartPosition()
					&& c.getStartPosition() < (method.getStartPosition() + method.getLength()))
				methodComments.add(c.getComment());
		
		return methodComments;
	}
	
	/**
	 * Manages the Enum and Class term extraction and tha Physical LOC Count validation as well
	 * @param compilationUnit AST representation of a given java file
	 * @param fileName The file name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected StringBuffer extractTermsFromCompilationUnit(CompilationUnit compilationUnit, String fileName) {
		StringBuffer buffer = new StringBuffer();
		int pack = compilationUnit.getPackage() == null ? 0 : 1;
		this.commentList = compilationUnit.getCommentList();
		
		// physical loc count validation
		if(locParameters.contains(LOCParameters.ONLY_PHYSICAL_LINES)) {
			String name = fileName;
			if(name != null) {
				PhysicalLOCCount locCount = new PhysicalLOCCount(commentList, sourceCode, sourceCode.split("\n").length);
				saveCompilationUnitLOCData(name, locCount);
			}
		}
		
		packageName = compilationUnit.getPackage() == null ? "" : compilationUnit.getPackage().getName().getFullyQualifiedName() + ".";
		
		int primeiraClasse = 0;
		// for all types declared in this compilation unit
		for (AbstractTypeDeclaration compUnitType : (List<AbstractTypeDeclaration>) compilationUnit.types()) {
			// for classes
			if (compUnitType instanceof TypeDeclaration) {
				extractTermsFromType(buffer, packageName, (TypeDeclaration) compUnitType, false,
						(primeiraClasse == 0 ? compilationUnit.imports().size() + pack : 0));
				primeiraClasse++;
			// for enumerations
			} else if (compUnitType instanceof EnumDeclaration) {
				boolean innerEnum = !((EnumDeclaration)compUnitType).isPackageMemberTypeDeclaration();
				extractTermsFromEnum(buffer, packageName, (EnumDeclaration) compUnitType, innerEnum,
						!innerEnum ? compilationUnit.imports().size() + 1 : 0);
			}
		}
		
		return buffer;
	}
	
	/**
	 * Manages the Enum terms extraction, LOC Count per Entity, and generation part of the VXL File
	 * @param buffer The output buffer to be printed in the resulting VXL file
	 * @param assignment The assignment to be concatenated with the entity's name, which is External entity's name or package name.
	 * @param anEnum The Given enum to be analyzed
	 * @param isInner The information the tells if the entity is inner to any other
	 * @param headerLines Externally provided headers count.
	 */
	@SuppressWarnings("unchecked")
	private void extractTermsFromEnum(StringBuffer buffer, String assignment, EnumDeclaration anEnum, boolean isInner, int headerLines) {
		
		String enumName = assignment + anEnum.getName().getIdentifier();
				
		String enumComment = anEnum.getJavadoc() == null ? "" : CommentUnit.processComment(anEnum.getJavadoc().toString());
		String enumVisibility = extractVisiibility(anEnum);
		
		/*
		 * Validation and management of Enum LOC extraction
		 */
		if(locParameters.contains(LOCParameters.LOC) && (isInner ? locParameters.contains(LOCParameters.INNER_CLASSES) : true)) {
			LOCCountPerEntity locCounter = new LOCCountPerEntity(anEnum, commentList, sourceCode);
			EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);
			locKeeper.setHeadersLOC(headerLines, isInner, locParameters.contains(LOCParameters.HEADERS));
			saveEntityLOCData(enumName, locKeeper);
		}
		
		final String NO_COMMENT = ""; // enum constants don't have javadoc associations
		
		// adding enum's info
		this.startEnum(buffer, enumName, enumComment);
		this.enumComment(buffer, getEnumDeclararionComments(anEnum));
		
		for (EnumConstantDeclaration enumConstant : (List<EnumConstantDeclaration>) anEnum.enumConstants()) {
			String constantName = enumConstant.getName().getIdentifier();
			this.constant(buffer, constantName, enumVisibility, NO_COMMENT);
		}
		this.endEnum(buffer);
	}
	
	/**
	 * Increments the LOC per Entity metrics with the given information
	 * @param typeName Name of the analyzed entity
	 * @param entity Metrics keeper
	 */
	protected void saveEntityLOCData(String typeName, EntityLOCKeeper entity) {
		int annotations = entity.getAnnotationsLOC();
		int total = entity.getLOC() + entity.getHeaderLOC() + annotations + entity.getInnerEntitiesLOC();
		
		entitiesLOCContent = entitiesLOCContent + "\n," + typeName + "," +	entity.getLOC() + "," + entity.getHeaderLOC() +
										"," + annotations + "," + entity.getInnerEntitiesLOC() + "," + total;
		
		totalLOC += entity.getLOC();
		totalHeader += entity.getHeaderLOC();
		totalAnnotation += annotations;
		totalInnerEntitiesLines += entity.getInnerEntitiesLOC();
		totalCodeLines += total;
	}
	
	/**
	 * Increments the LOC per file with the given information
	 * @param name File name
	 * @param compilationUnit Loc information keeper
	 */
	protected void saveCompilationUnitLOCData(String name, PhysicalLOCCount compilationUnit) {
		compilationUnitLOCContent = compilationUnitLOCContent + "\n" + name + "," + compilationUnit.getLOC();
	}
	
	/**
	 * Manages the Class and Interfaces terms extraction, LOC Count per Entity, and generation part of the VXL File
	 * @param buffer The output buffer to be printed in the resulting VXL file
	 * @param assignment The assignment to be concatenated with the entity's name, which is External entity's name or package name.
	 * @param type The Given entity to be analyzed
	 * @param isInner The information the tells if the entity is inner to any other
	 * @param headerLines Externally provided headers count.
	 */
	@SuppressWarnings("unchecked")
	private void extractTermsFromType(StringBuffer buffer, String assignment, TypeDeclaration type, boolean isInner, int headerLines) {
		
		// adding identifier and modifiers' info
		String typeName = assignment + type.getName().getIdentifier();
		String typeComment = type.getJavadoc() == null ? "" : CommentUnit.processComment(type.getJavadoc().toString());
		String isInterface = type.isInterface() ? "y" : "n";
		String isInnerType = (isInner) ? "y" : "n";
		
		/*
		 * Validation and management of Type LOC Extraction
		 */
		if(locParameters.contains(LOCParameters.LOC) && (isInner ? locParameters.contains(LOCParameters.INNER_CLASSES) : true)) {
			LOCCountPerEntity locCounter = new LOCCountPerEntity(type, commentList, sourceCode);
			EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);
			locKeeper.setHeadersLOC(headerLines, isInner, locParameters.contains(LOCParameters.HEADERS));
			saveEntityLOCData(typeName, locKeeper);
		}
		
		int typeModifiers = type.getModifiers();
		String isAbstract = (Modifier.isAbstract(typeModifiers)) ? "y" : "n";
		
		this.startClass(buffer, typeName, isInterface, isAbstract, isInnerType, typeComment);
		this.classComment(buffer, getTypeDeclarationComments(type));
		
		// adding fields' info
		for (FieldDeclaration field : ((TypeDeclaration) type).getFields())
			extractTermsFromField(buffer, field);
		
		// adding methods' info
		for (MethodDeclaration method : ((TypeDeclaration) type).getMethods())
			extractTermsFromMethod(buffer, typeName + ".", method);
		
		// adding enum info
		for (BodyDeclaration bodyDecl : (List<BodyDeclaration>) (((TypeDeclaration) type).bodyDeclarations()))
			if (bodyDecl instanceof EnumDeclaration) {
				extractTermsFromEnum(buffer, typeName + "$", (EnumDeclaration) bodyDecl, true, 0);
			}
		
		// adding inner classes' info
		for (TypeDeclaration innerType : type.getTypes())
			extractTermsFromType(buffer, typeName + "$", innerType, true, 0);
		
		this.endClass(buffer);
	}
	
	/**
	 * Saves the information from the AST about the field
	 * @param buffer buffer which keeps the result VXL file content
	 * @param field AST informations
	 */
	@SuppressWarnings("unchecked")
	private void extractTermsFromField(StringBuffer buffer, FieldDeclaration field) {
		
		for (VariableDeclarationFragment vdf : (List<VariableDeclarationFragment>) field.fragments()) {
			
			String fieldName = vdf.getName().getIdentifier();
			String fieldComment = field.getJavadoc() == null ? "" : CommentUnit.processComment(field.getJavadoc().toString());
			String fieldVisibility = extractVisiibility(field);
			
			// checks if a field is a constant
			int fieldModifiers = field.getModifiers();
			if (Modifier.isFinal(fieldModifiers)) {
				this.constant(buffer, fieldName, fieldVisibility, fieldComment);
			} else {
				this.field(buffer, fieldName, fieldVisibility, fieldComment);
			}
		}
	}
	
	/**
	 * Returns a converted version of the parameter models related to Generic Programming
	 * @param parametro String representation of the paremeter
	 * @return
	 */
	private static String validaParametro(String parametro) {
		while(parametro.contains("<"))
			parametro = parametro.replace("<", ":");
		while(parametro.contains(", "))
			parametro = parametro.replace(", ", "-");
		while(parametro.contains(">"))
			parametro = parametro.replace(">", "");
		
		return parametro;
	}
	
	/**
	 * Returns a representation of the parameter part of the given method
	 * @param method AST method information keeper
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getParametersAssignatures(MethodDeclaration method) {
		String retorno = "(";
		
		int indiceDoParametro = 1;
		int numeroDeParametros = ((List<SingleVariableDeclaration>) method.parameters()).size();
		for(SingleVariableDeclaration parameter : (List<SingleVariableDeclaration>) method.parameters())
			retorno = retorno + validaParametro(parameter.getType().toString()) + (indiceDoParametro++ == numeroDeParametros ? "" : ", ");
		return retorno + ")";
	}
	
	
	/**
	 * Saves the information provided by the AST about a given method
	 * @param buffer buffer which keeps the result VXL's content
	 * @param assignature The assignment to be concatenated with the method's name, which is External entity's name or package name.
	 * @param method AST method information keeper
	 */
	@SuppressWarnings("unchecked")
	private void extractTermsFromMethod(StringBuffer buffer, String assignature, MethodDeclaration method) {
		
		String methodName = assignature + method.getName().getIdentifier() + getParametersAssignatures(method);
		String methodComment = method.getJavadoc() == null ? "" : CommentUnit.processComment(method.getJavadoc().toString());
		String methodVisibility = extractVisiibility(method);
		
		// adding method name
		this.startMethod(buffer, methodName, methodVisibility, methodComment);
		this.methodComment(buffer, getMethodDeclarationComments(method));
		
		// adding method parameter
		for (SingleVariableDeclaration parameter : (List<SingleVariableDeclaration>) method.parameters())
			this.parameter(buffer, parameter.getName().getIdentifier());
		
		// adding local variables
		if (method.getBody() != null) {
			for (Statement statement : (List<Statement>) method.getBody().statements())
				if (statement instanceof VariableDeclarationStatement) {
					VariableDeclarationStatement vds = (VariableDeclarationStatement) statement;
					for (VariableDeclarationFragment vdf : (List<VariableDeclarationFragment>) vds.fragments())
						this.localVariable(buffer, vdf.getName().getIdentifier());
				}
		}
		
		// finishing method
		this.endMethod(buffer);
	}
	
	/**
	 * Returns the visibility from an entity in a String
	 * @param entityDeclaration AST representation of any structure which possesses a body
	 * @return
	 */
	protected String extractVisiibility(BodyDeclaration entityDeclaration) {
		
		String entityVisibility = "friendly";
		
		// verifying method modifiers
		int methodModifiers = entityDeclaration.getModifiers();
		if (Modifier.isPublic(methodModifiers)) {
			entityVisibility = "public";
		} else if (Modifier.isPrivate(methodModifiers)) {
			entityVisibility = "private";
		} else if (Modifier.isProtected(methodModifiers)) {
			entityVisibility = "protected";
		} else if (methodModifiers == Modifier.NONE) {
			entityVisibility = "friendly";
		}
		
		return entityVisibility;
	}
}