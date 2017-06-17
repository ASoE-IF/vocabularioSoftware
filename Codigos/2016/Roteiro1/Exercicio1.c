#include <stdio.h>

int main(void)
{
    char nome[50];
    printf("Digite o seu nome: ");
    scanf("%s", nome);

    printf("Ola %s", nome);

    return(0);
}
