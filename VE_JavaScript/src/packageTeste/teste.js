
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

function Bacia(capacidade) {
    this.capacidade = capacidade;
    this.volConteudo = 0;
    this.nomeConteudo = "";

    this.encher = function(quanto, comOQue) {
        this.volConteudo = volConteudo + quanto;
        this.nomeConteudo = comOQue;
    };

    this.remover = function(quanto) {
        // remove <quanto> do conteúdo da bacia
        // retorna o quanto foi removido

        if (quanto >= volConteudo) {
            var removido = volConteudo;
            this.volConteudo = 0;
            return removido;
        } else {
            this.volConteudo = volConteudo - quanto;
            return quanto;
        };
    };
};

function Monitor(marca, modelo) {
    this.marca = marca;
    this.modelo = modelo;

    this.getNomeProduto = function {
        return this.marca + " " + this.modelo;
    };
    this.getMarca = function {
        return this.marca;
    };
    this.getModelo = function {
        return this.modelo;
    };
};

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

