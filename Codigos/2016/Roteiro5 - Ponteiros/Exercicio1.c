#include<stdio.h>

char string(char nome[])
{
    int x;

    for(x = (6); x >= 0; x--)
    {
        printf("%c\n", *(nome + x));
    }
}
int main(void)
{
    string("iSRAEL");
}
