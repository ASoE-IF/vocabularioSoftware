#define PAREDE 186

void menu(void);
int botoes(char, int);
int botoes2(char, int, int, int);
void chamaclick(int, int);
void ladrilhos(void);

void ladrilhos(void)
{
	int linhas;

	for (linhas = 0; linhas <= 23; linhas++)
	{
		printf("%c", 205);
	}
}

void menu(void)
{
	printf("%c", 201);
	ladrilhos();
	printf("%c\n", 187);

	printf("%c\t   menu\t         %c\n", PAREDE, PAREDE);

	printf("%c", 204);
	ladrilhos();
	printf("%c\n", 185);

	printf("%c", PAREDE);
	textbackground(8);
	printf("Iniciar Jogo            ");
	textbackground(0);
	printf("%c\n", PAREDE);

	printf("%c", PAREDE);
	printf("Melhores Pontuacoes     ");
	printf("%c\n", PAREDE);

	 printf("%c", PAREDE);
	printf("Instrucoes              ");
	printf("%c\n", PAREDE);;

	printf("%c", PAREDE);
	printf("Sair                    ");
	printf("%c\n", PAREDE);

	printf("%c", 200);

	ladrilhos();
	printf("%c\n", 188);
}

int botoes(char tecla, int resultado)
{
	switch (tecla)
	{
	case 'H':
		if (resultado == 1)
			break;

		else
			resultado--;

		if (resultado == 1)
		{
			gotoxy(2, 4);
			textbackground(8);
			printf("Iniciar Jogo            ");
			textbackground(0);

			gotoxy(2, 5);
			printf("Melhores Pontuacoes     ");

			gotoxy(2, 6);
			printf("Instrucoes              ");

			gotoxy(2, 7);
			printf("Sair                    ");

		}

		if (resultado == 2)
		{
			gotoxy(2, 4);
			printf("Iniciar Jogo            ");

			gotoxy(2, 5);
			textbackground(8);
			printf("Melhores Pontuacoes     ");
			textbackground(0);

			gotoxy(2, 6);
			printf("Instrucoes              ");

			gotoxy(2, 7);
			printf("Sair                    ");
		}

		if (resultado == 3)
		{
			gotoxy(2, 4);
			printf("Iniciar Jogo            ");

			gotoxy(2, 5);
			printf("Melhores Pontuacoes     ");

			gotoxy(2, 6);
			textbackground(8);
			printf("Instrucoes              ");
			textbackground(0);

			gotoxy(2, 7);
			printf("Sair                    ");
		}
		break;

	case 'P':
		if (resultado == 4)
			break;

		else
			resultado++;

		if (resultado == 2)
		{
			gotoxy(2, 4);
			printf("Iniciar Jogo            ");

			gotoxy(2, 5);
			textbackground(8);
			printf("Melhores Pontuacoes     ");
			textbackground(0);

			gotoxy(2, 6);
			printf("Instrucoes             ");

			gotoxy(2, 7);
			printf("Sair                   ");
		}

		if (resultado == 3)
		{
			gotoxy(2, 4);
			printf("Iniciar Jogo            ");

			gotoxy(2, 5);
			printf("Melhores Pontuacoes     ");

			gotoxy(2, 6);
			textbackground(8);
			printf("Instrucoes              ");
			textbackground(0);

			gotoxy(2, 7);
			printf("Sair                    ");
		}

		if (resultado == 4)
		{
			gotoxy(2, 4);
			printf("Iniciar Jogo            ");

			gotoxy(2, 5);
			printf("Melhores Pontuacoes     ");

			gotoxy(2, 6);
			printf("Instrucoes              ");

			gotoxy(2, 7);
			textbackground(8);
			printf("Sair                    ");
			textbackground(0);
		}
		break;
	}
return resultado;
}

int botoes2(char tecla, int resultado, int x, int y)
{
	switch (tecla)
	{
	case 'H':
		if (resultado == 1)
			break;
		else
			resultado--;

			gotoxy(x, y);
			textbackground(8);
			printf("Sair       ");
			textbackground(0);

			printf("%c", PAREDE);

			printf("Iniciar Jogo");

		break;

	case 'P':
		if (resultado == 2)
			break;

		else
			resultado++;

			gotoxy(x, y);
			printf("Sair       ");
			printf("%c", PAREDE);
            textbackground(8);
			printf("Iniciar Jogo");
            textbackground(0);

		break;
	}
	return resultado;
}

void chamaclick(int x, int y)
{
    	int resultado = 2, a = 0;
	char tecla = '0', anterior = '0';
	while (tecla != 13)
{
		tecla = getch();
		if(tecla != anterior)
		{
			anterior == tecla;
			resultado = botoes2(tecla, resultado, x, y);
		}
	}

			switch (resultado)
		{
		case 1:
		
			exit(1);
			break;

		case 2:
			jogo();
			break;
		}
}
