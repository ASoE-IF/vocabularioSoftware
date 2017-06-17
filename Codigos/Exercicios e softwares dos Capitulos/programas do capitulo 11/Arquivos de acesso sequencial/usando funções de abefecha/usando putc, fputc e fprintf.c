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
			fputc("fputc: ola israel seu arquivo foi criado com sucesso.\n", file);
			putc("putc: ola israel seu arquivo foi criado com sucesso.\n", file);
			fprintf(file, "fprintf: ola israel seu arquivo foi criado com sucesso.\n");
		}
	return 0;
} 