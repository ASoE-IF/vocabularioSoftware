#include<stdio.h>

void main(void)
{
	FILE *tipos;
	int rep;
	tipos = fopen("Exercicio 11,16.txt", "w");
	for (rep = 0; rep <= 10; rep++)
		fprintf(tipos, "*");
	printf("\n|Tipo de dados |Tamanho|");
	printf("\n|**************|*******|\n");
	printf("|char          |%dbit   |\n", sizeof(char));
	printf("|unsigned char |%dbit   |\n", sizeof(unsigned char));
	printf("|short int                 |%dbit\n", sizeof(short int));
	printf("|unsigned short int|%dbit\n", sizeof(unsigned short int));
	printf("|int   |%dbit\n", sizeof(int));
	printf("|long int   |%dbit\n", sizeof(long int));
	printf("|unsigned long int    |%dbit\n", sizeof(unsigned long int));
	printf("|float    |%dbit\n", sizeof(float));
	printf("|double     |%dbit\n", sizeof(double));
	printf("|long double     |%dbit\n", sizeof(long double));
	fprintf(tipos, "%d", sizeof(char));
}