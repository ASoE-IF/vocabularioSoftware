#include <stdio.h>
#include <stdlib.h>

typedef int elem_fila;//define o pseudonome para int

typedef struct//estrutura da fila
{
	int cabeca;//contera o indice da cabeça da fila
	int cauda;//contera o indice para a calda da fila
	elem_fila *elementos;//ponteiro do tipo int, onde será armazenado o elemento da lista
	int tam_max;//tamanho maximo da lista

} Fila;

void construirFila(Fila * f, int tam_max);//prototipo da função construirFila
void destruirFila(Fila * f);//prototipo da função destruirFila
void enfileirar(Fila * f, elem_fila elem);//prototipo da função enfileirar
elem_fila desenfileirar(Fila * f);//prototipo da função desenfileirar
int estahVazio(Fila * f);//prototipo da função estahVazio
elem_fila cabeca(Fila * f);//prototipo da função cabeca
elem_fila cauda(Fila * f);//prototipo da função cauda
void imprimeFila(Fila * f);//prototipo da função imprimeFila

int main(void)//inicia o programa
{
	Fila *fila;//de clara uma fila que é um ponteiro do tipo Fila

	fila = (Fila *) malloc(sizeof(Fila));//aloca memoria para a fila
	construirFila(fila, 10);//constroi a fila, e passa como paramero a fila e o valor do tamanho max

	printf("Esta vazia = %d\n", estahVazio(fila));//imprime 1 se a fila esta vazia e 0 se não
	printf("\nAdicinando 556 e 56\n");
	enfileirar(fila, 556);//adiciona 556 a lista
	enfileirar(fila, 56);//adiciona 56 a lista
	imprimeFila(fila);//imprime a lista
	printf("\nEsta vazia = %d\n", estahVazio(fila));//imprime 1 se a fila esta vazia e 0 se não

	printf("\nAdicinando 576, 526, 5556, 5126, 5096, 5006, 5786 50096\n");
	enfileirar(fila, 576);//adiciona 576 a lista
	enfileirar(fila, 526);//adiciona 526 a lista
	enfileirar(fila, 5556);//adiciona 5556 a lista
	enfileirar(fila, 5126);//adiciona 5126 a lista
	enfileirar(fila, 5096);//adiciona 5096 a lista
	enfileirar(fila, 5006);//adiciona 5006 a lista
	enfileirar(fila, 5786);//adiciona 5786 a lista
	enfileirar(fila, 50096);//adiciona 50096 a lista
	imprimeFila(fila);//imprime a fila

	printf("\nTentando adicionar 56626 e 56674006\n");
	enfileirar(fila, 56626);//adiciona 56626 a lista
	enfileirar(fila, 56674006);//adiciona 56674006 a lista
	imprimeFila(fila);//imprime a fila

	printf("\nDesenfilerando!\n");
	desenfileirar(fila);//remove o elemento da cabeça
	imprimeFila(fila);//imprime a lista

	printf("\nDesenfilerando + 5 elementos!\n");
	desenfileirar(fila);//remove o elemento da cabeça
	desenfileirar(fila);//remove o elemento da cabeça
	desenfileirar(fila);//remove o elemento da cabeça
	desenfileirar(fila);//remove o elemento da cabeça
	desenfileirar(fila);//remove o elemento da cabeça
	imprimeFila(fila);//imprime a lista

	printf("\nElemento da cabeca: %d\n", cabeca(fila));//imprime apenas o elemento da cabeça
	printf("\nElemento da cauda: %d\n", cauda(fila));//imprime apenas o elemento da calda

	printf("\nDestruindo a fila!\n");
	destruirFila(fila);//destroi a fila
	imprimeFila(fila);//tenta imprimir a fila destruida
	return 0;
}

void construirFila(Fila * f, int tam_max)
{
	f->tam_max = tam_max;//coloca na fila, f->tam_max o valor de tam_max passado por parametro, obs: f->tam_max e tam_max são variaveis diferentes pois uma está dentro da fila e uma não interfere na outra
	f->cauda = -1;//faz cauda = -1, siginifica que a fila está vazia
	f->cabeca = -1;//faz cabeça = -1, siginifica que a fila está vazia
	f->elementos = (elem_fila *) calloc(f->tam_max, sizeof(elem_fila));//aloca memoria para o primeiro elemento da fila
}

void destruirFila(Fila * f)//recebe uma fila que pode estar cheia ou não e a destroi
{
	f->cabeca = -1;//zera a cabeça da fila
	f->cauda = -1;//zera o total de elementos da fila
	f->tam_max = -1;//zera o tamanho max, fazendo que a lista não tenha mais elementos
	 free(f->elementos);//libera a memoria armazenada para os elementos
	free(f);//libera a memoria alocada para a fila
}

void enfileirar(Fila * f, elem_fila elem)//adiciona um elemento a cabeça da fila
{
	if (f->cabeca < 0)//testa se a cabeça é menor que zero, se sim, ela é = a -1, logo ela está vazia, como se quer inserir um elemento, e lista tem que apontar agora para o indice do primeiro elemento, que é indice 1 = 0
	{
		f->cabeca++; //adiciona um a cabeça que era -1 e agora paça a ser 0
	}

	if (f->cauda == f->tam_max - 1)//testa se a fila esta cheia, como o ultimo elemento da lista é sempre tam_max - 1, então se cauda for igual a tam_max - 1, a fila está cheia e não se pode inserir mais elemento
	{
		printf("\nFila cheia!\n");

		return;//retorna para o local que chamou
	}
    //se a lista não tiver cheia, as linha abaixo são execultadas
	f->cauda += 1;////incrementa um a calda
	*(f->elementos + f->cauda) = elem;//adicona o elemento no final da fila

}

elem_fila desenfileirar(Fila * f)
{
	int cont;//variavel de contador

	for (cont = 0; cont < f->cauda; cont++)//loop para percorrer de 0 até a calda
	{
		*(f->elementos + cont) = *(f->elementos + cont + 1);//remove da cabeça e adiciona a cabeça o elemento que vem na sequencia, assim cada elementos avança uma casa para frente
	}
	*(f->elementos + f->cauda) = 0;//como todos os elementos avançaram uma posição quando o antigo elemeto da cabeça saio, então a posição que pertencia a calda deve ser esvaziada, ou sejá igualar a 0
	f->cauda -= 1;//decrementa um elemento no total que existe na calda
}

int estahVazio(Fila * f)//testa se está vazio
{
	return f->cabeca == -1;// retorna 1 verdadeiro 0 falso
}

elem_fila cabeca(Fila * f)//retorna o elemento da cabeça da fila
{
	return *(f->elementos + f->cabeca);//retorna o elemento da cabeça da fila
}

elem_fila cauda(Fila * f)//retorna o elemento da calda da fila
{
	return *(f->elementos + f->cauda);//retorna o elemento da calda da fila
}

void imprimeFila(Fila * f)//imprime a fila
{
	int cont;//contador

	printf("Elementos da Fila:\n");


	for (cont = 0; cont < f->tam_max; cont++)//contador vai do inicio ao tam_max da fila
	{
		printf("Posicao %d = %d\n", cont, *(f->elementos + cont));//imprime a posição do elemento e o elemento
	}
}
