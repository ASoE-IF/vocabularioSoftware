#include<stdio.h>

void imprimeVetor(char *vet)
{
    int x;

    printf("%d, %d\n", sizeof(vet), sizeof(char));

    for(x = 0; x < (sizeof(vet) / sizeof(char)); x++)
    {
        printf("%c", *(vet + x));
    }
}

int main(void)
{
    char nome[] = "israel";

    imprimeVetor(&nome);
}
