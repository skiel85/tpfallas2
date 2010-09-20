class WorkingMemory {
	/*int a = 0
	 int b = 0
	 int c = 0
	 int d = 0
	 int e = 0
	 */
	def propiedades = [:]
	
	void setProperty(String nombre, Object valor) {
		propiedades[nombre] = valor
	}
	
	Object getProperty(String nombre) {
		propiedades[nombre]
	}
}
