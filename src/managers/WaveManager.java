package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Play;

public class WaveManager {
	
	@SuppressWarnings("unused")
	private Play play;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int enemySpawnTick;
	private int enemySpawnTime;
	private int enemyIndex, waveIndex;
	private boolean waveStartTimer;
	private int waveTickLimit;
	private int waveTick;
	private boolean waveTickTimerOver;
	
	public WaveManager(Play play) {
		this.play = play;
	    createWaves();
	    enemySpawnTime = 120;
	    enemySpawnTick=enemySpawnTime;
	    waveTickLimit = 15 * 60;
	    waveTick = 0;
	 
	}
	
	public void update() {
		
		if(enemySpawnTick < enemySpawnTime)
		enemySpawnTick++;
		
		if(waveStartTimer) {
			waveTick++;
			if(waveTick >= waveTickLimit) {
			waveTickTimerOver = true;
			}
		}
	}
	
	public void increaseWaveIndex() {
		waveIndex++;
		waveTick = 0;
		waveTickTimerOver = false;
		waveStartTimer = false;
		
	}
	
	public boolean isWaveTimerOver() {
	
		return waveTickTimerOver;
	}
	
	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}

	private void createWaves() {
		
		waves.add(new Wave(new ArrayList<Integer>(
				Arrays.asList(0, 0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,1))));
		waves.add(new Wave(new ArrayList<Integer>(
				Arrays.asList(2, 2, 2 ,2 ,2 ,2 ,2 ,2 ,2 ,1))));
		waves.add(new Wave(new ArrayList<Integer>(
				Arrays.asList(3, 3, 3 ,3 ,3 ,3 ,3 ,3 ,3 ,3))));
	}

	public ArrayList<Wave> getWaves(){
		return waves;
	}

	public boolean isTimeForNewEnemy() {
		
		return enemySpawnTick >= enemySpawnTime;
	}
	
	public boolean isEnemyLeft() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}

	public boolean isThereMoreWaves() {
			
		return waveIndex + 1 < waves.size();
	}

	public void startWaveTimer() {
		
		waveStartTimer = true;
	}

	public void resetEnemyIndex() {
		enemyIndex = 0;
		
	}
	
	public int getWaveIndex() {
		return waveIndex;
	}
	
	public float getTimeLeft() {
		float ticksLeft = waveTickLimit - waveTick;
		return ticksLeft/60.0f;
	}

	public boolean isWaveTimerStarted() {
		
		return waveStartTimer;
	}
	
	public void reset() {
		waves.clear();
		createWaves();
		enemyIndex = 0;
		waveIndex = 0;
		waveTick = 0;
		enemySpawnTick = enemySpawnTime;
		waveStartTimer = false;
		waveTickTimerOver = false;
		
		
	}


}
