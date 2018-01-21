# DEF -> nome e parametros de cada... 100%
# Linhas em branco e comentarios... 100%
# Variaveis -> Falta o binOp e outras coisas
# Imports ???
# tratar listas... miss!
# Posicao na linha de cada um...100%...Falta testar!
# Determinar em que cada elemento esta nas funcoes -> Buscar maneira melhor que listas simples

# Melhorar esse codigo lixo, deixar com melhor desempenho, talvez O(nlogn)

import ast
import __future__
import tokenize
import StringIO

pfcf = __future__.print_function.compiler_flag

from ast import dump, PyCF_ONLY_AST

# Tratando comentarios e linhas em branco
lineNumber = 0
comentarios = []
linhas_comentarios = []
linhas_branco = []

# Tratando o resto
listaNomeVariaveis = []
listaValores = []
listaPrint = []

# Tratando o def
auxFunctions = 0
listaNomesFuncoes = []
listaVarsFuncoes = []


class MyVisitor(ast.NodeVisitor):
    def visit_Name(self, node):
        listaVarsFuncoes[auxFunctions].append(node.id)

    def visit_FunctionDef(self, node):
        listaNomesFuncoes.append(node.name)

    def visit_Str(self, node):
        listaPrint.append(node.s)

    def visit_StrValues(self, node):
        listaValores.append(node.s)

    def visit_Num(self, node):
        listaValores.append(node.n)

    def visit_NameVariaveis(self, node):
        listaNomeVariaveis.append(node.id)

class MyTransformer(ast.NodeTransformer):
    def visit_Name(self, node):
        return ast.Name(node.id, self)

    def visit_FunctionDef(self, node):
        return ast.FunctionDef()


    def visit_Str(self, node):
        return ast.Str(node.s)

    def visit_StrValues(self, node):
        return ast.Str(node.s)

    def visit_Num(self, node):
        return ast.Num(node.n)

    def visit_NameVariaveis(self, node):
        return ast.Name(node.id, self)


def extract(code):
    comment = None
    stringio = StringIO.StringIO(code)
    # pass in string  io.readline to generate_tokens
    for toktype, tokval, begin, end, line in tokenize.generate_tokens(stringio.readline):
        if toktype == tokenize.COMMENT:
            comentarios.append(tokenize.untokenize([(toktype, tokval)]))
            linhas_comentarios.append(lineNumber)


def d(s):

    for line in s.split('\n'):
        if len(line.strip()) == 0:
            linhas_branco.append(lineNumber)
        else:
            extract(line)
        global lineNumber
        lineNumber += 1

    print comentarios
    print linhas_comentarios
    print linhas_branco

    module = ast.parse(s)
    name_definitions = [node for node in module.body]

    for node in module.body:
        listaVarsFuncoes.append([])

        MyTransformer().visit(node)
        MyVisitor().visit(node)

        global auxFunctions
        auxFunctions += 1

    print "LISTA NOMES FUNCOES"
    print listaNomesFuncoes
    print 'AQUI SAO AS VARS'
    print listaVarsFuncoes

    print listaPrint
    print "LISTA DOS NOMES DAS VARIAVEIS"
    print listaNomeVariaveis
    print "LISTA VALORES"
    print listaValores


s = '''
def testes(param1, param2): # Isso aqui e coments
    # ISSO AQUI E COMENTS

    x = 'AKOWAWOKOKA' # wkaookawokwaok
    z = 10.5
    z = 20
    z = 30
    m = 15
    # AQUI E COMENTS SEM NADA

    print z+m
    print 'BIN OP NAO FUNFANDO'
    print 'AQUI E UM PRINT'


    return [10,20,30,40] # AQUI E UM OUTRO 
def testes2():
    x = 'MatheusCoutinho'
    return x
'''
d(s)