package br.com.edsdf.poc_facesecurity.bigboost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import br.com.edsdf.poc_facesecurity.PocFacesecurityApplication;

public class ClienteBibBoost {

	CloseableHttpClient client;
	
	public String buscaDadoBasico(String cpf) throws BigBoostException {
		String rootUrl = System.getProperty("URL_BIGBOOST");
		if (rootUrl == null) {
			throw new BigBoostException("Problema configuração, url do proxy bigboost não foi definida");
		}

		client = HttpClients.createDefault();
		
		try {
			HttpPost httpPost = new HttpPost(rootUrl + "/consulta");
			
			JSONObject json = new JSONObject();
			json.put("Datasets", "basic_data");
			json.put("q", "doc{" + cpf + "}");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("filtro", json.toString() ));
			
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			CloseableHttpResponse response = client.execute(httpPost);
			
			if( response.getStatusLine().getStatusCode() == 200) {
				return resultBody(response);
			}			
			
		} catch (Exception e) {
			throw new BigBoostException(e.getMessage());
		} finally {
			if( this.client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
	
	private String resultBody(CloseableHttpResponse response) throws UnsupportedOperationException, IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "ISO-8859-1"));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		return result.toString();
	}

}
