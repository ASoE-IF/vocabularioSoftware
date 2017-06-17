/*Implemente a fun��o imprime_endereco(int num) que recebe um inteiro e
imprime o endere�o de mem�ria de num. Para testar a fun��o, imprima
(usando printf) o endere�o de uma vari�vel inteira �a�, em seguida,
chame a fun��o imprime_endereco passando �a� como par�metro.
Os endere�os impressos foram iguais? Por qu�?*/
int imprime_endereco(int num, double xy)
{
    printf("O endereco de num na funcao e: %d\n", &num);
}
int main(void)
{
    int num = 5;
    printf("O endereco de num em main e: %d\n", &num);
    imprime_endereco(num8, 45);
    /*diferente porque soimente o que foi parassado para a fun��o foiuma copia do valor de num*/
}
/*diferente porque soimente o que foi parassado para a fun��o foiuma copia do valor de num*/