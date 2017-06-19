#include<stdio.h>

void teste(float num)
{
    if(num == 0)
        printf("%f e nulo\n", num);
    else
        printf("%f nao e nulo\n", num);
}
int main(void)
{
    float num;
    printf("Digite um numero: ");
    scanf("\n%f", &num);

    teste(num);
}
