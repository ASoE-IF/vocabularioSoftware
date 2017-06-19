#include<stdio.h>
#include<stdlib.h>

int main(void)
{
	struct tipo
	{
		int arq;
		struct tipo *tipo2;
	} abre, *abre2, *anterior, *novo;

	abre.tipo2 = NULL;
	abre2=&abre;
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
	//as representaçoes anteriores sao declaradas normalmente como vc ja sabe
	printf("\n");
	
	anterior=&abre;
	abre2=abre.tipo2;
	//as dua declaraçoes anteriores voltam ao inicio da lista
		while((abre2)&&(abre2->arq)<7){//esse laço controla onde deve ser inserido o novo elemento
		abre2=abre2->tipo2; //aponta para proximo
		anterior=anterior->tipo2;	//aponta para proximo
		}
		novo = (struct tipo *)malloc(sizeof(struct tipo));	//aloca memoria para o novo elemento
		novo->tipo2=abre2;	//nov aponta para proximo(tipo2) = a abre2
		anterior->tipo2=novo;	//faz anterior apontar para proximo(tipo2)= a novo
		novo->arq = 6; //insere o elemento 6 na lista antes do numero 7

	abre2=abre.tipo2;	//volta ao inicio
	
	while (abre2)	//percorre a lista
	{
		printf("%d\n", abre2->arq);	//imprime os elementos 1 por 1
		abre2=abre2->tipo2;	//aponta para proximo
	}
}