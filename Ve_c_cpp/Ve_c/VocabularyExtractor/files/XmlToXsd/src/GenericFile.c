 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

//Includes e Defines
#include<stdio.h>
#define CONSTANTEDEFINE 10
#define CONSTANTEDEFINECHAR "valor constante da constanteDefineChar"
#define macroSoma(a,b) (a+b)
#define macroStr(s) #s
#define macroConcat(a, b) a ## b
#error menssagem de erro com error

//Variaveis globais
signed char varCharComSignal;
unsigned int varIntUnsigned;
long double varLongDouble;
short int varShortInt;
const int varConst = 10;
volatile int varVolatil;
extern float varExtern;
static double varStatic;
int varAutoImplicita;
int *ptrGlobal;
float **ptrParaPtrGlobal;

//Prototipos de funções
int prototipo1();
int protoripo2(int, int);
int protoripo3(int x, int y);
prototipo4(int);
prototipo5(int x);


void funcaoDePreProcessador()
{
//Inicio da função: funcaoDePreProcessador
 /*esta é a função que contem todas as instruções
 * de preprocessador do C
 */
#if constanteDefine == 10
	printf("os valores são iguais, no #if\n");
#elif constanteDefine < 10
	printf("o valor é menor que 10, no #elif\n");
#else
	printf("os valores são diferentes, no #else do if\n");
#endif

#ifdef constanteDefine
	printf("a constanteDefine foi definida, no #ifdef\n");
#else
	printf("a constanteDefine não foi definida, no #else do ifdef\n");
#endif

#ifndef constanteDefine
	printf("a constanteDefine não foi definida, no #ifdef\n");
#else
	printf("a constanteDefine foi definida, no #else do #ifndef\n");
#endif

#undef CONSTANTEDEFINECHAR

#if defined constanteDefine
	printf("a constanteDefine foi definida, no #if defined\n");
#endif

#line 10 "Novo nome de ProjetoEmC.c"

#pragma "Menssagem na pragma"

//Fim da função: funcaoDePreProcessador
}

//Espaço entre funções


void funcaoDeDeclaracaoDeVariaveisLocais()
{
	signed char varCharComSignalLocal;
	unsigned int varIntUnsignedLocal;
	long double varLongDoubleLocal;
	short int varShortIntLocal;
	const int varConstLocal = 10;
	volatile int varVolatilLocal;
	extern float varExternLocal;
	static double varStaticLocal;
	register int varRegisterLocal;
	auto int varAutoExplicitaLocal;
	int varAutoImplicitaLocal;

	varCharComSignalLocal = 'x';
	varIntUnsignedLocal = 5;
	varAutoExplicitaLocal = varIntUnsignedLocal;
	varShortIntLocal = 10;
	varVolatilLocal = varShortIntLocal;
	varStaticLocal = varConstLocal;
	varRegisterLocal = varConstLocal;
	varAutoExplicitaLocal = 50;
	varAutoImplicitaLocal = varAutoImplicitaLocal;

	varCharComSignalLocal = varShortIntLocal = varRegisterLocal = varAutoExplicitaLocal = varAutoImplicitaLocal = 0;

}

void funcaoDeUsoDeVariaveisGlobais()
{
	/*Usando Váriaveis
	 * Globais
	 */
	varCharComSignal = 'x';
	varIntUnsigned = 20;
	varLongDouble = 2.22222;
	varShortInt = 50;
	varVolatil = 22;
	varStatic = 10;
	varAutoImplicita = 2;


	varCharComSignal = varShortInt = varAutoImplicita = 0;

	/*Usando novas váriveis locais
	 * que sobreescrevem as globais
	 */
	signed char varCharComSignal;
	unsigned int varIntUnsigned;
	long double varLongDouble;
	short int varShortInt;
	const int varConst = 10;
	volatile int varVolatil;
	extern float varExtern;
	static double varStatic;
	int varAutoImplicita;

	varCharComSignal = 'x';
	varIntUnsigned = 20;
	varLongDouble = 2.22222;
	varShortInt = 50;
	varVolatil = 22;
	varStatic = 10;
	varAutoImplicita = 2;


	varCharComSignal = varShortInt = varAutoImplicita = 0;

	//Fim da função
}

