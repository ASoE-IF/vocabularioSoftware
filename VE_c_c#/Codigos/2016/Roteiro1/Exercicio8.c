#include<stdio.h>

int main(void)
{
    int linha, funcoes, equipe, bugs;

    printf("Digite a quantidade de linhas: ");
    scanf("\n%d", &linha);
    printf("Digite a quantidade de funcoes: ");
    scanf("\n%d", &funcoes);
    printf("Digite o numero de membros da equipe: ");
    scanf("\n%d", &equipe);
    printf("Digite o numero de bugs do programa: ");
    scanf("\n%d", &bugs);

    printf("A eficiencia de equipe foi de %f\n", ((linha / funcoes) / equipe - (4.2 * bugs)));
}
