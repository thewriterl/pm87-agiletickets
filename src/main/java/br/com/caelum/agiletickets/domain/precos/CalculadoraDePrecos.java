package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {
	
	private static BigDecimal calculaPreco(Sessao sessao, double taxa) {
		
		return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(taxa)));
		
	}
	
	private static double calculaRazao(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
	}
	
	private static BigDecimal calculaTaxaTempo(Sessao sessao) {
		return sessao.getPreco().multiply(BigDecimal.valueOf(0.10));
	}
	

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			if(calculaRazao(sessao) <= 0.05) { 
				preco = calculaPreco(sessao,0.10);
			} else {
				preco = sessao.getPreco();
			}
			
			
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA))  {
			if(calculaRazao(sessao) <= 0.50) { 
				preco = calculaPreco(sessao, 0.20);
			} else {
				preco = sessao.getPreco();
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(calculaTaxaTempo(sessao));
			}
			
	
			
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}