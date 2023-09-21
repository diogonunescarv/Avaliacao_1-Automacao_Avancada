package io.sim.company;

import io.sim.company.Rota;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Company extends Thread {
    
    // private static ArrayList<BufferedWriter> carros;
    // private static ServerSocket server;
    private static ArrayList<Rota> listaRotas;
    private static ArrayList<Rota> rotasExecutando;
    private static ArrayList<Rota> rotasConcluidas;
    // private String nome;
    // private Socket con;
    // private InputStream in;
    // private InputStreamReader inr;
    // private BufferedReader bfr;

    public Company(ArrayList<Rota> listaRotas) {
        //this.con = con;
        this.listaRotas = listaRotas;
        // try {
        //     in  = con.getInputStream();
        //     inr = new InputStreamReader(in);
        //     bfr = new BufferedReader(inr);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
    
    @Override
    public void run() {
        System.out.println("Número total de Rotas: " + listaRotas.size());
        for (int i = 0; i < listaRotas.size(); i++) {
            System.out.println("ID: " + listaRotas.get(i).getID());
            System.out.println("Edges: " + listaRotas.get(i).getEdges());
        }
    }
}
