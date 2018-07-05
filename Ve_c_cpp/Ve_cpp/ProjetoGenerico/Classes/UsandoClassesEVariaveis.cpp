#include<stdio.h>

static volatile int variavel_do_arquivo;
const static int variavel_do_arc2 = variavel_de_fora_do_arquivo = variavel_do_arquivo;

it mss();

int funcaoGlobal(){
	int variavel_da_funcao_global;
	variavel_da_funcao_global = variavel_do_arquivo;
	variavel_do_arquivo = variavel_de_fora_do_arquivo;
}

class classeGlobal{
	private:
	int atributoDaClasseGlobal;
	int atributoDaClasseGlobal2 = atributoDaClasseGlobal = variavel_do_arc2;

	public:
	void metodoDaClasseGlobal(){
		atributoDaClasseGlobal = 5;
		atributoDaClasseGlobal2 = atributoDaClasseGlobal;

		int variavelLocal = 25;

		class classeInterna{
			private:
			int atributoInterno = variavelLocal;

			void metodoInterno(){
				atributoInterno = atributoDaClasseGlobal = variavelLocal = atributoDaClasseGlobal = atributoDaClasseGlobal;
			}
		};
	}

	virtual void methodPureVirtual() = 0;
};
