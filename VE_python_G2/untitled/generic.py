# encoding: utf-8
import numpy


# Variaveis Globais

string = "String Generica"
inteiro = 10
flutuante = 1.5
boolean = True
complexo = 2 + 4j
lista = [1,2,3]
tupla = (1,2,3)
dicionario = {'denomiacao':'CG','populacao':23000,'renda':1500}

# Prototipos de funções

def prototipo():
    return ''
def prototipo2(x):
    return x
def prototipo3(x,y):
    return x,y

def funcaoDeDeclaracaoDeVariaveisLocais():

    stringLocal = "String Generica"
    inteiroLocal = 10
    flutuanteLocal = 1.5
    booleanLocal = True
    complexoLocal = 2 + 4j
    listaLocal = [1, 2, 3]
    tuplaLocal = (1, 2, 3)
    dicionarioLocal = {'denomiacao': 'CG', 'populacao': 23000, 'renda': 1500}

    # Fim da Função

def funcaoDeUsoDeVariaveisGlobais():

    #Usando Variáveis Globais
    stringLocal = "String Funcao"
    inteiroLocal = 20
    flutuanteLocal = 2.5
    booleanLocal = False
    complexoLocal = 3 + 4j
    listaLocal = [4, 5, 6]
    tuplaLocal = (4, 5, 6)
    dicionarioLocal = {'denomiacao': 'KatyCity', 'populacao': 1, 'renda': 1000000}

    #Usando variáveis locais que sobreescrevem as globais

    stringLocal = "String Generica"
    inteiroLocal = 10
    flutuanteLocal = 1.5
    booleanLocal = True
    complexoLocal = 2 + 4j
    listaLocal = [1, 2, 3]
    tuplaLocal = (1, 2, 3)
    dicionarioLocal = {'denomiacao': 'CG', 'populacao': 23000, 'renda': 1500}

    # Fim da Função

def funcaoDeUsoDeVariaveisLocaisEOperadores():


    stringLocal = "String Generica"
    inteiroLocal = 10
    flutuanteLocal = 1.5
    booleanLocal = True
    complexoLocal = 2 + 4j
    listaLocal = [1, 2, 3]
    tuplaLocal = (1, 2, 3)
    dicionarioLocal = {'denomiacao': 'CG', 'populacao': 23000, 'renda': 1500}

    variavelLocal1 = 10
    variavelLocal2 = 10
    variavelLocal3 = 10

    variavelLocal1 = variavelLocal2
    variavelLocal3 = variavelLocal2 = variavelLocal1 = 20

    # Incremento e decremento
    variavelLocal1 += 1
    variavelLocal1 -= 1
    variavelLocal1 = variavelLocal2 - 10
    variavelLocal1 += variavelLocal2
    variavelLocal1 += 10

    # Expressões Lógicas

    variavelLocal1 > variavelLocal2
    variavelLocal1 >= variavelLocal2
    variavelLocal1 > variavelLocal2
    variavelLocal1 <= variavelLocal2
    variavelLocal3 == variavelLocal2
    variavelLocal3 != variavelLocal2
    ((variavelLocal3 == variavelLocal2) and (variavelLocal1 <= variavelLocal2))
    ((variavelLocal3 != variavelLocal2) or (variavelLocal1 == variavelLocal2));

    # Operador Vírgula
    variavelLocal1 = variavelLocal2 = 20, variavelLocal2 + 50
    variavelLocal2 = variavelLocal2 = 20, variavelLocal2 + 50, variavelLocal2 + 20

    # Cast de Tipos
    variavelLocal3 = int(flutuanteLocal)
    float(variavelLocal1) == float(variavelLocal2)

    # Fim da Função

def testeFor():
    return 1

def sqrNum(num):
    return num*num

def funcaoDeUsoDeComandoDeControle():

    variavelLocal1 = 10
    variavelLocal2 = 10
    variavelLocal3 = 10
    variavelLocal4 = "a"
    indice1 = 0
    indice2 = 101

    # Uso do Comando IF

    if (variavelLocal1 > variavelLocal2) and (variavelLocal3 < variavelLocal2):
        print "variavelLocao1: ",variavelLocal1
        if (variavelLocal1 != 50 and variavelLocal3 == 20):
            if (variavelLocal1 > variavelLocal2):
                print "variavelLocal2: ", variavelLocal2
            else:
                print "variavelLocal3: ", variavelLocal3

    else:
        if (variavelLocal1 == variavelLocal2 and variavelLocal3 == variavelLocal2):
            print "variavelLocao1: ", variavelLocal1
            if (variavelLocal2 >= variavelLocal2 or variavelLocal3 <= variavelLocal2):
                print "variavelLocal2: ", variavelLocal2
            else:
                if (variavelLocal1 == variavelLocal3 and variavelLocal3 == variavelLocal1):
                    print "variavelLocal3: ", variavelLocal3
                else:
                    print "variavelLocal2: ", variavelLocal2

    if variavelLocal2 == 2:
        print "variavelLocao1: ", variavelLocal1
    elif variavelLocal2 == 3:
        print "variavelLocal2: ", variavelLocal2
    elif variavelLocal2 == 1:
        print "variavelLocal3: ", variavelLocal3

    # Usando o Comando For

    for i in range(indice1,indice2,1):
        print "inicio do primeiro for"
        print "fim do primeiro for"
    for l in range(indice1,indice2,1):
        print "inicio do primeiro for"
        for j in range(0,10,1):
            print "inicio do segundo for"
            for k in range(0,5,1):
                print "inicio do terceiro for"
                print "fim do terceiro for"
            print "fim do segundo for"
        print "fim do primeiro for"

    for i in range(testeFor(),10,1):
        print "inicio do for com funcao"
        sqrNum(variavelLocal1)
        print "fim do for com funcao"

    # Usando o Comando While

    while variavelLocal4 == "a":
        print "Inicio do primeiro while"
        print "Loop infinito"
        print "fim do primeiro while"
        break

    while variavelLocal1 < 20 or variavelLocal2 < 20:
        print "Inicio do primeiro while"
        while variavelLocal1 != 5:
            print "inicio do while interno"
            print "fim do while interno"
        variavelLocal1 += 1
        variavelLocal2 += 1
        print "fim do primeiro while"

    while True:
        print "Inicio do primeiro while"
        print "Loop infinito"
        print "fim do primeiro while"
        if variavelLocal4 != "a":
            break
    # Fim da Função

def funcaoUsandoMatrizes():
    matriz = [[3, 4, 5], [6, 7, 8], [9, 0, 1]]
    novaMatriz = numpy.matrix(matriz)
    novaMatriz[0][0] = 0
    # Fim da Função

# Funções Dentro de Funções

def funcaoExemplo1():

    variavelLocal = 5
    def funcaoInterna():
        variavelLocal = 10
        variavelLocalDaFuncaoInterna = variavelLocal

    variavelLocal = 20
    # Fim da função

def funcaoExemplo2(parametro):

    variavelLocal = 5
    # Comentario externo

    def funcaoInterna(parametro):
        variavelLocal = 10
        # Comentario Interno
        variavelLocalDaFuncaoInterna = 0

    variavelLocal = 20
    return variavelLocal

def testeClasseEmFuncao():

    class ClasseInternaDaFuncao(object):

        atributoClasseFuncao = 1

        def imprimirMensagem(self, mensagemFuncao):
            return mensagemFuncao
        # Comentario de classe interna em função

# Usando Classe

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