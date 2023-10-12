package io.sim.projeto;

import io.sim.Car;
import io.sim.projeto.Rota;
import io.sim.projeto.Driver;
import it.polito.appeal.traci.SumoTraciConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Company extends Thread {

    private static ServerSocket carServerSocket;
    //private Socket alphaBankSocket;

    private int qtdCars;
    private ArrayList<Driver> motoristas;

    private ArrayList<Rota> rotas;
    private ArrayList<Rota> rotasEmExec;
    private ArrayList<Rota> rotasConc;

    public Company(int carServerPort, String xmlRotasPath, SumoTraciConnection sumo, int qtdCars, ArrayList<Driver> listDrivers) {
        try {
            // Inicializa o servidor para carros
            carServerSocket = new ServerSocket(carServerPort);
            System.out.println("Mobility Company: Servidor de Carros iniciado na porta " + carServerPort);

            // Conecta-se ao AlphaBank como cliente
            //alphaBankSocket = new Socket(alphaBankHost, alphaBankPort);
            //System.out.println("Mobility Company: Conectado ao AlphaBank em " + alphaBankHost + ":" + alphaBankPort);

            this.motoristas = listDrivers;
            // Inicializa a lista de rotas
            this.rotas = RouteExtractor.createRoutesFromXML(xmlRotasPath);
            this.qtdCars = qtdCars;
            rotasEmExec = new ArrayList<Rota>();
            rotasConc = new ArrayList<Rota>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int contador = 0;
        try {
            while (contador < qtdCars) {
                // Aceita conexões de carros como um servidor
                Socket carSocket = carServerSocket.accept();
                contador++;
                System.out.println("Mobility Company: Carro conectado de " + carSocket.getInetAddress());

                // Crie uma thread para lidar com cada carro
                CarManipulator carManipulator = new CarManipulator(carSocket, this);
                carManipulator.start();
            }
            System.out.println("Conectaram todos os carros!!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void executarNovaRota(String driverID){
        int i = 0;
        while (!motoristas.get(i).getID().equals(driverID)){
            i++;
        }
        Rota novaRota = rotas.remove(0);
        rotasEmExec.add(novaRota);
        motoristas.get(i).addRouteToExecute(novaRota);
    }

    public synchronized void terminaRota(String driverID) {
        int i = 0;
        while (!motoristas.get(i).getID().equals(driverID)){
            i++;
        }
        String idRota = motoristas.get(i).getCurrentRoute().getID();

        i = 0;
        while (!rotasEmExec.get(i).getID().equals(idRota)) {
            i++;
        }
        rotasConc.add(rotasEmExec.remove(i));
    }
}
