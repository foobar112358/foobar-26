package com.justindriggers.foobar;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Minion's bored game
 * ===================
 *
 * There you have it. Yet another pointless "bored" game created by the bored minions of Professor Boolean.
 *
 * The game is a single player game, played on a board with n squares in a horizontal row. The minion places a token on the left-most square and rolls a special three-sided die.
 *
 * If the die rolls a "Left", the minion moves the token to a square one space to the left of where it is currently. If there is no square to the left, the game is invalid, and you start again.
 *
 * If the die rolls a "Stay", the token stays where it is.
 *
 * If the die rolls a "Right", the minion moves the token to a square, one space to the right of where it is currently. If there is no square to the right, the game is invalid and you start again.
 *
 * The aim is to roll the dice exactly t times, and be at the rightmost square on the last roll. If you land on the rightmost square before t rolls are done then the only valid dice roll is to roll a "Stay". If you roll anything else, the game is invalid (i.e., you cannot move left or right from the rightmost square).
 *
 * To make it more interesting, the minions have leaderboards (one for each n,t pair) where each minion submits the game he just played: the sequence of dice rolls. If some minion has already submitted the exact same sequence, they cannot submit a new entry, so the entries in the leader-board correspond to unique games playable.
 *
 * Since the minions refresh the leaderboards frequently on their mobile devices, as an infiltrating hacker, you are interested in knowing the maximum possible size a leaderboard can have.
 *
 * Write a function answer(t, n), which given the number of dice rolls t, and the number of squares in the board n, returns the possible number of unique games modulo 123454321. i.e. if the total number is S, then return the remainder upon dividing S by 123454321, the remainder should be an integer between 0 and 123454320 (inclusive).
 *
 * n and t will be positive integers, no more than 1000. n will be at least 2.
 *
 *
 * Languages
 * =========
 *
 * To provide a Python solution, edit solution.py
 * To provide a Java solution, edit solution.java
 *
 * Test cases
 * ==========
 *
 * Inputs:
 * (int) t = 1
 * (int) n = 2
 * Output:
 * (int) 1
 *
 * Inputs:
 * (int) t = 3
 * (int) n = 2
 * Output:
 * (int) 3
 *
 * Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.
 */
public class MinionsBoredGame {

    private static final BigInteger MODULO_CONSTANT = BigInteger.valueOf(123454321);

    public static void main(String[] args) {
        System.out.println(answer(1, 2)); // 1

        System.out.println(answer(3, 2)); // 3
    }

    public static int answer(int t, int n) {
        BigInteger result;

        if (n == t) {
            result = BigInteger.valueOf(n);
        } else if (n == 2) {
            result = BigInteger.valueOf(t);
        } else {
            BigInteger[] foo = initBoard(n);

            for (int roll = 0; roll < t; roll++) {
                foo = roll(foo, n);
            }

            result = foo[n - 1];
        }

        return result.mod(MODULO_CONSTANT).intValue();
    }

    private static BigInteger[] initBoard(int size) {
        BigInteger[] board = new BigInteger[size];
        Arrays.fill(board, BigInteger.ZERO);
        board[0] = BigInteger.ONE;

        return board;
    }

    private static BigInteger[] roll(BigInteger[] possibilitiesPerSquare, int totalSquares) {
        BigInteger[] updatedPossibilities = initBoard(totalSquares);

        for (int square = 0; square < totalSquares; square++) {
            // "Stay"
            updatedPossibilities[square] = possibilitiesPerSquare[square];

            // "Left"
            if (square > 0) {
                updatedPossibilities[square] = updatedPossibilities[square].add(possibilitiesPerSquare[square - 1]);
            }

            // "Right"
            if (square < totalSquares - 2) {
                updatedPossibilities[square] = updatedPossibilities[square].add(possibilitiesPerSquare[square + 1]);
            }

            // Don't bother iterating through the entire array if we have nothing left to calculate
            if (BigInteger.ZERO.equals(updatedPossibilities[square])) {
                break;
            }
        }

        return updatedPossibilities;
    }
}