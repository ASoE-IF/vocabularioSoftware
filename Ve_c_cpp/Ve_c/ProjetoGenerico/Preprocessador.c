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
//#error menssagem de erro com error

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