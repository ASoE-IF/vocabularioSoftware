#include<stdio.h>
#include<time.h>

void main(void)
{
	FILE *armz;
	char *carta[9]={"mago","feiticeira","cavaleiro","dragão","batalha","soldado","obelisko", "dragao alado de rá", "sliffer"};
	int x = 0, esco;
	srand((unsigned)time(NULL));
	armz = fopen("cartas.txt", "w");
	fprintf(armz, "Baralho de cartas aleatorio\n\n");
	while (++x < 9)
	{
		esco = rand() % 9;
		fprintf(armz, "%d|%s\n", x, carta[esco]);
	}
}