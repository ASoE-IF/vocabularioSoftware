
int x = 5;
int main(void){
	x = 5;
	x = 5;
	int y = 8;
	y++;
	class classeIn{
	private:
		int field = x;
		int field2 = y;
		int field3 = field;
		int method(){
			x = y;
			field = x;
			field = field2;
		}
	};
}
