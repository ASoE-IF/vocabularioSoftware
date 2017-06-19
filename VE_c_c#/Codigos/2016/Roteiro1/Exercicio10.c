#include<stdio.h>

int main(void)
{
    float num, temp, soma, cont;

    temp = -999999999;
    soma = 0;
    cont = 0;

    printf("Digite valores inteiros: ");
    while(num >= temp)
    {
        scanf("\n%f", &num);

        if(num >= temp)
            temp = num;

        soma = soma + num;
        cont += 1;
    }

    printf("A media dos valores digitados e %f\n", (soma / cont));

    return (0);
}
