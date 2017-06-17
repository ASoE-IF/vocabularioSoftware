#include <stdio.h>
#include<math.h>

typedef struct
{
	double x;
	double y;
}Ponto;

typedef struct
{
	double x1, x2;
	double y1, y2;
}Retangulo;

double dist_ponto(Ponto *p1, Ponto *p2)
{
	return 	sqrt(pow((p1 -> x - p2 -> x), 2) + pow((p1 -> y - p2 -> y), 2));
}

double area_re(Retangulo *re)
{
	return ((re -> x2 - re -> x1) * (re -> y2 - re -> y1));
}

int dentro_fora(Retangulo *re, Ponto *p)
{
	if(((p -> x > re -> x1) && (p -> x < re -> x2)) && 
	((p -> y > re -> y1) && (p -> y < re -> y2)))
	{
		return (1);
	}
	
	else
	{
		return (0);
	}
}
int main(void)
{
	double distancia_euclidiana, area;
    
	Ponto p1 = {0.25, 0.75};
    Ponto p2 = {10.25, 25.14};
    Retangulo retangulo = {0, 10, 0, 10};
    
    distancia_euclidiana = dist_ponto(&p1, &p2);
    
    area = area_re(&retangulo);
    
    printf("A distancia euclidiana entre p1 e p2 e: %f\n\n", distancia_euclidiana);
    printf("A area do retangulo e: %f\n", area);
	printf("O resultado se o p1 esta no retangulo e: %d\n", dentro_fora(&retangulo, &p1));
	
    return 0;
}
