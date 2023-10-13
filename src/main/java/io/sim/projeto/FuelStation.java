package io.sim.projeto;

import java.util.concurrent.Semaphore;

import io.sim.Car;

public class FuelStation extends Thread {
    private Semaphore fuelPumps;
    //private AlphaBankClient alphaBankClient;

    public FuelStation() {
        this.fuelPumps = new Semaphore(2); // Duas bombas de combustível disponíveis
        //this.alphaBankClient = alphaBankClient;
    }

    public void refuelCar(Car car) throws Exception {
        try {
            fuelPumps.acquire(); // Tenta adquirir uma bomba de combustível
            System.out.println("Car " + car.getIdCar() + " is refueling at Fuel Station.");
            car.abastecendo();
            Thread.sleep(12000); // Tempo de abastecimento de 2 minutos (em milissegundos)
            car.fillFuelTank();
            System.out.println("Car " + car.getIdCar() + " finished refueling.");
            fuelPumps.release(); // Libera a bomba de combustível
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Implemente a lógica para a Fuel Station realizar transações com o AlphaBank
        // para receber pagamentos dos Drivers.
        // Você pode usar o alphaBankClient para se comunicar com o AlphaBank.
    }
}
