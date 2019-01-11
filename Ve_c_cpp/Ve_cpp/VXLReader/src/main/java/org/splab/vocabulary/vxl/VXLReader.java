package org.splab.vocabulary.vxl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPClass;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPFile;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPProject;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.javamodel.Enum;
import org.splab.vocabulary.vxl.iterator.javamodel.Func;
import org.splab.vocabulary.vxl.iterator.javamodel.Method;
import org.splab.vocabulary.vxl.iterator.javamodel.Struct;
import org.splab.vocabulary.vxl.iterator.javamodel.Union;

/**
 * A main class for entity extraction from a .vxl file
 * 
 * @author gustavojss / Catharine Quintans
 * @author Israel Gomes de Lima
 * 
 *         Modificado por Israel Gomes de Lima a partir da classe original feita
 *         por: gustavojss / Catharine Quintans
 */
public class VXLReader {

	private VXLIterator iterator;

	/**
	 * Creates a new reader with the given properties
	 * 
	 * @param props
	 *            a set of properties
	 */
	public VXLReader(Properties props) {
		this.iterator = VXLIterator.createNewIterator(props);
	}

	/**
	 * Creates a new reader with the given properties and .vxl file
	 * 
	 * @param props
	 *            a set of properties
	 * @param vxlFileName
	 *            the .vxl file name
	 * @throws JAXBException
	 *             if there's a problem extracting information from the .vxl
	 *             file
	 */
	public VXLReader(Properties props, String vxlFileName) throws JAXBException {
		this.iterator = VXLIterator.createNewIterator(props);
		this.load(vxlFileName);
	}

