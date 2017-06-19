#include<stdio.h>
#include<stdlib.h>

void lerVetor(int *vetor)
{
    int x;
    for(x = 0; x < sizeof(*vetor); x++)
    {
        vetor[x] = (x * 2);
    }
}

int main(void)
{
    int tamanho;
    int *vetor;
    int x;

    printf("Digite o tamanho do vetor: ");
    scanf("\n%d", &tamanho);

    if((vetor = (int *) calloc(tamanho, sizeof(int))) == NULL)
    {
        printf("Erro fatal!\n");
        exit(1);
    }
    
    lerVetor(vetor);

    for(x = 0; x<tamanho; x++)
    {
        printf("%d - [%d]\n", x, vetor[x]);
    }

    free(vetor);
}
