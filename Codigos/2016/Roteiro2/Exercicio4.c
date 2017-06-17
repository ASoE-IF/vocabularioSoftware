#include<stdio.h>
#include<math.h>

float delta(float a, float b, float c)
{
    return ((b * b) - (4 * a * c));
}

void raiz(float b, float delt)
{
    float x1, x2;
    if(delt < 0)
    {
        printf("As raízes são números complexos\n");
    }
    else
    {
        x1 = -b + sqrt(delt);
        x2 = -b - sqrt(delt);
        printf("A solucao de equacao e: S = {%.2f, %.2f}\n", x1, x2);
    }
}

int main(void)
{
    float a, b, c, delt;

    printf("Digite a: ");
    scanf("\n%f", &a);
    while(a == 0)
    {
        printf("ERRO, a = 0, tente novamente\n");
        printf("Digite a: ");
        scanf("\n%f", &a);
    }

    printf("Digite b: ");
    scanf("\n%f", &b);
    printf("Digite c: ");
    scanf("\n%f", &c);

    delt = delta(a, b, c);
    raiz(b, delt);

    getchar();
}
