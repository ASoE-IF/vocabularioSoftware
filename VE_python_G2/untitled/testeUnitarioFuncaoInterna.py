#encoding: utf-8

import unittest

from funcoesParaTestes import funcaoTesteFuncaoInterna


listaAuxiliar = []
listaAuxiliar2 = []


class TestaGenerico(unittest.TestCase):

    def testeFuncaoInterna(self):

        listaAuxiliar, listaAuxiliar2 = funcaoTesteFuncaoInterna()

        self.assertEqual(listaAuxiliar,listaAuxiliar2)
        self.assertEqual(len(listaAuxiliar),len(listaAuxiliar2))



unittest.main()