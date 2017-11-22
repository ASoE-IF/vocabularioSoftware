import ast
import __future__

pfcf = __future__.print_function.compiler_flag

from ast import dump, PyCF_ONLY_AST

listaNomeVariaveis = []
listaValores = []
listaPrint = []
listaNomesFuncoes = []


class MyVisitorNameDef(ast.NodeVisitor):
    def visit_FunctionDef(self, node):
        listaNomesFuncoes.append(node.name)


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
        listaValores.append(node.s)


class MyTransformerString(ast.NodeTransformer):
    def visit_Str(self, node):
        return ast.Str(node.s)


class MyVisitorNum(ast.NodeVisitor):
    def visit_Num(self, node):
        listaValores.append(node.n)


class MyTransformerNum(ast.NodeTransformer):
    def visit_Num(self, node):
        print(node.n)
        return ast.Num(node.n)


class MyVisitorName(ast.NodeVisitor):
    def visit_Name(self, node):
        listaNomeVariaveis.append(node.id)


class MyTransformerName(ast.NodeTransformer):
    def visit_Name(self, node):
        print(node.id)
        return ast.Name(node.id, self)


def d(s):
    print len(dump(compile(ast.parse(s), '<String>', 'exec', pfcf | PyCF_ONLY_AST)))

    print dump(compile(ast.parse(s), '<String>', 'exec', pfcf | PyCF_ONLY_AST))
    b = dump(compile(ast.parse(s).body[1], '<String>', 'exec', pfcf | PyCF_ONLY_AST))
    e = len(ast.parse(s).body)

    for i in range(e):
        print dump(compile(ast.parse(s).body[i], '<String>', 'exec', pfcf | PyCF_ONLY_AST))
        print dump(compile(ast.parse(s).body[i].name, '<String>', 'exec', pfcf | PyCF_ONLY_AST))
    print b

    d = ast.parse('''
x = 'wajiawjiawji'
y = 'awkoaw' 
z = 1777
''')

    module = ast.parse(s)

    name_definitions = [node for node in module.body]

    for i in range(len(name_definitions)):
        MyTransformerNameDef().visit(name_definitions[i])
        MyVisitorNameDef().visit(name_definitions[i])
    print "\n LISTA NOMES FUNCOES"
    print listaNomesFuncoes

    function_definitions = [node for node in module.body[0].body if isinstance(node, ast.Assign)]
    for i in range(len(function_definitions)):
        MyTransformerString().visit(function_definitions[i])
        MyVisitorString().visit(function_definitions[i])

        MyTransformerNum().visit(function_definitions[i])
        MyVisitorNum().visit(function_definitions[i])

        MyTransformerName().visit(function_definitions[i])
        MyVisitorName().visit(function_definitions[i])

        print_definitions = [node for node in module.body[0].body if isinstance(node, ast.Print)]
    for i in range(len(print_definitions)):
        MyTransformerPrint().visit(print_definitions[i])
        MyVisitorPrint().visit(print_definitions[i])

    print listaPrint
    print "LISTA DOS NOMES DAS VARIAVEIS"
    print listaNomeVariaveis
    print "LISTA VALORES"
    print listaValores


s = '''
def testes(awkkoawkoaw): # Isso aqui e coments
    # ISSO AQUI E COMENTS
    x = 'AKOWAWOKOKA' # wkaookawokwa
    z = 10.5
    m = 15
    print z+m
    print 'BIN OP NAO FUNFANDO'
    print 'AQUI E UM PRINT'
    return [10,20,30,40]
def testes2():
    x = 'MatheusCoutinho'
    return x
'''

d(s)
