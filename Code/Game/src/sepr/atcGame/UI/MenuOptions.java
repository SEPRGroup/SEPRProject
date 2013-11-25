package sepr.atcGame.UI;

public enum MenuOptions {
	PLAY ("Play"), //0
	CHOOSEAIRPORT ("Choose Airport"),//1
	SELECTDIFFICULTY ("Select Difficulty"),//2
	EXIT ("Exit"),//3
	EASY ("Easy"),//4
	MEDIUM ("Medium"),//5
	HARD ("Hard"),//6
	AIRPORT1 ("Airport 1"),//7
	AIRPORT2 ("Airport 2"),//8
	BACK ("Back");//9
	
	private final String labelText;
	MenuOptions(String text) {
		this.labelText = text;
	}
	
	public String labelText() { return labelText; }
		
}
