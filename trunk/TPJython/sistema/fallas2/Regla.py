#Modulo WorkingMemory.py
class Regla:

    def cumple(self,working_memory):
        return true

    def ejecutar(self,working_memory):
         return 1


class Mi_Regla(Regla):
    _a=0
    _b=0
    _c=0
    _d=0

    def cumple(self,working_memory):
        if ( (int(self._a) == working_memory._a) and (int(self._b) == working_memory._b) ):
            return True
        else:
            return False


    def ejecutar(self,working_memory):
            if( self.cumple(working_memory) ):
                working_memory._c=int(self._c)
