/*Aplicativo completo com todas as entidades e estruturas de um
* Aplicativo em C.
* Autor: Israel Gomes de Lima
* Data: 20/09/2017
*/

#include<iostream>
#include<exception>

using std::cout;
using std::exception;

class TrataErro1: public exception{
public:
    TrataErro1::TrataErro1():exception("Erro"){
    }
    void usandoThrow(){
        throw TrataErro1();
    }
};

int main(void) throw (TrataErro1 erro1, TrataErro2) {

    try{
        int var = 5;

        var = var / 0;
    }
    catch(TrataErro1 &erroGerado){
        cout << "Erro Gereado";
    }
}
