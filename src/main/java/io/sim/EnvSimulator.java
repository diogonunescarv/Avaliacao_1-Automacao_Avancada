package io.sim;

import java.io.IOException;
import java.util.ArrayList;

import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.objects.SumoColor;
import de.tudresden.sumo.objects.SumoStringList;
import io.sim.projeto.CarCreator;
import io.sim.projeto.Company;
import io.sim.projeto.DriverCreator;
import io.sim.projeto.Driver;
import it.polito.appeal.traci.SumoTraciConnection;

public class EnvSimulator extends Thread{

    private SumoTraciConnection sumo;

    public EnvSimulator(){

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
			String localHost = "127.0.0.1";
			int portaServidor = 23415;
			int qtdCars = 10;

			ArrayList<Car> carList = CarCreator.createCarList(qtdCars, sumo, localHost, portaServidor);
			ArrayList<Driver> DriverList = DriverCreator.createDrivers(sumo, carList);
			Company company = new Company(23415, "data/dados.xml", sumo, qtdCars, DriverList);
			company.start();
		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

}
