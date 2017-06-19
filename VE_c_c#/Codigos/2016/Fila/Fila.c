#include <stdio.h>
#include <stdlib.h>

typedef int elem_fila;//define o pseudonome para int

typedef struct//estrutura da fila
{
	int cabeca;//contera o indice da cabe�a da fila
	int cauda;//contera o indice para a calda da fila
	elem_fila *elementos;//ponteiro do tipo int, onde ser� armazenado o elemento da lista
	int tam_max;//tamanho maximo da lista

} Fila;

void construirFila(Fila * f, int tam_max);//prototipo da fun��o construirFila
void destruirFila(Fila * f);//prototipo da fun��o destruirFila
void enfileirar(Fila * f, elem_fila elem);//prototipo da fun��o enfileirar
elem_fila desenfileirar(Fila * f);//prototipo da fun��o desenfileirar
int estahVazio(Fila * f);//prototipo da fun��o estahVazio
elem_fila cabeca(Fila * f);//prototipo da fun��o cabeca
elem_fila cauda(Fila * f);//prototipo da fun��o cauda
void imprimeFila(Fila * f);//prototipo da fun��o imprimeFila

int main(void)//inicia o programa
{
	Fila *fila;//de clara uma fila que � um ponteiro do tipo Fila

	fila = (Fila *) malloc(sizeof(Fila));//aloca memoria para a fila
	construirFila(fila, 10);//constroi a fila, e passa como paramero a fila e o valor do tamanho max

	printf("Esta vazia = %d\n", estahVazio(fila));//imprime 1 se a fila esta vazia e 0 se n�o
	printf("\nAdicinando 556 e 56\n");
	enfileirar(fila, 556);//adiciona 556 a lista
	enfileirar(fila, 56);//adiciona 56 a lista
	imprimeFila(fila);//imprime a lista
	printf("\nEsta vazia = %d\n", estahVazio(fila));//imprime 1 se a fila esta vazia e 0 se n�o

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
	desenfileirar(fila);//remove o elemento da cabe�a
	imprimeFila(fila);//imprime a lista

	printf("\nDesenfilerando + 5 elementos!\n");
	desenfileirar(fila);//remove o elemento da cabe�a
	desenfileirar(fila);//remove o elemento da cabe�a
	desenfileirar(fila);//remove o elemento da cabe�a
	desenfileirar(fila);//remove o elemento da cabe�a
	desenfileirar(fila);//remove o elemento da cabe�a
	imprimeFila(fila);//imprime a lista

	printf("\nElemento da cabeca: %d\n", cabeca(fila));//imprime apenas o elemento da cabe�a
	printf("\nElemento da cauda: %d\n", cauda(fila));//imprime apenas o elemento da calda

	printf("\nDestruindo a fila!\n");
	destruirFila(fila);//destroi a fila
	imprimeFila(fila);//tenta imprimir a fila destruida
	return 0;
}

void construirFila(Fila * f, int tam_max)
{
	f->tam_max = tam_max;//coloca na fila, f->tam_max o valor de tam_max passado por parametro, obs: f->tam_max e tam_max s�o variaveis diferentes pois uma est� dentro da fila e uma n�o interfere na outra
	f->cauda = -1;//faz cauda = -1, siginifica que a fila est� vazia
	f->cabeca = -1;//faz cabe�a = -1, siginifica que a fila est� vazia
	f->elementos = (elem_fila *) calloc(f->tam_max, sizeof(elem_fila));//aloca memoria para o primeiro elemento da fila
}

void destruirFila(Fila * f)//recebe uma fila que pode estar cheia ou n�o e a destroi
{
	f->cabeca = -1;//zera a cabe�a da fila
	f->cauda = -1;//zera o total de elementos da fila
	f->tam_max = -1;//zera o tamanho max, fazendo que a lista n�o tenha mais elementos
	 free(f->elementos);//libera a memoria armazenada para os elementos
	free(f);//libera a memoria alocada para a fila
}

void enfileirar(Fila * f, elem_fila elem)//adiciona um elemento a cabe�a da fila
{
	if (f->cabeca < 0)//testa se a cabe�a � menor que zero, se sim, ela � = a -1, logo ela est� vazia, como se quer inserir um elemento, e lista tem que apontar agora para o indice do primeiro elemento, que � indice 1 = 0
	{
		f->cabeca++; //adiciona um a cabe�a que era -1 e agora pa�a a ser 0
	}

	if (f->cauda == f->tam_max - 1)//testa se a fila esta cheia, como o ultimo elemento da lista � sempre tam_max - 1, ent�o se cauda for igual a tam_max - 1, a fila est� cheia e n�o se pode inserir mais elemento
	{
		printf("\nFila cheia!\n");

		return;//retorna para o local que chamou
	}
    //se a lista n�o tiver cheia, as linha abaixo s�o execultadas
	f->cauda += 1;////incrementa um a calda
	*(f->elementos + f->cauda) = elem;//adicona o elemento no final da fila

}

elem_fila desenfileirar(Fila * f)
{
	int cont;//variavel de contador

	for (cont = 0; cont < f->cauda; cont++)//loop para percorrer de 0 at� a calda
	{
		*(f->elementos + cont) = *(f->elementos + cont + 1);//remove da cabe�a e adiciona a cabe�a o elemento que vem na sequencia, assim cada elementos avan�a uma casa para frente
	}
	*(f->elementos + f->cauda) = 0;//como todos os elementos avan�aram uma posi��o quando o antigo elemeto da cabe�a saio, ent�o a posi��o que pertencia a calda deve ser esvaziada, ou sej� igualar a 0
	f->cauda -= 1;//decrementa um elemento no total que existe na calda
}

int estahVazio(Fila * f)//testa se est� vazio
{
	return f->cabeca == -1;// retorna 1 verdadeiro 0 falso
}

elem_fila cabeca(Fila * f)//retorna o elemento da cabe�a da fila
{
	return *(f->elementos + f->cabeca);//retorna o elemento da cabe�a da fila
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
		printf("Posicao %d = %d\n", cont, *(f->elementos + cont));//imprime a posi��o do elemento e o elemento
	}
}
