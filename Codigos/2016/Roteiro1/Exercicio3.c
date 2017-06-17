#include <stdio.h>

int main(void)
{
    int x, y;

    printf("Digite um inteiro: ");
    scanf("\n%d", &x);

    printf("Digite outro inteiro: ");
    scanf("\n%d", &y);

    if(x > y)
        printf("%d e o maior\n", x);
    else
        printf("%d e o maior\n", y);

    return(0);
}
