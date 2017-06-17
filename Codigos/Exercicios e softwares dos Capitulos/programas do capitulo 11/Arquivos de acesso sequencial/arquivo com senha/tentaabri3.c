#include<stdio.h>

int main(void){
int v[5];
FILE *file;
int i, x;
file=fopen("3.jpg", "rb");
i=fread(v, sizeof(int), 5, file);
for(x=0; x<i; x++)
printf("%2d", v[x]);
}