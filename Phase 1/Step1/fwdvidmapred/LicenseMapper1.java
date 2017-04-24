
// Solution - As discussed by the fellow classmate in class
// modified by nehal

package U.CC;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.util.*;
import java.lang.StringBuilder;

 /*
  * @nehal
  *
  *
  */
 public class LicenseMapper1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

   public void map(LongWritable key, Text value,
                   OutputCollector output, Reporter reporter) throws IOException
{
     // Prepare the input data.
    String license = value.toString();
    //System.out.println("< "+key.toString()+" > "+"< "+license+" >");
    String licenseMap[] = license.split(" ");
    //System.out.println("length >>"+licenseMap.length);
    // we are not interested in people who have no friends at all
    if(licenseMap.length == 4){

    String plate_number = licenseMap[1].trim(); // plate numbers apparantly have trailing spaces
    String confidence = licenseMap[3];
	
	output.collect(new Text(plate_number), new Text(confidence));

   // int indexOfcrime = friend.indexOf("#");
    // emmit reverse friendship only if part2 is a person and not a crime
	/*
    if(indexOfcrime == -1){
      output.collect(new Text(person), new Text(friend));
      output.collect(new Text(friend), new Text(person));
    }else{
      output.collect(new Text(person), new Text("list"));
    }
*/

}else{
  System.out.println("< "+key.toString()+" > "+"< "+license+" >");
  return;
}

   }

 }
