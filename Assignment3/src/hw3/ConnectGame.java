package hw3;

import java.util.Random;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 */
public class ConnectGame {
	private ShowDialogListener dialogListener;
	@SuppressWarnings("unused")
	private ScoreUpdateListener scoreListener;
	private int gridWidth;
	private int gridHeight;
	private int maxLevel;
	private int minLevel;
	private Random random;
	private Grid gameGrid;
	private boolean firstSelect;
	private Tile[] selectedTiles;
	private int numSelected;
	private long score;

	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		// TODO
		gridWidth = width;
		gridHeight = height;
		maxLevel = max;
		minLevel = min;
		random = rand;
		score = 0;
		firstSelect = false;
		selectedTiles = new Tile[width * height];
		gameGrid = new Grid(width, height);
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
		Tile randTile = new Tile(random.nextInt(maxLevel - minLevel) + minLevel);
		return randTile;
	}

	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				Tile newTile = getRandomTile();
				newTile.setLocation(x, y);
				gameGrid.setTile(newTile, x, y);
			}
		}
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
		boolean isAdjacent = false;
		int t1X = t1.getX();
		int t1Y = t1.getY();
		int t2X = t2.getX();
		int t2Y = t2.getY();
		if ((t1X <= t2X + 1) && (t1X >= t2X - 1)) {
			if ((t1Y <= t2Y + 1) && (t1Y >= t2Y - 1)) {
				isAdjacent = true;
			}
		}
		return isAdjacent;
	}

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		if (firstSelect) {
			return false;
		} else {
			firstSelect = true;
			gameGrid.getTile(x, y).setSelect(true);
			selectedTiles[0] = new Tile(gameGrid.getTile(x, y).getLevel());
			selectedTiles[0].setLocation(x, y);
			selectedTiles[0].setSelect(true);
			numSelected = 1;
			return true;
		}
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		// TODO
			if (!gameGrid.getTile(x, y).isSelected()) {
				if (isAdjacent(selectedTiles[numSelected - 1], gameGrid.getTile(x, y))) {
					if (numSelected <= 1 && (selectedTiles[0].getLevel() == gameGrid.getTile(x, y).getLevel())) {
						gameGrid.getTile(x, y).setSelect(true);
						selectedTiles[numSelected] = new Tile(gameGrid.getTile(x, y).getLevel());
						selectedTiles[numSelected].setLocation(x, y);
						selectedTiles[numSelected].setSelect(true);
					}
					if (numSelected > 1 && ((selectedTiles[numSelected - 1].getLevel() == gameGrid.getTile(x, y) .getLevel())
							|| (selectedTiles[numSelected - 1].getLevel() + 1 == gameGrid.getTile(x, y).getLevel()))) {
						gameGrid.getTile(x, y).setSelect(true);
						selectedTiles[numSelected] = new Tile(gameGrid.getTile(x, y).getLevel());
						selectedTiles[numSelected].setLocation(x, y);
						selectedTiles[numSelected].setSelect(true);
					}
					numSelected++;
				}
			}
			else if(gameGrid.getTile(x, y).isSelected()){
				int tempX = selectedTiles[numSelected-1].getX();
				int tempY = selectedTiles[numSelected-1].getY();
				gameGrid.getTile(tempX, tempY).setSelect(false);
				selectedTiles[numSelected-1] = null;
				numSelected--;
			}
		}
	

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		if (!gameGrid.getTile(x, y).isSelected()) {
			return false;
		} else {
			if (isAdjacent(selectedTiles[numSelected - 1], gameGrid.getTile(x, y))) {
				gameGrid.getTile(x, y).setSelect(true);
				selectedTiles[numSelected] = new Tile(gameGrid.getTile(x, y).getLevel());
				selectedTiles[numSelected].setLocation(x, y);
				selectedTiles[numSelected].setSelect(true);
				if (numSelected == 1) {
					gameGrid.getTile(x, y).setSelect(false);
					return true;
				}
				if (numSelected > 1) {
					upgradeLastSelectedTile();
					dropSelected();
					score += gameGrid.getTile(x, y).getValue();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		// TODO
		int lastTileX = selectedTiles[numSelected - 1].getX();
		int lastTileY = selectedTiles[numSelected - 1].getY();
		int newLevel = selectedTiles[numSelected - 1].getLevel() + 1;
		gameGrid.getTile(lastTileX, lastTileY).setLevel(newLevel);
		gameGrid.getTile(lastTileX, lastTileY).setSelect(false);
		if (newLevel >= maxLevel) {
			maxLevel++;
			minLevel++;
			dialogListener.showDialog("New block "+ gameGrid.getTile(lastTileX, lastTileY).getValue()+", removing blocks "+Math.pow(2, minLevel-1));
		}
		selectedTiles[numSelected - 1].setLevel(0);
		numSelected--;
		firstSelect = false;
	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		Tile[] tilesArr = new Tile[numSelected];
		for (int i = 0; i < numSelected; i++) {
			tilesArr[i] = selectedTiles[i];
		}
		return tilesArr;
	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				if (gameGrid.getTile(x, y).getLevel() == level) {
					gameGrid.getTile(x, y).setLevel(0);
				}
			}
		}
		for (int x = gridWidth - 1; x >= 0; x--) {
			for (int y = gridHeight - 1; y >= 0; y--) {
				if (gameGrid.getTile(x, y).getLevel() == 0) {
					for (int thisY = y; thisY >= 0; thisY--) {
						if (gameGrid.getTile(x, thisY).getLevel() != 0) {
							gameGrid.setTile(gameGrid.getTile(x, thisY), x, y);
							gameGrid.setTile(new Tile(0), x, thisY);
						}
					}
				}
			}
		}

		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				if (gameGrid.getTile(x, y).getLevel() == 0) {
					gameGrid.setTile(getRandomTile(), x, y);
				}
			}
		}
		for (int i = 0; i <= numSelected; i++) {
			selectedTiles[i] = null;
		}
		numSelected = 0;
		firstSelect = false;
	}

	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {
		// TODO
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				if (gameGrid.getTile(x, y).isSelected() == true) {
					gameGrid.getTile(x, y).setLevel(0);
				}
			}
		}

		for (int x = gridWidth - 1; x >= 0; x--) {
			for (int y = gridHeight - 1; y >= 0; y--) {
				if (gameGrid.getTile(x, y).getLevel() == 0) {
					for (int thisY = y; thisY >= 0; thisY--) {
						if (gameGrid.getTile(x, thisY).getLevel() != 0) {
							gameGrid.setTile(gameGrid.getTile(x, thisY), x, y);
							gameGrid.setTile(new Tile(0), x, thisY);
						}
					}
				}
			}
		}

		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				if (gameGrid.getTile(x, y).getLevel() == 0) {
					unselect(x,y);
					gameGrid.setTile(getRandomTile(), x, y);
				}
			}
		}
		numSelected = 0;
		firstSelect = false;
	}

	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
		for(int i = 0; i < numSelected; i++) {
			if(selectedTiles[i].getX() == x && selectedTiles[i].getY() == y) {
				selectedTiles[i].setLevel(0);
				numSelected--;
			}
		}
		gameGrid.getTile(x, y).setSelect(false);
	}

	/**
	 * Gets the player's score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return gameGrid;
	}

	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return minLevel;
	}

	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return maxLevel;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		this.score = score;
	}

	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		gameGrid = grid;
	}

	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		// TODO
		minLevel = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		// TODO
		maxLevel = maxTileLevel;
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}
}