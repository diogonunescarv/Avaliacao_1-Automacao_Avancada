package io.sim.projeto;

import de.tudresden.sumo.objects.SumoColor;
import it.polito.appeal.traci.SumoTraciConnection;

import io.sim.Car;

import java.util.ArrayList;
import java.util.Random;

public class CarCreator {

    // Método estático para criar uma lista de objetos Auto com cores aleatórias
    public static ArrayList<Car> createCarList(int numCars, SumoTraciConnection sumo, String companyServerHost, int companyServerPort) {
        ArrayList<Car> carList = new ArrayList<Car>();

        // Defina as características comuns para os novos objetos Auto
        boolean on_off = true;
        long acquisitionRate = 500;
        int fuelType = 2; // Gasolina
        int fuelPreferential = 2; // Gasolina
        double fuelPrice = 3.40;
        int personCapacity = 1;
        int personNumber = 1;

        Random random = new Random();

        for (int i = 0; i < numCars; i++) {
            // Gere uma cor aleatória
            SumoColor randomColor = new SumoColor(
                    random.nextInt(256), // Valor de vermelho entre 0 e 255
                    random.nextInt(256), // Valor de verde entre 0 e 255
                    random.nextInt(256), // Valor de azul entre 0 e 255
                    126 // Valor de alfa (transparência) fixo em 126
            );

            String idCar = "Carro " + (i + 1);
            
            try {
                // Crie um novo objeto Car com características comuns e cor aleatória
                Car car = new Car(on_off, idCar, randomColor, sumo, acquisitionRate, fuelType, fuelPreferential, fuelPrice, personCapacity, personNumber, companyServerHost, companyServerPort);
                
                // Adicione o novo objeto Car à lista
                carList.add(car);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }   
        }

        return carList;
    }
}
