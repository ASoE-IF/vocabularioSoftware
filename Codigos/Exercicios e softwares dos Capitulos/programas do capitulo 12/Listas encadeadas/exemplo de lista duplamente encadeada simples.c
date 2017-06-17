#include<stdio.h>

int main(void)
{
	int nume;
	struct lista{
			int numero;
			struct lista *proximo;
			struct lista *anterior;
			}inicio, *nodo;
			
			inicio.proximo=NULL; //lista vazia;
			inicio.anterior=NULL; //aponta para anterior
			
			nodo=&inicio; //aponta para o inicio da lista
			for(nume=0; nume<=10; nume+=2)
			{
					nodo->proximo=(struct lista *)malloc(sizeof(struct lista));
					nodo->proximo->anterior=nodo;
					nodo=nodo->proximo;
					nodo->numero=nume;
					nodo->proximo=NULL;
					}
					
					nodo=inicio.proximo; //exibe a lista
					
					do{
							printf("%d  ", nodo->numero);
							nodo=nodo->proximo;
						}
						while(nodo->proximo);//motra 10 somente uma vez
						printf("\n");
						do{
								printf("%d  ", nodo->numero);
								nodo=nodo->anterior;
							}
							while(nodo->anterior);
							
}