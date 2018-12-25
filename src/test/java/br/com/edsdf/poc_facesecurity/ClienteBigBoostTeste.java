package br.com.edsdf.poc_facesecurity;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.edsdf.poc_facesecurity.bigboost.ClienteBibBoost;

public class ClienteBigBoostTeste {

	public static void main(String[] args) {
		System.out.println(">>>vai<<<");
		System.setProperty("URL_BIGBOOST", "http://localhost:8090");
		
		ClienteBibBoost cliente =new ClienteBibBoost();
		try {
			String ret = cliente.buscaDadoBasico("070.680.938-68");
			
			JSONObject j = new JSONObject(ret);
			
			JSONArray arr = j.getJSONArray("Result");
			
			String nome = arr.getJSONObject(0).getJSONObject("BasicData").getString("Name");
			
			System.out.println("Nome: "+ nome);
			
			System.out.println( ret );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
