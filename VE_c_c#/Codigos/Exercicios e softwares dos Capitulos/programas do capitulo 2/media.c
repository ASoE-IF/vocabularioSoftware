#include<stdio.h>
main(){
       float x, y, z, soma, media, produlto;
       printf("entre com 3 inteiros");
       scanf("%f%f%f", &x, &y, &z);
       soma=x+y+z;
       media=soma/3;
       produlto=x*y*z;
       printf("a soma e %.2f\n", soma);
       printf("a media e %.2f\n", media);
       printf("o produlto e %.2f\n", produlto);
system("pause");
return 0;
}