	/**
	 * Loads the specified .vxl file, extracting entities from it
	 * 
	 * @param vxlFileName
	 *            the .vxl file name
	 * @throws JAXBException
	 *             if there's a problem extracting information from the .vxl
	 *             file
	 */
	public void load(String vxlFileName) throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance("org.splab.vocabulary.vxl.iterator.javamodel");

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		CPPProject cProject = (CPPProject) unmarshaller.unmarshal(new File(vxlFileName));
		this.iterator.setCProject(cProject);
	}

	/**
	 * Returns an entity iterator for this reader
	 * 
	 * @return an entity iterator
	 */
	public VXLIterator iterator() {
		return this.iterator;
	}

	/**
	 * Simple Execution for term extraction from a .vxl file
	 * 
	 * @param args
	 *            USAGE: [vxl file name] [properties file name]
	 * @throws IOException
	 *             if there's a problem reading the file
	 * @throws FileNotFoundException
	 *             if there's no property file with the given name
	 * @throws JAXBException
	 *             if there's a problem extracting information from the .vxl
	 *             file
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, JAXBException {
		final String MANUAL = "Invalid input. You must set the following options:"
				+ "\n\t-vxl: the path/name of the resulting VXL file"
				+ "\n\t-prop: the path/name of the resulting properties file"
				+ "\n\n\tEXAMPLE: -vxl ~/ProjectVXL.vxl -prop ~/ProjectProperties.properties";

		try {
			String vxlFileName = args[0];
			String propsFileName = args[1];

			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-vxl")) {
					vxlFileName = args[++i];
				} else if (args[i].equals("-prop")) {
					propsFileName = args[++i];
				}
			}

			// loading properties
			Properties props = new Properties();
			props.load(new BufferedInputStream(new FileInputStream(propsFileName)));

			// creating an entity iterator
			VXLReader reader = new VXLReader(props, vxlFileName);
			VXLIterator iterator = reader.iterator();

			// iterating all entities
			int containerCount = 0;
			int enumInnerCount = 0;
			int enumExternCount = 0;
			int structInnerCount = 0;
			int structExternCount = 0;
			int unionInnerCount = 0;
			int unionExternCount = 0;
			int functionInnerCount = 0;
			int functionExternCount = 0;
			int classInnerCount = 0;
			int classExternCount = 0;
			int methodGeneralCount = 0;
			int fileCount = 0;

			while (iterator.hasNext()) {

				containerCount++;
				ContainerEntity container = iterator.next();
				Object obj = container.getEntity();

				if (obj instanceof CPPFile) {
					CPPFile file = (CPPFile) obj;
					fileCount++;
					System.out.println("FILE: " + file.getName());
					System.out.println(container.getIdentifiers());

					for (Enum eEnum : file.getEnum()) {
						enumExternCount++;
						System.out.println("ENUM EXTERNO: " + eEnum.getName());
						System.out.println(container.getIdentifiers());
					}

					for (Union eUnion : file.getUnion()) {
						unionExternCount++;
						System.out.println("UNION EXTERNO: " + eUnion.getName());
						System.out.println(container.getIdentifiers());
					}

					for (Struct eStruct : file.getStrt()) {
						structExternCount++;
						System.out.println("STRUCT EXTERNO: " + eStruct.getName());
						System.out.println(container.getIdentifiers());
					}

					for (CPPClass clazz : file.getClazz()) {
						containerCount++;
						classExternCount++;

						System.out.println("CLASS: " + clazz.getName());
						System.out.println(container.getIdentifiers());

						for (Enum eEnum : clazz.getEnum()) {
							enumInnerCount++;
							System.out.println("ENUM INTERNO: " + eEnum.getName() + "  A CLASSE " + clazz.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Union eUnion : clazz.getUnion()) {
							unionInnerCount++;
							System.out.println("UNION INTERNO: " + eUnion.getName() + "  A CLASSE " + clazz.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Struct eStruct : clazz.getStrt()) {
							structInnerCount++;
							System.out
									.println("STRUCT INTERNO: " + eStruct.getName() + "  A CLASSE " + clazz.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Func eFunc : clazz.getFunc()) {
							functionInnerCount++;
							System.out
									.println("FUNCTION INTERNO: " + eFunc.getName() + "  A CLASSE " + clazz.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Method eMethod : clazz.getMth()) {
							methodGeneralCount++;
							System.out.println("METODO: " + eMethod.getName() + "  DA CLASSE " + clazz.getName());
							System.out.println(container.getIdentifiers());

							for (Enum eEnum : eMethod.getEnum()) {
								enumInnerCount++;
								System.out.println(
										"ENUM INTERNO: " + eEnum.getName() + "  AO METODO " + eMethod.getName());
								System.out.println(container.getIdentifiers());
							}

							for (Union eUnion : eMethod.getUnion()) {
								unionInnerCount++;
								System.out.println(
										"UNION INTERNO: " + eUnion.getName() + "   AO METODO " + eMethod.getName());
								System.out.println(container.getIdentifiers());
							}

							for (Struct eStruct : eMethod.getStrt()) {
								structInnerCount++;
								System.out.println(
										"STRUCT INTERNO: " + eStruct.getName() + "  AO METODO " + eMethod.getName());
								System.out.println(container.getIdentifiers());
							}

							for (Func eFunc : eMethod.getFunc()) {
								functionInnerCount++;
								System.out.println(
										"FUNCTION INTERNO: " + eFunc.getName() + "  AO METODO " + eMethod.getName());
								System.out.println(container.getIdentifiers());
							}

							for (CPPClass mEClass : eMethod.getClazz()) {
								classInnerCount++;
								System.out.println(
										"CLASSE INTERNA: " + mEClass.getName() + "  AO METODOE " + eMethod.getName());
								System.out.println(container.getIdentifiers());
							}
						}

						for (CPPClass eClass : clazz.getClazz()) {
							classInnerCount++;
							System.out.println("CLASSE INTERNA: " + eClass.getName() + "  A CLASSE " + clazz.getName());
							System.out.println(container.getIdentifiers());
						}
					}

					for (Func func : file.getFunc()) {
						containerCount++;
						functionExternCount++;

						System.out.println("FUNC: " + func.getName());
						System.out.println(container.getIdentifiers());

						for (Enum eEnum : func.getEnum()) {
							enumInnerCount++;
							System.out.println("ENUM INTERNO: " + eEnum.getName() + " a FUNCAO: " + func.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Union eUnion : func.getUnion()) {
							unionInnerCount++;
							System.out.println("UNION INTERNO: " + eUnion.getName() + " a FUNCAO: " + func.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Struct eStruct : func.getStrt()) {
							structInnerCount++;
							System.out.println("STRUCT INTERNO: " + eStruct.getName() + " a FUNCAO: " + func.getName());
							System.out.println(container.getIdentifiers());
						}

						for (Func eFunc : func.getFunc()) {
							functionInnerCount++;
							System.out.println("FUNCTION INTERNO: " + eFunc.getName() + " a FUNCAO: " + func.getName());
							System.out.println(container.getIdentifiers());
						}

						for (CPPClass eClass : func.getClazz()) {
							classInnerCount++;
							System.out.println("CLASSE INTERNA: " + eClass.getName() + "  A CLASSE " + func.getName());
							System.out.println(container.getIdentifiers());
						}
					}
				}
				System.out.println(container.getIdentifiers());
			}

			System.out.println("Total: " + containerCount + " entities");
			System.out.println("Total: " + enumInnerCount + " enumInternos");
			System.out.println("Total: " + enumExternCount + " enumExternos");
			System.out.println("Total: " + unionInnerCount + " unionInternos");
			System.out.println("Total: " + unionExternCount + " unionExternos");
			System.out.println("Total: " + structInnerCount + " structInternos");
			System.out.println("Total: " + structExternCount + " structExternos");
			System.out.println("Total: " + functionInnerCount + " functionInternos");
			System.out.println("Total: " + functionExternCount + " functionExternos");
			System.out.println("Total: " + classInnerCount + " classInternos");
			System.out.println("Total: " + classExternCount + " classExternos");
			System.out.println("Total: " + methodGeneralCount + " methodsTotais");
			System.out.println("Total: " + fileCount + " files");

			printProperties(props);

			System.out.println("VXL Reader has finished.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(MANUAL);
		}
	}

	private static void printProperties(Properties props) {
		System.out.println("------------- Properties -------------");
		for (Object key : props.keySet())
			System.out.println(" " + key.toString() + ": " + props.getProperty(key.toString()));
		System.out.println("--------------------------------------");
	}
}