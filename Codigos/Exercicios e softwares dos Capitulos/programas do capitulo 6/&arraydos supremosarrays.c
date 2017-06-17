#include<stdio.h>

void exibe_matriz(int valores[], int numero_elementos)
{
	int i;

	printf("prestes a exibir %d valores\n", numero_elementos);
	for (i = 0; i <= numero_elementos; i++)
		printf("%d\n", valores[i]);
}

void main(void)
{
	int notas[5] = { 70, 80, 90, 100, 90 };
	int conta[10] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	int pequeno[2] = { -33, -44 };

	exibe_matriz(notas, 5);
	exibe_matriz(conta, 10);
	exibe_matriz(pequeno, 2);
}