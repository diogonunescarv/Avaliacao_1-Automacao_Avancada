package io.sim.projeto;

import io.sim.Car;
import io.sim.TransportService;

import it.polito.appeal.traci.SumoTraciConnection;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Driver extends Thread {
    private SumoTraciConnection sumo;
    
    private String ID;
    private ArrayList<Rota> routesToExecute; // Rotas a serem executadas
    private ArrayList<Rota> executedRoutes;   // Rotas já executadas
    private Rota currentRoute;                // Rota em execução
    private Car car;                           // Veículo associado ao motorista
    //private AlphaBankClient alphaBankClient;   // Cliente para interagir com o AlphaBank
    //private BotPayment botPayment;             // Thread para pagamento à Fuel Station
    private ReentrantLock routesLock;          // Lock para acesso seguro aos ArrayLists de rotas

    public Driver(SumoTraciConnection sumo, String _ID, Car car) {
        this.sumo = sumo;
        this.ID = _ID;
        this.car = car;
        //this.alphaBankClient = alphaBankClient;
        this.routesToExecute = new ArrayList<>();
        this.executedRoutes = new ArrayList<>();
        //this.botPayment = new BotPayment(car, alphaBankClient);
        this.routesLock = new ReentrantLock();
    }

    private void startarCarro(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Thread t = new Thread(car);
		t.start();
    }

    public String getID() {
        return ID;
    }

    public void addRouteToExecute(Rota route) {
        routesLock.lock();
        try {
            routesToExecute.add(route);
        } finally {
            routesLock.unlock();
        }
    }

    public Rota getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Rota route) {
        currentRoute = route;
    }

    public void run() {
        startarCarro();
        conectarCarro();

        if (mandarID()) {
            while (true) {
                Rota nextRoute = getNextRouteToExecute();
                if (nextRoute != null) {
                    setCurrentRoute(nextRoute);
                    // Executar a rota atual aqui
                    // Ao final da execução, você pode mover a rota para o ArrayList de rotas executadas.
                    moveRouteToExecuted(currentRoute);
                    
                    synchronized (Driver.class) {
                        TransportService tS = new TransportService(true, car.getIdCar(), currentRoute, car, sumo);
                        tS.start();
                    }

                    // try {
                    //     Thread.sleep(5000);
                    // } catch (InterruptedException e) {
                    //     // TODO Auto-generated catch block
                    //     e.printStackTrace();
                    // }
                    
                }

                // if (!car.isOn_off()) {
                //     pedirRota();
                // }
            }
        }
        
    }

    private void conectarCarro() {
        car.conectar();
    }

    private void pedirRota() {
        car.solicitarRota();
    }
    
    private boolean mandarID() {
        return car.mandaID();
    }

    private Rota getNextRouteToExecute() {
        routesLock.lock();
        try {
            if (!routesToExecute.isEmpty()) {
                return routesToExecute.remove(0);
            }
        } finally {
            routesLock.unlock();
        }
        return null;
    }

    private void moveRouteToExecuted(Rota route) {
        routesLock.lock();
        try {
            executedRoutes.add(route);
        } finally {
            routesLock.unlock();
        }
    }

    public ArrayList<Rota> getExecutedRoutes() {
        return executedRoutes;
    }

    public Car getCar() {
        return car;
    }

    // public AlphaBankClient getAlphaBankClient() {
    //     return alphaBankClient;
    // }

    // public BotPayment getBotPayment() {
    //     return botPayment;
    // }
}

