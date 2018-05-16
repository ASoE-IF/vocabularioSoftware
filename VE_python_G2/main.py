# Created by: Matheus de Souza Coutinho - UFCG/IFPB
# VocabularyExtractor - Python

# 2 - arquivo que vai ser extraido
# 3 - nome do arquivo que vai ser feito
# 4 - diretorio p onde vai

# C:\Python27\python ./PycharmProjects\untitled1\testes.py C:/Users/Matheus/Documents/TCC_IFPB/untitled/arquivosPython arquivoNovo.xml C:/Users/Matheus/PycharmProjects/untitled1
import ast
import __future__
import tokenize
import StringIO
import sys

pfcf = __future__.print_function.compiler_flag

from ast import dump, PyCF_ONLY_AST

# Tratando classes
listaClasses = []
arquivo = open(sys.argv[3] + sys.argv[2], 'w')
dentroDoMetodoDaClasse = True
writeLines = 0

# Tratando comentarios e linhas em branco
lineNumber = 0
comments = []
coments_lines = []
linhas_branco = []

# Tratando o resto
auxVars = 0
listaNomeVariaveis = []
listaValores = []
listaPrint = []
listaNumLine = []

# Tratando o def
auxFunctions = 0
listaNomesFuncoes = []
listaVarsFuncoes = []

imports = []

class MyVisitorVars(ast.NodeVisitor):
    def visit_Name(self, node):
        listaVarsFuncoes[auxFunctions].append(node.id)
        arquivo.write("     "*writeLines+"<param name=\""+node.id+"\""" />\n")


class MyTransformerVars(ast.NodeTransformer):
    def visit_Name(self, node):
        return ast.Name(node.id, self)


class MyVisitorNameDef(ast.NodeVisitor):
    def visit_FunctionDef(self, node):
        listaNomesFuncoes.append(node.name)
        arquivo.write("     "*writeLines+"<mth name=\""+node.name+"\""">\n")


class MyTransformerNameDef(ast.NodeTransformer):
    def visit_FunctionDef(self, node):
        return ast.FunctionDef()


class MyVisitorPrint(ast.NodeVisitor):
    def visit_Str(self, node):
        listaPrint.append(node.s)


class MyTransformerPrint(ast.NodeTransformer):
    def visit_Str(self, node):
        return ast.Str(node.s)


class MyVisitorString(ast.NodeVisitor):
    def visit_Str(self, node):
        listaValores[auxVars].append(node.s)


class MyTransformerString(ast.NodeTransformer):
    def visit_Str(self, node):
        return ast.Str(node.s)


class MyVisitorNum(ast.NodeVisitor):
    def visit_Num(self, node):
        listaValores[auxVars].append(node.n)



class MyTransformerNum(ast.NodeTransformer):
    def visit_Num(self, node):
        return ast.Num(node.n)


class MyVisitorName(ast.NodeVisitor):
    def visit_Name(self, node):
        listaNomeVariaveis.append(node.id)
        correctingPositions(node)


class MyTransformerName(ast.NodeTransformer):
    def visit_Name(self, node):
        return ast.Name(node.id, self)

class MyVisitorNameIf(ast.NodeVisitor):
    def visit_Name(self, node):
        listaNomeVariaveis.append(node.id)
        arquivo.write("     "*writeLines+"<field name=\""+node.id+"\""" />\n")

class MyTransformerImport(ast.NodeTransformer):
    def visit_Import(self, node):
        return ast.alias(node.name)

class MyVisitorImport(ast.NodeVisitor):
    def visit_Import(self, node):
        imports.append(node.name)



class MyTransformerNameIf(ast.NodeTransformer):
    def visit_Name(self, node):
        return ast.Name(node.id, self)

def IfInIfCatch(node):
    if isinstance(node, ast.If):
        IfinIf(node)
    if isinstance(node, ast.While):
        ForInFor(node)
    if isinstance(node, ast.For):
        ForInFor(node)
    listaValores.append([])
    MyTransformerNameIf().visit(node)
    MyVisitorNameIf().visit(node)
    global auxVars
    auxVars += 1