void funcaoDeUsoDeVariaveisLocaisEOperadores()
{
	//declaração e atribuição
	int variavelLocal1 = 10;
	int variavelLocal2 = 10;
	int variavelLocal3 = 10;
	int varBit1 = 10;
	int varBit2 = variavelLocal3;
	int *ponteiro1, *ponteiro2;
	int *ponteiro3 = & variavelLocal1;
	float variavelFloatLocal = 5.00;

	variavelLocal1 = variavelLocal2;
	variavelLocal3 = variavelLocal2 = variavelLocal1 = 20;

	//incremento, decremento
	variavelLocal1++;
	--variavelLocal1;
	variavelLocal1 = variavelLocal2 - 10;
	variavelLocal1 += variavelLocal2;
	variavelLocal1 += 10;
	variavelLocal1 = variavelLocal2++;

	//expressões lógicas
	variavelLocal1 > variavelLocal2;
	variavelLocal1 >= variavelLocal2;
	variavelLocal1 > variavelLocal2;
	variavelLocal1 <= variavelLocal2;
	variavelLocal3 == variavelLocal2;
	variavelLocal3 != variavelLocal2;
	((variavelLocal3 == variavelLocal2) && (variavelLocal1 <= variavelLocal2));
	((variavelLocal3 != variavelLocal2) || (variavelLocal1 == variavelLocal2));
	(!(variavelLocal3 != variavelLocal2) || !(variavelLocal1 == variavelLocal2));
	((variavelLocal2 > 10) && (variavelLocal2 < 30) || ((variavelLocal2 == 5) == !(variavelLocal3 != variavelLocal2)));

	//expressões bit a bit
	varBit1 & varBit2;
	varBit1 | varBit2;
	varBit1 ^ varBit2;
	varBit1 = ~varBit2;
	varBit1 = varBit1 << 2;
	varBit2 = varBit2 >> 1;
	varBit1 = varBit1 >> varBit2;
	varBit2 = varBit2 << varBit1;

	//Operador ternário
	variavelLocal3 = variavelLocal1 > variavelLocal2 ? 200 : 100;

	//Operações com ponteiros
	ponteiro1 = &ponteiro3;
	*ponteiro1 = 20;
	ponteiro2 = ponteiro1;
	*ponteiro2 = *ponteiro2 + * ponteiro1;

	//operador sizeof
	sizeof varBit1;
	sizeof (int);
	varBit1 = sizeof (int) == 4 ? 100 : 200;

	//Operador Virgula
	variavelLocal1 = (variavelLocal2 = 20, variavelLocal2 + 50);
	variavelLocal2 = (variavelLocal2 = 20, variavelLocal2 + 50, variavelLocal2 + 20);

	//cast de tipos
	variavelLocal3 = (int) variavelFloatLocal;
	(float) variavelLocal1 == (float) variavelLocal2;

	//alocação de memoria
	float *p;
	p = calloc(100, sizeof(float));

	//fim da função
}

/*funções para uso na função de controle*/
int prompt(void)
{
	return 0;
}

void readNum(void)
{
	return 50;
}

void sqrNum(int num)
{
	return num * num;
}

