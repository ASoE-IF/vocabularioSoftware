int controle(int *cob_pos, int *cob_pos2, int tamanho, int campo[][30],
			 char direcao, char *opcional)
{
	int verifica;
	if ((direcao != 'H') && (direcao != 'K') && (direcao != 'M')
		&& (direcao != 'P'))
		direcao = *opcional;
	if ((*opcional == 'H') && (direcao == 'P'))
		direcao = *opcional;
	if ((*opcional == 'P') && (direcao == 'H'))
		direcao = *opcional;
	if ((*opcional == 'M') && (direcao == 'K'))
		direcao = *opcional;
	if ((*opcional == 'K') && (direcao == 'M'))
		direcao = *opcional;
	switch (direcao)
	{
	case 'H':
		campo[*cob_pos][*cob_pos2] = tamanho;
		*cob_pos -= 1;
		for (verifica = 1; verifica <= tamanho; verifica++)
		{
			if (campo[*cob_pos][*cob_pos2] == verifica)
				game_over();
		}
		*opcional = direcao;
		break;

	case 'K':
		campo[*cob_pos][*cob_pos2] = tamanho;
		*cob_pos2 -= 1;
		for (verifica = 1; verifica <= tamanho; verifica++)
		{
			if (campo[*cob_pos][*cob_pos2] == verifica)
				game_over();
		}
		*opcional = direcao;
		break;

	case 'M':
		campo[*cob_pos][*cob_pos2] = tamanho;
		*cob_pos2 += 1;
		for (verifica = 1; verifica <= tamanho; verifica++)
		{
			if (campo[*cob_pos][*cob_pos2] == verifica)
				game_over();
		}
		*opcional = direcao;
		break;

	case 'P':
		campo[*cob_pos][*cob_pos2] = tamanho;
		*cob_pos += 1;
		for (verifica = 1; verifica <= tamanho; verifica++)
		{
			if (campo[*cob_pos][*cob_pos2] == verifica)
				game_over();
		}
		*opcional = direcao;
		break;

	default:
		break;
	}

}
