#include<stdio.h>
#include<stdlib.h>

int main(void)
{
	struct tipo
	{
		int arq;
		struct tipo *tipo2;
	} abre, *abre2, *anterior, *novo;	//declara anterior e novo como sendo ponteiros

	abre.tipo2 = NULL;
	abre2 = &abre;
	for (int nume = 1; nume <= 10; nume += 2)
	{
		abre2->tipo2 = (struct tipo *)malloc(sizeof(struct tipo));
		abre2 = abre2->tipo2;
		abre2->arq = nume;
		abre2->tipo2 = NULL;
	}
	abre2 = abre.tipo2;

	while (abre2)
	{
		printf("%d\n", abre2->arq);
		abre2 = abre2->tipo2;
	}

	printf("\n");

	anterior = &abre;	//controla o elemento anterior e esta apontando ao inicio da lista
	abre2 = abre.tipo2;	//faz abre2voltar ao inicio
	while ((abre2) && (abre2->arq) < 7)	//controna o no atual anterior e novo para inserir entre 5e7
	{
		abre2 = abre2->tipo2;	//aponta para proximo
		anterior = anterior->tipo2;	//aponta para proximo
	}
	novo = (struct tipo *)malloc(sizeof(struct tipo));	//aloca memoria para o novo elemento
	novo->tipo2 = abre2;	//atribue a novo->tipo2 o valor de abre2
	anterior->tipo2 = novo;	//atribue o valor de novo a anterior->tipo
	novo->arq = 6;	//insere o novo elemento na lista

	abre2 = abre.tipo2; //volta ao inicio

	while (abre2)	//percorre a lista
	{
		printf("%d\n", abre2->arq);	//imprime a nova lista
		abre2 = abre2->tipo2;	//aponta para proximo
	}
	printf("\n");
	abre2 = abre.tipo2;	//volta ao inicio
	anterior = abre.tipo2;	//volta ao inicio
	while (abre2)	//percorre a lista
	{
		if (abre2->arq == 7) //se a lista for igual a 7
		{
			anterior->tipo2 = abre2->tipo2;	//atribue abre2->tipo2 a anterior->tipo2
			free(abre2);	//libera a memoria alocada para 7na lista, o removendo
			break;	//para o laço
		}
		else
		{
			abre2 = abre2->tipo2;	//esses dois apontam para proximo elementos
			anterior = anterior->tipo2;
		}
	}

	abre2 = abre.tipo2; // volta ao inicio

	while (abre2)	//esse novo laço imprime a nova lista
	{
		printf("%d\n", abre2->arq);
		abre2 = abre2->tipo2;
	}
}