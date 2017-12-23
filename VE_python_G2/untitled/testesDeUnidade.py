# encoding: utf-8

import unittest

# Lendo o arquivo VXL após o processamento do código genérico
leitura = open('arquivoTest3.txt','r')

listaVXL = []
listaAuxiliar = leitura.readlines()
stringAuxiliar = ''

for i in range(0, len(listaAuxiliar), 1):
    listaVXL.append(listaAuxiliar[i].strip())

class TestaGenerico(unittest.TestCase):

    def testeNomeClasse(self):

        stringAuxiliar = '<class name="Calculadora">'
        self.assertIn(stringAuxiliar,listaVXL)

    def testeNomeArquivo(self):

        stringAuxiliar = '<module name="generic.py">'
        self.assertIn(stringAuxiliar, listaVXL)

    def testeMetodosClasse(self):

        stringAuxiliar = '<mth name="soma">'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<mth name="subtrai">'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<mth name="multiplica">'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<mth name="divide">'
        self.assertIn(stringAuxiliar, listaVXL)

    def testeAtributosClasse(self):

        stringAuxiliar = '<param name="self" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<param name="a" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<param name="b" />'
        self.assertIn(stringAuxiliar, listaVXL)


    def testeVariaveisGlobais(self):

        stringAuxiliar = '<field name="string" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="inteiro" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="flutuante" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="boolean" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="complexo" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="lista" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="tupla" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="dicionario" />'
        self.assertIn(stringAuxiliar, listaVXL)

    def testeMetodosVazios(self):

        stringAuxiliar = '<mth name="prototipo">'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<mth name="prototipo2">'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<param name="x" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<mth name="prototipo3">'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<param name="y" />'
        self.assertIn(stringAuxiliar, listaVXL)

    def testeVariaveisLocais(self):

        stringAuxiliar = '<field name="stringLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="inteiroLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="flutuanteLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="booleanLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="complexoLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="listaLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="tuplaLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<field name="dicionarioLocal" />'
        self.assertIn(stringAuxiliar, listaVXL)

    def testeComentarios(self):

        stringAuxiliar = '<cmtt name=" Comentário de método da classe" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name=" Comentario de classe" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name="Usando variáveis locais que sobreescrevem as globais" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name=" Usando o Comando While" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name=" Comentario Interno" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name=" Comentario externo" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name=" encoding: utf-8" />'
        self.assertIn(stringAuxiliar, listaVXL)

        stringAuxiliar = '<cmtt name=" Variaveis Globais" />'
        self.assertIn(stringAuxiliar, listaVXL)


unittest.main()
