package io.sim;

import io.sim.projeto.Rota;

import java.util.ArrayList;

import org.python.modules.synchronize;

import de.tudresden.sumo.cmd.Route;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.objects.SumoStringList;
import it.polito.appeal.traci.SumoTraciConnection;

public class TransportService extends Thread {

	private String idTransportService;
	private boolean on_off;
	private SumoTraciConnection sumo;
	private Car car;
	private Rota rota;

	public TransportService(boolean _on_off, String _idTransportService, Rota _rota, Car _car, SumoTraciConnection _sumo) {

		this.on_off = _on_off;
		this.idTransportService = _idTransportService;
		this.rota = _rota;
		this.car = _car;
		this.sumo = _sumo;
	}

	@Override
	public void run() {
		try {
			
			this.initializeRoutes();

			Thread.sleep(1000);

			car.setOn_off(true);

			//while (true) {
				SumoStringList listaCarros;
				while (this.on_off) {
					try {
						this.sumo.do_timestep();
						listaCarros = (SumoStringList) sumo.do_job_get(Vehicle.getIDList());
						if (!listaCarros.contains(car.getIdCar())) {
							System.out.println(car.getIdCar() + ": terminou a rota");
							car.setOn_off(false);
							this.on_off = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					Thread.sleep(this.car.getAcquisitionRate());
					if (this.getSumo().isClosed()) {
						this.on_off = false;
						System.out.println("SUMO is closed...");
					}
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	public synchronized void initializeRoutes() {

		SumoStringList edge = new SumoStringList();
		edge.clear();
		String aux = this.rota.getEdges();

		for (String e : aux.split(" ")) {
			edge.add(e);
		}

		try {
			sumo.do_job_set(Route.add(this.rota.getID(), edge));
			//sumo.do_job_set(Vehicle.add(this.auto.getIdAuto(), "DEFAULT_VEHTYPE", this.itinerary.getIdItinerary(), 0,
			//		0.0, 0, (byte) 0));

			sumo.do_job_set(Vehicle.addFull(this.car.getIdCar(), 				//vehID
											this.rota.getID(), 	                //routeID 
											"DEFAULT_VEHTYPE", 					//typeID 
											"now", 								//depart  
											"0", 								//departLane 
											"0", 								//departPos 
											"0",								//departSpeed
											"current",							//arrivalLane 
											"max",								//arrivalPos 
											"current",							//arrivalSpeed 
											"",									//fromTaz 
											"",									//toTaz 
											"", 								//line 
											this.car.getPersonCapacity(),		//personCapacity 
											this.car.getPersonNumber())		//personNumber
					);
			
			sumo.do_job_set(Vehicle.setColor(this.car.getIdCar(), this.car.getColorAuto()));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void setNewRoute(Rota novaRota) {
		this.rota = novaRota;
	}

	public boolean isOn_off() {
		return on_off;
	}

	public void setOn_off(boolean _on_off) {
		this.on_off = _on_off;
	}

	public String getIdTransportService() {
		return this.idTransportService;
	}

	public SumoTraciConnection getSumo() {
		return this.sumo;
	}

	public Car getAuto() {
		return this.car;
	}

	public Rota getRota() {
		return this.rota;
	}
}