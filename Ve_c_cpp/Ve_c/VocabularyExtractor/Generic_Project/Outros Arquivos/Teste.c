
int x = 5;
int main(void){
	x = 5;
	x = 5;
	int y = 8;
	y++;
	int func_interna(){
		x = 6;
		x++;
		y = 6;
		y++;
	}
}

void funcaoUsandoPonteiros(int *ponteiro1)
{
	int var = 10;
	int *ponteiro2;
	int *ponteiro3 = &var;
	int **ponteiroParaPonteiro, ***ponteiroParaPonteiroParaPonteiro;
	int *matrizDePonteiro[10];
	int matriz;

	ponteiro2 = &var;

	var = *ponteiro2 + 5;
	ponteiro1 = ponteiro2;

	ponteiro1--;
	ponteiro1++;
	ponteiro1 = ponteiro2 + 5;

	ponteiro1 == ponteiro2;
	matrizDePonteiro[2] = &var;
	ponteiro2 = matrizDePonteiro;
	*(ponteiro2 + 5) = 10;
	funcaoDeMatriz(matrizDePonteiro);

	ponteiroParaPonteiro = ponteiro2;
	ponteiroParaPonteiroParaPonteiro = ponteiroParaPonteiro;
	**ponteiroParaPonteiro += 20;
	***ponteiroParaPonteiroParaPonteiro += (**ponteiroParaPonteiro + 10);
	ponteiro1 = malloc(50 * sizeof(int));
	matriz = malloc(80);
}