def IfinIf(nodeCatch):
    nodes = [node for node in nodeCatch.body]
    for i in range(len(nodes)):
        IfInIfCatch(nodes[i])

def ForAndWhileCatch(node):
    if isinstance(node, ast.For):
        ForInFor(node)
    if isinstance(node, ast.While):
        ForInFor(node)
    if isinstance(node ,ast.If):
        IfinIf(node)
    listaValores.append([])
    MyTransformerName().visit(node)
    MyVisitorName().visit(node)
    global auxVars
    auxVars += 1


def ForInFor(nodeCatch):
    nodes = [node for node in nodeCatch.body]
    for i in range(len(nodes)):
        ForAndWhileCatch(nodes[i])

def extract(code):
    stringio = StringIO.StringIO(code)
    coments_lines = []
    for toktype, tokval, begin, end, line in tokenize.generate_tokens(stringio.readline):
        if tokenize.COMMENT == toktype:
            comments.append(tokenize.untokenize([(toktype, tokval)])[1:])
            coments_lines.append(begin[0])
        if toktype == 3 and begin[0] != end[0]:
            comments.append(line)
            coments_lines.append(begin[0])

def correctingPositions(node):
    if len(listaNomeVariaveis) > len(listaValores):
        listaValores[auxVars].append(node.id)
        listaNomeVariaveis.pop()
    else:
        arquivo.write("     "*writeLines+"<field name=\""+node.id+"\""" />\n")


def comentsDefinition(superior, inferior):
    definedComments = []
    for i in range(len(coments_lines)-1,-1,-1):
        if superior <= coments_lines[i]+1 and inferior >= coments_lines[i]+1:
            print coments_lines
            definedComments.append(comments[i])
            comments.pop(i)
            coments_lines.pop(i)

    for i in range(len(definedComments)):
        arquivo.write("     "*writeLines+"<cmtt name=\""+definedComments[i]+"\""" />\n")

def catchWhiteLines(s):
    extract(s)
    for line in s.split('\n'):
        if len(line.strip()) == 0:
            linhas_branco.append(lineNumber)
        global lineNumber
        lineNumber += 1

def catchAssign(body):
    listaValores.append([])
    listaNumLine.append(body.lineno)
    MyTransformerString().visit(body)
    MyVisitorString().visit(body)
    MyTransformerNum().visit(body)
    MyVisitorNum().visit(body)
    MyTransformerName().visit(body)
    MyVisitorName().visit(body)
    global auxVars
    auxVars += 1

def catchNameDef(body):
    listaVarsFuncoes.append([])
    MyTransformerNameDef().visit(body)
    MyVisitorNameDef().visit(body)
    global writeLines
    writeLines+=1
    MyTransformerVars().visit(body.args)
    MyVisitorVars().visit(body.args)
    global writeLines
    writeLines-=1
    global auxFunctions
    auxFunctions += 1

def catchClass(classes_definitions, x):
    global writeLines
    writeLines = x
    for i in range(len(classes_definitions)):
        arquivo.write("     "*x+"<class name=\""+classes_definitions[i].name+"\""">\n")
        superior = classes_definitions[i].lineno
        inferior = 0
        #Criando lista das funcoes da class atual
        name_definitionss = [node for node in classes_definitions[i].body if isinstance(node, ast.FunctionDef)]

        #Criando lista com os nodes do corpo da class
        body_definitios = [node for node in classes_definitions[i].body]

        #Criando lista com as classes
        insideClass = [node for node in classes_definitions[i].body if isinstance(node, ast.ClassDef)]

        insideMthClass(True)
        #Capturando as variaveis da class
        global writeLines
        writeLines = x+1
        for k in range(len(body_definitios)):
            if isinstance(body_definitios[k], ast.Assign):
                catchAssign(body_definitios[k])
            #Procurando estruturas primarias na class
            if isinstance(body_definitios[k], ast.If):
                IfinIf(body_definitios[k])
            if isinstance(body_definitios[k], ast.While):
                ForAndWhileCatch(body_definitios[k])
            if isinstance(body_definitios[k], ast.For):
                ForAndWhileCatch(body_definitios[k])
            if k == len(body_definitios)-1:
                inferior = body_definitios[k].lineno
        catchFunction(name_definitionss, x+1)
        catchClass(insideClass, x+1)
        comentsDefinition(superior, inferior)
        arquivo.write("    "*x+"</class>\n")

