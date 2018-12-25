package br.com.edsdf.poc_facesecurity.negocio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import br.com.edsdf.poc_facesecurity.bigboost.ClienteBibBoost;

public class CadastroProcurado {

	private final static String UPLOADED_FOLDER = "foto/";
	
	JdbcTemplate jdbcTemplate;
	public CadastroProcurado(JdbcTemplate jt) {
		this.jdbcTemplate = jt;
	}
	

	public String cadastro(String cpf, MultipartFile file) {
		try {
			String home = System.getProperty("user.home");
			String filePath = home + "/" + UPLOADED_FOLDER;
			if (!new File(filePath).isDirectory()) {
				new File(filePath).mkdirs();
			}

			byte[] bytes = file.getBytes();
			cpf = cpf.replaceAll("[^0-9]+","");
			

			ClienteBibBoost cliente =new ClienteBibBoost();
			
			String ret = cliente.buscaDadoBasico(cpf);
			
			Path path = Paths.get(filePath + cpf + "_" + file.getOriginalFilename());
			Files.write(path, bytes);
			
			JSONObject j = new JSONObject(ret);
			
			JSONArray arr = j.getJSONArray("Result");
			
			String nome = arr.getJSONObject(0).getJSONObject("BasicData").getString("Name");

			if( jaExisteProcurado(cpf) ) {
				apagaRegistro("procurado", cpf);
			}
			JSONObject json = new JSONObject();
			json.put("cpf", cpf);
			json.put("nome", nome);
			json.put("foto", filePath + cpf + "_" +file.getOriginalFilename() );

			String sql = "insert into conteiner (tipo, dado) values (?, ?::json)";
			
			jdbcTemplate.update(sql, "procurado", json.toString() );
			


			return ret;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject j = new JSONObject();
				j.put("sucess", false);
				j.put("msg", e.getMessage());
				return j.toString();
			} catch (Exception e1) {
			}
		}
		return "erro";
	}


	private void apagaRegistro(String string, String cpf) {
		String sql = "delete from conteiner where dado ->> 'cpf' = ? and tipo = ?";
		
		jdbcTemplate.update(sql, cpf, "procurado" );
	}


	private boolean jaExisteProcurado(String cpf) {
		String sql = "select count(*) foto from conteiner where dado ->> 'cpf' = ? and tipo = 'procurado'";
		
		int count = jdbcTemplate.queryForObject(
                sql, new Object[] { cpf }, Integer.class);
		
		return count > 0;
	}


	public String bustaTodosProcurados() {
		String sql = "SELECT dado FROM conteiner WHERE tipo = 'procurado' ";
		String ret = "";
			List<Map<String, Object>> lm = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> m: lm ) {
				PGobject pjo = (PGobject)m.get("dado");
				if( !ret.isEmpty() ) ret += ", ";
				ret += pjo.getValue();
			}
		return "["+ret+"]" ;
	}


	public String buscaImagem(String id) {
		String sql = "select dado ->> 'foto' foto from conteiner where dado ->> 'cpf' = ? limit 1";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, String.class);
		
	}

}
