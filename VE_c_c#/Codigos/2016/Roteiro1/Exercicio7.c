#include<stdio.h>

int main(void)
{
    int num;

    printf("Digite um inteiro: ");
    scanf("\n%d", &num);

    if((num % 2) == 0)
        printf("%d e par\n", num);
    else
        printf("%d e impar\n", num);
}
