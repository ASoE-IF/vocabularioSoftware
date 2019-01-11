 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

//Includes e Defines
#include<iostream>
#define CONSTANTEDEFINE 10
#define CONSTANTEDEFINECHAR "valor constante da constanteDefineChar"
#define macroSoma(a,b) (a+b)
#define macroStr(s) #s
#define macroConcat(a, b) a ## b
#error menssagem de erro com error

using std::cout;
using std::endl;

void funcaoDePreProcessador()
{
//Inicio da função: funcaoDePreProcessador
 /*esta é a função que contem todas as instruções
 * de preprocessador do C
 */
#if constanteDefine == 10
	cout << "os valores são iguais, no #if\n" << endl;
#elif constanteDefine < 10
	cout << "o valor é menor que 10, no #elif\n" << endl;
#else
	cout << "os valores são diferentes, no #else do if\n" << endl;
#endif

#ifdef constanteDefine
	cout << "a constanteDefine foi definida, no #ifdef\n" << endl;
#else
	cout << "a constanteDefine não foi definida, no #else do ifdef\n" << endl;
#endif

#ifndef constanteDefine
	cout << "a constanteDefine não foi definida, no #ifdef\n" << endl;
#else
	cout << "a constanteDefine foi definida, no #else do #ifndef\n" << endl;
#endif

#undef CONSTANTEDEFINECHAR

#if defined constanteDefine
	cout << "a constanteDefine foi definida, no #if defined\n" << endl;
#endif

#line 10 "Novo nome de ProjetoEmC.c"

#pragma "Menssagem na pragma"

//Fim da função: funcaoDePreProcessador
}

int main(void){
}
