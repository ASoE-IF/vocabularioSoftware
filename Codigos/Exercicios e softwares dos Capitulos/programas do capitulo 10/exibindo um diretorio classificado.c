#include<stdio.h>
#include<stdlib.h>

int main(void)
{
	struct lista_arq
	{
		int nomearq;
		struct lista_arq *proximo;
	} inicio, *nodo, *novo, *anterior;
	
		inicio.proximo = NULL;
			// encontra a posição correta.
			anterior = &inicio;
			nodo = inicio.proximo;
			
				for (int nume = 1; nume <= 10; nume += 2)
	{
		nodo->proximo = (struct lista_arq *)malloc(sizeof(struct lista_arq));
		nodo = nodo->proximo;
		nodo->nomearq = nume;
		nodo->proximo= NULL;
	}
		while (nodo)
		{
			printf("%d\n", nodo->nomearq);
			nodo = nodo->proximo;
		}
			
									while (nodo)
			{
				nodo = nodo->proximo;
				anterior = anterior->proximo;
			}
			if (novo == NULL)
			{
				printf("Memoria insuficiente para armazenar a lista.\n");
				exit(1);
			}
			for(int x=0; x<=10; x++){
			novo = (struct lista_arq *)malloc(sizeof(struct lista_arq));
			novo->proximo = nodo;
			anterior->proximo = novo;
			novo->nomearq=x;
		}
		nodo = inicio.proximo;
		while (nodo)
		{
			printf("%s\n", nodo->nomearq);
			nodo = nodo->proximo;
		}
	getchar();
	}