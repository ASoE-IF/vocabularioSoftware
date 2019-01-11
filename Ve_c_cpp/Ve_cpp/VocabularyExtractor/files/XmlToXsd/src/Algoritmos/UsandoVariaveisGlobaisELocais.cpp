 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

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

	::varCharComSignal = 'x';
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
