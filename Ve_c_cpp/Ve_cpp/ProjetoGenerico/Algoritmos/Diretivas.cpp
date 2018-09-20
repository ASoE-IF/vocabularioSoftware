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
#error "menssagem de erro com error"

#error menssagem de er&ro com error

using std::cout;
using std::endl;

/**Comentário com caracteres especiais: < e > & $ ! \ / \\ * + -
*/

void funcaoDePreProcessador()
{
//Inicio da fun��o: funcaoDePreProcessador
 /*esta � a fun��o que contem todas as instru��es
 * de preprocessador do C
 */
#if constanteDefine == 10
	cout << "os valores s�o iguais, no #if\n" << endl;
#elif constanteDefine < 10
	cout << "o valor � menor que 10, no #elif\n" << endl;
#else
	cout << "os valores s�o diferentes, no #else do if\n" << endl;
#endif

#ifdef constanteDefine
	cout << "a constanteDefine foi definida, no #ifdef\n" << endl;
#else
	cout << "a constanteDefine n�o foi definida, no #else do ifdef\n" << endl;
#endif

#ifndef constanteDefine
	cout << "a constanteDefine n�o foi definida, no #ifdef\n" << endl;
#else
	cout << "a constanteDefine foi definida, no #else do #ifndef\n" << endl;
#endif

#undef CONSTANTEDEFINECHAR

#if defined constanteDefine
	cout << "a constanteDefine foi definida, no #if defined\n" << endl;
#endif

#line 10 "Novo nome de ProjetoEmC.c"

#pragma "Menssagem na pragma"

//Fim da fun��o: funcaoDePreProcessador
}

int main(void){
}
