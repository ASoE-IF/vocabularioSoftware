#include<stdio.h>

int main(void)
{
	printf("Arquivo %s: Cheguei com sucesso a linha %d\n", __FILE__, __LINE__);
#line 100 "usandoline.C"
	printf("Arquivo %s: Cheguei com sucesso a linha %d\n", __FILE__, __LINE__);
}