# Created by: Matheus de Souza Coutinho - UFCG/IFPB
# VocabularyExtractor - Python

# DEF -> nome e parametros de cada... 100%
# Linhas em branco e comentarios... 100%
# Atributos... 100%
# Comentarios nos metodos das classes... 100%
# Comentarios nos metodos do body... 100%
# Comentarios nas classes... 100%
# Comentarios so no corpo... 100%
# Comentarios na classe... 100%
# Comentarios nos metodos... 100%


#Bugs:
    # Comentarios na ultima linha de qualquer elemento ficam perdidos(Nao sabem a quem pertence)

import ast
import __future__
import tokenize
import StringIO

pfcf = __future__.print_function.compiler_flag

from ast import dump, PyCF_ONLY_AST

# Tratando classes
listaClasses = []
arquivo = open('arquivoTest3.txt', 'w')
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


# Visitor = adiciona na lista, transformer = transforma em string cada no

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

#Extrai comentarios
def extract(code):
    stringio = StringIO.StringIO(code)
    for toktype, tokval, begin, end, line in tokenize.generate_tokens(stringio.readline):
        if toktype == tokenize.COMMENT:
            comments.append(tokenize.untokenize([(toktype, tokval)])[1:])
            coments_lines.append(lineNumber)


# Nao usa
def correctingPositions(node):
    if len(listaNomeVariaveis) > len(listaValores):
        listaValores[auxVars].append(node.id)
        listaNomeVariaveis.pop()
    else:
        arquivo.write("     "*writeLines+"<field name=\""+node.id+"\""" />\n")


#Pega o tamanho dele e saber se tem comentarios
def comentsDefinition(superior, inferior):
    definedComments = []
    for i in range(len(coments_lines)-1,-1,-1):
        if superior <= coments_lines[i]+1 and inferior >= coments_lines[i]+1:
            definedComments.append(comments[i])
            comments.pop(i)
            coments_lines.pop(i)

    for i in range(len(definedComments)):
        arquivo.write("     "*writeLines+"<cmtt name=\""+definedComments[i]+"\""" />\n")

# pega as linhas em branco
def catchWhiteLines(s):
    for line in s.split('\n'):
        if len(line.strip()) == 0:
            linhas_branco.append(lineNumber)
        else:
            extract(line)
        global lineNumber
        lineNumber += 1

# Pegar as variaveis
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

# Pegar o nome da funcao e parametros
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
        superiorClasse = classes_definitions[i].lineno

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

        #Captura primeiro o nome das funcoes
        for k in range(len(name_definitionss)):
            superior = name_definitionss[k].lineno
            catchNameDef(name_definitionss[k])

        #Agora entra no corpo das funcoes dentro da class
            nodes = [node for node in name_definitionss[k].body]
            insideMthClass(True)
            global writeLines
            writeLines = x+2
            for i in range(len(nodes)):
                inferior = 0
                # Capturando as variaveis dentro da funcao da class
                if isinstance(nodes[i], ast.Assign):
                    catchAssign(nodes[i])

                if i == len(nodes)-1:
                    inferior = nodes[i].lineno
            comentsDefinition(superior, inferior)
            writeLines-=1
            arquivo.write("     "*writeLines+"</mth>\n")
        if i == len(nodes)-1:
            inferiorClasse = nodes[i].lineno
        insideMthClass(False)
        comentsDefinition(superiorClasse, inferiorClasse)
        catchClass(insideClass, x+1)
        arquivo.write("    "*x+"</class>\n")

#Metodo responsavel para definir a posicao de escrita do XVL
def insideMthClass(status):
    global dentroDoMetodoDaClasse
    dentroDoMetodoDaClasse = status

# Chama tudo
def d(s):
    #Capturando a posicao das linhas em branco
    catchWhiteLines(s)
    #Recebendo a astParse do arquivo
    module = ast.parse(s)

    #Criando listas com os metodos, classes e resto do modulo

    #Criando lista das funcoes do modulo
    name_definitions = [node for node in module.body if isinstance(node, ast.FunctionDef)]

    #Criando lista das classes do modulo
    classes_definitions = [node for node in module.body if isinstance(node, ast.ClassDef)]

    #Criando lista com o resto das estruturas
    nodes_body = [node for node in module.body]


    insideMthClass(False)

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
    for i in range(len(name_definitions)):
        catchNameDef(name_definitions[i])
        global writeLines
        writeLines = 2
        nodes = [node for node in name_definitions[i].body]
        inferior = 0
        superior = name_definitions[i].lineno
        for k in range(len(nodes)):
            if isinstance(nodes[k], ast.Assign):
               insideMthClass(True)
               catchAssign(nodes[k])
            if isinstance(nodes[k], ast.For):
               ForAndWhileCatch(nodes[k])
            if isinstance(nodes[k], ast.While):
               ForAndWhileCatch(nodes[k])
            if isinstance(nodes[k], ast.If):
               IfInIfCatch(nodes[k])

            if k == len(nodes)-1:
                inferior = nodes[k].lineno
        comentsDefinition(superior, inferior)
        global writeLines
        writeLines = 1
        arquivo.write("     </mth>\n")

    #Escrevendo comentarios do modulo
    for i in range(len(comments)-1,-1,-1):
        arquivo.write("     <cmtt name=\""+comments[i]+"\""" />\n")
        comments.pop(i)

    arquivo.write("</module>\n")

from os import walk
s = []
allFiles = ""
myPath = 'C:/Users/mathe/Downloads/Tcc_IFPB-master/untitled'
for root, dirs, files in walk(myPath):
    for file in files:
        if file.endswith('codigoMenor.py'):
            openFile = open(root+"/"+file, "r")
            for lines in openFile.readlines():
                allFiles += lines
            arquivo.write("<module name=\"" + file + "\">\n")
            d(allFiles)
            allFiles = ""
