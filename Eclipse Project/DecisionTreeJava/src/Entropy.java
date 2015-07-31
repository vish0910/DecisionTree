import java.util.ArrayList;


/**
 * @author Vishal Doshi
 *
 */
public class Entropy {
	//Calculate entropy
	public static double calculateEntropy(ArrayList<Record> recordsList){
		double entropy = 0.0;
		double rlsize=(double)recordsList.size();
		if(rlsize==0){
			return entropy;
		}
		int yesCount=0;
		int noCount = 0;
		for(Record record : recordsList){
			ArrayList<Attribute> al = record.getRecord();
			if(al.get(al.size()-1).getValue().equals("yes")){
				yesCount++;
			}
			else{
				noCount++;
			}
		}
		double py = yesCount/rlsize;
		double pn = noCount/rlsize;
		if(py!=0.0)
			entropy+=(-py) * (Math.log(py) / Math.log(2));
		if(pn!=0.0)
			entropy+=(-pn) * (Math.log(pn) / Math.log(2));

		System.out.println("Yes:"+ yesCount+"\nNo:"+noCount);
		System.out.println("Entropy:"+entropy);
		return entropy;
	}
}
