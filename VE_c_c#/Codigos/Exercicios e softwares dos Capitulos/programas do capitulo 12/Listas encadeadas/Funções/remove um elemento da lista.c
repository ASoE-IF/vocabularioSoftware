int removerElemento(lista ** listaTotal, int valorProcurado)
{
	lista *elementoAuxiliar, *elementoAnterior;
	int encontrou = 0;
	elementoAuxiliar = *listaTotal;
	elementoAnterior = *listaTotal;	/* Percorre a lista ateh encontrar o
									   elemento (nó) procurado */
	while (encontrou == 0 && elementoAuxiliar != NULL)
	{
		if (elementoAuxiliar->info == valorProcurado)
		{						/* Encontrou o elemento (nó) procurado */
			encontrou = 1;
		}
		else
		{						/* nao encontrou o elemento(nó). Avança ao
								   proximo */
			elementoAnterior = elementoAuxiliar;
			elementoAuxiliar = (lista *) elementoAuxiliar->proximo;
		}
	}

	if (encontrou == 1)
	{							/* Verifica se o elemento (nó) encontrado eh
								   o primeiro da lista */
		if (elementoAnterior == elementoAuxiliar
			&& elementoAuxiliar == *listaTotal)
		{
			*listaTotal = (lista *) elementoAuxiliar->proximo;
		}						/* Caso o elemento(nó) encontrado nao seja o
								   primeiro da lista */
		else
		{
			elementoAnterior->proximo = elementoAuxiliar->proximo;
		}						/* Libera a memoria alocada */
		free(elementoAuxiliar);
		elementoAuxiliar = NULL;
		return 1;
	}							/* Retorna zero indicando que o elemento(no)
								   nao foi encontrado */
	return 0;
}