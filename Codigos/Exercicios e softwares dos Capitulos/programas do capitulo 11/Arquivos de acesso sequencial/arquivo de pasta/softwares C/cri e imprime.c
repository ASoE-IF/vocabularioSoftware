#include<stdio.h>

int main (void){
    FILE *arquivo, *oi;
    
         oi = fopen("ticoss.txt", "w");
         fprintf(oi, "tico tus es\?");
         
         
    arquivo = fopen("ticoss.txt", "r");
    
    char tico[30];
    while(fgets(tico, 30, arquivo)!= NULL){
                         printf("%s", tico);
                         }
                         
                         fclose(arquivo);
                         
                         getchar();
}
    
