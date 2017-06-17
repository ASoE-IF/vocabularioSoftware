#include<stdio.h>

int main(void)
{
	int nume;
	struct lista
	{
		int numero;
		struct lista *proximo;
		struct lista *anterior;
	} inicio, *nodo, *novo;

	inicio.proximo = NULL;		// lista vazia;
	inicio.anterior = NULL;		// aponta para anterior

	nodo = &inicio;				// aponta para o inicio da lista
	for (nume = 1; nume <= 10; nume += 2)
	{
		nodo->proximo = (struct lista *)malloc(sizeof(struct lista));
		nodo->proximo->anterior = nodo;
		nodo = nodo->proximo;
		nodo->numero = nume;
		nodo->proximo = NULL;
	}

	nodo = inicio.proximo;		// exibe a lista

	do
	{
		printf("%d  ", nodo->numero);
		nodo = nodo->proximo;
	}
	while (nodo->proximo);		// motra 10 somente uma vez
	printf("\n");
	do
	{
		printf("%d  ", nodo->numero);
		nodo = nodo->anterior;
	}
	while (nodo->anterior);

	printf("\n\n");

	nodo = &inicio;				// volta ao inicio
	for (nume = 2; nume <= 10; nume += 2)
	{
		int encontrado;
		novo = (struct lista *)malloc(sizeof(struct lista));
		novo->numero = nume;
		novo = &inicio;

		do
		{
			if (nodo->numero > novo->numero)
			{
				novo->proximo = NULL;
				novo->proximo = nodo->anterior;
				nodo->anterior->proximo = novo;
				nodo->anterior = novo;
				encontrado = 1;
			}
			else
				nodo = nodo->proximo;
		}
		while ((nodo->proximo) && (!encontrado));

		if (!encontrado)
			if (nodo->numero > novo->numero)
			{
				novo->proximo = nodo;
				novo->anterior = nodo->anterior;
				novo->anterior->proximo = nodo;
				nodo->anterior = novo;
			}
			else
			{
				novo->proximo = NULL;
				novo->anterior = nodo;
				nodo->proximo = novo;
			}
	}
	nodo = inicio.proximo;		// exibe a lista

	do
	{
		printf("%d  ", nodo->numero);
		nodo = nodo->proximo;
	}
	while (nodo->proximo);		// motra 10 somente uma vez
	printf("\n");
	do
	{
		printf("%d  ", nodo->numero);
		nodo = nodo->anterior;
	}
	while (nodo->anterior);
}