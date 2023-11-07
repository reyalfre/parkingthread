import java.util.Random;
import java.util.concurrent.Semaphore;

public class Coche extends Thread {
    private Semaphore semaphore;
    private int numeroCoche;
    private Boolean expulsado;

    public Coche(Semaphore semaphore, int numeroCoche) {
        this.semaphore = semaphore;
        this.numeroCoche = numeroCoche;
        this.expulsado = false;
    }

    public Boolean isExpulsado() {
        return expulsado;
    }

    public void setExpulsado(Boolean expulsado) {
        this.expulsado = expulsado;
    }

    @Override
    public void run() {
        Random random = new Random();
        int tiempoDeEspera;

        while (true) {
            tiempoDeEspera = random.nextInt(4000) + 1000;
            System.out.println("El coche " + numeroCoche + " recorre la ciudad " + tiempoDeEspera + " ms.");

            try {
                Thread.sleep(tiempoDeEspera);
            } catch (InterruptedException e) {
                System.out.println("El coche " + numeroCoche + " ha sido despertado.");
            }

            // After completing the drive, try to enter the parking lot
            try {
                CentroComercial.incrementarCochesEntrando();
                semaphore.acquire();
                System.out.println("El coche " + numeroCoche + " ha entrado en el parking.");
                System.out.println(CentroComercial.getCochesEntrando()+" coches han entrado en total al parking");
                Thread.sleep(1000); // Simulate the car being parked
                semaphore.release(); // Car leaves the parking lot
                System.out.println("El coche " + numeroCoche + " ha salido del parking.");
            } catch (InterruptedException e) {
                System.out.println("El coche " + numeroCoche + " fue interrumpido mientras esperaba en el parking.");
            }
        }
    }
}
