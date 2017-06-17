#include <stdio.h>

int main(void)
{
    int num, temp, cont, x;
    temp = 0;
    printf("Digite um inteiro positivo: ");
    scanf("\n%d", &num);

    for(cont = 1; cont <= num; cont++)
    {
        x = temp;
        for(temp = (temp + 1); temp <= (x + 1); temp++)
        {
            printf("%d ", temp);
        }
        printf("\n");
    }
}