def catchFunction(names_definitions, x):
    global writeLines
    writeLines = x
    for i in range(len(names_definitions)):
        catchNameDef(names_definitions[i])
        superiorFunction = names_definitions[i].lineno
        inferiorFunction = 0
        global writeLines
        writeLines = x+1
        names_definitionss = [node for node in names_definitions[i].body if isinstance(node, ast.FunctionDef)]
        class_definitions = [node for node in names_definitions[i].body if isinstance(node, ast.ClassDef)]
        body_definitios = [node for node in names_definitions[i].body]
        for k in range(len(body_definitios)):
            if isinstance(body_definitios[k], ast.Assign):
                catchAssign(body_definitios[k])
            #Procurando estruturas primarias na class
            if isinstance(body_definitios[k], ast.If):
                IfinIf(body_definitios[k])
            if isinstance(body_definitios[k], ast.While):
                ForAndWhileCatch(body_definitios[k])
            if isinstance(body_definitios[k], ast.For):
                ForAndWhileCatch(body_definitios[k])
            if k == len(body_definitios)-1:
                inferiorFunction = body_definitios[k].lineno

        insideMthClass(False)
        catchClass(class_definitions, x+1)
        catchFunction(names_definitionss, x+1)
        comentsDefinition(superiorFunction, inferiorFunction)
        writeLines-=1
        arquivo.write("     "*writeLines+"</mth>\n")



def catchImports(importsss):
    for i in range(len(importsss)):
        astAlias = [node for node in importsss[i].names if isinstance(node, ast.alias)]
        arquivo.write("<imp name = \""+astAlias[0].name+"\""" />\n")

#Metodo responsavel para definir a posicao de escrita do XVL
def insideMthClass(status):
    global dentroDoMetodoDaClasse
    dentroDoMetodoDaClasse = status

def main(s):
    #Capturando a posicao das linhas em branco
    catchWhiteLines(s)
    #Recebendo a astParse do arquivo
    module = ast.parse(s)
    modules = [node for node in module.body if isinstance(node, ast.Import)]

    #Criando lista das funcoes do modulo
    name_definitions = [node for node in module.body if isinstance(node, ast.FunctionDef)]

    #Criando lista das classes do modulo
    classes_definitions = [node for node in module.body if isinstance(node, ast.ClassDef)]

    #Criando lista com o resto das estruturas
    nodes_body = [node for node in module.body]


    insideMthClass(False)

    catchImports(modules)
    #TOTAL REFATORAMENTO DESSA PARTE
    catchClass(classes_definitions, 1)
    insideMthClass(False)
    global writeLines
    writeLines = 1
    #Capturando variaveis do modulo
    for i in range(len(nodes_body)):
        if isinstance(nodes_body[i], ast.Assign):
            catchAssign(nodes_body[i])
    #Capturando estruturas do modulo
        if isinstance(nodes_body[i], ast.If):
            IfinIf(nodes_body[i])
        if isinstance(nodes_body[i], ast.While):
            ForAndWhileCatch(nodes_body[i])
        if isinstance(nodes_body[i], ast.For):
            ForAndWhileCatch(nodes_body[i])
    #Capturando funcoes do modulo
    catchFunction(name_definitions, 1)

    #Escrevendo comentarios do modulo
    for i in range(len(comments)-1,-1,-1):
        arquivo.write("     <cmtt name=\""+comments[i]+"\""" />\n")
        comments.pop(i)

    arquivo.write("</module>\n")

from os import walk
s = []
allLines = ""
myPath = sys.argv[1]
for root, dirs, files in walk(myPath):
    for file in files:
        if file.endswith('.py'):
            openFile = open(root+"/"+file, "r")
            for lines in openFile.readlines():
                allLines += lines
            arquivo.write("<module name=\"" + file + "\">\n")
            main(allLines)
            allLines = ""
