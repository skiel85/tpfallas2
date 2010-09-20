	def IngresarDato(nombreVariable)
	{
		InputStreamReader isr = new InputStreamReader(System.in)
		BufferedReader br = new BufferedReader(isr)
		
		println "Ingrese dato " + (nombreVariable) + " : "
		return br.readLine().toInteger()
	}


	def CargarReglas(){
		def pathArchivo = "./src/reglas.txt" 
		def lineasReglas = new File(pathArchivo)		
		def reglas = []
		
        lineasReglas.eachLine{ linea,number ->        	
        	reglas.add(ParsearRegla(linea,number))
		}
		
		return reglas
	}

	def ParsearRegla(linea,numeroRegla){

		def parteReglas = linea.split(">")		
		def reglaNueva = [:]

		reglaNueva.name = "Regla " + numeroRegla
		reglaNueva.conditions = parteReglas[0].split("&")
		reglaNueva.actions = parteReglas[1].split("&")
        reglaNueva.executed = false
				
		return reglaNueva
	}
	
	def ResolverConflictos(reglasQueCumplen)
	{		
		return reglasQueCumplen[0]
	}

	
	def conjuntoReglas = CargarReglas()	
    def workingMemory = [:]

    workingMemory.a = IngresarDato("a")	
	EjecutarReglas(workingMemory, conjuntoReglas)
	
	if (workingMemory.b == null){		
		workingMemory.b = IngresarDato("b")
		EjecutarReglas(workingMemory, conjuntoReglas)
	}

	if (workingMemory.c == null){		
		workingMemory.c = IngresarDato("c")
		EjecutarReglas(workingMemory, conjuntoReglas)
	}

	if (workingMemory.d == null){		
		workingMemory.d = 0
	}
	if (workingMemory.e == null){		
		workingMemory.e = 0
	}
	if (workingMemory.f == null){		
		workingMemory.f = 0
	}


	println("*****************************************")
	println("Programa Finalizado")
	println("")
	println("Working memory> ")
	println(workingMemory)
	
	
	def ConstruirMemoryArray(workingMemory){

		def workingArray = []

        if (workingMemory.a){        	
        	workingArray.add("a")
        }		
        if (workingMemory.b){
        	workingArray.add("b")
        }
        if (workingMemory.c){
        	workingArray.add("c")
        }
        if (workingMemory.d){
        	workingArray.add("d")
        }
        if (workingMemory.e){
        	workingArray.add("e")
        }
        if (workingMemory.e){
        	workingArray.add("f")
        }

        return workingArray
	}
	

	def VerificarRegla(workingArray,regla){
        def k
		def found = false

		for(k=0;k<regla.conditions.size();k++) {
			found = workingArray.contains(regla.conditions[k])
			if(!found)
				return false
		}
		return found
	}

	def EjecutarRegla(workingMemory,regla){
		 def k

		for(k=0;k<regla.actions.size();k++) {
			workingMemory[regla.actions[k]] = 1
		}

		 regla.executed = true
		 
		println(regla.name + " ejecutada")
	}
       

	def HayReglasSinEjecutar(conjuntoReglas){
		 for(i=0;i<conjuntoReglas.size();i++) {
	        	if (!(conjuntoReglas[i].executed)){
	        		return true
	        	}
		 }
		 return false
	}
	
	def EjecutarReglas(workingMemory, conjuntoReglas)
	{
		def reglasQueCumplen = []
		def i
        for(i=0;i<conjuntoReglas.size();i++) {
        	if (!(conjuntoReglas[i].executed)){
        		
        		def memoryArray = ConstruirMemoryArray(workingMemory)
        		
        		if (VerificarRegla(memoryArray, conjuntoReglas[i])){
        			reglasQueCumplen.add(conjuntoReglas[i])
        		}
        	}        	
        }

		if(reglasQueCumplen.size() > 0)
		{
			def reglaAEjecutar = ResolverConflictos(reglasQueCumplen)
			EjecutarRegla(workingMemory,reglaAEjecutar)

			//Llamo a ejecutar todas las reglas ya que la regla que se ejecuto puede haber activado alguna			
			if (HayReglasSinEjecutar(conjuntoReglas)){
				EjecutarReglas(workingMemory,conjuntoReglas)
			}
		}
	}
	


