#include <stdio.h>

int main (void){
    FILE *arquivo;
    
    arquivo = fopen("tico.txt", "r");
    
    char tico[30];
    while(fgets(tico, 30, arquivo)!= NULL){
                         printf("%s", tico);
                         }
                         
                         fclose(arquivo);
                         
                         getchar();
}
    
