#include <stdio.h>
//comentario de linha
#include <stdlib.h>
#define MAX 100
#define SOMA(a,b) ((a)+(b))
#error erro_gerado

/*este é um commentario ue pode se estender por varias
linhas*/

typedef int elem_lista; //cria um nome alternativo para int

static int variavelstatic // variavel statica
typedef struct Nodo //declara um estrutura de N� da lista: Nodo
{
	elem_lista elem;//elemento da lista
	struct Nodo *prox;//estrutura que vai apontar para o proximo elemento da lista
	struct Nodo *ant;//estrutura que vai apontar para o elemento anterior da lista
}t_Nodo;//cria um pesudonome para Nodo, isso � um tipo

typedef struct//estrutura de onde será criada a lista
{
	t_Nodo *cabeca;//estrutura do tipo t_Nodo que contera a cabeca da lista
} Lista;//tipo lista

enum colors {/*enum*/black, blue, green, cyan, red, purple, yellow, white};//enums
void constroiLista(Lista * l);//prototipo de constroiLista
void destroiLista(Lista * l);//prototipo de destroiLista
int busca_lista(Lista * l, elem_lista k);//prototipo de busca_lista
void insere_na_lista(Lista * l, elem_lista x);//prototipo de insere_na_lista
elem_lista apaga_da_lista(Lista * l, elem_lista x);//prototipo de apaga_da_lista
int estahVazio(Lista * l);//prototipo de estahVazio
elem_lista cabeca(Lista * l);//prototipo de cabeca
void imprimeLista(Lista * l);//prototipo de imprimeLista

static void fun()
{
	printf("");
}

//este commentario ta no meiod de definições
int main(int x, double y, float z, char a)//inicia o programa
{
	t_Nodo *nodoLista = (t_Nodo *) malloc(sizeof(t_Nodo));//cria uma variavel ponteiro do tipo t_Nodo e aloca memoria pra ela, este � o ponteiro de inicio de toda a lista, pode-se dizer que ele � o "inicio de tudo"
	Lista *l = (Lista *) malloc(sizeof(Lista));//cria uma variavel ponteiro do tipo Lista e aloca memoria

	l->cabeca = nodoLista;//faz cabe�a da lista apontar para o endere�o da variavel nodoLista
	constroiLista(l);//envia o endere�o de nodo para contruir a lista

	printf("Lista vazia = %d\n", estahVazio(l));//testa se a lista est� vazia
	printf("Inserindo os elementos 1, 2, 3, 4, 5 e 6\n");
	insere_na_lista(l, 1);//insere na lista
	insere_na_lista(l, 2);//insere na lista
	insere_na_lista(l, 3);//insere na lista
	insere_na_lista(l, 4);//insere na lista
	insere_na_lista(l, 5);//insere na lista
	insere_na_lista(l, 6);//insere na lista

	imprimeLista(l);//chama a fun��o imprimir para imprimir a lista
	printf("Lista vazia = %d\n", estahVazio(l));//testa se a lista est� vazia

	printf("\nTentando remover 57\n");
	printf("Elemento removido: %d\n", apaga_da_lista(l, 57));//tenta apagar um elemento inexistente da lista e retorna um erro

	printf("\nElemento da cabeca: %d\n", cabeca(l));//retorna o elemento do topo da lista
	 imprimeLista(l);//imprime a lista

	printf("\nRemovendo 5\n");
	printf("Elemento removido: %d\n", apaga_da_lista(l, 5));//apaga o elemento 5 da lista

	printf("\nRemovendo 6\n");
	printf("Elemento removido: %d\n", apaga_da_lista(l, 6));//apaga o elemento 6 da lista

	printf("\nRemovendo 2\n");
	printf("Elemento removido: %d\n", apaga_da_lista(l, 2));//apaga o elemento 2 da lista
	imprimeLista(l);//imprime a lista atualizada sem os elementos que foram removidos

	printf("\nInserindo o elemento 7\n");
	insere_na_lista(l, 7);//insere o 7 na lista
	imprimeLista(l);//imprime a lista atualizada com o 7 inserido

	printf("\n3 esta na lista = %d\n", busca_lista(l, 3));//busca se o 3 est� na lista e retorna 1, verdadeiro, e 0 falso
	printf("6 esta na lista = %d\n", busca_lista(l, 6));//busca se o 6 est� na lista e retorna 1, verdadeiro, e 0 falso

	printf("\nElemento da cabeca: %d\n", cabeca(l));//retorna e imprime a cabe�a da lista

	printf("\nDestruindo Lista!\n");
	destroiLista(l);//destroi a lista
	imprimeLista(l);//tenta imprimir a lista destruida

//obs: tentar imprimir a lisa destruida pode causar o travamento do programa em alguns computadores

	free(l);//libera a memoria alocada para a lista
	free(nodoLista);//libera a memoria alocada para o nodo da lista
}//fim de main
//comentario depois de main

