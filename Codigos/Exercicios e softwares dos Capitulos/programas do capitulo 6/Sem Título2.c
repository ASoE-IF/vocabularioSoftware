#include<stdio.h>
#include<stdlib.h>
main(){
       int valor[6], x;
       for(x=0; x<6; x++){
                printf("Digite um valo: ");
                scanf("%d", &valor[x]);
}
for(x=0; x<6; x++){
printf("\n%d\n", valor[x]);
}
system("pause");
}