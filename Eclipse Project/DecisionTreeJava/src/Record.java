import java.util.ArrayList;


public class Record {
	ArrayList<Attribute> record;
	Record(ArrayList<Attribute> al){
		record = al;
	}
	ArrayList<Attribute> getRecord(){
		return record;
	}
	public String toString(){
		String recordline="";
		for( Attribute at : record){
			recordline+=at.toString()+" ";
		}
		
		return recordline;
	}
}
