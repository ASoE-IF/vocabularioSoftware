#encoding: utf-8

import unittest

from funcoesParaTestes import funcaoTesteVariaveisGlobais


listaAuxiliar = []
listaAuxiliar2 = []


class TestaGenerico(unittest.TestCase):

    def testeVariaveisGlobais(self):

        listaAuxiliar, listaAuxiliar2 = funcaoTesteVariaveisGlobais()
        print listaAuxiliar
        print
        print(listaAuxiliar2)
        self.assertEqual(listaAuxiliar,listaAuxiliar2)
        self.assertEqual(len(listaAuxiliar),len(listaAuxiliar2))



unittest.main()

