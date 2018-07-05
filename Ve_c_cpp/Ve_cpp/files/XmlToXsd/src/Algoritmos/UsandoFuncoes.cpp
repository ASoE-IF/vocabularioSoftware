 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

#include<iostream>
#include<string>

using std::string;
using std::cout;

//Prototipos de funções
int prototipo1();
int protoripo2(int, int);
int protoripo3(int x, int y);
void prototipo4(int);
void prototipo5(string x = "valor");
void prototipo6(int, string, double);

//Espaço entre funções

/**Abaixo funções para demonstrar o
 * Uso de Funções em c++
 */

static volatile int funcao_sem_parametros()
{
	int variavelLocal;

	variavelLocal = 20;
}

void funcao1(string x, int y, double z)
{
	int variavelLocal;

	variavelLocal = 20;
}

inline int funcao2(const double var)
{
	int variavelLocal;
	int &aliasVar = variavelLocal;

	aliasVar = 20;

	variavelLocal = 20;
}

void sobrecarregada(int valor){
}

void sobrecarregada(double valor){
}

int main(void){

}
