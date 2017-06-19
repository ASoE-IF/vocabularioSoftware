#include<stdio.h>
#include<time.h>
int game(){
int vida, vida2, pontos, danos, pontos2, danos2, atk, atk2, novos, novos2;
vida=2000;
vida2=2000;
printf("Jogador                                 Computador\n");
printf("Portos de vida atuais %d       ", vida);
printf("       Portos de vida atuais %d\n", vida);
srand((unsigned)time(NULL));
atk=rand()%3000;
printf("atk igual a %d            ", atk);
atk2=rand()%3000;
printf("            atk igual a %d\n", atk2);
{
if(atk>atk2)
danos=atk-atk2;
if(atk>atk2)
printf("Danos recebidos 0                      Danos recebidos %d\n", danos);
if(atk>atk2)
pontos=vida-danos;
if(atk>atk2)
printf("Pontos de vida restantes %d         Pontos de vida restantes %d\n", vida, pontos);
if(atk2>atk)
danos2=atk2-atk;
if(atk2>atk)
printf("Danos recebidos %d                      Danos recebidos 0\n", danos2);
if(atk2>atk)
pontos2=vida2-danos2;
if(atk2>atk)
printf("Pontos de vida restantes %d           Pontos de vida restantes %d\n", pontos2, vida2);
if(pontos<=0)
printf("Fim de jogo\n");
if(pontos<=0)
return 0;
if(pontos<=0)
printf("Fim de jogo\n");
if(pontos<=0)
return 0;
else
printf("Em breve...");
}}
main(){
game();
}