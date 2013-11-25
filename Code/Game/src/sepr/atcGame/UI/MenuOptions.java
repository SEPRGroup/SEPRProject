package sepr.atcGame.UI;

public enum MenuOptions {
	PLAY ("Play"),
	CHOOSEAIRPORT ("Choose Airport"),
	SELECTDIFFICULTY ("Select Difficulty"),
	EXIT ("Exit"),
	EASY ("Easy"),
	MEDIUM ("Medium"),
	HARD ("Hard"),
	BACK ("Back");

	private final String labelText;
	MenuOptions(String text) {
		this.labelText = text;
	}
	
	public String labelText() { return labelText; }
		
}
