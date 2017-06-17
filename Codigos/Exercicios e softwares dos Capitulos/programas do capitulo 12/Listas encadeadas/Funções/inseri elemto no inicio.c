void inserirInicio(lista ** listaTotal, lista * elemento)
{
	if (*listaTotal == NULL)
	{
		*listaTotal = elemento;
	}
	else
	{
		elemento->proximo = (struct lista *)*listaTotal;
		*listaTotal = elemento;
	}
