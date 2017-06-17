package org.splabs.vocabulary.vxl.extractor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.splabs.vocabulary.vxl.vloccount.LOCParameters;

/**
 * A term extractor for Eclipse's Java Project Specification
 * @author gustavojss
 */
public class ASTVocabularyExtractor extends SourceCodeVocabularyExtractor {
	
	

	private IJavaProject javaProject;
	
	/**
	 * Creates a new term extractor for Eclipse's Java Project Specification
	 * @param javaProject the Eclipse's project
	 * @param javaProjectName the project's name
	 * @param javaProjectRevision the project's revision name
	 */
	public ASTVocabularyExtractor(IJavaProject javaProject, String javaProjectName, String javaProjectRevision, List<LOCParameters> locParameters) {
		super(javaProjectName, javaProjectRevision, locParameters);
		this.javaProject = javaProject;
		
		extractTermsFromProject();
	}
	
	@Override
	protected void extractTermsFromProject() {
		
		try {
			// for all folders 
			for (IPackageFragmentRoot pfr : this.javaProject.getPackageFragmentRoots())
				if (pfr instanceof PackageFragmentRoot) {
					extractTermsFromProjectFolder(pfr);
				}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.endProject();
	}
	
	/**
	 * Extracts terms from the project folder
	 * @param packageFragmentRoot the folder specification
	 * @throws JavaModelException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void extractTermsFromProjectFolder(IPackageFragmentRoot packageFragmentRoot) throws JavaModelException, InvocationTargetException, InterruptedException {
		
		if (!(packageFragmentRoot instanceof JarPackageFragmentRoot)) {
			for (IJavaElement javaElement : packageFragmentRoot.getChildren())
				if (javaElement instanceof IPackageFragment) {
					extractTermsFromPackage((IPackageFragment) javaElement);
				}
		}
	}
	
	/**
	 * Extracts terms from the java package
	 * @param pckg the package specification
	 * @throws JavaModelException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void extractTermsFromPackage(IPackageFragment pckg) throws JavaModelException, InvocationTargetException, InterruptedException {
		
		this.startPackage(pckg.getElementName());
		
		for (ICompilationUnit compilationUnit : pckg.getCompilationUnits())
			extractTermsFromCompilationUnit(compilationUnit);
		
		this.endPackage();
	}
	
	/**
	 * Extracts terms from a .java file
	 * @param compilationUnit
	 * @throws JavaModelException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void extractTermsFromCompilationUnit(ICompilationUnit compilationUnit) throws JavaModelException, InvocationTargetException, InterruptedException {
		
		// extract AST tree from source code
		char[] sourceCode = compilationUnit.getSource().toCharArray();
		getASTTreeFromSourceCode(sourceCode);
		
		ASTNode tree = super.astNode;
		CompilationUnit astCompilationUnit = (CompilationUnit) tree;
		//extractTermsFromCompilationUnit(astCompilationUnit);
	}
}