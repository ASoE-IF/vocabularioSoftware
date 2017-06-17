#include<stdio.h>

int main(void)
{
    float dolar;

    printf("Digite quantos dolar voce tem: ");
    scanf("\n%f", &dolar);

    printf("seus %f dolar(es) e igual %f reais", dolar, (dolar * 3.55));

    return(0);
}
