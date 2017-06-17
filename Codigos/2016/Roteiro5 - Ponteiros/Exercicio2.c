#include<stdio.h>

int contaImpares(int *num[], int x)
{
    int cont = 0, y, z;

    for(y = 0; y < x; y++)
    {
        z = *(num + y);
        if((z % 2) != 0)
        {
            cont += 1;
        }
    }

    return cont;
}

int main(void)
{
    int num[5] = {1, 2, 3, 4, 5};

    printf("O total de numeros impares e: %d", contaImpares(num, 5));
}
