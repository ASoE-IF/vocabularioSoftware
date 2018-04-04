# encoding: utf-8

# Aqui ficam as funções que serão chamadas nos códigos de testes, elas pegam um ponto de inicio e outro de fim
# no VXL geral e no VXL do codigo menor a ser testado e jogam em duas listas que serão comparadas nos asserts dos testes


def funcaoTesteVariaveisGlobais():

    leitura = open('vxlCodigoGenerico.txt', 'r')
    segundaLeitura = open('arquivoTest3.txt', 'r')

    listaVXL = []
    listaCodigoMenor = []
    listaAuxiliar = leitura.readlines()
    listaAuxiliar2 = segundaLeitura.readlines()
    indiceAuxiliar = 0
    indiceAuxiliar2 = 0

    for i in range(0, len(listaAuxiliar), 1):
        listaVXL.append(listaAuxiliar[i].strip())

    for i in range(0, len(listaAuxiliar2), 1):
        listaCodigoMenor.append(listaAuxiliar2[i].strip())

    listaAuxiliar = []
    listaAuxiliar2 = []

    for i in range(0, len(listaVXL), 1):

        stringAuxiliar = listaVXL[i]
        if stringAuxiliar == '<field name="string" />':
            indiceAuxiliar = i

        if stringAuxiliar == '<field name="dicionario" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar.append(listaVXL[i])

    for i in range(0, len(listaCodigoMenor), 1):

        stringAuxiliar = listaCodigoMenor[i]
        if stringAuxiliar == '<field name="string" />':
            indiceAuxiliar = i

        if stringAuxiliar == '<field name="dicionario" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar2.append(listaCodigoMenor[i])


    return listaAuxiliar, listaAuxiliar2


def funcaoTesteVariaveisLocais():
    leitura = open('vxlCodigoGenerico.txt', 'r')
    segundaLeitura = open('arquivoTest3.txt', 'r')

    listaVXL = []
    listaCodigoMenor = []
    listaAuxiliar = leitura.readlines()
    listaAuxiliar2 = segundaLeitura.readlines()
    indiceAuxiliar = 0
    indiceAuxiliar2 = 0

    for i in range(0, len(listaAuxiliar), 1):
        listaVXL.append(listaAuxiliar[i].strip())

    for i in range(0, len(listaAuxiliar2), 1):
        listaCodigoMenor.append(listaAuxiliar2[i].strip())

    listaAuxiliar = []
    listaAuxiliar2 = []

    for i in range(0, len(listaVXL), 1):

        stringAuxiliar = listaVXL[i]
        if stringAuxiliar == '<mth name="funcaoDeDeclaracaoDeVariaveisLocais">':
            indiceAuxiliar = i

        if stringAuxiliar == '<field name="dicionarioLocal4" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar.append(listaVXL[i])

    for i in range(0, len(listaCodigoMenor), 1):

        stringAuxiliar = listaCodigoMenor[i]
        if stringAuxiliar == '<mth name="funcaoDeDeclaracaoDeVariaveisLocais">':
            indiceAuxiliar = i

        if stringAuxiliar == '<field name="dicionarioLocal4" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar2.append(listaCodigoMenor[i])

    return listaAuxiliar, listaAuxiliar2


def funcaoTesteClasse():
    leitura = open('vxlCodigoGenerico.txt', 'r')
    segundaLeitura = open('arquivoTest3.txt', 'r')

    listaVXL = []
    listaCodigoMenor = []
    listaAuxiliar = leitura.readlines()
    listaAuxiliar2 = segundaLeitura.readlines()
    indiceAuxiliar = 0
    indiceAuxiliar2 = 0

    for i in range(0, len(listaAuxiliar), 1):
        listaVXL.append(listaAuxiliar[i].strip())

    for i in range(0, len(listaAuxiliar2), 1):
        listaCodigoMenor.append(listaAuxiliar2[i].strip())

    listaAuxiliar = []
    listaAuxiliar2 = []

    for i in range(0, len(listaVXL), 1):

        stringAuxiliar = listaVXL[i]
        if stringAuxiliar == '<class name="Calculadora">':
            indiceAuxiliar = i

        if stringAuxiliar == '<cmtt name=" Comentario de classe" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar.append(listaVXL[i])

    for i in range(0, len(listaCodigoMenor), 1):

        stringAuxiliar = listaCodigoMenor[i]
        if stringAuxiliar == '<class name="Calculadora">':
            indiceAuxiliar = i

        if stringAuxiliar == '<cmtt name=" Comentario de classe" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar2.append(listaCodigoMenor[i])

    return listaAuxiliar, listaAuxiliar2


def funcaoTesteFuncaoInterna():
    leitura = open('vxlCodigoGenerico.txt', 'r')
    segundaLeitura = open('arquivoTest3.txt', 'r')

    listaVXL = []
    listaCodigoMenor = []
    listaAuxiliar = leitura.readlines()
    listaAuxiliar2 = segundaLeitura.readlines()
    indiceAuxiliar = 0
    indiceAuxiliar2 = 0

    for i in range(0, len(listaAuxiliar), 1):
        listaVXL.append(listaAuxiliar[i].strip())

    for i in range(0, len(listaAuxiliar2), 1):
        listaCodigoMenor.append(listaAuxiliar2[i].strip())

    listaAuxiliar = []
    listaAuxiliar2 = []

    for i in range(0, len(listaVXL), 1):

        stringAuxiliar = listaVXL[i]
        if stringAuxiliar == '<mth name="funcaoExemplo2">':
            indiceAuxiliar = i

        if stringAuxiliar == '<cmtt name=" Comentario externo" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar.append(listaVXL[i])

    for i in range(0, len(listaCodigoMenor), 1):

        stringAuxiliar = listaCodigoMenor[i]
        if stringAuxiliar == '<mth name="funcaoExemplo2">':
            indiceAuxiliar = i

        if stringAuxiliar == '<cmtt name=" Comentario externo" />':
            indiceAuxiliar2 = i + 1

    for i in range(indiceAuxiliar, indiceAuxiliar2, 1):
        listaAuxiliar2.append(listaCodigoMenor[i])

    return listaAuxiliar, listaAuxiliar2