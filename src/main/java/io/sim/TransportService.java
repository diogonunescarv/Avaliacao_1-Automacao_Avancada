package io.sim;

import de.tudresden.sumo.cmd.Route;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.objects.SumoStringList;
import it.polito.appeal.traci.SumoTraciConnection;

public class TransportService extends Thread {

	private String idTransportService;
	private boolean on_off;
	private SumoTraciConnection sumo;
	private Car car;
	private Itinerary itinerary;

	public TransportService(boolean _on_off, String _idTransportService, Itinerary _itinerary, Car _car,
			SumoTraciConnection _sumo) {

		this.on_off = _on_off;
		this.idTransportService = _idTransportService;
		this.itinerary = _itinerary;
		this.car = _car;
		this.sumo = _sumo;
	}

	@Override
	public void run() {
		try {
			
			this.initializeRoutes();

			this.car.start();

			while (this.on_off) {
				try {
					this.sumo.do_timestep();
				} catch (Exception e) {
				}
				Thread.sleep(this.car.getAcquisitionRate());
				if (this.getSumo().isClosed()) {
					this.on_off = false;
					System.out.println("SUMO is closed...");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initializeRoutes() {

		SumoStringList edge = new SumoStringList();
		edge.clear();
		String[] aux = this.itinerary.getItinerary();

		for (String e : aux[1].split(" ")) {
			edge.add(e);
		}

		System.out.println(edge);

		try {
			sumo.do_job_set(Route.add(this.itinerary.getIdItinerary(), edge));
			//sumo.do_job_set(Vehicle.add(this.auto.getIdAuto(), "DEFAULT_VEHTYPE", this.itinerary.getIdItinerary(), 0,
			//		0.0, 0, (byte) 0));
			
			sumo.do_job_set(Vehicle.addFull(this.car.getIdCar(), 				//vehID
											this.itinerary.getIdItinerary(), 	//routeID 
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

	public Itinerary getItinerary() {
		return this.itinerary;
	}
}