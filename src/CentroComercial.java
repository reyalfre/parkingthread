import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class CentroComercial {
    public static final int NUMERO_PLAZAS_PARKING = 100;
    public static final int NUMERO_COCHES = 10;
    private static int cochesEntrando = 0; //Contador de coches que entran al parking
    public static void main(String[] args) {
        Semaphore semaforoEstacionamiento = new Semaphore(NUMERO_PLAZAS_PARKING,true);

        List<Coche> coches = new ArrayList<>();
        for (int i =1; i<=NUMERO_COCHES; i++){
            Coche coche=new Coche(semaforoEstacionamiento, i);
            coche.setPriority(i);
            coche.start();
            coches.add(coche);
        }
        Scanner scanner = new Scanner(System.in);

        while (coches.stream().anyMatch(coche -> !coche.isExpulsado())){
            System.out.println("ESCRIBE NUMERO DE COCHE A EXPULSAR");
            int numeroCocheAMatar = scanner.nextInt();
            for (Coche coche: coches){
                if (coche.isExpulsado()){
                    continue;
                }
                if (coche.getPriority()== numeroCocheAMatar){
                    coche.interrupt();
                    coche.setExpulsado(true);
                    break;
                }
            }
        }
    }
    public static synchronized void incrementarCochesEntrando() {
        cochesEntrando++;
    }

    public static synchronized void decrementarCochesEntrando() {
        cochesEntrando--;
    }

    public static synchronized int getCochesEntrando() {
        return cochesEntrando;
    }
}
