#include<stdio.h>
#include<stdlib.h>

int main(void)
{
	int tamanhoDaTurma, cont;
	float *notas;
	float media = 0;
	
	printf("Digite o tamanho da turma: ");
	scanf("\n%d", &tamanhoDaTurma);
	
	if((notas = (float *) malloc(tamanhoDaTurma * sizeof(float))) == NULL)
	{
		printf("Erro fatal!");
		exit(1);
	}

	for(cont = 0; cont < tamanhoDaTurma; cont++)
	{
		printf("Digite uma notas: ");
		scanf("%f", &notas[cont]);
		media += notas[cont];
	}
	
	printf("A media e: %f\n", media / tamanhoDaTurma);
	
	free(notas);
}
