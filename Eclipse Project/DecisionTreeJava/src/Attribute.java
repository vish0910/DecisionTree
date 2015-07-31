
/**
 * @author Vishal Doshi
 *
 */
public class Attribute {
	private String name;
	private String value;
	static enum names{
		outlook,
		temperature,
		humidity,
		wind,
		playtennis
	};
	static enum outlook{
		sunny,
		overcast,
		rain
	};
	static enum temperature{
		hot,
		mild,
		cool
	};
	static enum humidity{
		high,
		normal
	};
	static enum wind{
		weak,
		strong
	};
	static enum playtennis{
		no,
		yes
	}
	Attribute(int name, String value){
		this.name = names.values()[name].toString();
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return ""+name+":"+value;
	}
	

}
