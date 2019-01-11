/*Aplicativo completo com todas as entidades e estruturas de um
* Aplicativo em C.
* Autor: Israel Gomes de Lima
* Data: 20/09/2017
*/
#include<iostream>
#include<string>


//Usando Classes
int funcc(int x){
}

int varx;
int vary = funcc(varx);

using std::string;

class GenericClass1
{
private:
    string nome, jogo;
    int quantidade;

public:

    string nomePublico;
    int x = funcc(varx);

    GenericClass1()
    {
        nome = "";
        jogo = "";
    }

    GenericClass1(string name)
    {
        nome = name;
    }

    GenericClass1(int quantidade1, string name = "valor")
    {
        quantidade = quantidade1;
        nome = name;
    }


    void setNome(string nome1)
    {
        nome = nome1;
    }

    string getNome()
    {
        return nome;
    }

    static void printUniversal()
    {
    }

    void printUniversal2() const
    {
    }

    ~GenericClass1()
    {
    }

};

//Classe gen�rica 2

int varGlobal = 5;
class GenericClass2
{
private:
    string nome;
    int quantidade;

public:

    GenericClass2();

    GenericClass2(string name);
    GenericClass2(int, int var2 = varGlobal, int var = varGlobal);
    GenericClass2(int, int);

    void setNome(string nome1);
    string getNome();
};

GenericClass2::GenericClass2()
{
    nome = "";
}

GenericClass2::GenericClass2(string name)
{
    nome = name;
}

GenericClass2::GenericClass2(int var2 = varGlobal, int var3 = varGlobal)
{
}

void GenericClass2::setNome(string nome1)
{
    nome = nome1;
}

string GenericClass2::getNome()
{
    return nome;
}


class GenericClass3
{
private:
    GenericClass1 obj1;

public:
    void metodoGenerico(const char * const first)
    {
    }
};

//Usando Classes e fun��es friends

class GenericClass4
{
private:
    static GenericClass1 obj1;

public:
    friend void metodoGenerico();
    friend string &operator<<( string &, const GenericClass1 & );

    friend string &operator<<( string &output, const GenericClass1 &number )
    {
    	obj1 = null;
    }

    explicit GenericClass4(){
    }
};


//Usando heran�a
class GenericClass5: public GenericClass1{

        explicit GenericClass5(string nome):GenericClass1(nome){
    }

    virtual void metodoVirtual() const{
    }

    virtual void metodoVirtualPura() const = 0;

    int prototipo2(int x), atributo, prototipo();
};


//Usando classe interna
class GenericClass6{

private:
    int atributo1;
    class GenericClassInterna1{

    public:
        void metodoGenerico1(){

        }
    };
    public:
    enum ena{CONST1}var;


public:
    void metodoGenerico(){
        GenericClassInterna1 obj;
    }
};

//Usando objetos
int main()
{
    GenericClass1 obj1;
    string nome = "nome";
    GenericClass1 obj2 = new GenericClass1(nome);

    obj1.setNome("nome");
    obj1.setNome("nome");

    obj1.nomePublico = "Nome publico";
    delete obj1;

    int method(), method2();
}
