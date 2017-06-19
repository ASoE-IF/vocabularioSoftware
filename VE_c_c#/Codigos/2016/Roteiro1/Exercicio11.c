#include<stdio.h>

int fibbonaci(int num)
{
    if(num < 2)
        return (num);
    else
        return(fibbonaci(num - 1) + fibbonaci(num - 2));
}

int main(void)
{
    int num;

    printf("Digite um numero: ");
    scanf("\n%d", &num);

    printf("A sequencia de fibbonaci de %d e %d\n", num, fibbonaci(num));
}
