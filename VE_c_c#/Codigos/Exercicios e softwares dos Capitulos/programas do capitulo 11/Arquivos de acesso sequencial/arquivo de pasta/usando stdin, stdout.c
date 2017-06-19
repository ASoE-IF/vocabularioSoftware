#include<stdio.h>

int main(void){
char x[50];
printf("ola\n");
fprintf(stdout, "ola\n");
scanf("%s", x);
fscanf(stdin, "%s", x);
}