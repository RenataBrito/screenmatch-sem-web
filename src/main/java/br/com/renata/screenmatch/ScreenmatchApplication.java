package br.com.renata.screenmatch;

import br.com.renata.screenmatch.main.Main;
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
//		System.out.println(json);
//		json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json"); //ele le outros jsons
//		System.out.println(json);

		Main main = new Main();
		main.exibeMenu();

	}
}
