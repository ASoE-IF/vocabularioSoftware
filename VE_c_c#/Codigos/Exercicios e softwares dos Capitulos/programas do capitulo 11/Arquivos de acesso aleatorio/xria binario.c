#include<stdio.h>
#include<stdlib.h>

int main(void)
{
	FILE *fp;
	int i, v[10];
	
	for(i=0; i<10; i++)
		{
			printf("Insere o %d-ésimo N°\n", i+1);
			scanf("%d", &v[i]);
		}
		
		if((fp=fopen("datas.dat", "wb"))==NULL)
		{
		printf("Impossivel criar o arquivo datas.dat.\n");
		exit(1);
		}
		
	if(fwrite(v, sizeof(4), 10, fp)!=10)
	fprintf(fp,  "erro ao criar o arquivo!!!");
	
	fclose(fp);
}