#include<stdio.h>

int main(void)
{
    int TVideo, TAudio, TDados, Capacidade;

    printf("Digite a taxa de transmissao maxima de video: ");
    scanf("\n%d", &TVideo);

    printf("Digite a taxa de transmissao maxima de audio: ");
    scanf("\n%d", &TAudio);

    printf("Digite a taxa de transmissao maxima de dados: ");
    scanf("\n%d", &TDados);

    printf("Digite a capacidade do canal contratado: ");
    scanf("\n%d", &Capacidade);

    printf("A quantidade maxima de dados transmitida pelo usuario foi de: %f dados\n",
           ((TVideo*5.2 + TAudio*3.4 + TDados*1.5) / Capacidade));

    return(0);
}
