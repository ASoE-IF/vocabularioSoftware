lista *criarElemento(int info)
{
	lista *no = (lista *) malloc(sizeof(lista));
	if (no == NULL)
	{
		return NULL;
	}
	no->info = info;
	no->proximo = NULL;
	return no;
}