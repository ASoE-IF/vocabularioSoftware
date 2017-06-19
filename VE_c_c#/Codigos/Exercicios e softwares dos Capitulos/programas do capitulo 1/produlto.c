#include<stdio.h>

main()
{
int x, y, soma, produlto, diferenca, quociente, resto;
printf("digite um numero: ");
scanf("%d", &x);
printf("digite um numero: ");
scanf("%d", &y);
soma=x+y;
printf("a soma é %d\n", soma);
produlto=x*y;
printf("a produlto é %d\n", produlto);
diferenca=x-y;
printf("a diferença é %d\n", diferenca);
quociente=x/y;
printf("o quociente é %d\n", quociente);
resto=x%y;
printf("o resto é %d\n", resto);
system("pause");
return 0;
}
