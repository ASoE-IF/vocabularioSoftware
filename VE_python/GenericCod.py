# coding=utf-8

# Controle de Fluxo


from __future__ import print_function

temp = int(raw_input("Entre com a temperatura:"))

if temp < 0:
    print('\nCongelando...\n')
elif 0 <= temp <= 20:
    print ('\nFrio\n')
elif 21 <= temp <= 25:
    print ('\nNormal\n')
elif 26 <= temp <= 35:
    print ('\nQuente\n')
else:
    print('\nMuito quente!!!\n')

# Loops

# For

a = 0
for x in range(1, 100):
    a = a + x
print ('Valor referente ao for: ', a)

# while

b = 0
c = 1

while x <= 100:
    b = b + x
    c = c + 1
print (b)

# Tipos

# Numeros

# convertendo de real para inteiro

print ('int(3.14) = ', int(3.14))

# convertendo de inteiro ṕara real

print ('float(5) = ',float(5))

# calculo entre inteiro e real resulta em real

print ("int('20', 8) =", int('20', 8)) #base 8
print ("int('20', 16) =", int('20', 16))

# operacoes com numeros complexos

d = 3 + 4j

print ('c = ', c)
print ('Parte real: ', c.real)
print ('Parte imaginaria:', c.imag)
print ('conjugado:', c.conjugnate())



