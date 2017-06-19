#include<stdio.h>
#include<stdlib.h>

int main(){
char *x;
x=getenv("File");
if(*x)
printf("ola = %s\n", x);
else
printf("ola nao respone");
}