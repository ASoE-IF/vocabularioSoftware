#include <stdio.h>

int main(void)
{
    int x, y;
    printf("Digite um numero: ");
    scanf("\n%d", &x);

    printf("Digite outro inteiro: ");
    scanf("\n%d", &y);

    printf("A soma de %d\n", (x + y));

    return(0);
}
