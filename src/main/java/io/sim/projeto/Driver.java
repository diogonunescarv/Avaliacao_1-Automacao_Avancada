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
        while (true) {
            Rota nextRoute = getNextRouteToExecute();
            if (nextRoute != null) {
                setCurrentRoute(nextRoute);
                // Executar a rota atual aqui
                // Ao final da execução, você pode mover a rota para o ArrayList de rotas executadas.
                moveRouteToExecuted(currentRoute);

                createTS();
            }  
        }
    }

    public synchronized void createTS(){
        TransportService tS = new TransportService(true, car.getIdCar(), currentRoute, car, sumo);
        tS.start();
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

