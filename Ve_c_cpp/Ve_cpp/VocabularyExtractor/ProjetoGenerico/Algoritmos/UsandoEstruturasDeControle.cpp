 #include<iostream>

 /*Aplicativo completo com todas as entidades e estruturas de um
 * Aplicativo em C.
 * Autor: Israel Gomes de Lima
 * Data: 20/09/2017
 */

using std::cout;
using std::endl;

 /*funções para uso na função de controle*/
int prompt(void)
{
	return 0;
}

int readNum(void)
{
	return 50;
}

int sqrNum(int num)
{
	return num * num;
}

int funcaoDeUsoDeComandoDeControle()
{
	int variavelLocal1 = 10;
	int variavelLocal2 = 10;
	int variavelLocal3 = 10;
	char variavelLocal4 = 'A';

	//usando o comando if
	if(variavelLocal1 > variavelLocal2 && variavelLocal3 < variavelLocal2)
	{
		cout << "variavelLocao1: %d\n" << variavelLocal1 << endl;
		if(variavelLocal1 != 50 || variavelLocal3 == 20)
		{
			if(variavelLocal1 > variavelLocal2)
			{
				cout << "variavelLocao2: %d\n" << variavelLocal2 << endl;
			}
			else
			{
				cout << "variavelLocao3: %d\n" << variavelLocal3 << endl;
			}
		}
	}
	else
	{
		if(variavelLocal1 == variavelLocal2 && variavelLocal3 == variavelLocal2)
		{
			cout << "variavelLocao1: %d\n" << variavelLocal1 << endl;

			if(variavelLocal2 >= variavelLocal2 || variavelLocal3 <= variavelLocal2)
			{
				cout << "variavelLocao2: %d\n" << variavelLocal2 << endl;
			}
			else
			{
				if(variavelLocal1 == variavelLocal3 && variavelLocal3 == variavelLocal1)
				{
					cout << "variavelLocao3: %d\n" << variavelLocal3 << endl;
				}
				else
				{
					cout << "variavelLocao2: %d\n" << variavelLocal2 << endl;
				}
			}
		}
	}

	switch(variavelLocal2)
	{
	case 1:
	case 2:
		cout << "variavelLocao1: %d\n" << variavelLocal1 << endl;
		break;
	case 3:
		cout << "variavelLocao2: %d\n" << variavelLocal2 << endl;
		break;
	default:
		switch(variavelLocal2)
		{
		case 1:
			cout << "variavelLocao1: %d\n" << variavelLocal1 << endl;
			break;
		default:
			cout << "variavelLocao3: %d\n" << variavelLocal3 << endl;
			break;
		}
		break;
	}


	//usando o comando for
	for(variavelLocal1 = 0; variavelLocal1 < 100; variavelLocal1++)
	{
		cout << "Inicio do primeiro for" << endl;
		for(variavelLocal2 = 0; variavelLocal2 < 100; variavelLocal3 += 2)
		{
			cout << "Inicio do segundo for (primeiro for interno" << endl;
			cout << "fim do segundo for (primeiro for interno" << endl;
		}
		cout << "fim do primeiro for" << endl;
	}

	for(variavelLocal2 = 0, variavelLocal3 = 0; variavelLocal2 < 100; variavelLocal2++, variavelLocal3 += 2)
	{
		cout << "Inicio do primeiro for" << endl;
		cout << "fim do primeiro for" << endl;
	}
	for(variavelLocal3 = 0; variavelLocal3 < 100 && variavelLocal2 == 99; variavelLocal3 += 2)
	{
		cout << "Inicio do primeiro for" << endl;
		cout << "fim do primeiro for" << endl;
	}

	for(; variavelLocal1 < 100; variavelLocal1++)
	{
		cout << "Inicio do primeiro for" << endl;
		for(; ; variavelLocal3 += 2)
		{
			cout << "Inicio do segundo for (primeiro for interno" << endl;
			for( ; ; )
			{
				cout << "Inicio do terceiro for (primeiro for interno" << endl;
				cout << "Loop infinito" << endl;
				cout << "fim do terceiro for (primeiro for interno" << endl;
			}
			cout << "fim do segundo for (primeiro for interno" << endl;
		}
		cout << "fim do primeiro for" << endl;
	}

	for(prompt(); variavelLocal1 = readNum(); prompt())
	{
		cout << "Inicio do primeiro for" << endl;
		sqrNum(variavelLocal1);
		cout << "fim do primeiro for" << endl;
	}

	//Usando while
	while(variavelLocal4 == 'A')
	{
		cout << "Inicio do primeiro while" << endl;
		cout << "Loop infinito" << endl;
		cout << "fim do primeiro while" << endl;
	}

	while(variavelLocal1 < 20 || variavelLocal2 > 20)
	{
		cout << "Inicio do primeiro while" << endl;

		while(variavelLocal1 != 5)
		{
			cout << "Inicio do primeiro while (interno)" << endl;
			cout << "fim do primeiro while (interno)" << endl;
		}

		cout << "fim do primeiro while" << endl;
	}

	//Usando do while
	do
	{
		cout << "Inicio do primeiro while (interno)" << endl;
		cout << "Loop infinito" << endl;
		cout << "fim do primeiro while (interno)" << endl;
	}while(variavelLocal4 == 'A');


	do
	{
		cout << "Inicio do primeiro while" << endl;
		do
		{
			cout << "Inicio do primeiro while (interno)" << endl;
			cout << "Loop infinito" << endl;
			cout << "fim do primeiro while (interno)" << endl;
		}while(variavelLocal1 < 20 || variavelLocal2 > 20);

		cout << "fim do primeiro while" << endl;
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
		cout << "Printf abaixo do rotulo\n" << endl;
	goto fim;
}

int main(void){
}
