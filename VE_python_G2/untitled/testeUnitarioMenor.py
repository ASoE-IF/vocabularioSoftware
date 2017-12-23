# encoding: utf-8

import unittest

leitura = open('vxlCodigoGenerico.txt','r')
segundaLeitura = open('arquivoTest3.txt','r')

listaVXL = []
listaCodigoMenor = []
listaAuxiliar = leitura.readlines()
stringAuxiliar = ''
listaAuxiliar2 = segundaLeitura.readlines()
indiceAuxiliar = 0
indiceAuxiliar2 = 0
indiceVXL = 0
indiceMenor = 0

for i in range(0, len(listaAuxiliar), 1):
    listaVXL.append(listaAuxiliar[i].strip())

for i in range(0, len(listaAuxiliar2), 1):
    listaCodigoMenor.append(listaAuxiliar2[i].strip())

listaAuxiliar = []
listaAuxiliar2 = []

for i in range(0,len(listaVXL),1):

    stringAuxiliar = listaVXL[i]
    if stringAuxiliar == '<field name="string" />':
        indiceAuxiliar = i

    if stringAuxiliar == '<field name="dicionario" />':
        indiceAuxiliar2 = i + 1


for i in range(indiceAuxiliar,indiceAuxiliar2,1):

    listaAuxiliar.append(listaVXL[i])

for i in range(0,len(listaCodigoMenor),1):

    stringAuxiliar = listaCodigoMenor[i]
    if stringAuxiliar == '<field name="string" />':
        indiceAuxiliar = i

    if stringAuxiliar == '<field name="dicionario" />':
        indiceAuxiliar2 = i + 1


for i in range(indiceAuxiliar,indiceAuxiliar2,1):

    listaAuxiliar2.append(listaCodigoMenor[i])


class TestaGenerico(unittest.TestCase):

    def testeVariaveis(self):

        self.assertEqual(listaAuxiliar,listaAuxiliar2)
        self.assertEqual(len(listaAuxiliar),len(listaAuxiliar2))


unittest.main()