void funcaoDeUsoDeComandoDeControle()
{
	int variavelLocal1 = 10;
	int variavelLocal2 = 10;
	int variavelLocal3 = 10;
	char variavelLocal4 = 'A';

	//usando o comando if
	if(variavelLocal1 > variavelLocal2 && variavelLocal3 < variavelLocal2)
	{
		printf("variavelLocao1: %d\n", variavelLocal1);
		if(variavelLocal1 != 50 || variavelLocal3 == 20)
		{
			if(variavelLocal1 > variavelLocal2)
			{
				printf("variavelLocao2: %d\n", variavelLocal2);
			}
			else
			{
				printf("variavelLocao3: %d\n", variavelLocal3);
			}
		}
	}
	else
	{
		if(variavelLocal1 == variavelLocal2 && variavelLocal3 == variavelLocal2)
		{
			printf("variavelLocao1: %d\n", variavelLocal1);

			if(variavelLocal2 >= variavelLocal2 || variavelLocal3 <= variavelLocal2)
			{
				printf("variavelLocao2: %d\n", variavelLocal2);
			}
			else
			{
				if(variavelLocal1 == variavelLocal3 && variavelLocal3 == variavelLocal1)
				{
					printf("variavelLocao3: %d\n", variavelLocal3);
				}
				else
				{
					printf("variavelLocao2: %d\n", variavelLocal2);
				}
			}
		}
	}

	switch(variavelLocal2)
	{
	case 1:
	case 2:
		printf("variavelLocao1: %d\n", variavelLocal1);
		break;
	case 3:
		printf("variavelLocao2: %d\n", variavelLocal2);
		break;
	default:
		switch(variavelLocal2)
		{
		case 1:
			printf("variavelLocao1: %d\n", variavelLocal1);
			break;
		default:
			printf("variavelLocao3: %d\n", variavelLocal3);
			break;
		}
		break;
	}


	//usando o comando for
	for(variavelLocal1 = 0; variavelLocal1 < 100; variavelLocal1++)
	{
		printf("Inicio do primeiro for");
		for(variavelLocal2 = 0; variavelLocal2 < 100; variavelLocal3 += 2)
		{
			printf("Inicio do segundo for (primeiro for interno");
			printf("fim do segundo for (primeiro for interno");
		}
		printf("fim do primeiro for");
	}

	for(variavelLocal2 = 0, variavelLocal3 = 0; variavelLocal2 < 100; variavelLocal2++, variavelLocal3 += 2)
	{
		printf("Inicio do primeiro for");
		printf("fim do primeiro for");
	}
	for(variavelLocal3 = 0; variavelLocal3 < 100 && variavelLocal2 == 99; variavelLocal3 += 2)
	{
		printf("Inicio do primeiro for");
		printf("fim do primeiro for");
	}

	for(; variavelLocal1 < 100; variavelLocal1++)
	{
		printf("Inicio do primeiro for");
		for(; ; variavelLocal3 += 2)
		{
			printf("Inicio do segundo for (primeiro for interno");
			for( ; ; )
			{
				printf("Inicio do terceiro for (primeiro for interno");
				printf("Loop infinito");
				printf("fim do terceiro for (primeiro for interno");
			}
			printf("fim do segundo for (primeiro for interno");
		}
		printf("fim do primeiro for");
	}

	for(prompt(); variavelLocal1 = readNum(); prompt())
	{
		printf("Inicio do primeiro for");
		sqrNum(variavelLocal1);
		printf("fim do primeiro for");
	}

	//Usando while
	while(variavelLocal4 == 'A')
	{
		printf("Inicio do primeiro while");
		printf("Loop infinito");
		printf("fim do primeiro while");
	}

	while(variavelLocal1 < 20 || variavelLocal2 > 20)
	{
		printf("Inicio do primeiro while");

		while(variavelLocal1 != 5)
		{
			printf("Inicio do primeiro while (interno)");
			printf("fim do primeiro while (interno)");
		}

		printf("fim do primeiro while");
	}

	//Usando do while
	do
	{
		printf("Inicio do primeiro while (interno)");
		printf("Loop infinito");
		printf("fim do primeiro while (interno)");
	}while(variavelLocal4 == 'A');


	do
	{
		printf("Inicio do primeiro while");
		do
		{
			printf("Inicio do primeiro while (interno)");
			printf("Loop infinito");
			printf("fim do primeiro while (interno)");
		}while(variavelLocal1 < 20 || variavelLocal2 > 20);

		printf("fim do primeiro while");
	}
	while(variavelLocal1 < 20 || variavelLocal2 > 20);

	//Usando Returns
	switch(variavelLocal2)
	{
	case 1:
		break;
		return variavelLocal1;
	case 2:
		return 5;
		break;
	default:
		return variavelLocal1 + variavelLocal2 + variavelLocal3 + 5;
		break;
	}

	//Usando goto
	fim:
		printf("Printf abaixo do rotulo\n");
	goto fim;
}

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
	char string[] = "valor da string";
	char chars[] = {'a', 'b', 'c', 'd'};
	int x, y;
	int vetor2[] = {0, 0, 0, 5, vetor[2], vetor[vetor[5]], 5, y, x};
	int matriz[100][100];
	int matriz2[][10] = {{5, 2, 6, vetor[5]}, {2, 8, vetor2[5], 8}};

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

	ponteiro2 = &var;

	var = *ponteiro2 + 5;
	ponteiro1 = ponteiro2;

	ponteiro1--;
	ponteiro1++;
	ponteiro1 = ponteiro2 + 5;

	ponteiro1 == ponteiro2;
	matrizDePonteiro[2] = &var;
	ponteiro2 = matrizDePonteiro;
	*(ponteiro2 + 5) = 10;
	funcaoDeMatriz(matrizDePonteiro);

	ponteiroParaPonteiro = ponteiro2;
	ponteiroParaPonteiroParaPonteiro = ponteiroParaPonteiro;
	**ponteiroParaPonteiro += 20;
	***ponteiroParaPonteiroParaPonteiro += (**ponteiroParaPonteiro + 10);
	ponteiro1 = malloc(50 * sizeof(int));
	matriz = malloc(80);
}

/**Abaixo funções para demonstrar o
 * Uso de Funções em c, desde de criação de
 * funções globais até funções internas
 */
