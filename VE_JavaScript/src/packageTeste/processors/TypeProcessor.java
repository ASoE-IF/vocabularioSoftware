package packageTeste.processors;

import org.eclipse.wst.jsdt.core.dom.TypeDeclaration;

public class TypeProcessor {

    public static void process(TypeDeclaration typeDec) {
        System.out.println("Classe/Interface encontrada:");
        System.out.println("\t" + extractName(typeDec));
    }

    private static String extractName(TypeDeclaration typeDec) {
        return typeDec.getName().toString();
    }

//    private static String[] extractMethods(TypeDeclaration typeDec) {
//        // todo
//    }
//
//    private static String[] extractFields(TypeDeclaration typeDec) {
//        // todo
//    }
}
