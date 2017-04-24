// Solution - As discussed by the fellow classmate in class
// modified by nehal

package U.CC;

 import java.io.IOException;
 import java.util.Iterator;

 import org.apache.hadoop.io.IntWritable;
 import org.apache.hadoop.io.Text;
 import org.apache.hadoop.io.WritableComparable;
 import org.apache.hadoop.mapred.MapReduceBase;
 import org.apache.hadoop.mapred.OutputCollector;
 import org.apache.hadoop.mapred.Reducer;
 import org.apache.hadoop.mapred.Reporter;
 import java.lang.StringBuilder;
 import java.util.*;
 
 
 
 //reading from csv imports
import java.io.BufferedReader; 
import java.io.IOException; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.Files; 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList; 
import java.util.List;



 public class LicenseReducer1 extends MapReduceBase implements Reducer<Text, Text, Text, Text>

{
   	private static String my_cwd = "/home/rutika/CAVtags.txt";

	//private static String my_cwd =  "/home/CAVtags.txt";

   private static List<VehicleSpecies> vehiclesSpeciesList = readModelsFromCSV(my_cwd);

   public void reduce(Text key, Iterator<Text> values,
                      OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

     String plate_number = key.toString();
	 //System.out.println(">>>>>>>>>>>>>>>> "+plate_number);

	 
	 String finalOutput = "{ \"LicensePlate\" : \""+plate_number+"\" , "+getVehicleSpecies(plate_number)+" },";
	 
	 
	 //System.out.println(finalOutput);
	 
	 output.collect(new Text(finalOutput) ,new Text(""));

  }
  
  public static class VehicleSpecies {
	    private int year;
		private String manufacturer, makeAndModel, exhaustStandard, fuelType, decalColour;
		//private double mpg;

		public VehicleSpecies(int year, String manufacturer,String makeAndModel, String exhaustStandard ,String fuelType ,String decalColour) {
			super();
			
			this.year = year;
			this.manufacturer = manufacturer;
			this.makeAndModel = makeAndModel;
			this.exhaustStandard = exhaustStandard;
			this.fuelType = fuelType;
			this.decalColour = decalColour;
			//this.mpg = mpg;
		}

		public int getYear() {
			return year;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public String getMakeAndModel() {
			return makeAndModel;
		}
		
		public String getExhaustStandard() {
			return exhaustStandard;
		}
		
		public String getFuelType() {
			return fuelType;
		}
		
		public String getDecalColour() {
			return decalColour;
		}

		@Override
		public String toString() {
			return   "\"Year\" : \""+ year + "\" , \"Manufacturer\" : \"" + manufacturer + "\" , \"MakeAndModel\" : \""+ makeAndModel + "\" , \"FuelType\" : \""+ fuelType + "\"";
		}

	}
  
 
  private static List<VehicleSpecies> readModelsFromCSV(String fileName) {
	  System.out.println("in readModelsFromCSV fileName >>>> "+fileName);
	  List<VehicleSpecies> vehicles = new ArrayList<>(); 
	  Path pathToFile = Paths.get(fileName); 
	  // create an instance of BufferedReader 
	  // using try with resource, Java 7 feature to close resources 
	  try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) 
	 //try (BufferedReader br = Files.newBufferedReader(pathToFile, Charset.forName("UTF-8")) 
	  { 
	  // read the first line from the text file 
	  String line = br.readLine();
	  // loop until all lines are read 
	  //System.out.println("in try readModelCSV line >>>> "+line);
	  while (line != null) { 
	  // use string.split to load a string array with the values from 
	  // each line of 
	  // the file, using a comma as the delimiter 
	  String[] attributes = line.split(",");
	  //System.out.println("in try readModelCSV while attributes[0] >>>> "+attributes[0]);
	  VehicleSpecies vehicleSpecies = createVehicleSpecies(attributes); 
	  // adding book into ArrayList 
	  vehicles.add(vehicleSpecies); 
	  // read next line before looping 
	  // if end of file reached, line would be null 
	  line = br.readLine(); 
	  }
	  } 
	
      catch (Exception e) {
		  System.out.println(" Exception >> "+e);
		  e.printStackTrace(); 
		  } 

        //catch (MalformedInputException mie) {
		//  System.out.println(" MalformedInputException >> "+mie);
		 // mie.printStackTrace(); 
		 // }		  
    return vehicles; 
    }
	
	
	private static VehicleSpecies createVehicleSpecies(String[] metadata) {
		
		int year = Integer.parseInt(metadata[0]); 
		String manufacturer = metadata[1] ;
		String makeAndModel= metadata[2] ;
		String exhaustStandard= metadata[3] ;
		String fuelType= metadata[4] ;
		String decalColour= metadata[5] ;
		// create and return book of this metadata 
		return new VehicleSpecies(year, manufacturer, makeAndModel, exhaustStandard ,fuelType , decalColour); 
		}
	
	
	/**
	 * @param licensePlateNumber
	 * @return
	 */
	private static VehicleSpecies getVehicleSpecies(String licensePlateNumber) {
		// gwt same make model for the given licenseplate if repeats
		System.out.println("in method getVehicleSpecies  licensePlateNumber >>> "+licensePlateNumber+" div by size >> "+vehiclesSpeciesList.size());
		try{
	    // This i.e abs(hash) could make say, 231 and -231 same, but let's see	
		int bucket = Math.abs(licensePlateNumber.hashCode()) % vehiclesSpeciesList.size();
		VehicleSpecies species = vehiclesSpeciesList.get(bucket);
		System.out.println("Success >> "+licensePlateNumber.hashCode()+" >>> "+licensePlateNumber);
		return species;
			
		}catch(Exception e){
			System.out.println("Exception >> "+e+" L P N >>> "+licensePlateNumber.hashCode()+"  >>> "+licensePlateNumber);
			
			
		}	
	return null;	
	}
  
 }
