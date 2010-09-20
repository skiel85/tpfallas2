import java.util.Iterator;


class MotorInferencia {
	
	static InputStreamReader isr = new InputStreamReader(System.in)
	static BufferedReader br = new BufferedReader(isr)
	
	static Rule crearRegla(numeroRegla, premisas, conectores) {					
		Rule regla = new Rule()
		regla.name = "Regla " + numeroRegla;
		
		def conds = [];
		for(j in 0..(conectores.size()-1)){
			Closure closure;
			if(conectores[j]=="AND"){
				closure = {x, y -> println ("x:$x and y:$y => " + (x && y)); return x && y};
			}else if(conectores[j]=="OR"){
				closure = {x, y -> println ("x:$x or y:$y => " + (x || y));return x || y};
			}
			if(closure!=null)conds.add(closure);
		}
		
		regla.conditions = conds;
		regla.evaluate  = {wm ->
			def result 
			def j = 0
			println(regla.name);
			for(i in 0..(regla.conditions.size()-1)){
				if(result==null){
					if(regla.conditions[i](wm.getProperty(premisas[j]), wm.getProperty(premisas[j+1]))){
						result = 1;
					}else{
						result = 0;
					}
					j++;
				}else{
					if(regla.conditions[i](result, wm.getProperty(premisas[j+1]))){
						result = 1;
					}else {
						result = 0;
					}
					j++;
				}
			}
			println(regla.name+" dio "+result);
			return result;
		};		
		regla.action = {wm -> wm.setProperty(premisas[premisas.size()-1], 1); println("Se ejecutó " + regla.name + " y el resultado es " + premisas[premisas.size()-1] + "=1") }
		regla.result = {wm ->
			if(wm.getProperty(premisas[premisas.size()-1])){
				return true;
			}else{
				return false;
			}
		}
		return regla
	}
	
	static def IngresarDato(nombreVariable) {
		println "Ingrese dato " + (nombreVariable) + " : "
		return br.readLine().toInteger()
	}
	
	//lee las reglas desde un archivo
	//to-do: a partir de lo que lee, hay que crear las reglas segun los conectores y propiedades que conforman la regla. (simplificar)
	static def LeerReglas(RuleSet conjunto)
	{
		def archivo = new File("./src/reglas.txt");
		def numeroRegla = 1;
		
		archivo.eachLine{ line ->
			def premisas = [];
			def conectores = [];
			def indexPremisas = 0;
			def indexConectores = 0;
			println ("--------Regla"+numeroRegla);
			def lineArray = line.split(" ");
			def cant = lineArray.size();
			for( i in 0..(cant-1)) {
				//println(lineArray[i]);
				if(i%2==0){
					premisas[indexPremisas] = lineArray[i];
					print(premisas[indexPremisas]+" ")
					//					println("premisa "+indexPremisas+" es "+premisas[indexPremisas]);
					indexPremisas++;
				}else{
					conectores[indexConectores] = lineArray[i];
					print(conectores[indexConectores]+" ");
					//					println("conector "+indexConectores+" es "+conectores[indexConectores]);
					indexConectores++;
				}
			}
			println();
			println("----Fin Regla"+numeroRegla);
			Rule rule = crearRegla(numeroRegla, premisas, conectores);
			conjunto.addRule(rule);
			numeroRegla++;1
		}
	}
	
	static void main(def args) {
		RuleSet conjunto = new RuleSet()		
		WorkingMemory wm = new WorkingMemory()
		
		LeerReglas(conjunto);
		
		wm.a = IngresarDato("a")
		
		//EjecutarReglasHaciaAdelante(wm, conjunto)
		
		wm.b = IngresarDato("b");
		
		//EjecutarReglasHaciaAdelante(wm, conjunto)
		
		wm.c = IngresarDato("c");
		
		//EjecutarReglasHaciaAdelante(wm, conjunto)
		
		wm.d = IngresarDato("d");
		EjecutarReglasHaciaAtras(wm, conjunto);
		
		println wm.dump()
	}
	
	static def EjecutarReglasHaciaAdelante(WorkingMemory wm, RuleSet conjunto) {
		RuleSet reglasQueCumplen = new RuleSet()
		
		conjunto.rules.each {			
			if(it.evaluate(wm)) {				
				reglasQueCumplen.rules<<it
			}
		}
		
		if(reglasQueCumplen.rules.size() > 0) {
			Rule reglaAEjecutar = ResolverConflictos(reglasQueCumplen);
			reglaAEjecutar.action(wm)
			reglaAEjecutar.executed = true;
		}
		else{
			println("No cumple ninguna regla");
		}
	}
	
	static def EjecutarReglasHaciaAtras(WorkingMemory wm, RuleSet conjunto) {
		RuleSet reglasQueCumplen = new RuleSet()
		
		conjunto.rules.each {
			if((it.result(wm))&&(it.evaluate(wm))) {
				reglasQueCumplen.rules<<it
			}
		}
		
		if(reglasQueCumplen.rules.size() > 0) {
			reglasQueCumplen.rules.each{
				println("Se cumple la regla "+ it.getName());
			}
		}
		else{
			println("No cumple ninguna regla");
		}
	}
	static def ResolverConflictos(RuleSet reglasQueCumplen) {
		reglasQueCumplen.rules.getAt(0)
	}
}
