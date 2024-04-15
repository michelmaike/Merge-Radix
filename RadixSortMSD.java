import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RadixSortMSD{
    public static void main(String[] args){
        int[] dados = lerDadosDoArquivo("C:/Users/miche/Downloads/dados100_mil.txt");
        
        long tempoInicial = System.nanoTime();
        radixSortMSD(dados, 0, dados.length - 1,encontrarMaiorNumeroDeDigitos(dados));
        long tempoGasto = System.nanoTime()- tempoInicial;

        System.out.println("tempo de execuçao do radix sort: "+formatarTempo(tempoGasto));
    }

    public static int[] lerDadosDoArquivo(String caminhoDoArquivo) {
        ArrayList<Integer> lista = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(caminhoDoArquivo)); //PARTE FEITA POR IA
            scanner.useDelimiter("[,\\s\\[\\]]+"); 
            while (scanner.hasNextInt()) {
                lista.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Verifique o caminho.");
            return new int[0];
        }
        return lista.stream().mapToInt(i -> i).toArray();
    }

    public static void radixSortMSD(int[] input,int inicio,int fim,int digito){
        if(digito <=0 || inicio >= fim) return;

        final int base = 10;
        int[] contagem = new int[base+ 1];
        int[] saida = new int[fim - inicio + 1];

        int expoente = (int) Math.pow(base, digito-1);
        for(int i = inicio; i <= fim; i++) {
            int index = (input[i] < 0 ? -input[i] : input[i]) / expoente % base;
            contagem[index + 1]++;
        }

        for(int i = 1; i<contagem.length; i++) {
            contagem[i] += contagem[i-1];
        }

        for(int i = inicio; i <= fim; i++){
            int index = (input[i] < 0 ? -input[i] : input[i]) / expoente % base;
            saida[contagem[index]++] = input[i];
        }

        System.arraycopy(saida,0, input,inicio,saida.length);

        for(int i = 0;i < base;i++){
            radixSortMSD(input, inicio+contagem[i],inicio + contagem[i + 1] - 1, digito- 1);
        }
    }

    public static int encontrarMaiorNumeroDeDigitos(int[] dados){                     //ALGUMAS PARTES FEITAS COM AJUDAS DE IA
        int max = 0;
        for(int i : dados){
            int digito = (int) Math.log10(Math.abs(i) +0.5)+1;
            if(digito > max){
                max = digito;
            }
        }
        return max;
    }

    private static String formatarTempo(long nanosegundos){
        long milissegundos = nanosegundos / 1000000;
        long segundos = (milissegundos / 1000) % 60;
        long minutos = (milissegundos / (1000*60)) % 60;
        long horas = (milissegundos / (1000*60* 60)) % 24;

        return String.format("%02d:%02d:%02d:%02d",horas,minutos,segundos,milissegundos % 1000);
    }
}
