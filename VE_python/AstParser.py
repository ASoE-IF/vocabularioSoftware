# coding=utf-8
"""

"""

from __future__ import (absolute_import, division, print_function, unicode_literals)

import ast


conteudoCodigo = ""


arquivo = open("codigoTeste.py", "r")

for conteudo in arquivo:
    conteudoCodigo += conteudo

tree = ast.parse(conteudoCodigo)


# Extração de nomes de funções

for node in ast.walk(tree):
    if isinstance(node, ast.FunctionDef):
        print("Nome de funcs: \n", "{}".format("-"), node.name, "\n")

# Extração de nomes de classes


for node in ast.walk(tree):
    if isinstance(node, ast.ClassDef):
        print ("Nome de classes: \n", "{}".format("-"), node.name, "\n")

for node in ast.walk(tree):
    if isinstance(node, ast.For):
        print ()

'''





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