void constroiLista(Lista * l)//fun��o que controi a lista, recebe a referencia para a lista criada no main
{
	l->cabeca->prox = NULL;//faz a estrutura de proximo elemento da cabe�a da lista(prox) apontar para NULL,
	l->cabeca->ant = NULL;//faz a estrutura de elemento anterior da cabe�a da lista(ant) apontar para NULL,

	//Ou seja a lista esta vazia
}


void destroiLista(Lista * l)
{
	t_Nodo *inicio = l->cabeca->prox;//cria um variavel que aponta para a cabe�a da lista ou seja, para o inicio

	while (inicio != NULL)//percorre a lista desde o inicio at� o final da lista
	{
		free(inicio->ant);//libera a memoria do elemento anterior da lista
		if (inicio->prox == NULL)//testa se a lista j� chegou ao final, se sim ele libera a memoria armazenda para a variavel inicio
		{
			free(inicio);//libera a memoria de inicio
			break;//quebra o loop
		}
		inicio = inicio->prox;//faz inicio apontar sempre para a proxima estrutura, ou seja o proximo elemento da lista
	}

	l->cabeca->prox = NULL;//conclui a destrui��o fazendo o prox apontar para NULL
	l->cabeca->ant = NULL;//conclui a destrui��o fazendo o ant apontar para NULL
}

int busca_lista(Lista * l, elem_lista k)//fun��o que busca na lista, recebe uma lista e um elemento a ser buscado na lista
{
	t_Nodo *inicio = l->cabeca->prox;//que uma variavel ponteiro que aponta para a cabe�a da lista ou seja, o inicio

	while (inicio != NULL)//percorre do inicio da lista at� quando ela for NULL
	{
		if (inicio->elem == k)//testa se o elemento passado, k est� na lista
			break;//quebra o loop

		inicio = inicio->prox;//faz a lista apontar para o proximo elemento sa o elemento da lista n�o for igual a k
	}

    /*se k n�o estiver na lista a lista, inicio apontara para NULL, ou seja se n�o existe k na lista ela � nula, e se existe
    ela � diferente de nula, */
	return inicio != NULL;//retorna 1 se estiver na lista e 0 se n�o estiver na lista
}

void insere_na_lista(Lista * l, elem_lista x)//insere na lista, recebe de parametro a lista e o elemento a ser inserido
{
	t_Nodo *novo, *temp;//declara um variavel ponteiro de tipo t_Nodo novo e temp

	novo = (t_Nodo *) malloc(sizeof(t_Nodo));//aloca memoria para o novo elemento da lista

	if (novo == NULL)//testa se a memoria foi alocada corretamente
	{
		printf("Sem memoria!\n");
		exit(1);//encerra o programa inteiro se e memoria n�o tiver sido alocada corretamente
	}
	novo->elem = x;//coloca o elemento x no nodo novo

	temp = l->cabeca->prox;//faz temp apontar para o proximo elemento da lista
	l->cabeca->prox = novo;//faz a cabe�a apontar para o novo elemento
	novo->prox = temp;//faz o proximo elemento da lista apontar para o antigo elemento da cabe�a

	if (temp == NULL)//se temp for NULL, ent�o a lista est� vazia
	{
		novo->ant = l->cabeca;//se a lista est� vazia, o anterior do novo aponta para o anterior a ela, que nesse caso vai ser uma lista que possui elementos nulo, e n�o entra no calculo como um elemento incluso da lista, mas apenas funciona como a origem da lista, para est� lista, se este primeiro elemento n�o tivesse sido criado, n�o teria como adicionar novos elementos a lista
		return;//retorna ao chamador
	}

	temp->ant = novo;//faz o elemento da antiga cabe�a apontar para a nova cabe�a
	novo->ant = l->cabeca;//faz o novo elemento apontar para o proximo elemento da lista
}

