#include <stdio.h>
#include <stdlib.h>

#define VAZIA -1

typedef struct //declara struct da pilha
{
	int tamMax;//guarda tamanho maximo da pilha
	int top;//posição do elemento do topo da pilha
	char *elementos;//tipo de elementos da pilha
} Pilha; //tipo pilha

void initPilha(Pilha * p, int tamMax);//prototipo da função que inicializa a pilha
void freePilha(Pilha * p);//prototipo da função que libera os elementos da pilha
char pop(Pilha * p);//prototipo da função que remove um elemento da pilha
void push(Pilha * p, char elem);//prototipo da função que adciona um elemento da pilha
int empty(Pilha * p);//prototipo da função que testa se a pilha esta vazia ou cheia
char peek(Pilha * p);//prototipo da função que retorna o elemento do topo da pilha
void imprimePilha(Pilha * p);//prototipo da função que imprime a pilha

int main(void)
{
	Pilha *pilha = (Pilha *) malloc(sizeof(Pilha));//declara e aloca memoria para uma pilha
	initPilha(pilha, 5);//inicializa a pilha

	printf("Pilha vazia(1 - true, 0 false) = %d\n", empty(pilha));//imprime 1 se vazia e 0 se tiver elementos na pilha

	imprimePilha(pilha);//imprime os elementos da pilha

	printf("\nInserindo \'a\', \'b\'\n");
	push(pilha, 'a');//insere 'a' na pilha
	push(pilha, 'b');//insere 'b' na pilha

	printf("\nPilha vazia(1 - true, 0 false) = %d\n", empty(pilha));//imprime 1 se vazia e 0 se tiver elementos na pilha

	imprimePilha(pilha);//imprime a pilha

	printf("\nInserindo \'c\', \'d\', \'e\', \'f\'\n");
	 push(pilha, 'c');//insere 'c' na pilha
	push(pilha, 'd');//insere 'd' na pilha
	push(pilha, 'e');//insere 'e' na pilha
	push(pilha, 'f');//tenta inserir 'f' na pilha, como a pilha agora esta cheia, a função aviza que a pilha esta cheia

	imprimePilha(pilha);//imprime a pilha

	printf("\n%c foi removido!\n", pop(pilha));//remove o ultimo elemento da pilha
	imprimePilha(pilha);//imprime a pilha
	printf("\n%c foi removido!\n", pop(pilha));//remove o ultimo elemento da pilha
	imprimePilha(pilha);//imprime a pilha

	freePilha(pilha);//libera a memoria alocada para os elementos da pilha
	printf("\nPilha Limpa\n");
	imprimePilha(pilha);//tenta imprimir os elementos inexistentes da pilha

	printf("%c", *(pilha->elementos));//tenta forçar a impressao de um elementos que já não existe na pilha

	free(pilha);//libera definitivamente a memoria alocada para o ponteiro pilha
}

void initPilha(Pilha * p, int tamMax)//cria a funcão que inicializa a pilha
{
	p->tamMax = tamMax;//adiciona tamMax a tamMax da pilha
	p->top = VAZIA;//esvazia a pilha

	char *conteudo = (char *)calloc(tamMax, sizeof(char));//aloca tamMax vez tamanho de char para elementos da pilha
	p->elementos = conteudo;//elementos da pilha aponta para a memora alocada
}

void freePilha(Pilha * p)//cria a função que libera a memoria dos elementos da pilha
{
	p->top = -1;//esvazia o total de elementos ja na pilha
	p->tamMax = 0;//esvazia o tamanho da pilha

	free(p->elementos);//libera a memoria dos elementos da pilha
}

char pop(Pilha * p)//cria a função que remove um elemento da pilha e o retorna
{
	char temp = *(p->elementos + p->top); //cria temp e o adciona o elemento no topo da pilha

	*(p->elementos + p->top) = 0;//deleta o elemento do topo
	p->top -= 1;//deminui o total de elementos na pilha

	return temp;//retorna o elemento removido
}

void push(Pilha * p, char elem)//cria função que adicina um elemento a pilha
{
	p->top += 1;//incrementa 1 no tamanho d pilha

	if (p->top == p->tamMax)//testa se a pilha esta cheia
	{
		p->top -= 1;//top deve empre ser menor que tamMax
		printf("\nPilha cheia!\n");
		return;//retorna ao chamador
	}

	*(p->elementos + p->top) = elem;//adiciona na pilha o valor passado como parametro
}

int empty(Pilha * p)//cria função que testa se a pilha esta vazia
{
	return p->top == VAZIA; //testa se a pilha esta vazia e retorna: 1 true, 0 false
}

char peek(Pilha * p)//cria função que retorna o valor do topo da pilha
{
	if (empty(p))//se a pilha estiver vazia, encerra o programa
	{
		printf("Pilha sem elementos!");
		exit(1);
	}
	return p->elementos[p->top];//se não retorna o valor do topo
}

void imprimePilha(Pilha * p)//cria função que imprime a pilha
{
	int cont;//declara contadr

	printf("Elementos da Pilha:\n");


	for (cont = 0; cont < p->tamMax; cont++)//percorre toda a pilha
	{
		printf("Posicao %d = %c\n", cont, *(p->elementos + cont));//imprime um por um os elementos da pilha
	}
}
