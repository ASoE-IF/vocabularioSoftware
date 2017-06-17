#include<stdio.h>

int main(void)
{
	FILE *file;
	char digite[50];
	printf("Digite o nome do arquivo a ser criado: ");
	scanf("%s", digite);
	file=fopen(digite, "w");
	while(file=fgetc(digite)!=EOF){
	putc(digite, file);
	}
	fclose(file);
}