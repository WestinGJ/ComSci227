package hw2;

import api.PlayerPosition;
import api.BallType;
import static api.PlayerPosition.*;
import static api.BallType.*;
/**
 * Class that models the game of three-cushion billiards.
 * 
 * @author Westin Gjervold
 */
public class ThreeCushion {
	/**
	 * Stores the lag winner
	 */
	private static PlayerPosition lag_winner;
	/**
	 * Stores the current player
	 */
	private PlayerPosition inning_player;
	/**
	 * Stores player A's cue ball color
	 */
	private static BallType player_A_cue_ball;
	/**
	 * Stores player B's cue ball color
	 */
	private static BallType player_B_cue_ball;
	/**
	 * Stores the current cue ball
	 */
	private BallType inning_cue_ball;
	/*
	 * Stores the first ball hit
	 */
	private BallType first_ball_hit;
	/**
	 * Stores the second ball hit
	 */
	private BallType second_ball_hit;
	/**
	 * Stores the number of points to win
	 */
	private int points_to_win;
	/**
	 * Stores the current inning
	 */
	private int inning;
	/**
	 * Stores player A score
	 */
	private int player_A_score;
	/**
	 * Stores player_B_score
	 */
	private int player_B_score;
	/**
	 * Stores number of cushion impacts
	 */
	private int num_cushion_impacts;
	/**
	 * Stores if break shot is true or false
	 */
	private boolean break_shot;
	/**
	 * Stores if bank shot is true or false
	 */
	private boolean bank_shot;
	/**
	 * Stores if shot is in progress
	 */
	private boolean shot_in_progress;
	/**
	 * Stores if inning is in progress
	 */
	private boolean inning_in_progress;
	/**
	 * Stores if its a valid shot
	 */
	private boolean valid_shot;
	/**
	 * Stores if game is over
	 */
	private boolean game_over;
	/**
	 * Stores if point is scored
	 */
	private boolean point_scored;

	/**
	 * Creates Three Cushion Pool game
	 * 
	 * @param lagWinner:   player who won the lag
	 * @param pointsToWin: number of points needed to win
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
		lag_winner = lagWinner;
		points_to_win = pointsToWin;
		inning = 1;
	}

	/**
	 * Indicates the given ball has impacted the given cushion.
	 */
	public void cueBallImpactCushion() {
		num_cushion_impacts += 1;
		if ((first_ball_hit == null) && (num_cushion_impacts >= 3)) {
			bank_shot = true;
		}
	}

	/**
	 * Indicates the player's cue ball has struck the given ball.
	 * 
	 * @param ball: the color of the ball that was hit
	 */
	public void cueBallStrike(BallType ball) {
		if (first_ball_hit == null && num_cushion_impacts > 0 && isBreakShot()) {
			foul();
		}
		if (first_ball_hit == null) {
			first_ball_hit = ball;
		} else if (second_ball_hit == null) {
			second_ball_hit = ball;
		}
		if (first_ball_hit == second_ball_hit) {
			second_ball_hit = null;
		}
		if (isBreakShot()) {
			if ((first_ball_hit == RED) && (num_cushion_impacts >= 3)) {
				point_scored = true;
			}
			if (first_ball_hit != RED) {
				foul();
			}
		} else {
			if ((first_ball_hit != inning_cue_ball) && (num_cushion_impacts >= 3)) {
				point_scored = true;
			}
		}
	}

	/**
	 * Indicates the cue stick has struck the given ball.
	 * 
	 * @param ball: the color of the cue ball hit
	 */
	public void cueStickStrike(BallType ball) {
		inning_in_progress = false;
		if (!game_over) {
			point_scored = false;
			valid_shot = false;
			num_cushion_impacts = 0;
			first_ball_hit = null;
			second_ball_hit = null;
			if (isShotStarted()) {
				foul();
			} else {
				shot_in_progress = true;
				inning_in_progress = true;
				if (ball.equals(getCueBall())) {
					valid_shot = true;
				} else if (!ball.equals(getCueBall())) {
					foul();
				}
			}
		}
	}

	/**
	 * Indicates that all balls have stopped motion.
	 */
	public void endShot() {
		//Checks if the shot is a bank shot
		if (bank_shot && first_ball_hit != null && second_ball_hit != null) {
			bank_shot = true;
		} else {
			bank_shot = false;
		}
		// If the shot is not a foul and doesn't score points
		if (valid_shot && !point_scored) {
			if (inning_player == PLAYER_A) {
				inning_player = PLAYER_B;
				inning_cue_ball = player_B_cue_ball;
				inning += 1;
			} else if (inning_player == PLAYER_B) {
				inning_player = PLAYER_A;
				inning_cue_ball = player_A_cue_ball;
				inning += 1;
			}
			shot_in_progress = false;
			inning_in_progress = false;
			bank_shot = false;
			break_shot = false;
		}
		// If the shot scores points
		else if (valid_shot && point_scored) {
			if (inning_player == PLAYER_A) {
				player_A_score += 1;

			} else if (inning_player == PLAYER_B) {
				player_B_score += 1;
			}
			break_shot = false;
			shot_in_progress = false;
		}
		// If a player wins
		if (player_A_score == points_to_win || player_B_score == points_to_win) {
			game_over = true;
			break_shot = false;
			bank_shot = false;
			shot_in_progress = false;
		}
	}

