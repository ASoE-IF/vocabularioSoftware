//comentario externo 1

static int dobro(int paraDobrar)
{
	//comentario interno 1
	return paraDobrar * 2;
}
//comentario externo 2	
static void imprimi(int valor)
{
	//comentario interno 2
	printf("%d\n", valor);
	printf("%d\n", valor);
	printf("%d\n", valor);
	printf("%d\n", valor);
}
//comentario externo 3
static void main(char args)
{
	//comentario interno 3
	imprimi(dobro(5));
}
//comentario externo 4