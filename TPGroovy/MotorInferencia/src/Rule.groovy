class Rule {   
	String name
	Closure[] conditions
	def evaluate
	def action
	def result
	boolean executed = false;
}

