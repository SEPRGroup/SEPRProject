package sepr.atcGame;

import java.util.Queue;

public enum GeneratedAircrafts {
	AIRCRAFT1 ("Aircraft1"),
	ARICRAFT2 ("Aircraft2"),
	ARICRAFT3 ("Aircraft3"),
	AIRCRAFT4 ("Aircraft4"),
	AIRCRAFT5 ("Aircraft5");
	
	
	private String name;
	GeneratedAircrafts(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
