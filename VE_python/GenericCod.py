# coding=utf-8

# Controle de Fluxo


from __future__ import print_function
import string
import UserString

#==================================Loops=====================================#

# For

a = 0
for x in range(1, 100):
    a = a + x
print ('\nValor referente ao for: ', a)

# while

x = range(10)

while x <= 10:
    x += 1

print("\nLista de nums do while ", x)

#==================================Tipos de Variaveis=====================================#

# Numeros

# convertendo de real para inteiro

print ('\nint(3.14) = ', int(3.14))

# convertendo de inteiro ṕara real

print ('\nfloat(5) = ', float(5))

# calculo entre inteiro e real resulta em real

print ("\nint('20', 8) =", int('20', 8)) #base 8
print ("\nint('20', 16) =", int('20', 16))


#==================================Texto/Strings=====================================#


J = "Jesus"

# Concatenação

print ('The king ' + J )

# Interpolação

print ('tamanho da string %s => %d' % (J, len(J)))

# String tratada como sequência

for ch in J: 
	print (ch)

# Strings são objetos

if J.startswith('C'): 
	print (J.upper())

# o que acontecerá?

print (3 * J)

# 3 * s é consistente com s + s + s

# Zeros a esquerda

print ('\nAgora são %02d:%02d.' % (16, 30))

# Real (número após o ponto controla as casas decimais)

print ('\nPercentagem: %.1f%%, Exponencial:%.2e' % (5.333, 0.00314))

# Octal e hexadecimal

print ('\nDecimal: %d, Octal: %o, Hexadecimal: %x' % (10, 10, 10))

musicos = [('Page', 'guitarrista', 'Led Zeppelin'), ('Fripp', 'guitarrista', 'King Crimson')]

# Parâmetros identificados pela ordem

msg = '{0} é {1} do {2}'

for nome, funcao, banda in musicos:
	print('\n'+ msg.format(nome, funcao, banda))

# Parâmetros identificados pelo nome

msg = '{saudacao}, são {hora:02d}:{minuto:02d}'

print ('\n'+ msg.format(saudacao='Bom dia', hora=7, minuto=30))

# Função builtin format()

print ('\nPi =', format(3.14159, '.3e'))

# O alfabeto

a = string.ascii_letters

# Rodando o alfabeto um caractere para a esquerda

b = a[1:] + a[0]

# A função maketrans() cria uma tabela de tradução
# entre os caracteres das duas strings que ela
# recebeu como parâmetro.
# Os caracteres ausentes nas tabelas serão
# copiados para a saída.

tab = string.maketrans(a, b)

# A mensagem...

msg = '''Esse texto será traduzido..
Vai ficar bem estranho.
'''

# A função translate() usa a tabela de tradução
# criada pela maketrans() para traduzir uma string

print ('\n'+ string.translate(msg, tab))

# Cria uma string template

st = string.Template('$aviso aconteceu em $quando')

# Preenche o modelo com um dicionário

s = st.substitute({'aviso': 'Falta de eletricidade',
'quando': '03 de Abril de 2002'})

# Mostra:
# Falta de eletricidade aconteceu em 03 de Abril de 2002

print (s)

# String mutável

s = UserString.MutableString('JESUS')
s[0] = 'J'

print ('\n'+ s) # mostra "Jesus"

#As strings unicode podem convertidas para strings convencionais através do método decode()

# String unicode

u = u'Hüsker Dü'

# Convertendo para str

s = u.encode('latin1')

print ('\n'+ s, '=>', type(s))

# String str

s = 'Hüsker Dü'

u = s.decode('utf-8')

print ('\n'+ repr(u), '=>', type(u))


#==================================Listas=====================================#

print ('\n'+ "========Listas=======")

lista = ['A', 'B', 'C']

print ('lista:', lista)

# A lista vazia é avaliada como falsa

while lista:

# Em filas, o primeiro item é o primeiro a sair
# pop(0) remove e retorna o primeiro item

	print ('Saiu', lista.pop(0), ', faltam', len(lista))

# Mais itens na lista

lista += ['D', 'E', 'F']

print ('lista:', lista)

while lista:

# Em pilhas, o primeiro item é o último a sair
# pop() remove e retorna o último item

	print ('Saiu', lista.pop(), ', faltam', len(lista))




