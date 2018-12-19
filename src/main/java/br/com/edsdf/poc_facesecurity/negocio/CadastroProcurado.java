package br.com.edsdf.poc_facesecurity.negocio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

public class CadastroProcurado {

	private final static String UPLOADED_FOLDER = "foto/";
	
	JdbcTemplate jdbcTemplate;
	public CadastroProcurado(JdbcTemplate jt) {
		this.jdbcTemplate = jt;
	}
	

	public String cadastro(String nome, String identificador, MultipartFile file) {
		try {
			String home = System.getProperty("user.home");
			String filePath = home + "/" + UPLOADED_FOLDER;
			if (!new File(filePath).isDirectory()) {
				new File(filePath).mkdirs();
			}

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + identificador + "_" + file.getOriginalFilename());
			Files.write(path, bytes);
			
			JSONObject json = new JSONObject();
			json.put("nome", nome);
			json.put("identificador", identificador );
			json.put("foto", filePath + identificador + "_" +file.getOriginalFilename() );

			String sql = "insert into conteiner (tipo, dado) values (?, ?::json)";
			
			jdbcTemplate.update(sql, "procurado", json.toString() );
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "sucesso";
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
		String sql = "select dado ->> 'foto' foto from conteiner where dado ->> 'identificador' = ? ";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, String.class);
		
	}

}
