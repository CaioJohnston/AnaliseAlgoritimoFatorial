import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FatorialCSV {
    public static int calcularFatorialRecursivo(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * calcularFatorialRecursivo(n - 1);
        }
    }

    public static int calcularFatorialIterativo(int n) {
        int resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<String[]> linhasCSV = new ArrayList<>();
        int valorInicial = 10;
        int valorAtual = valorInicial;
        boolean erroEncontrado = false;

        while (!erroEncontrado) {
            int fatorialRecursivo;
            int fatorialIterativo;
            String erroRecursivo = "Nenhum erro";
            String erroIterativo = "Nenhum erro";
            double tempoRecursivoSegundos = 0.0;
            double tempoIterativoSegundos = 0.0;

            // 1.2 - Qual dos algoritmos falha primeiro (erro de memória) esse comportamento é constante?
            long startTimeRecursivo = System.nanoTime();
            try {
                fatorialRecursivo = calcularFatorialRecursivo(valorAtual);
            } catch (StackOverflowError e) {
                erroRecursivo = "Erro de memória";
                erroEncontrado = true;
            }
            long endTimeRecursivo = System.nanoTime();

            long startTimeIterativo = System.nanoTime();
            try {
                fatorialIterativo = calcularFatorialIterativo(valorAtual);
            } catch (OutOfMemoryError e) {
                erroIterativo = "Erro de memória";
                erroEncontrado = true;
            }
            long endTimeIterativo = System.nanoTime();

            tempoRecursivoSegundos = (endTimeRecursivo - startTimeRecursivo) / 1e9;
            tempoIterativoSegundos = (endTimeIterativo - startTimeIterativo) / 1e9;

            String[] linha = {
                Integer.toString(valorAtual),
                String.format("%.9f", tempoRecursivoSegundos),
                String.format("%.9f", tempoIterativoSegundos),
                erroRecursivo,
                erroIterativo
            };

            linhasCSV.add(linha);

            valorAtual *= 10; // aumentando o valor para o próximo teste
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("resultados.csv"))) {
            String[] cabecalho = {"Valor", "Tempo Recursivo (s)", "Tempo Iterativo (s)", "Erro Recursivo", "Erro Iterativo"};
            writer.writeNext(cabecalho);
            writer.writeAll(linhasCSV);
            System.out.println("Resultados escritos em 'resultados.csv'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
