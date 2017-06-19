#include<stdio.h>

void inverteVetor(int  *vetor, int *vetorInvertido)
{
	int x, y;
	y = 0;



	for(x = 4; x >= 0; x--)
	{
		*(vetorInvertido + y) = *(vetorInvertido + x);
		y += 1;
	}
	
		for(x = 0; x < 5; x++)
	{
		printf("%d ",*(vetor + x));
	}
	
}

int main(void)
{
	int vetor[5] = {1, 2, 3, 4, 5};
	int *vetorInvertido;


	inverteVetor(vetor, &vetorInvertido);

	getchar();
}
