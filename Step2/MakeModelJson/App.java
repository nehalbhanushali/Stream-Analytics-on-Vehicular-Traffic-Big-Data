package neu.stream.bigdata;

import java.util.ArrayList;
import java.util.List;

public class App {
	public static class VehicleSpecies {
		private String make, model;
		private double mpg;

		public VehicleSpecies(String make, String model, double mpg) {
			super();
			this.make = make;
			this.model = model;
			this.mpg = mpg;
		}

		public String getMake() {
			return make;
		}

		public String getModel() {
			return model;
		}

		public double getMpg() {
			return mpg;
		}

		@Override
		public String toString() {
			return    make + " " + model + " " + mpg;
		}

	}

	private static List<VehicleSpecies> vehiclesSpecies = new ArrayList<>();

	static {
		vehiclesSpecies.add(new VehicleSpecies("Honda", "Civic", 38));
		vehiclesSpecies.add(new VehicleSpecies("Hyundai", "Accent", 32));
		vehiclesSpecies.add(new VehicleSpecies("Hyundai", "Accent", 32));
	}

	public static void main(String[] args) {
//		System.out.println(getVehicleSpecies(""));
		
		String list[] = {"CRM3233","CRM32390","CRM3237"};
		String jsonVal = " { ";
		for(int i = 0; i< list.length;i++){
			
			String lineRecord = list[i]+ " " + getVehicleSpecies(list[i]);	
			String[] stringArray = lineRecord.split(" ");
		    String titles[] = {"LicensePlate","Make","Model","mpg"};
		    if(titles.length == stringArray.length){
		    	jsonVal += " { ";
		    	for (int j = 0; j < stringArray.length; j++) {
				       
				       if(j != stringArray.length-1){
				    	   jsonVal += "  \""+ titles[j]+ "\" : \""+stringArray[j]+"\" , ";
	
				       }else{
				    	   jsonVal += "  \""+ titles[j]+ "\" : "+stringArray[j]+"  ";
				       }
				        
				      }
		    	if( i == list.length-1 ){
		    		jsonVal += " } ";	
		    	}else{
		    		jsonVal += " }, ";	
		    	}
		        
		    	
		    }
		    
			
		}
		System.out.println(jsonVal);
	
		
		
		
		
	}

	/**
	 * @param licensePlateNumber
	 * @return
	 */
	private static VehicleSpecies getVehicleSpecies(String licensePlateNumber) {
		int bucket = licensePlateNumber.hashCode() % vehiclesSpecies.size();
		VehicleSpecies species = vehiclesSpecies.get(bucket);
		return species;
	}
}