from java.io import *
from Regla import *
from java.util import LinkedList

class Reglas_Builder:

    def leer_archivo(self):

        archivo = File("reglas.txt")
        fr = FileReader(archivo)
        br = BufferedReader(fr)
        linea = br.readLine()
        res = LinkedList()
        while( linea != None ):
            res.add(self.crear_regla(linea))
            linea = br.readLine()

        return res

    def crear_regla(self,linea):
        regla = Mi_Regla()
        partes = linea.split( "then" )
        condiciones_izquierda = partes[0].split(" ")
        condiciones_derecha = partes[1].split(" ")

        for condicion in condiciones_izquierda:
            if(condicion != "if"):
                self.cargar_regla_condiciones(regla,condicion)

        for condicion in condiciones_derecha:
            self.cargar_regla_accion(regla,condicion)

        return regla


    def cargar_regla_condiciones(self,regla,string):
       valores = string.split("=")
       if(valores[0] != ""):
           operando = valores[0]
           resultado = valores[1]
           if(operando == "a"):
               regla._a=resultado
           if(operando == "b"):
               regla._b=resultado

    def cargar_regla_accion(self,regla,string):
        valores = string.split("=")
        if(valores[0] != ""):
            regla._c = valores[1]


