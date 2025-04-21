/**
 * Unit test for Dice class.
 *
 * Testing Analysis
 * - The Method Coverage for Dice is 100%.
 * - The Line Coverage for Dice is 100%.
 */

package com.rollo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

public class DiceUnitTest {

    @Test
    public void TestDiceDConstructor() {
        Dice sampleDice = new Dice();
        assertTrue(sampleDice.getDiceType().isEmpty());
        assertTrue(sampleDice.getPipColour().isEmpty());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, sampleDice.getPossibleSides());
        assertTrue(sampleDice.getCurrentSide() >= 1 && sampleDice.getCurrentSide() <= 6);
    }//TestDiceDConstructor

    @Test
    public void TestDiceConstructor() {
        int[] sides = {4, 2, 3, 7, 11, 6};
        Dice sampleDice = new Dice("Gold", "Red", sides);
        assertEquals("Gold", sampleDice.getDiceType());
        assertEquals("Red", sampleDice.getPipColour());
        assertTrue(sampleDice.getCurrentSide() == sides[0] ||
                sampleDice.getCurrentSide() == sides[1] ||
                sampleDice.getCurrentSide() == sides[2] ||
                sampleDice.getCurrentSide() == sides[3] ||
                sampleDice.getCurrentSide() == sides[4] ||
                sampleDice.getCurrentSide() == sides[5]);
    }//TestDiceConstructor

    @Test
    public void TestSetDiceType() {
        Dice sampleDice = new Dice();
        sampleDice.setDiceType("Steel");
        assertEquals("Steel", sampleDice.getDiceType());
    }//TestSetDiceType

    @Test
    public void TestSetPipColour() {
        Dice sampleDice = new Dice();
        sampleDice.setPipColour("Blue");
        assertEquals("Blue", sampleDice.getPipColour());
    }//TestSetPipColour

    @Test
    public void TestSetPossibleSides() {
        int[] newSides = {1, 2, 3, 10, 20, 30};
        Dice sampleDice = new Dice();
        sampleDice.setPossibleSides(newSides);
        assertArrayEquals(newSides, sampleDice.getPossibleSides());
    }//TestSetPossibleSides

    @Test
    public void TestRoll() {
        Dice sampleDice = new Dice();
        int rolledSide = sampleDice.getRandomSide();
        assertTrue(rolledSide == 1 || rolledSide == 2 || rolledSide == 3 ||
                rolledSide == 4 || rolledSide == 5 || rolledSide == 6);
    }//TestRoll

    @Test
    public void TestGetCurrentSide() {
        Dice sampleDice = new Dice();
        int initSide = sampleDice.getCurrentSide();

        sampleDice.getRandomSide(); // Roll the dice

        int newSide = sampleDice.getCurrentSide();

        assertNotEquals(initSide, newSide);
    }//TestGetCurrentSide

    @Test
    public void TestDiceToString() {
        Dice sampleDice = new Dice("Wildcard", "Black", new int[]{1, 2, 3, 4, 5, 6});

        String sampleString = sampleDice.getCurrentSide() + " (" + sampleDice.getDiceType() + ", "
                + sampleDice.getPipColour() + ", " +
                Arrays.toString(sampleDice.getPossibleSides()) + ")";

        assertEquals(sampleString, sampleDice.toString());
    }//TestDiceToString
}//DiceUnitTest

