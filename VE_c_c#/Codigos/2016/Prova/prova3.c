#include<stdio.h>
#include<stdlib.h>

struct conjunto
{
	int tamanho;
	int *elementos;
};

int intersecao(struct conjunto *A, struct conjunto *B)
{
	struct conjunto C;
	int cont, cont2 = 0;
	
	C.tamanho = A->tamanho + B->tamanho;
	
	if(((C.elementos) = (int *) calloc(C.tamanho, sizeof(int))) == NULL)
	{
		printf("Erro");
		exit(1);
	}
	
	for(cont = 0; cont < A->tamanho; cont++)
	{
		C.elementos[cont] = A->elementos[cont];
	}
	
	for(cont = A->tamanho; cont < C.tamanho; cont++)
	{
		C.elementos[cont] = B->elementos[cont2];
		cont2 += 1;
	}
	
	printf("Intersecao A + B = C = {");
	for(cont = 0; cont < C.tamanho; cont++)
	{
		printf("%d,", C.elementos[cont]);
	}
	printf("}\n");
	
	free(C.elementos);
}


int diferenca(struct conjunto *A, struct conjunto *B)
{
	struct conjunto C;
	int cont, cont2 = 0;
	int valida, temp;
	
	C.tamanho = 0;
	if(((C.elementos) = (int *) malloc(sizeof(int))) == NULL)
	{
		printf("Erro");
		exit(1);
	}
	
	for(cont = 0; cont < A->tamanho; cont++)
	{
		valida = 0;
		for(cont2 = 0; cont2 < B->tamanho; cont2++)
		{
			if(A->elementos[cont] == B->elementos[cont2])
			{
				valida += 1;
				temp = 0;
				break;
			}
			else
			{
				temp = A->elementos[cont];
			}
		}
		
		if(valida == 0)
		{
			C.elementos[cont] = temp;
			realloc(C.elementos, C.tamanho + sizeof(int));
			C.tamanho += 1;
		}
	}
	
	for(cont = 0; cont < B->tamanho; cont++)
	{
		valida = 0;
		for(cont2 = 0; cont2 < A->tamanho; cont2++)
		{
			if(B->elementos[cont] == A->elementos[cont2])
			{
				valida += 1;
				temp = 0;
				break;
			}
			else
			{
				temp = B->elementos[cont];
			}
		}
		
		if(valida == 0)
		{
			C.elementos[C.tamanho] = temp;
			realloc(C.elementos, C.tamanho + sizeof(int));
			C.tamanho += 1;
		}
	}
	
	printf("Conjunto diferenca A - B = C = {");
	for(cont = 0; cont < C.tamanho; cont++)
	{	
		printf("%d,", C.elementos[cont]);
	}
	printf("}\n");
	
	
	free(C.elementos);
}

int main(void)
{
	int cont;
	struct conjunto A;
	struct conjunto B;
	
	A.tamanho = 5;
	B.tamanho = 5;
	
	if(((A.elementos) = (int *) calloc(A.tamanho, sizeof(int))) == NULL)
	{
		printf("Erro");
		exit(1);
	}
	
	if(((B.elementos) = (int *) calloc(B.tamanho, sizeof(int))) == NULL)
	{
		printf("Erro");
		exit(1);
	}
	
	printf("A = {");
	for(cont = 0; cont < A.tamanho; cont++)
	{
		A.elementos[cont] = cont;
		printf("%d,", A.elementos[cont]);
	}
	printf("}\n");
	
	printf("B = {");
	for(cont = 0; cont < B.tamanho; cont++)
	{
		B.elementos[cont] = cont + 2;
		printf("%d,", B.elementos[cont]);
	}
	printf("}\n");
	
	intersecao(&A, &B);
	diferenca(&A, &B);
	
	free(A.elementos);
	free(B.elementos);
}
