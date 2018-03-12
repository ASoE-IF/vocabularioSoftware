
function juntarStrings(string1, string2) {
    return string1 + string2;
};

var Casa = (function() {
    // constructor
    function Casa() {
        this._nome = null;
        this._dono = null;
        this._valor = null;
    };

    Casa.prototype.getDono = function() {
        return this._dono;
    };
    Casa.prototype.setDono = function(dono) {
        this._dono = dono;
    };
    Casa.prototype.getValor = function() {
        return this._valor;
    };
    Casa.prototype.setValor = function(valor) {
        this._valor = valor;
    };
    Casa.prototype.getNome = function() {
        return this._nome;
    };
    Casa.prototype.setNome = function(nome) {
        this._nome = nome;
    };

    return Casa;
})();

var CasaBranca = new Casa();
CasaBranca.setNome("Casa Branca");
CasaBranca.setDono("Donald Trump");
CasaBranca.setValor(100);

var SupremoTribunal = new Casa();
SupremoTribunal.setNome("Supremo Tribunal");
SupremoTribunal.setDono("Cármen Lúcia");
SupremoTribunal.setValor(100);

alert(CasaBranca.getDono() + " é dono da casa de nome " + CasaBranca.getNome() + ", ela vale " + CasaBranca.getValor());
alert(SupremoTribunal.getDono() + " é dono da casa de nome " + SupremoTribunal.getNome() + ", ela vale " + SupremoTribunal.getValor());

var valor1 = prompt("Digite a primeira string, pessoa");
var valor2 = prompt("Digite a segunda string, pessoa");

var juncao = juntarStrings(valor1, valor2);

alert("A junção deu \"" + juncao + "\"");

