#include<stdio.h>
#include<stdlib.h>

int main(void)
{

int *apt, *bpt, *cpt;
int a=5, b=10, c=15, d=20;
apt=(int *)malloc(2 * sizeof(int));
bpt=(int *)calloc(2, sizeof(int)); 
cpt=(int *)malloc(2 * sizeof(int));
apt=&a;
bpt=&b;
cpt=&c;
printf("%d:%d:%d\n", *apt, *bpt, *cpt);
printf("%p:%p:%p\n", &apt, &bpt, &cpt);
apt=(int *)realloc(apt, 2 * sizeof(int));
bpt=(int *)realloc(bpt, 2 * sizeof(int)); 
cpt=(int *)realloc(cpt, 2 * sizeof(int));
apt=&d;
bpt=&d;
cpt=&d;
printf("%d:%d:%d\n", *apt, *bpt, *cpt);
printf("%p:%p:%p\n", &apt, &bpt, &cpt);
}