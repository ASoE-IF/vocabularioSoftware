void desturirLista(lista ** listaTotal)
{
	lista *elementoAuxiliar1, *elementoAuxiliar2;
	elementoAuxiliar1 = *listaTotal;
	while (elementoAuxiliar1 != NULL)
	{
		elementoAuxiliar2 = (lista *) elementoAuxiliar1->proximo;
		free(elementoAuxiliar1);
		elementoAuxiliar1 = elementoAuxiliar2;
	}
	*listaTotal = NULL;
}