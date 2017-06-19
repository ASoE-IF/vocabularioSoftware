#include <stdio.h>

int main(void)
{
    int idade, segundos;

    printf("Digite sua idade: ");
    scanf("\n%d", &idade);

    segundos = (idade * 31536000);

    printf("Voce viveu %d segundo\n", segundos);

    return(0);
}