static volatile int funcao_principal()
{
	int variavelLocal;

	int funcaoInterna()
	{
		int variavelLocalDaFuncaoInterna;

		variavelLocal = 10;

		int variavelLocal = 20;

		variavelLocalDaFuncaoInterna = variavelLocal;
	}

	variavelLocal = 20;
}

void funcao1_1()
{
	int variavelLocal;

	int funcaoInterna()
	{
		int variavelLocalDaFuncaoInterna;

		variavelLocal = 10;

		int variavelLocal = 20;

		variavelLocalDaFuncaoInterna = variavelLocal;
	}

	variavelLocal = 20;
}

int funcao2(void)
{
	int variavelLocal;

	int funcaoInterna()
	{
		int variavelLocalDaFuncaoInterna;

		variavelLocal = 10;

		int variavelLocal = 20;

		variavelLocalDaFuncaoInterna = variavelLocal;
	}

	variavelLocal = 20;
}

float funcao3(int param1)
{
	int variavelLocal;

	int funcaoInterna(int param1)
	{
		int variavelLocalDaFuncaoInterna;

		variavelLocal = 10;

		int variavelLocal = 20;

		variavelLocalDaFuncaoInterna = variavelLocal = param1 = 0;
	}

	variavelLocal = 20;

	return variavelLocal;
}

double funcao4(int *paramPtr1, int param2)
{
	int variavelLocal = param2;
	*paramPtr1 = &variavelLocal;

	int funcaoInterna(int *paramPtr1)
	{
		int variavelLocalDaFuncaoInterna;

		variavelLocal = 10;

		int variavelLocal = 20;

		variavelLocalDaFuncaoInterna = variavelLocal = *paramPtr1 = 0;
	}

	funcaoInterna(&variavelLocal);
	funcao4(&variavelLocal, funcao3(funcao3(funcao3(3))));
}

void funcao5(int param1, int param2, ...)
{

}

/*Usando estruturas, uniões enumerações e
 * tipos definidos pelo usuario
 */

/*comentario de bloco antes da estrutura
 * */
struct estrutura1 /*comentario de bloco da estrutura*/
{
	/*comentario1 de bloco da estrutura*/
	int membro1;
	/*comentario2 de bloco da estrutura*/
	int membro2;
	double membroVetor[10];
	/*comentario3 de bloco da estrutura*/
}/*comentario4 de bloco da estrutura*/;/*comentario de bloco fora da estrutura*/
/*comentario de bloco no fim da estrutura*/

//comentario de linha no fora da estrutura
struct estrutura2//comentario de linha no inicio da estrutura
{
	//comentario2 de linha  da estrutura
	int membro1;
	//comentario3 de linha  da estrutura
	int membro2;
	double membroVetor[10];
	//comentario de linha da estrutura
}/*comentario de nloco no dentro da estrutura*/var1, var2, *var3/*comentario de linha no dentro da estrutura*/;//comentario de linha no fora da estrutura

struct
{
	int membro1;
	int membro2;
	double membroVetor[10];
}var4, var5, var6[10];

struct estrutura3
{
	int membro1;
	int membro2;
	struct estrutura1 estruturaInterna;
	double membroMatriz[10][10];
	int campoDeBits1 : 10;
	int campoDeBits2 : 20;
}var7, var8, *var9;

int funcaoUsandoEstrutura()
{
	struct estrutura1 varStruct1;
	struct estrutura2 varStruct2;
	struct estrutura2 varStruct3[100];
	struct estrutura1 *ponteiroStruct;

	varStruct1.membro1 = 1;
	varStruct1.membro2 = varStruct1.membro1 + varStruct1.membro1;
	varStruct2.membro1 += varStruct1.membro1;
	var1.membro1 = varStruct2.membro1 * varStruct1.membro2;
	var2 = var1;

	varStruct3[varStruct1.membro1] = var2;
	varStruct3[varStruct3[varStruct1.membro1].membro1] = varStruct3[varStruct1.membro1];

	funcaoDeMembroDeEstrutura(var1.membro1);
	funcaoDeEstrutura(var1);

	ponteiroStruct = &var1;
	ponteiroStruct->membro1 = 800;
	ponteiroStruct->membro1 = 900 - ponteiroStruct->membro1;
	var3 = &var1;
	var7.estruturaInterna.membro1 = 50;
}

int funcaoDeMembroDeEstrutura(int membro)
{

}
int funcaoDeEstrutura(struct estrutura2 estrutura)
{
	printf("Valor do membro1 da estrutura: %d\n", estrutura.membro1);
}

