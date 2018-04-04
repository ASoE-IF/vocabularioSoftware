

class Calculadora(object):

    atributoClasse = 1
    # Comentario de classe

    class ClasseInterna(object):

        atributoClasseInterna = 1

        def imprimir(self, mensagem):
            return mensagem
        #Comentario de classe interna

    def soma(self, a, b):
        # Comentário de método da classe
        return a + b

    def subtrai(self, a, b):
        return a - b

    def multiplica(self, a, b):
        return a * b

    def divide(self, a, b):
        return a / b