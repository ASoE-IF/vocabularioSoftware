#include<stdio.h>

int main(void)
{
int le;
FILE *file;
if((file=fopen("arq.txt", "r"))==NULL){
printf("Memoria insuficiente.\n");
return 0;
}

else
		{
		printf("Sucesso ao abrir o arquivo.\n");
			while((le=getc(file))!=EOF){
			putchar(le);
			}
			fclose(file);
		}
	return 0;
}
