
import ast


class analizarCodigo(ast.NodeVisitor):
    def __init__(self):
        self.Carregado = set()
        self.Armazenado = set()
        self.Deletado = set()

    def Visit_Name(self, node):
        if isinstance(node.ctx, ast.Load):
            self.Carregado.add(node.id)
        elif isinstance(node.ctx, ast.Store):
            self.Armazenado.add(node.id)
        elif isinstance(node.ctx, ast.Del):
            self.Deletado.add(node.id)




if __name__ == '__main__':

    code = '''
            
for i in range(10):
    print(i)
del i

'''
    tree = ast.parse(code, mode='exec')

    c = analizarCodigo()
    c.visit(tree)
    print ('Carregado', c.Carregado,
        'Armazenado', c.Armazenado,
        'Deletado', c.Deletado)