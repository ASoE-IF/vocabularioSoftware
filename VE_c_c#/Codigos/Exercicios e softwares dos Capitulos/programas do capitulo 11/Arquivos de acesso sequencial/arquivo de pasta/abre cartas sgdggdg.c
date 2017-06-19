#include<stdio.h>

int main(void){
FILE *saida, *entrada;
char letra;

if((entrada=fopen("cartas.txt", "r"))==NULL)
printf("Impossivel abrir o arquivo.");
else
if((saida=fopen("abre conficartas.txt", "w"))==NULL)
printf("impossivel criar o arquivo.");
else
{
while(letra=fgetc(entrada)!=EOF)
putc(letra, saida);
fclose(entrada);
fclose(saida);
}
}