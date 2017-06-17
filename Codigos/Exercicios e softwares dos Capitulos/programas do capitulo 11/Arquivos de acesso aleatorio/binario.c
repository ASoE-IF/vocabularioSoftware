#include<stdio.h>

int main(void){
FILE *file;

if(file=fopen("binario.txt", "wb")!=NULL){
printf("Arquivo nao pode ser criado.");
}
int ab;
fwrite(&ab, sizeof(int), 1, file);
fprintf(file, "abc");

}