#include<stdio.h>

char tabuleiro[3][3];

int jogar(int x, int y, char c);
int imprime_tab();

int main(void)
{
    int contX, contY; //variaveis usadas no for de limpar o tabuleiro
    int x, y, c, player; //variaveis do jogo
    int status = 1; //variavel que define se o jogo acabou ou nao acabou(1 jogo acontecendo, 0 jogo nao acontecendo)

    player = 1;//define qual player esta jogando

    printf("********Jogo da Velha********\n\n");

    /*Esse for garante que quaso exista lixo na
    memoria do array, ele seja transformado em zero*/
    for(contX = 0; contX < 3; contX++)
    {
        printf("|");
        for(contY = 0; contY < 3; contY++)
        {
            tabuleiro[contX][contY] = 0;
            printf("%c|", tabuleiro[contX][contY]);
        }
        printf("\n");
    }

    while(status == 1)//loop infinito ate o fim do jogo, ou seja até status ser zero
    {
        //Solicita a entrada x do usuario
        printf("\nDigite uma posicao x: ");
        scanf("\n%d", &x);

        while((x < 0) || (x > 2))//testa a entrada x do usuario
        {
            printf("Valor invalido! Tente novamente\n");
            printf("Digite uma posicao x: ");
            scanf("\n%d", &x);
        }

        //solicita a entrada y do usuario
        printf("Digite uma posicao y: ");
        scanf("\n%d", &y);

        while((y < 0) || (y > 2))//testa a entrada y do usuario
        {
            printf("Valor invalido! Tente novamente\n");
            printf("Digite uma posicao y: ");
            scanf("\n%d", &y);
        }

        if(player == 1)//troca de player entre os players 1(X) e 2(O)
        {
            c = 'X';
            player = 0;
        }

        else
        {
            c = 'O';
            player = 1;
        }

        /*chama a funcao jogar e colocar o valor de retorno em status
         se status for 1 o jogo continua, se for 0 é fim de jogo*/
        status = jogar(x, y, c);
    }
    printf("\nFim de Jogo!\n");

    //fim do programa e fim de jogo
}

int jogar(int x, int y, char c)
{
    int teste;//variavel usada para testar o valor de retorno

    tabuleiro[x][y] = c; //insere o valor de c no tabuleiro

    teste = imprime_tab();//chama a fução para imprimir o tabuleiro e guarda o valor de retorno em teste

    if(teste == 0)//testa se o valor de teste é ou não zero, se for, então o tabuleiro esta cheio, e é fim de jogo
        return 0;//retorna zero, fim de jogo

    else//senão ainda tem posções livres no array e o jogo deve continuar
        return 1;//retorna 1, o jogo continua
}

int imprime_tab()
{
    int contX, contY;//variaveis para contar
    int teste = 0;//armazena quantas posições do tabuleiro ainda estão vazias

    printf("\n");
    for(contX = 0; contX < 3; contX++)
    {
        printf("|");
        for(contY = 0; contY < 3; contY++)
        {
            if(tabuleiro[contX][contY] == 0)//esta se existe posição vazia no tabuleiro, se sim incrementa em teste
                teste += 1;

            printf("%c|", tabuleiro[contX][contY]);
        }
        printf("\n");
    }

    return (teste);//retorna o valor de teste para a função jogar
}
