import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class MergeSort {
    public static void main(String[] args){
        int[] dados = lerDadosDoArquivo("C:/Users/miche/Downloads/dados100_mil.txt");
        long tempoInicial = System.nanoTime();

        ordenarMerge(dados,0,dados.length-1);

        long tempoGasto = System.nanoTime()-tempoInicial;
        System.out.println("tempo de execuçao do Merge Sort: "+formatarTempo(tempoGasto));
    }

    public static int[] lerDadosDoArquivo(String caminhoDoArquivo) { //PARTE FEITA COM IA, na força bruta é possível mas
        ArrayList<Integer> lista = new ArrayList<>();                //o vscode se transforma em uma carroça
        try {
            Scanner scanner = new Scanner(new File(caminhoDoArquivo));
            scanner.useDelimiter("[,\\s\\[\\]]+");

            while (scanner.hasNextInt()) {
                lista.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Por favor, verifique o caminho.");
            return new int[0]; 
        }

        // Converte ArrayList para array de int
        return lista.stream().mapToInt(i -> i).toArray();
    }

    public static void ordenarMerge(int[] array, int esquerda, int direita){
        if(esquerda<direita){
            int meio = esquerda + (direita-esquerda) / 2;
            ordenarMerge(array, esquerda, meio);
            ordenarMerge(array, meio + 1, direita);
            juntar(array,esquerda, meio, direita); 
        }
    }

    private static void juntar(int[] array,int esquerda,int meio,int direita){
        int tamanhoEsquerda = meio- esquerda + 1;
        int tamanhoDireita = direita-meio;
        int[] esq = new int[tamanhoEsquerda];
        int[] dir = new int[tamanhoDireita];

        for(int i = 0; i<tamanhoEsquerda; ++i){
            esq[i] = array[esquerda + i];
        }
        for(int j = 0; j<tamanhoDireita; ++j){
            dir[j] = array[meio + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = esquerda;
        while(i<tamanhoEsquerda && j<tamanhoDireita){
            if(esq[i] <= dir[j]){
                array[k] = esq[i];
                i++;
            }else{
                array[k] = dir[j];
                j++;
            }
            k++;
        }

        while(i<tamanhoEsquerda){
            array[k] = esq[i];
            i++;
            k++;
        }
        while(j<tamanhoDireita){
            array[k] = dir[j];
            j++;
            k++;
        }
    }

    private static String formatarTempo(long nanosegundos){
        long milissegundo = nanosegundos / 1000000;
        long segundo = (milissegundo / 1000)% 60;
        long minuto = (milissegundo / (1000*60))% 60;
        long hora = (milissegundo / (1000*60*60))%24;

        return String.format("%02d:%02d:%02d:%02d",hora,minuto,segundo,milissegundo % 1000);
    }
}
