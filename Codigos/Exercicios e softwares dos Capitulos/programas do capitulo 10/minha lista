#include<stdio.h>
#include<stdlib.h>	//por calsa de malloc

int main(void)
{
	struct tipo	//struct tipo
	{
		int arq;	//campo/membro de struct tipo do tipo int
		struct tipo *tipo2;	//ponteiro para o proximo elemento ele é do tipo struct tipo
	} abre, *abre2;		//ponteiro para uma struct do tipo struct int

	abre.tipo2 = NULL;		//significa lista vazia ou seja NULL
	abre2=&abre;				//faz o ponteiro da lista apontar para inicio ou vazia
	for (int nume = 1; nume <= 10; nume += 2)	/*percorre em um laço de 2 em 2 e adciona o nume na lista*/
	{
		abre2->tipo2 = (struct tipo *)malloc(sizeof(struct tipo)); /*aloca memoria para o novo elemento da lista*/
		abre2 = abre2->tipo2;	//faz abre2 apontar para o proximo elemento(tipo2)
		abre2->arq = nume;	//faz abre2 apontar para o membro(arq) que recebe o valor de nume
		abre2->tipo2 = NULL;	//faz a lista apontar para o final
	}
	abre2 = abre.tipo2;	//faz a lista voltar a o inicio para poder percorrela e imprimila

	while (abre2)	//percorre a lista em um laço while até chegar ao fim
	{
		printf("%d\n", abre2->arq);	//imprime cada elemento da lista
		abre2 = abre2->tipo2;		//aponta para o proximo elemento(tipo2) até o fim dela
	}
}