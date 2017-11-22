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
print("Nomes de variáveis \n ", NomesFuncoes)

# Extração de nomes de classes


for node in ast.walk(tree):
    if isinstance(node, ast.ClassDef):
        NomesVariaveis.append(node.name)
print("Nomes de funções \n ", NomesVariaveis)

# Extração de Numeros

for node in ast.walk(tree):
    if isinstance(node, ast.Num):
        Numeros.append(node.n)
print("Numeros existentes \n ", Numeros)


def GeradorAst(s):
    print(dump(compile(s, '<String>', 'exec', pfcf | PyCF_ONLY_AST)))


name_definitions = [node for node in tree.body]

print(name_definitions)

'''
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
