package io.sim;

import java.io.IOException;
import java.util.ArrayList;

import de.tudresden.sumo.objects.SumoColor;
import io.sim.projeto.Driver;
import io.sim.projeto.Rota;
import io.sim.projeto.RouteExtractor;
import it.polito.appeal.traci.SumoTraciConnection;

public class EnvSimulator3 {
    private SumoTraciConnection sumo;

    public EnvSimulator3(){

    }

    public void run(){

		/* SUMO */
		String sumo_bin = "sumo-gui";		
		String config_file = "map/map.sumo.cfg";
		
		// Sumo connection
		this.sumo = new SumoTraciConnection(sumo_bin, config_file);
		sumo.addOption("start", "1"); // auto-run on GUI show
		//sumo.addOption("quit-on-end", "1"); // auto-close on end

		try {
			sumo.runServer(12345);

			ArrayList<Rota> rotas = RouteExtractor.createRoutesFromXML("data/dados2.xml");
			System.out.println(rotas.size());
			//if (i1.isOn()) {
				// fuelType: 1-diesel, 2-gasoline, 3-ethanol, 4-hybrid
				int fuelType = 2;
			 	int fuelPreferential = 2;
			 	double fuelPrice = 3.40;
			 	int personCapacity = 1;
			 	int personNumber = 1;
			 	SumoColor green = new SumoColor(0, 255, 0, 126);
			 	Car c1 = new Car(true, "CAR1", green,"D1", sumo, 500, fuelType, fuelPreferential, fuelPrice, personCapacity, personNumber);
			 	Driver d1 = new Driver(sumo, "D1", c1);
				TransportService tS1 = new TransportService(true, "CAR1", rotas.remove(0), c1, sumo);

			 	tS1.initializeRoutes();

				tS1.start();
                Thread.sleep(5000);
				Thread t = new Thread(c1);
				t.start();
			//}

		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
