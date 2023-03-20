package com.innowise.intervals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalsTest {

    @Test
    void intervalConstruction(){
        assertEquals("D#",Intervals.intervalConstruction(new String[]{"P4", "G#", "dsc"}));
        assertEquals("Gbb",Intervals.intervalConstruction(new String[]{"m2", "Fb", "asc"}));
        assertEquals("D",Intervals.intervalConstruction(new String[]{"M2", "C", "asc"}));
        assertEquals("F#",Intervals.intervalConstruction(new String[]{"P5", "B", "asc"}));
        assertEquals("F#",Intervals.intervalConstruction(new String[]{"M7", "G", "asc"}));
        assertEquals("A",Intervals.intervalConstruction(new String[]{"m2", "Bb", "dsc"}));
        assertEquals("Abb",Intervals.intervalConstruction(new String[]{"M3", "Cb", "dsc"}));
        assertEquals("G#",Intervals.intervalConstruction(new String[]{"m3", "B", "dsc"}));
        assertEquals("D#",Intervals.intervalConstruction(new String[]{"M2", "E#", "dsc"}));
        assertEquals("B",Intervals.intervalConstruction(new String[]{"P4", "E", "dsc"}));
        assertEquals("E",Intervals.intervalConstruction(new String[]{"m2", "D#", "asc"}));

    }

    @Test
    void intervalIdentification(){
        assertEquals("M2",Intervals.intervalIdentification(new String[]{"C", "D"}));
        assertEquals("P5",Intervals.intervalIdentification(new String[]{"B", "F#", "asc"}));
        assertEquals("m2",Intervals.intervalIdentification(new String[]{"Fb", "Gbb"}));
        assertEquals("M7",Intervals.intervalIdentification(new String[]{"G", "F#", "asc"}));
        assertEquals("m2",Intervals.intervalIdentification(new String[]{"Bb", "A", "dsc"}));
        assertEquals("M3",Intervals.intervalIdentification(new String[]{"Cb", "Abb", "dsc"}));
        assertEquals("P4",Intervals.intervalIdentification(new String[]{"G#", "D#", "dsc"}));
        assertEquals("P4",Intervals.intervalIdentification(new String[]{"E", "B", "dsc"}));
        assertEquals("M2",Intervals.intervalIdentification(new String[]{"E#", "D#", "dsc"}));
        assertEquals("m3",Intervals.intervalIdentification(new String[]{"B", "G#", "dsc"}));
    }
}