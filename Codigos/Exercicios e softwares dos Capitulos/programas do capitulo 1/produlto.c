#include<stdio.h>

main()
{
int x, y, soma, produlto, diferenca, quociente, resto;
printf("digite um numero: ");
scanf("%d", &x);
printf("digite um numero: ");
scanf("%d", &y);
soma=x+y;
printf("a soma � %d\n", soma);
produlto=x*y;
printf("a produlto � %d\n", produlto);
diferenca=x-y;
printf("a diferen�a � %d\n", diferenca);
quociente=x/y;
printf("o quociente � %d\n", quociente);
resto=x%y;
printf("o resto � %d\n", resto);
system("pause");
return 0;
}
