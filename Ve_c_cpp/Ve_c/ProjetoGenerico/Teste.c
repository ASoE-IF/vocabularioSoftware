
int x = 5;
int main(void){
	x = 5;
	x = 5;
	int y = 8;
	y++;
	int func_interna(){
		x = 6;
		x++;
		y = 6;
		y++;
	}
}
