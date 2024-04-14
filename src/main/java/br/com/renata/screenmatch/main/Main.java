package br.com.renata.screenmatch.main;

import br.com.renata.screenmatch.model.DadosEpisodio;
import br.com.renata.screenmatch.model.DadosSerie;
import br.com.renata.screenmatch.model.DadosTemporada;
import br.com.renata.screenmatch.service.ConsumoAPI;
import br.com.renata.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b317d573";
    private final String TEMPORADA = "&season=";
    private Scanner leitura = new Scanner(System.in); //escaneia a entrada do teclado
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    public void exibeMenu(){
        System.out.println("Digite o nome da serie: ");
        var nomeSerie = leitura.nextLine();
        var enderecoCompleto = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        var json = consumoAPI.obterDados(enderecoCompleto);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas(); i++){
            var enderecoCompletoTemporada = ENDERECO + nomeSerie.replace(" ", "+") + TEMPORADA + i +  API_KEY;
			json = consumoAPI.obterDados(enderecoCompletoTemporada);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);
        temporadas.forEach(t -> t.episodios().forEach(ep -> System.out.println(ep.titulo()))); //lambda

    }
}
