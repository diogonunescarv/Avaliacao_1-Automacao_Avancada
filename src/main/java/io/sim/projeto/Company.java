package io.sim.projeto;

import io.sim.Car;
import io.sim.projeto.Rota;
import it.polito.appeal.traci.SumoTraciConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Company extends Thread {

    private static ServerSocket carServerSocket;
    //private Socket alphaBankSocket;

    private int qtdCars;

    private ArrayList<Rota> rotas;
    private ArrayList<Rota> rotasEmExec;
    private ArrayList<Rota> rotasConc;

    public Company(int carServerPort, String xmlRotasPath, SumoTraciConnection sumo, int qtdCars) {
        try {
            // Inicializa o servidor para carros
            carServerSocket = new ServerSocket(carServerPort);
            System.out.println("Mobility Company: Servidor de Carros iniciado na porta " + carServerPort);

            // Conecta-se ao AlphaBank como cliente
            //alphaBankSocket = new Socket(alphaBankHost, alphaBankPort);
            //System.out.println("Mobility Company: Conectado ao AlphaBank em " + alphaBankHost + ":" + alphaBankPort);

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
            while (contador < 100) {
                // Aceita conexões de carros como um servidor
                Socket carSocket = carServerSocket.accept();
                contador++;
                System.out.println("Mobility Company: Carro conectado de " + carSocket.getInetAddress());

                // Crie uma thread para lidar com cada carro
                //CarManipulator carManipulator = new CarManipulator(carSocket);
                //carManipulator.start();
            }
            System.out.println("Conectaram todos os carros!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
