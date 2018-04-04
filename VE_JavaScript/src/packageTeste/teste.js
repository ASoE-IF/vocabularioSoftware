
// declarando construtores de objeto (tipos)

// utilizando prototipos
function Casa() {
    this._nome = null;
    this._dono = null;
    this._valor = null;
}

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

// utilizando unicas funcoes
function Recipiente(capacidade) {
    this.capacidade = capacidade;
    this.volConteudo = 0;
    this.nomeConteudo = "";

    this.encher = function(quanto, comOQue) {
        this.volConteudo = this.volConteudo + quanto;
        this.nomeConteudo = comOQue;
    };

    this.remover = function(quanto) {
        // remove <quanto> do conteúdo da bacia
        // retorna o quanto foi removido

        if (quanto >= this.volConteudo) {
            var removido = this.volConteudo;
            this.volConteudo = 0;
            return removido;
        } else {
            this.volConteudo = this.volConteudo - quanto;
            return quanto;
        }
    };
}

// criando objetos a partir dos construtores

var casaBranca = new Casa();
casaBranca.setNome("Casa Branca");
casaBranca.setDono("Donald Trump");
casaBranca.setValor(100);

console.log(casaBranca.getDono()+" é dono da casa de nome "+casaBranca.getNome()+", ela vale "+casaBranca.getValor());

var bacia = new Recipiente(50);

bacia.encher(25, "água");
console.log("a bacia tem "+bacia.volConteudo+"L de "+bacia.nomeConteudo);

var removido = bacia.remover(20);
console.log("tirei "+removido+"L da bacia, agora ela tem "+bacia.volConteudo+"L de "+bacia.nomeConteudo);

// funcao exemplo

function juntarStrings(string1, string2) {
    return string1 + string2;
}

var valor1 = prompt("Digite a primeira string, pessoa");
var valor2 = prompt("Digite a segunda string, pessoa");

var juncao = juntarStrings(valor1, valor2);

console.log("A junção deu \"" + juncao + "\"");

