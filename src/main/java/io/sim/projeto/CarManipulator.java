package io.sim.projeto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import io.sim.TransportService;

public class CarManipulator extends Thread {
    private Socket carSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private Company company;

    public CarManipulator(Socket carSocket, Company c) {
        this.company = c;
        this.carSocket = carSocket;
        try {
            input = new DataInputStream(carSocket.getInputStream());
            output = new DataOutputStream(carSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(carSocket.getInputStream());
            String driverID = dis.readUTF(); // Lê a tal mensagem
            company.executarNovaRota(driverID);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        while (true) {
            


            
        }
       
    }
}
