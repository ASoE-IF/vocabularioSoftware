#encoding: utf-8

import unittest

from funcoesParaTestes import funcaoTesteVariaveisLocais


listaAuxiliar = []
listaAuxiliar2 = []


class TestaGenerico(unittest.TestCase):

    def testeVariaveisLocais(self):

        listaAuxiliar, listaAuxiliar2 = funcaoTesteVariaveisLocais()
        self.assertEqual(listaAuxiliar,listaAuxiliar2)
        self.assertEqual(len(listaAuxiliar),len(listaAuxiliar2))



unittest.main()