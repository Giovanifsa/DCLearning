import java.math.BigDecimal;

import ecommerce.models.Loja;
import ecommerce.models.Produto;



public class TesteCalcularPrecoVenda {

	public static void main(String[] args) {
		
		Produto produto = new Produto();
		
		Loja loja = new Loja();
		
		produto.setCusto(new BigDecimal(100));
		
		produto.setMargemDeLucroPorcentual(new BigDecimal(50));
		
		loja.setDespesaRateada(new BigDecimal(5));
		
		calcula(produto, loja);
		
		
		
		System.out.println(produto.getPrecoDeVenda());
		
	}

	
	
	public static BigDecimal calcula(Produto produto, Loja loja) {
		BigDecimal preco = new BigDecimal(0);
		preco = produto.getCusto().add(loja.getDespesaRateada()).
				multiply(new BigDecimal(1).add(produto.getMargemDeLucroPorcentual()).divide(new BigDecimal(100)));
		
		BigDecimal precoFinal = new BigDecimal(0);
		
		precoFinal = produto.getCusto().add(preco);
		
		
		return produto.setPrecoDeVenda(precoFinal);
		
	}
	
}	
	