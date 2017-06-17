#include<stdio.h>

void teste(float num)
{
    if(num > 0)
        printf("%f e positivo\n", num);
    else
    {
        if(num < 0)
            printf("%f e negativo\n", num);
        else
            printf("%f e zero\n", num);
    }
}
int main(void)
{
    float num;
    printf("Digite um numero: ");
    scanf("\n%f", &num);

    teste(num);
}
