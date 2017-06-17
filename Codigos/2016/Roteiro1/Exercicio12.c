#include<stdio.h>

int main(void)
{
    int num, cont, divcont, soma;
    divcont = 0;
    soma = 1;

    for(num = 5; num <= 20; num++)
    {
        for(cont = 1; cont <= num; cont++)
        {
            if((num % cont) == 0)
                divcont += 1;
            if(divcont > 2)
                break;
        }

        if(divcont == 2)
        {
            soma = soma * num;
        }

        divcont = 0;
    }

    printf("A soma e %d", soma);
}
