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


/*iguais, porque foi passado o endere�o de a, e num est� apontando para esse endere�o*/
