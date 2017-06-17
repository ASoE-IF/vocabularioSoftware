#include <stdio.h>

int main(void)
{
    float base, altura;

    printf("Area de um retangulo\n");
    printf("Digite a base do retangulo: ");
    scanf("\n%f", &base);

    printf("Digite a altura do rentangulo: ");
    scanf("\n%f", &altura);

    printf("A area do rentangulo e %f\n", (base * altura));

    return(0);
}
