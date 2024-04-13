package br.com.renata.screenmatch;

import br.com.renata.screenmatch.model.DadosSerie;
import br.com.renata.screenmatch.service.ConsumoAPI;
import br.com.renata.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args); //vai chamar o run de baixo ele acaba sendo o metodo main
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=the+office&apikey=b317d573");
//		System.out.println(json);
//		json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
//		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados); //vai printar do jeito que é o toString do record pq o DadosSerie.class é record

	}
}
