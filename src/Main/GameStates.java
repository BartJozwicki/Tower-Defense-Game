package Main;

public enum GameStates {

	 PLAYING, TUTORIAL, MENU, EDIT, SETTINGS, GAME_OVER, GAME_WON;
	
	public static GameStates gameState = MENU;
	
	public static void SetGameState(GameStates state) {
		gameState = state;
	}
	
}
