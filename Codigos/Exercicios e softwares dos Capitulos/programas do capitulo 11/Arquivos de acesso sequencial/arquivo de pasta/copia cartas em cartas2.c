#include<stdio.h>

int main(void)
{
	FILE *file, *copia;
	file = fopen("cartas.txt", "r");
	copia = fopen("cartas2.txt", "w");
	int arq;
	while ((arq = fgetc(file)) != EOF)
	{
		putchar(arq);
		fputc(arq, copia);
	}
	fclose(file);
	fclose(copia);
}