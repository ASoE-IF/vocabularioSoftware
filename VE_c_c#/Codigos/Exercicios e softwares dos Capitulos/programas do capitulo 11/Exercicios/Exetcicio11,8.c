#include<stdio.h>

int main(void)
{
	FILE *client;
	int conta = 1;;
	char nome[10];
	float saldo;
	client = fopen("Exercicio 11,8.txt", "w");
	fprintf(client, "|Sistema de contabilidades.|\n|*******|*******|**********|\n\
|Conta\t||Nome\t|Saldo\t   |\n|*******|*******|***********\n");
	while (conta != 0)
	{
		printf("conta: ");
		scanf("%d", &conta);
		if (conta == 0){
			fprintf(client, "|**************************");
				fclose(client);
					return 0;
					}
		printf("nome: ");
		scanf("%s", nome);
		printf("saldo: ");
		scanf("%f", &saldo);
		fprintf(client, "|%d\t|%s\t|%.2f\n", conta, nome, saldo);
	}
	return 0;
}