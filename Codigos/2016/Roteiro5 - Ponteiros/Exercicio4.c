int imprime_endereco(int *num)
{
    printf("O endereco de num na funcao e: %d\n", num);
}

int main(void)
{
    int a = 50;

    printf("O endereco de num em main e: %d\n", &a);
    imprime_endereco(&a);
}


/*iguais, porque foi passado o endereço de a, e num está apontando para esse endereço*/
