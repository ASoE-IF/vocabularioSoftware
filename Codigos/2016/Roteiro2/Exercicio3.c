#include<stdio.h>

float delta(float a, float b, float c)
{
    return ((b * b) - (4 * a * c));
}

int main(void)
{
    float a, b, c;

    printf("Digite a: ");
    scanf("\n%f", &a);
    printf("Digite b: ");
    scanf("\n%f", &b);
    printf("Digite c: ");
    scanf("\n%f", &c);

    printf("O delta e %f\n", delta(a, b, c));
}
