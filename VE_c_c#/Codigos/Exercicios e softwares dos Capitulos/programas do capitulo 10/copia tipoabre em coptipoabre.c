#include<stdio.h>

int main(void)
{
	struct tipo{
	int tipoint;
	float tipofloat;
	}tipoabre, coptipoabre;
	tipoabre.tipoint=32437;
	tipoabre.tipofloat=1.4337;
	coptipoabre=tipoabre;
	printf("Tipo original (tipoabre)\n");
		printf("tipoabre.tipoint = %d\ntipoabre.tipofloat = %f\n\n", tipoabre.tipoint, tipoabre.tipofloat);
	printf("Copiado em (coptipoabre)\n");
		printf("coptipoabre.tipoint = %d\ncoptipoabre.tipofloat = %f\n\n", coptipoabre.tipoint, coptipoabre.tipofloat);
}