/*usando unions
 * 1
 */
union union_1/*comentario de bloco na union*/
{
	/*comentario1 de bloco na union*/
	int membro1;
	/*comentario2 de bloco na union
	 */
	int membro2;
	double membroVetor[10];
	/*comentario3 de bloco na union*/
}/*comentario4 de bloco na union*/;/*comentario de bloco fora da union*/
/*comentario de bloco fora da union*/

//comentario de linha antes da union2
union union2//comentario de linha na union
{
	//comentario1 de linha na union
	int membro1;
	//comentario2 de linha na union
	int membro2;
	double membroVetor[10];
	//comentario3 de linha na union
}/*comentario de bloco na union*/var10, var11, /*comentario de bloco na union*/*var12/*comentario de bloco na union*/;//comentario de linha fora da union
//comentario fora da union

union
{
	int membro1;
	int membro2;
	double membroVetor[10];
}var13, var14, var15[10];

union union3
{
	int membro1;
	int membro2;
	union union1 unionInterna;
	double membroMatriz[10][10];
	int campoDeBits1 : 10;
	int campoDeBits2 : 20;
}var16, var17, *var18;

int funcaoUsandoUnion()
{
	union union1 varUnion1;
	union union2 varUnion2;
	union union2 varUnion3[100];
	union union1 *ponteiroUnion;

	varUnion1.membro1 = 1;
	varUnion1.membro2 = varUnion1.membro1 + varUnion1.membro1;
	varUnion2.membro1 += varUnion1.membro1;
	var10.membro1 = varUnion2.membro1 * varUnion1.membro2;
	var11 = var10;

	varUnion3[varUnion1.membro1] = var11;
	varUnion3[varUnion3[varUnion1.membro1].membro1] = varUnion3[varUnion1.membro1];

	funcaoDeMembroDeUnion(var10.membro1);
	funcaoDeUnion(var10);

	ponteiroUnion = &var10;
	ponteiroUnion->membro1 = 800;
	ponteiroUnion->membro1 = 900 - ponteiroUnion->membro1;
	var12 = &var10;
	var18->unionInterna.membro1 = var12->membro1;
}

int funcaoDeMembroDeUnion(int membro)
{

}
int funcaoDeUnion(union union2 union1)
{
	printf("Valor do membro1 da union1: %d\n", union1.membro1);
}

//enumeracoes
enum enumeration // enumaration1
{
	//enumeration
	CONST1, CONST2, CONST3, CONST4, CONST5, CONST6 = 100, CONST7, CONST8, CONST9, CONST10/*enumaration*/
	//enum
}/*enuma*/;//fora da enum

void funcaoUsandoEnum(enum enumeration paramEnum, int param2)
{
	enum enumeration varEnum1 = CONST1;
	enum enumeration varEnum2;
	varEnum2 = CONST2;

	int var1 = CONST6;

	printf("%d", CONST4);

	varEnum1 = varEnum2 = CONST7;

	funcaoUsandoEnum(varEnum1, CONST5);
	funcaoUsandoEnum(CONST6, CONST5);
}

//usando typedef

typedef struct estrutura4
{
	int membro1;
	int membro2;
	double membroVetor[10];
}var19, var20, *var21;

typedef struct
{
	int membro1;
	int membro2;
	double membroVetor[10];
}var24, var22, var23;

/*comentario fora da funcao
 * */
//comnetario de linha
void funcaoUsandoTypedef()//comentario da funcao
{
	/*comentario de bloco no inicio
	 * da funcao
	 */
	typedef int inteiro;

	inteiro num1;
	inteiro num2 = num1;
	num1 = 50;

	/*comentario de bloco no meio
		 * da funcao
		 */

	var19 var1;
	var1.membro1 = 5;
	struct estrutura4 tipo2;
	tipo2 = var1;
	/*comentario de bloco no fim
		 * da funcao
		 */
}//comentario fora da funcao
//comentario de lina
int main(void)
{
	//Inicio de main

	funcaoDePreProcessador();
	printf("%d\n", CONSTANTEDEFINE);
	printf("%d %s\n", __LINE__, __FILE__);
	printf(macroStr(palavra));

	/*comentário de bloco no meio
	 * de main
	 */
	int xy = 10;

	//comentario de linha no meio de main

	printf("\nvalor de xy = %d\n", macroConcat(x, y));


	funcaoDeDeclaracaoDeVariaveisLocais();
	//funcaoDeUsoDeComandoDeControle();
	//Fim de main
}/*comentario fora*/
//comentario de linha
//Fim do arquivo
