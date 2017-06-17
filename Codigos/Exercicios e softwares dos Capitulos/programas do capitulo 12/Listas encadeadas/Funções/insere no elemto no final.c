void inserirFim(lista ** listaTotal, lista * elemento)
{
	lista *elementoAuxiliar;
	elementoAuxiliar = *listaTotal;	/* Percorre a lista ateh encontrar o
									   ultimo elemento */
	while (elementoAuxiliar->proximo != NULL)
	{
		elementoAuxiliar = (lista *) elementoAuxiliar->proximo;
	}
	elementoAuxiliar->proximo = (struct lista *)elemento;
}