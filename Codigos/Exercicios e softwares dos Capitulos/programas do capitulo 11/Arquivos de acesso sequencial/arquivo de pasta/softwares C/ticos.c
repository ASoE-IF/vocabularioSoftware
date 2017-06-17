#include <stdio.h>

int main (void){
    FILE *arquivo;
    
         arquivo = fopen("tico.txt", "w");
         fprintf(arquivo, "tico tus es\?");
    return 0;
}
