# coding=utf-8
"""

"""

from __future__ import (absolute_import, division, print_function, unicode_literals)

import __future__

import ast

pfcf = __future__.print_function.compiler_flag

from ast import dump, PyCF_ONLY_AST

conteudoCodigo = ""

NomesFuncoes = []
NomesVariaveis = []
ValorStrings =[]
Numeros = []
NewData = []

arquivo = open("codigoTeste.py", "r")

for conteudo in arquivo:
    conteudoCodigo += conteudo

tree = ast.parse(conteudoCodigo)

# Extração de nomes de funções

for node in ast.walk(tree):
    if isinstance(node, ast.FunctionDef):
        NomesFuncoes.append(node.name)
print("Nomes de Funções \n ", NomesFuncoes)

# Extração de nomes de classes


for node in ast.walk(tree):
    if isinstance(node, ast.ClassDef):
        NomesVariaveis.append(node.name)
print("Nomes de Variáveis \n ", NomesVariaveis)

VisitNos = ast.NodeVisitor

for node in ast.walk(tree):
    if isinstance(node, ast.Str):
        pass

print ("Valores aleatorios \n", NewData)



class EncontraStr (ast.NodeVisitor):
    def visit_Str(self, node):
        ValorStrings.append(node.s)

class RetornaStr(ast.NodeTransformer):
    def visit_Str(self, node):
        return ast.Str(node.s)


GetBodyDumpValue = [node for node in tree.body]

for i in range(len(GetBodyDumpValue)):
    RetornaStr().visit(GetBodyDumpValue[i])
    EncontraStr().visit(GetBodyDumpValue[i])

print ("Valores de Strings \n", ValorStrings)


'''
def GeradorAst(s):
    lendo = (dump(compile(s.body[1], '<String>', 'exec', pfcf | PyCF_ONLY_AST)))
    temain = len(tree.body)

    for size in range(temain):
        print (dump(compile(s.body[size], '<String>', 'exec', pfcf | PyCF_ONLY_AST)))
        #print (dump(compile(s.body[size], '<String>', 'exec', pfcf | PyCF_ONLY_AST)))

        print (lendo)

GeradorAst(tree)

# Extração de Numeros

for node in ast.walk(tree):
    if isinstance(node, ast.Num):
        Numeros.append(node.n)
print("Numeros existentes \n ", Numeros)




name_definitions = [node for node in tree.body]

print(name_definitions)

for node in ast.walk(tree):
    if isinstance(node, ast.arguments):
        print(node)
for node in ast.walk(tree):
    if isinstance(node, ast.augAssign):
        target = code(tree.target)
        op = code(tree.op)
        value = code(tree.value)
        value = "%s%s%s" % (target, op, value)
        print (node.targets)
for node in ast.walk(tree):
    print (node)
    
Nomes de funcoes  

for node in ast.walk(tree):
    if isinstance(node, ast.Assign):
        print (node.__dict__)
   =================================================================================================     
for n in ast.walk(tree):
       if isinstance(n, ast.Assign) and isinstance(n.value, ast.Call) and n.value.func.id == ('struct'):
           print (n, "otrr")
     =================================================================================================  
# Extração de nomes de funções
for node in ast.walk(tree):
    if isinstance(node, ast.FunctionDef):
        print("Nome de funcs: \n", "{}".format("-"), node.name, "\n")
# Extração de nomes de classes
for node in ast.walk(tree):
    if isinstance(node, ast.ClassDef):
        print ("Nome de classes: \n", "{}".format("-"), node.name, "\n")
'''

arquivo.close()