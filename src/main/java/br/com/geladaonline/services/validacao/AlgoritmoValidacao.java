package br.com.geladaonline.services.validacao;

public enum AlgoritmoValidacao {

	MODULO_11{
		public boolean validar(String cpf){
			return cpf.equals("123456789");
		}
		public String getNomeAlgoritmo(){
			return "Módulo 11";
		}
	},
	RECEITA{
		public boolean validar(String cpf){
			return cpf.equals("123456789");
		}
		public String getNomeAlgoritmo(){
			return "Validação da Receita Federal";
		}
	},
	TODOS{
		public boolean validar(String cpf){
			return MODULO_11.validar(cpf) && RECEITA.validar(cpf);
		}
		public String getNomeAlgoritmo(){
			return MODULO_11.getNomeAlgoritmo() +" "+ RECEITA.getNomeAlgoritmo();
		}
	};
	
	public abstract boolean validar(String cpf);
	public abstract String getNomeAlgoritmo();
	
}
