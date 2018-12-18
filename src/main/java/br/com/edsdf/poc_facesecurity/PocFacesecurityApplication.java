package br.com.edsdf.poc_facesecurity;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@EnableAutoConfiguration
public class PocFacesecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocFacesecurityApplication.class, args);
	}

	@RequestMapping("/json")
	String home() {
		return "ola";
	}
	
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

    	System.out.println(model);
    	
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));

        return "uploadForm";
    }
    
    
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("parameter1") MultipartFile parameter1,
            RedirectAttributes redirectAttributes) {

//        storageService.store(file);
        
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}

