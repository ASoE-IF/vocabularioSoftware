#include <stdio.h>
int cria(void){
    FILE *arquivos;
    
         arquivos = fopen("tico.txt", "w");
         fprintf(arquivos, "tico tus es\?");
    return 0;
}
int main (void){
    cria();
    FILE *arquivo;
    
    arquivo = fopen("tico.txt", "r");
    
    char tico[30];
    while(fgets(tico, 30, arquivo)!= NULL){
                         printf("%s", tico);
                         }
                         
                         fclose(arquivo);
                         
                         getchar();
}
    
