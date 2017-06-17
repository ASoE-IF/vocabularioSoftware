#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>

#define MAX 80

char *p[MAX];
int spos;
int rpos;
int enter(void), qstore(char *q), review(void), delete(void), *qretrieve(void);

int main(void)
{
	char s[80];
	register int t;
	
	for(t=0; t<MAX; ++t) p[t] = NULL; //inicia a matriz com NULL
	
	for( ; ; )
	{
		printf("Inserir, Listar, Remover, Sair: ");
		gets(s);
		*s = toupper(*s);
		
			switch(*s){
				case 'I':
						enter( );
						break;
				case 'L':
						review( );
						break;
				case 'R':
						delete( );
						break;
				case 'S':
						exit(0);
						}
			}
}

//Insere um evento na lista

int enter(void)
{
	char s[256]; *p;
	
	do {
			printf("Insira um evento %d: ", spos+1);
			gets(s);
			if(*s==0) break; //Nenhuma entrada
			*p=malloc(strlen(s)+1);
			if(!p){
					printf("Sem memoria. \n");
					return;
					}
					strcpy(*p, s);
					if(*s) qstore(*p);
				}
		while(*s);
}

//Ve o que a na fila

int review(void)
{
	register int t;
	for(t=rpos; t<spos; ++t)
			printf("%d. %s", t+1, p[t]);
}

//apaga um elemento da fila
int delete(void)
{
	char *p;
	
	if((p = qretrieve())==NULL)
	return;
	printf("%s\n", p);
}

//Armazena um evento
int qstore(char *q)
{
	if(spos==MAX){
		printf("Lista cheia.\n");
		return;
	}
	
	p[spos] = q;
	spos++;
}

//Recupera um evento
int *qretrieve(void)
{
	if(rpos==spos){
			printf("Sem eventos.\n");
			return NULL;
		}
	rpos++;
	return p[rpos-1];
}	