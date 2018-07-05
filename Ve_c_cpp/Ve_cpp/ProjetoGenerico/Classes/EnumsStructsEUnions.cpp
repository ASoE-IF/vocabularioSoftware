 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

 /*Usando estruturas, uniões enumerações e
 * tipos definidos pelo usuario
 */

/*comentario de bloco antes da estrutura
 * */
#include<stdio.h>
int funcaoDeMembroDeEstrutura(int membro)
{

}
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

int funcaoDeEstrutura(struct estrutura2 estrutura)
{
	printf("Valor do membro1 da estrutura: %d\n", estrutura.membro1);
}

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

	ponteiroStruct->membro1 = 800;
	ponteiroStruct->membro1 = 900 - ponteiroStruct->membro1;
	var3 = &var1;
	var7.estruturaInterna.membro1 = 50;
}

/*usando unions
 * 1
 */
union union1/*comentario de bloco na union*/
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

int funcaoDeMembroDeUnion(int membro)
{

}
int funcaoDeUnion(union union2 union1)
{
	printf("Valor do membro1 da union1: %d\n", union1.membro1);
}

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

	ponteiroUnion->membro1 = 800;
	ponteiroUnion->membro1 = 900 - ponteiroUnion->membro1;
	var12 = &var10;
	var18->unionInterna.membro1 = var12->membro1;
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


int main(void){
}
