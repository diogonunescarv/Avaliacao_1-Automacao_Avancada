package io.sim.projeto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CarManipulator extends Thread {
    private Socket carSocket;
    private DataInputStream input;
    private DataOutputStream output;

    public CarManipulator(Socket carSocket) {
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
        //try {
            // Realize a comunicação específica do carro aqui
            // Exemplo: Envie comandos para o carro e leia dados dele
            while (true) {
                // Fazendo algo
            }
       // } //catch (IOException e) {
           // e.printStackTrace();
       // } finally {
            // Lide com o fechamento da conexão e liberação de recursos
            // try {
            //     input.close();
            //     output.close();
            //     carSocket.close();
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
        //}
    }
}
