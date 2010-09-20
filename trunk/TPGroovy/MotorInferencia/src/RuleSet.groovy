class RuleSet{ 
  public ArrayList<Rule> rules = new ArrayList<Rule>() 
  
  void addRule(Rule rule){
		rules.add(rule); 
  }
  
  ArrayList<Rule> getRules(){
  		return rules;	
  }
}
