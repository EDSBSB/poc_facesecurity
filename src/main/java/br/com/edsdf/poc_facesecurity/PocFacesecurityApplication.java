package br.com.edsdf.poc_facesecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.edsdf.poc_facesecurity.negocio.CadastroProcurado;

@RestController
@EnableAutoConfiguration
public class PocFacesecurityApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(PocFacesecurityApplication.class, args);
	}

	@RequestMapping("/json")
	String home() {
		return "ola";
	}
	
	@GetMapping("/bustaTodosProcurados")
	@Procedure("application/json")
    public String bustaTodosProcurados(){
		return new CadastroProcurado(jdbcTemplate).bustaTodosProcurados();
//		return new ResponseEntity<Object>(new CadastroProcurado(jdbcTemplate).bustaTodosProcurados(), HttpStatus.OK);
    }
    
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("nome") String nome, @RequestParam("identificador") String identificador, @RequestParam("file") MultipartFile file) {
    	System.out.println("nome: "+ nome);
    	if( identificador == null || identificador.isEmpty()) {
            return "Informe o indentificador do procurado";
    	}
    	
        if (file.isEmpty()) {
            return "Informe o arquivo";
        }
        return new CadastroProcurado(jdbcTemplate).cadastro(nome, identificador, file );
    }
}

