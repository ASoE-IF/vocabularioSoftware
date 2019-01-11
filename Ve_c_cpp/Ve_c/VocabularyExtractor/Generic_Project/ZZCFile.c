int main(void){
	int var1, var3;
	int matriz[10];
	
	var1 = var2;
	matriz[5] = var1;
	matriz[var3] = var2;
	
	struct estrutura{
		int field1;
		int field2[10];
	}var4, var5[10];
	
	struct estrutura var6;
	var6.field1 = 5;
	var6.field2[var3] = 5;
	
	var4.field2[5] = var6.field2[var3];
	var5[5].field1 = 5;
	var5[var6.field1].field1 = 5;
	var5[(var6.field1+var6.field1)].field1 = 5;
}