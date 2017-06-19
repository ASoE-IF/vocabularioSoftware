#include<stdio.h>

void inverteVetor(char  *vetor, char *vetorInvertido)
{
	int x, y = 0;


		printf("%d\n\n", vetorInvertido);


	for(x = (sizeof(vetor) - 1); x >= 0; x--)
	{
		*(vetorInvertido + y) = *(vetor + x);
		printf("%c ", vetor[x]);
		y += 1;
	}
	printf("\n");

    for(x = 0; x < (sizeof(vetor)); x++)
	{
		printf("%d\n", &vetorInvertido[x]);
	}
	printf("\n");
	for(x = 0; x < (sizeof(vetor)); x++)
	{
		printf("%c ", vetorInvertido[x]);
	}
	printf("\n");
}

int main(void)
{
	char vetor[] = "12345678";
	char *vetorInvertido[50];
	int x;

	printf("%d\n", &vetorInvertido);

	inverteVetor(vetor, &vetorInvertido);

	for(x = 0; x < (sizeof(vetor) / sizeof(char)); x++)
	{
		printf("%c\n", vetorInvertido);
	}
	for(x = 0; x < (sizeof(vetor)); x++)
	{
		printf("%d \n", &vetorInvertido[x]);
	}

	getchar();
}
