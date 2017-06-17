lista *buscarElemento(lista ** listaTotal, int valorProcurado)
{
	lista *elementoAuxiliar;
	int encontrou = 0;
	elementoAuxiliar = *listaTotal;
	/* Percorre a lista ateh encontrar o no procurado, ou ateh o fim da lista */
	while (encontrou == 0 && elementoAuxiliar != NULL)
	{							/* Verifica se o no atual contem o valorProcurado */
		if (elementoAuxiliar->info == valorProcurado)
		{						/* encontrou o no procurado */
			encontrou = 1;
		}
		else
		{
			elementoAuxiliar = (lista *) elementoAuxiliar->proximo;
		}
	}							/* Se encontrou o no procurado, retorna o no */
	if (encontrou == 1)
	{
		return elementoAuxiliar;
	}							/* Se nao encontrou, retorna NULL */
	else
	{
		return NULL;
	}
}