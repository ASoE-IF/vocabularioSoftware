#include<stdio.h>
#include<stdlib.h>

int main(void)	//inicia o programa
{
	struct tipo	//cria uma struct do tipo (tipo)
	{
		int arq;	//cria um membro da lista do tipo int da lista (tipo)
		struct tipo *tipo2;	//cria um ponteiro para o proximo elemento da lista
	} abre, *abre2;	//cria uma variavel e um ponteiro de para inicio e o no da lista

	abre.tipo2 = NULL;	//declara a lista como NULL ou seja ela esta vazia
	abre2=&abre;			//faz com que abre2 aponte em NULL ou seja indica lista vazia(inicio)
	for (int nume = 1; nume <= 10; nume += 2)	//usa um laço for para inserir elementos na lista
	{
		abre2->tipo2 = (struct tipo *)malloc(sizeof(struct tipo));	//alloca memoria dinamica para cada
																											//elemento da lista
		abre2 = abre2->tipo2;		//faz com que abre2(no) aponte para o proximo elemnto da lista
		abre2->arq = nume;			//faz com que abre2(no) aponte para arq que guarda o elemnto da lista
		abre2->tipo2 = NULL;		//quando nao existe mais elemnto a lista aponta em NULL(FIM)
	}
	abre2 = abre.tipo2;	//faz a lista apontar para o inicio

	while (abre2)	//executa um laço para poder imprimir cada elemnto da lista
	{
		printf("%d\n", abre2->arq);	//imprime o elemento da lista
		abre2 = abre2->tipo2;	//apobta para o proximo elemento da lista
	}
	abre2=&abre;	//faz a lista voutar ao inicio 
	while(abre2->tipo2)	//percorre a lista em um laço while
	abre2=abre2->tipo2;	//aponta sempre para o proximo elemento da lista
	
	//insere um elemento no fim da lista
	
	abre2->tipo2=(struct tipo *)malloc(sizeof(struct tipo));	//aloca memoria para o novo elemnto
	abre2=abre2->tipo2;	//aponta para o proximo elemnto
	abre2->arq=6;	//insere um novo elemnto no fim da lista
	printf("\n%d\n\n", abre2->arq);	//imprime o novo elemento
	abre2->tipo2=NULL;		//aponta para NULL ou FIM da lista
	
		abre2 = abre.tipo2;	//vouta ao inicio da lista

	while (abre2)	//percorre a lista em um laço
	{
		printf("%d\n", abre2->arq);	//imprime todos os elementos incluindo o novo.
		abre2 = abre2->tipo2;		//aponta para o proximo elemento
	}
}