#include<stdio.h>

int main(void)
{
	FILE *abre;
	
	     char abrem[50];
	     
	abre = fopen("Exercicio 11,8.txt", "r");
	
	while(fgets(abrem, 50, abre)!=NULL){
                       printf("%s", abrem);
	}
	                   fclose(abre);
	                   getchar();
 }
