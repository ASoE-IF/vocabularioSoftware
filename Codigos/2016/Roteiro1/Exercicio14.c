#include<Stdio.h>
#include<stdlib.h>

int main(void)
{
    float notas, mediaLista, total, notaFinal;
    int contLista = 0;
    notas = 0;
    mediaLista = 0;

    printf("*********Notas das listas*********\n");
    while(notas >= 0)
    {
        printf("Digite uma nota da %d lista: ", (contLista + 1));
        scanf("\n%f", &notas);
        if(notas >= 0)
        {
            contLista += 1;
            mediaLista = mediaLista + notas;
        }

    }
    mediaLista = mediaLista / contLista;
    mediaLista = ((mediaLista * 40) / 100);

    printf("\n*********Nota do projeto*********\n");
    printf("Digite a nota do projeto: ");
    scanf("\n%f", &notas);
    notas = ((notas * 60) / 100);

    total = (notas + mediaLista);

    printf("\n*********Situacao do estudante*********\n");
    if(total >= 70)
    {
        printf("Situacao = Aprovado\n");
        printf("Media = %.2f\n", total, contLista);
    }
    else
    {
        if((total < 70) && (total >= 40))
        {
            notaFinal = ((500 - (6 * total)) / 4);
            printf("Situacao = Prova final\n");
            printf("Media = %.2f\n", total);
            printf("Nota necessaria na final: %.2f\n", notaFinal);

            printf("\n*********Prova final*********\n");
            printf("Digite sua nota da final: ");
            scanf("\n%f", &notaFinal);

            total = (((6 * total) + (4 * notaFinal)) / 10);

            if(total >= 50)
            {
                printf("Situacao = Aprovado\n");
                printf("Media final: %.2f\n", total);
            }
            else
            {
                printf("Situacao = Reprovado\n");
                printf("Media final: %.2f\n", total);
            }
        }
        else
        {
            printf("Situacao = Reprovado\n");
            printf("Media = %.2f\n", total);
        }
    }
}