elem_lista apaga_da_lista(Lista * l, elem_lista x)//apaga um elemento da lista
{
	t_Nodo *inicio, *temp;//cria um ponteiro de nodo inicio e temp
	inicio = l->cabeca->prox;//inicio aponta para a cabe�a da lista

	while (inicio != NULL)//percorre a lista at� o fim
	{
		if (inicio->elem == x)//se elemento da lista for igual a x, ent�o x esta na lista
			break;//acaba com o loop

		inicio = inicio->prox;//aponta para o proximo elemento da lista
	}

	if (inicio == NULL)//se inicio for igual a NULL, � porque a lista foi pecorrida t� o fim e o elemento x n�o foi encontrado nela
	{
		printf("\nElemento inexistente, retornando valor de erro\n");//imprime uma mensagem de erro
		return -7746738;//retorna um valor de erro que nada interfere no funcionamento do programa
	}
	//se x estiver na lista os codigos abaixo s�o execultados
	temp = inicio;//temp aponta para a posi��o onde x foi encontrado, mantendo assim o valor de x por equanto
	inicio = inicio->prox;//inicio aponta para o proximo elemento, agora inicio n�o tem mais o elemento igual a x
	inicio->ant = temp->ant;//faz o inicio conter  uma referencia para o elemento anterior da lista
	temp->ant->prox = inicio;//faz o elemento anterior a x ter o referencia para o proximo elemento depois do que � igual a x

	free(temp);//libera a memoria armazenada para o elemento igual a x

	return x;//retorna o elemento que foi removido, que era igual a x
}

int estahVazio(Lista * l)//testa se est� vazia
{
	if (l == NULL)//se a lista for NULL ela est� vazia
		return 1;//retorna 1, verdadeiro
	if (l->cabeca == NULL)//se a cabe�a da lista for NULL, a lista tamb�m est� vazia
		return 1;//retorna 1, vardadeiro
	if (l->cabeca->prox == NULL)//se o proximo elemento da cabe�a for NULL a lista tamb�m est� vazia, n�o se esque�a que por padr�o esta lista possui um elemento que � anterior a cabe�a, esse elemento � o inicio desta lista e sem ele a lista n�o funciona, se esse inicio de tudo n�o aponta para o proximo elemento da lista ent�o a lista est� vazia
		return 1;//retorna 1, verdadeiro

	return 0;// se n�o tiver vazia ela retorna 0
}

elem_lista cabeca(Lista * l)//retorna o elemento da cabe�a
{
	return l->cabeca->prox->elem;//retorna o elemento da cabe�a, que � na verdade o proximo elemento do inicio de tudo
}
/*este comentario ocupa 3 linha - 1
2
3*/
void imprimeLista(Lista * l)//imprime a lista
{
	t_Nodo *inicio = l->cabeca->prox;//aponta para o primeiro elemento da lista que � o proximo elemento do inicio de tudo
	int cont = 1;//contador

	printf("\nElementos da lista\n");
	while (l == NULL)//enquanto existir elementos na lista ele continua percorrendo
	{
		printf("Elemento %d = %d\n", cont, inicio->elem);//imprinme o elemento da lista
		inicio = inicio->prox;//aponta para o proximo elemento
		cont += 1;//incrementa 1 ao contador
	}

	if(!l)
	{
		int x;
		x = 5;
		printfdoif("testo");
		printfdoif2("tsto");
	}
	else
	{
		freesdoelse("fdd");
		//Comentario no if
	}
}


//este comentario esta no fim
