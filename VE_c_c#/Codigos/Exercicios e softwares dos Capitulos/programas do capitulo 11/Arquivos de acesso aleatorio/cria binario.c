#include<stdio.h>

int main(void){
FILE *file;
int v[5]={12,13,14,15,156};
int x;
file=fopen("datas.dat", "wb");
int n;
n=fwrite(v, sizeof(int), 10, file);
for(x=0; x<n; x++)
fprintf( "%d", v[x]);
}