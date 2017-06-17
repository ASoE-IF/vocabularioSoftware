#include<stdio.h>
#include<conio.h>

int main(void)
{
	FILE *senha_ptr, *conta_ptr;
	char conta[50];
	int senha_da_conta, confirm_senha;
	int opcao, tentativa;
	char opacao[8];
	while ((opcao > 3) || (opcao < 1))
	{
		printf("Jogo da Memoria.\n\n");
		printf("1-Entrar.\n2-Criar conta.\n3-Sair.\n\n");
		printf("Digite uma alternativa que nao existe para enxerrar.\nDigite sua escolha: ");
		scanf("%d", &opcao);
		switch (opcao)
		{
		case 1:
			clrscr();
			printf("Digite sua conta: ");
			scanf("%s", conta);
			clrscr();
			printf("Digite sua senha: ");
			scanf("%d", &senha_da_conta);
								senha_ptr=fopen(conta, "r");
						fscanf(senha_ptr, "%d", &confirm_senha);
	
			if(senha_da_conta==confirm_senha)
			printf("ola");
			else printf("loginou senha incorretos.");
			break;
		case 2:
			clrscr();
			break;
		case 3:
			clrscr();
			break;
		default:
			{
					clrscr();
			}

		}
	}
}