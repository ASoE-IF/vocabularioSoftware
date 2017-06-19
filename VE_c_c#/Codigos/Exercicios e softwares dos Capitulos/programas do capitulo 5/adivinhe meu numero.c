#include<stdio.h>
#include<time.h>
digite(){
        int x, y;
        printf("Estou pensando em um numero,\nadivinhe qual e.\n");
        printf("Digite uma tentativa: ");
        scanf("%d", &x);
        srand((unsigned)time(NULL));
        y=rand()%1;
        if(x==y)
        printf("Parabéns.\nvocê acertou.\n");
        printf("Quer tentar novamente?");
        tente();
        if(x!=y)
        printf("Você errou.");
        printf("Tenta novamente:\n");
        denovo();
}
denovo(){
         int x, y;
         printf("Digite uma tentativa: ");
         scanf("%d", &x);
         if(x==y)
         printf("Parabéns.\nvocê acertou.\n");
         printf("Quer tentar novamente?");
         tente();
         if(x!=y)
         printf("Você errou.");
         printf("Tente novamente:\n");
         denovo();
}
tente(){
        int a;
        printf("\n1)Continue.\n2)Sair.\n");
        printf("Digite: ");
        scanf("%d", &a);
        if(a==1)
        digite();
        if(a>1)
        return 0;
}
main()
{
      digite();
}
