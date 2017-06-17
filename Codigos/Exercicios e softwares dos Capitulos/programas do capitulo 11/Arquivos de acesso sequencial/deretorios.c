/* Cria um arquivo seqüencial */
#include<stdio.h>
main()
{
	int conta;
	char nome[30];
	float saldo;
	FILE *cfPtr;
			if ((cfPtr = fopen("clientes.dat", "w")) == NULL)
			printf("Arquivo nao pode ser aberto\n");
		else
		{
			printf("Digite a conta, o nome e o saldo.\n");
			printf("Digite EOF para encerrar a entrada de dados.\n");
			printf("?  ");
			scanf(" %d %s %f ", &conta, nome, &saldo);
			while (!feof(stdin))
			{
			fprintf(cfPtr, "%d %s %.2f\n", conta, nome, saldo);
			printf("? ");
				scanf("%d%s%f", fcconta, nome, &saldo);
			}
			fclose(cfPtr);
		}
	return 0;
}