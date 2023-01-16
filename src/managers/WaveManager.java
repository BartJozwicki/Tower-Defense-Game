package managers;

import java.util.ArrayList;

import java.util.Arrays;

import events.Wave;
import scenes.Play;
import static helper.Constants.Enemies.*;

/* This class takes care of the "waves of enemies" */
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
	private int initDelayTick, initDelayTime;
	private boolean waveTickTimerOver, initDelay;

	public WaveManager(Play play) {
		this.play = play;
		createWaves();
		enemySpawnTime = 120;
		enemySpawnTick = 0;
		waveTickLimit = 15 * 60;
		waveTick = 0;
		initDelayTime = 2000;
		waveStartTimer = false;
		initDelay = true;
	}

	// 120 UPS
	public void update() {

		if (initDelay) {
			initDelayTick++;
			if (initDelayTick > initDelayTime)
				initDelay = false;
		}

		if (enemySpawnTick < enemySpawnTime)
			enemySpawnTick++;

		if (waveStartTimer) {
			waveTick++;
			if (waveTick >= waveTickLimit) {
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

	// Each number adds one enemy, sorted by difficulty
	private void createWaves() {
		

		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(PEON, PEON, PEON, PEON, PEON, PEON, PEON, PEON, PEON, PEON))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(WARRIOR, WARRIOR, WARRIOR, WARRIOR, WARRIOR, WARRIOR, WARRIOR, WARRIOR, WARRIOR, WARRIOR))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR, BLOODY_WARRIOR))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN, BLOODY_SPEARMAN))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(FIRST_BOSS))));
	}

	public ArrayList<Wave> getWaves() {
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
		float ticksLeft;
		if (initDelay) {
			ticksLeft = initDelayTime - initDelayTick;
		} else {
			ticksLeft = waveTickLimit - waveTick;
		}
		return ticksLeft / 60.0f;
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
		initDelay = true;
		initDelayTick = 0;
	}

	public boolean isInitDelayOver() {

		return !initDelay;
	}

}
