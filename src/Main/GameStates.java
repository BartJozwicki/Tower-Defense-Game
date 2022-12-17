package Main;

public enum GameStates {

	 PLAYING, MENU, EDIT, SETTINGS, GAME_OVER;
	
	public static GameStates gameState = MENU;
	
	public static void SetGameState(GameStates state) {
		gameState = state;
	}
	
}
