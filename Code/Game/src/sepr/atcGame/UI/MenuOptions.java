package sepr.atcGame.UI;

public enum MenuOptions {
		START (new MenuButton("START")),
		PLAY (new MenuButton("Play")), 
		CHOOSEAIRPORT (new MenuButton("Choose Airport")),
		AIRPORT1 (new MenuButton("Heathrow")),
		AIRPORT2 (new MenuButton("Airport 2")),
		EXIT (new MenuButton("Exit"));
		

	    public final MenuButton menuButton;
		MenuOptions(MenuButton menuButton) {
			this.menuButton = menuButton;
		}


}
