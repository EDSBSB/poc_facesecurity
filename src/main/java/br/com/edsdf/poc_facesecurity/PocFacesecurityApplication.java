package br.com.edsdf.poc_facesecurity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.edsdf.poc_facesecurity.negocio.CadastroProcurado;

@RestController
@EnableAutoConfiguration
public class PocFacesecurityApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Value("${URL_BIGBOOST}")
	public String URL_BIGBOOST;

	public static void main(String[] args) {
		SpringApplication.run(PocFacesecurityApplication.class, args);

	}

	@PostConstruct
	public void init() {
		System.setProperty("URL_BIGBOOST", URL_BIGBOOST);
	}

	@RequestMapping("/json")
	String home() {
		return "ola";
	}

	@GetMapping("/bustaTodosProcurados")
	@Procedure("application/json")
	public String bustaTodosProcurados() {

		return new CadastroProcurado(jdbcTemplate).bustaTodosProcurados();
		// return new ResponseEntity<Object>(new
		// CadastroProcurado(jdbcTemplate).bustaTodosProcurados(), HttpStatus.OK);
	}

	@PostMapping("/upload")
	@Procedure("application/json")
	public String handleFileUpload(@RequestParam("cpf") String cpf, @RequestParam("file") MultipartFile file) {
		if (cpf == null || cpf.isEmpty()) {
			return "Informe o indentificador do procurado";
		}

		if (file.isEmpty()) {
			return "Informe o arquivo";
		}
		return new CadastroProcurado(jdbcTemplate).cadastro(cpf, file);
	}

	@PostMapping("/uploadAndStartMatch")
	public String uploadAndStartMatch(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return "Informe o arquivo";
		}
		// return new CadastroProcurado(jdbcTemplate).cadastro(nome, identificador, file
		// );

		return "Falta implementar a chamanda assincrona ao findface";
	}

	@RequestMapping(value = "/procurado/foto", method = RequestMethod.GET)
	public void getImageAsByteArray(@RequestParam("id") String id, HttpServletResponse response) throws IOException {

		String pathImagem = new CadastroProcurado(jdbcTemplate).buscaImagem(id);

		InputStream in = new FileInputStream(new File(pathImagem));
		// InputStream in =
		// servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		// response.setContentType(MediaType.ima);
		IOUtils.copy(in, response.getOutputStream());
	}
}
