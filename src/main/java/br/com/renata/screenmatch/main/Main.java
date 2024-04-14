package br.com.renata.screenmatch.main;

import br.com.renata.screenmatch.model.DadosEpisodio;
import br.com.renata.screenmatch.model.DadosSerie;
import br.com.renata.screenmatch.model.DadosTemporada;
import br.com.renata.screenmatch.model.Episodio;
import br.com.renata.screenmatch.service.ConsumoAPI;
import br.com.renata.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b317d573";
    private final String TEMPORADA = "&season=";
    private Scanner leitura = new Scanner(System.in); //escaneia a entrada do teclado
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Digite o nome da serie: ");
        var nomeSerie = leitura.nextLine();
        var enderecoCompleto = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        try {
            var json = consumoAPI.obterDados(enderecoCompleto);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= dados.totalTemporadas(); i++) {
                var enderecoCompletoTemporada = ENDERECO + nomeSerie.replace(" ", "+") + TEMPORADA + i + API_KEY;
                json = consumoAPI.obterDados(enderecoCompletoTemporada);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            //temporadas.forEach(t -> t.episodios().forEach(ep -> System.out.println(ep.titulo()))); //lambda

            //colocando todos os dados em uma lista
            List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream())
                    .collect(Collectors.toList()); //coleta pra uma lista
            //.toList() -> dados imutavel, nao pode da um add depois

//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(d -> new Episodio(t.numero(), d))
                    ).collect(Collectors.toList());

            episodios.forEach(System.out::println);

            System.out.println("A partir de que ano quer ve os eps? ");
            var ano = leitura.nextInt();
            leitura.nextLine();

            LocalDate dataBusca = LocalDate.of(ano,1,1);
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            episodios.stream()
                    .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                    .forEach(e -> System.out.println(
                            "Temporada: " + e.getTemporada() +
                                    " Episodio: " + e.getTitulo() +
                                    " Data lancamento: " + e.getDataLancamento().format(formatador)
                    ));

        } catch (NullPointerException ex) {
            System.out.println("Problema na chamada do endpoint: " + ex.getMessage());
        }
    }
}
