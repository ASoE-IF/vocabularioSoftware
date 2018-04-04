#encoding: utf-8

import unittest

from funcoesParaTestes import funcaoTesteClasse


listaAuxiliar = []
listaAuxiliar2 = []


class TestaGenerico(unittest.TestCase):

    def testeClasse(self):

        listaAuxiliar, listaAuxiliar2 = funcaoTesteClasse()
        self.assertEqual(listaAuxiliar,listaAuxiliar2)
        self.assertEqual(len(listaAuxiliar),len(listaAuxiliar2))



unittest.main()