package sepr.atcGame.UI;

public enum MenuOptions {
		START (new MenuButton(" START ")),
		PLAY (new MenuButton("Play")),
		CHOOSEAIRPORT (new MenuButton("   Choose Airport   ")),
		SELECTDIFFICULTY (new MenuButton("    Select Difficulty    ")),
		EXIT (new MenuButton("Exit")),
		EASY (new MenuButton("Easy")),
		MEDIUM (new MenuButton(" Medium ")),
		HARD (new MenuButton("Hard")),
		AIRPORT1 (new MenuButton(" Heathrow ")),
		AIRPORT2 (new MenuButton(" Airport 2 ")),
		BACK (new MenuButton("Back"));
		

	    public final MenuButton menuButton;
		MenuOptions(MenuButton menuButton) {
			this.menuButton = menuButton;
		}


}
