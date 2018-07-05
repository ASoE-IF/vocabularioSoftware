 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

#include<iostream>
#include<string>


bool (*compare) (int, int);

using std::string;

//Funções de matriz
void funcaoDeMatriz(int *vetor)
{

}
void funcaoDeMatriz2(int vetor[100])
{

}
void funcaoDeMatriz3(int vetor[])
{

}

void funcaoUsandoMatrizes()
{
	int vetor[100];
	char stri[] = "valor da string";
	char chars[] = {'a', 'b', 'c', 'd'};
	int x, y;
	int vetor2[] = {0, 0, 0, 5, vetor[2], vetor[vetor[5]], 5, y, x};
	int matriz[100][100];
	int matriz2[][10] = {{5, 2, 6, vetor[5]}, {2, 8, vetor2[5], 8}};
	string texto[10];

	int *ponteiro;

	x = vetor[1];
	y = vetor[vetor[1]];
	x = vetor[vetor[vetor[1]]];

	funcaoDeMatriz(vetor);

	matriz[0][0] = 0;
	matriz2[vetor[0]][0] = 0;
	matriz[x][y] = 0;
	matriz2[matriz[0][0]][matriz[0][0]] = 5;

	//novas operações com ponteiros
	ponteiro = vetor;
	ponteiro[2] = 5;
	*(ponteiro + 2) = 5;
}

void funcaoDePonteiro(int *p1)
{

}
void funcaoDePonteiro2(int *p1[])
{

}
void funcaoUsandoPonteiros(int *ponteiro1)
{
	int var = 10;
	int *ponteiro2;
	int *ponteiro3 = &var;
	int **ponteiroParaPonteiro, ***ponteiroParaPonteiroParaPonteiro;
	int *matrizDePonteiro[10];
	int matriz;
	int variavel = 50 * sizeof(int);
	int x = 5;
    int *ptr1;
    const int *ptr2;
    int const *ptr3;
    int * const ptr4 = &x;
    const int * const ptr5 = &x;

	ponteiro2 = &var;

	var = *ponteiro2 + 5;
	ponteiro1 = ponteiro2;

	ponteiro1--;
	ponteiro1++;
	ponteiro1 = ponteiro2 + 5;

	ponteiro1 == ponteiro2;
	matrizDePonteiro[2] = &var;
	*(ponteiro2 + 5) = 10;

	**ponteiroParaPonteiro += 20;
	***ponteiroParaPonteiroParaPonteiro += (**ponteiroParaPonteiro + 10);
}

int main(){
    int x = 5;
    int *ptr1;
    const int *ptr2;
    int const *ptr3;
    int * const ptr4 = &x;
    const int * const ptr5 = &x;
}
