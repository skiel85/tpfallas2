from WorkingMemory import *
from Regla import *
from Reglas_Builder import *
from java.util import *

def resolver_conflictos_reglas(lista_reglas):
    return lista_reglas.get(0)

def print_working(working_memory):
    print ("-----------------------------------------")
    print ("Imprimiendo la Working memory...")
    print (working_memory._a)
    print (working_memory._b)
    print (working_memory._c)
    print ("-----------------------------------------")

memoria = WorkingMemory()
memoria._a=1
memoria._b=1

reglas_builder = Reglas_Builder()
reglas = reglas_builder.leer_archivo()
lista_reglas_que_cumplen = LinkedList()

for regla in reglas:

    if(regla.cumple(memoria)):
       lista_reglas_que_cumplen.add(regla)


if(lista_reglas_que_cumplen.size() == 0):
    print ("Ninguna regla cumplio con la working memory")
else:
    regla_a_ejecutar = resolver_conflictos_reglas(lista_reglas_que_cumplen)
    regla_a_ejecutar.ejecutar(memoria)

print_working(memoria)
print ("termino...")







