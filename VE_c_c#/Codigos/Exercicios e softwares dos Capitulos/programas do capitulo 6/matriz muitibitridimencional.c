#include<stdio.h>

int exibe_2d_matriz(int matriz[ ][10], int linhas)
{
int i, j;
for (i = 0; i < linhas; i++)
		for (j = 0; j < 10; j++)
		printf("matriz[%d][%d] = %d\n", i, j, matriz[i][j]);
}
void main(void){
int a[1][10]={{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0}};
int b[2][10]={{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0},
					{11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0}};
int c[3][10]={ {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0},	
					{11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0},
					{21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0}};
								
exibe_2d_matriz(a, 1);
exibe_2d_matriz(b, 2);
exibe_2d_matriz(c, 3);
}