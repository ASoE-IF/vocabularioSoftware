#include<stdio.h>

int main(void)
{
#ifdef __cplusplus
printf("estou utilizando C++\n");
#else
printf("estou utilizando C\n");
#endif
}