	/**
	 * A foul immediately ends the player's inning, even if the current shot has not
	 * yet ended.
	 */
	public void foul() {
		if (!isGameOver()) {
			inning_in_progress = false;
			valid_shot = false;
			if (inning_player == PLAYER_A) {
				inning_player = PLAYER_B;
				inning_cue_ball = player_B_cue_ball;
				inning += 1;
			} else if (inning_player == PLAYER_B) {
				inning_player = PLAYER_A;
				inning_cue_ball = player_A_cue_ball;
				inning += 1;
			}
			bank_shot = false;
			break_shot = false;
		}
	}

	/**
	 * Gets the cue ball of the current player.
	 * 
	 * @return: current cue ball
	 */
	public BallType getCueBall() {
		return inning_cue_ball;
	}

	/**
	 * Gets the inning number.
	 * 
	 * @return: inning number
	 */
	public int getInning() {
		return inning;
	}

	/**
	 * Gets the current player
	 * 
	 * @return: current player
	 */
	public PlayerPosition getInningPlayer() {
		return inning_player;
	}

	/**
	 * Gets the number of points scored by Player A.
	 * 
	 * @return: player A score
	 */
	public int getPlayerAScore() {
		return player_A_score;
	}

	/**
	 * Gets the number of points scored by Player B.
	 * 
	 * @return: player B score
	 */
	public int getPlayerBScore() {
		return player_B_score;
	}

	/**
	 * Returns true if and only if the most recently completed shot was a bank shot.
	 * 
	 * @return: bank_shot
	 */
	public boolean isBankShot() {
		return bank_shot;
	}

	/**
	 * Returns true if and only if this is the break shot (i.e., the first shot of
	 * the game).
	 * 
	 * @return: break_shot
	 */
	public boolean isBreakShot() {
		return break_shot;
	}

	/**
	 * Returns true if the game is over (i.e., one of the players has reached the
	 * designated number of points to win).
	 * 
	 * @return: game_over
	 */
	public boolean isGameOver() {
		return game_over;
	}

	/**
	 * Returns true if the shooting player has taken their first shot of the inning.
	 * 
	 * @return: inning_in_progress
	 */
	public boolean isInningStarted() {
		return inning_in_progress;
	}

	/**
	 * Returns true if a shot has been taken (see cueStickStrike()), but not ended
	 * (see endShot()).
	 * 
	 * @return: shot_in_progress
	 */
	public boolean isShotStarted() {
		return shot_in_progress;
	}

	/**
	 * Sets whether the player that won the lag chooses to break (take first shot),
	 * or chooses the other player to break.
	 * 
	 * @param selfBreak: returns true if the lag winner want to serve first
	 * @param cueBall:   the cue ball color of the lag winner
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
		break_shot = true;
		game_over = false;
		if ((lag_winner == PLAYER_A) && selfBreak) {
			if (cueBall == WHITE) {
				player_A_cue_ball = WHITE;
				player_B_cue_ball = YELLOW;
				inning_cue_ball = WHITE;
				inning_player = PLAYER_A;
			} else if (cueBall == YELLOW) {
				player_A_cue_ball = YELLOW;
				player_B_cue_ball = WHITE;
				inning_cue_ball = YELLOW;
				inning_player = PLAYER_A;
			}
		} else if ((lag_winner == PLAYER_A) && !selfBreak) {
			if (cueBall == WHITE) {
				player_A_cue_ball = WHITE;
				player_B_cue_ball = YELLOW;
				inning_cue_ball = YELLOW;
				inning_player = PLAYER_B;
			} else if (cueBall == YELLOW) {
				player_A_cue_ball = YELLOW;
				player_B_cue_ball = WHITE;
				inning_cue_ball = WHITE;
				inning_player = PLAYER_B;
			}
		} else if ((lag_winner == PLAYER_B) && selfBreak) {
			if (cueBall == WHITE) {
				player_A_cue_ball = YELLOW;
				player_B_cue_ball = WHITE;
				inning_cue_ball = WHITE;
				inning_player = PLAYER_B;
			} else if (cueBall == YELLOW) {
				player_A_cue_ball = WHITE;
				player_B_cue_ball = YELLOW;
				inning_cue_ball = YELLOW;
				inning_player = PLAYER_B;
			}
		} else if ((lag_winner == PLAYER_B) && !selfBreak) {
			if (cueBall == WHITE) {
				player_A_cue_ball = YELLOW;
				player_B_cue_ball = WHITE;
				inning_cue_ball = YELLOW;
				inning_player = PLAYER_A;
			} else if (cueBall == YELLOW) {
				player_A_cue_ball = WHITE;
				player_B_cue_ball = YELLOW;
				inning_cue_ball = WHITE;
				inning_player = PLAYER_A;
			}
		}
	}

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}
}