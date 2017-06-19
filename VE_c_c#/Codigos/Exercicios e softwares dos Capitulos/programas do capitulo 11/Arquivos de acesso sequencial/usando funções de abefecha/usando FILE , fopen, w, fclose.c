#include<stdio.h>

int main(void)
{
FILE *file;
if((file=fopen("arq.txt", "w"))==NULL){
printf("Memoria insuficiente.\n");
return 0;
}

else
		{
			printf("Sucesso ao criar o arquivo.\n");
		}
	fclose(file);
} 