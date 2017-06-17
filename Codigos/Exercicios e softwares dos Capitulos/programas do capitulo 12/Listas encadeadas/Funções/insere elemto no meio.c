void inserirMeio(lista ** listaTotal, lista * elemento, int info)
{
	lista *elementoAuxiliar;
	int encontrou = 0;
	elementoAuxiliar = *listaTotal;
	/* Percorre a lista ateh encontrar o no procurado */
	while (encontrou == 0 && elementoAuxiliar->proximo != NULL)
	{							/* Verifica se o no atual eh o no procurado */
		if (elementoAuxiliar->info == info)
		{						/* O novo no sera inserido apos o no atual */
			elemento->proximo = elementoAuxiliar->proximo;
			elementoAuxiliar->proximo = (struct lista *)elemento;
			encontrou = 1;
		}
		else
		{
			elementoAuxiliar = (lista *) elementoAuxiliar->proximo;
		}
	}
}