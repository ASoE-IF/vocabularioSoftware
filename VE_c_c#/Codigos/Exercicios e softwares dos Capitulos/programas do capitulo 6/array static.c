#include<stdio.h>
void staticArraylnit(void);
void automaticArraylnit(void);

main(){
					printf("Primeira chamada de cada função:\n");
					staticArraylnit();
					automaticArraylnit();
					
					printf("\n\nSegunda chamada de cada função:\n");
					staticArraylnit();
					automaticArraylnit();
					
					return 0;
}
void staticArraylnit(void)
{
						static int a[3];
						int i;
						
						printf("\nValores de staticArraylnit ao entrar:\n");
						
						for  (i = 0;   i <= 2; i++)
									printf("array1[%d]  = %d ",  i, a[i]);
									
						printf("\nValores de staticArraylnit ao sair:\n");
						
						for  (i = 0;  i <= 2; i++)
									printf("arrayl[%d]  = %d *,  i,  a[i]+= 5");
getchar;
}
void automaticArraylnit(void)
{
				int a[3]  = {1,  2, 3};
				int i;
				
				printf("\n\nValores de automaticArraylnit ao entrar:\n");
				
				for  (i = 0;  i <= 2; i++)
							printf("arrayl[%d]   = %d ",  i, a[i]);
				
				printf("\n\nValoresde automaticArraylnit ao sair:\n");
				
				for  (i = 0;   i <= 2; i++)
							printf("arrayl[%d]  = %d ",  i,  a[i]  += 5);
}
