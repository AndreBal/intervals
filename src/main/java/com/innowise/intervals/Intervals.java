package com.innowise.intervals;

import java.util.*;

public class Intervals {

    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 3;
    private static final List<String> DIRECTION_INDICATORS;

    private static final String DESCENDING_INDICATOR;
    private static final List<String> CONSTRUCTION_INPUT_NOTES;
    private static final List<String> IDENTIFICATION_INPUT_NOTES;
    private static final Map<String,Integer[]> INTERVALS_DATA;
    private static final LinkedHashMap<String,Integer> NOTES_WITH_SEMITONES;
    private static final ArrayList <String> INPUT_NOTES;
    private static final Map<Integer,String> SEMITONES_WITH_NOTE_POSTFIX;

    static{
        DESCENDING_INDICATOR = "dsc";
        DIRECTION_INDICATORS = Arrays.asList("asc", DESCENDING_INDICATOR);
        CONSTRUCTION_INPUT_NOTES  = Arrays.asList("Cb", "C", "C#", "Db", "D", "D#", "Eb", "E", "E#", "Fb", "F", "F#", "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B", "B#");
        IDENTIFICATION_INPUT_NOTES  = Arrays.asList("Cbb", "Cb", "C", "C#", "C##", "Dbb", "Db", "D", "D#", "D##", "Ebb", "Eb", "E", "E#", "E##", "Fbb", "Fb", "F", "F#", "F##", "Gbb", "Gb", "G", "G#", "G##", "Abb", "Ab", "A", "A#", "A##", "Bbb", "Bb", "B", "B#", "B##");
        Map<String,Integer[]> intervalsData = new HashMap<>();
        intervalsData.put("m2", new Integer[]{1, 2});
        intervalsData.put("M2", new Integer[]{2, 2});
        intervalsData.put("m3", new Integer[]{3, 3});
        intervalsData.put("M3", new Integer[]{4, 3});
        intervalsData.put("P4", new Integer[]{5, 4});
        intervalsData.put("P5", new Integer[]{7, 5});
        intervalsData.put("m6", new Integer[]{8, 6});
        intervalsData.put("M6", new Integer[]{9, 6});
        intervalsData.put("m7", new Integer[]{10, 7});
        intervalsData.put("M7", new Integer[]{11, 7});
        intervalsData.put("P8", new Integer[]{12, 8});
        INTERVALS_DATA = intervalsData;
        LinkedHashMap<String,Integer> notesWithSemitones = new LinkedHashMap<>();
        notesWithSemitones.put("C", 2);
        notesWithSemitones.put("D", 2);
        notesWithSemitones.put("E", 1);
        notesWithSemitones.put("F", 2);
        notesWithSemitones.put("G", 2);
        notesWithSemitones.put("A", 2);
        notesWithSemitones.put("B", 1);
        NOTES_WITH_SEMITONES = notesWithSemitones;
        INPUT_NOTES = new ArrayList<>(NOTES_WITH_SEMITONES.keySet());
        Map<Integer,String> semitonesWithNotePostfix = new HashMap<>();
        semitonesWithNotePostfix.put(-2,"bb");
        semitonesWithNotePostfix.put(-1,"b");
        semitonesWithNotePostfix.put(0,"");
        semitonesWithNotePostfix.put(1,"#");
        semitonesWithNotePostfix.put(2,"##");
        SEMITONES_WITH_NOTE_POSTFIX = semitonesWithNotePostfix;
    }

    public static String intervalConstruction(String[] args){
        if(!isArraySizeValid(args)){
            throw new RuntimeException("Illegal number of elements in input array");
        }
        if(!isIntervalConstructionArgsValid(args)){
            throw new RuntimeException("Invalid arguments in input array");
        }
        String intervalString = args[0];
        String note = args[1];
        Integer inputNoteSemitoneNum = getNoteSemitone(note);
        note = getNoteWithoutSemitones(note);
        Integer semitoneNum = INTERVALS_DATA.get(intervalString)[0];
        Integer degreeNum = INTERVALS_DATA.get(intervalString)[1];
        int iteratorPosition = INPUT_NOTES.indexOf(note);
        boolean intervalIsDescending = isIntervalDescending(args);
        semitoneNum = (intervalIsDescending)? semitoneNum-inputNoteSemitoneNum:semitoneNum+inputNoteSemitoneNum;

        if(intervalIsDescending){
            semitoneNum = -semitoneNum;
            while (degreeNum > 1) {
                iteratorPosition--;
                if (iteratorPosition < 0) {
                    iteratorPosition = INPUT_NOTES.size() - 1;
                }
                note = INPUT_NOTES.get(iteratorPosition);
                semitoneNum += NOTES_WITH_SEMITONES.get(note);
                degreeNum--;
            }
        }else {
            while (degreeNum > 1) {
                iteratorPosition++;
                if (iteratorPosition >= INPUT_NOTES.size()) {
                    iteratorPosition = 0;
                }
                semitoneNum -= NOTES_WITH_SEMITONES.get(note);
                note = INPUT_NOTES.get(iteratorPosition);
                degreeNum--;
            }
        }

        return note+getNotePostfix(semitoneNum);
    }


    public static String intervalIdentification(String[] args){
        if(!isIntervalIdentificationArgsValid(args)){
            throw new RuntimeException("Cannot identify the interval");
        }
        String startingNote;
        String finishingNote;
        if(isIntervalDescending(args)){
            startingNote = args[1];
            finishingNote = args[0];
        }else{
            startingNote = args[0];
            finishingNote = args[1];
        }

        int startingNoteSemitone = getNoteSemitone(startingNote);
        int finishingNoteSemitone = getNoteSemitone(finishingNote);
        int intervalSemitone = finishingNoteSemitone-startingNoteSemitone;

        String currentNote;

        int iteratorPosition = INPUT_NOTES.indexOf(getNoteWithoutSemitones(startingNote));
        int intervalEndPosition = INPUT_NOTES.indexOf(getNoteWithoutSemitones(finishingNote));

        while(iteratorPosition!=intervalEndPosition){
            currentNote = INPUT_NOTES.get(iteratorPosition);
            intervalSemitone += NOTES_WITH_SEMITONES.get(currentNote);
            iteratorPosition++;
            if (iteratorPosition >= INPUT_NOTES.size()) {
                iteratorPosition = 0;
            }
        }

        return getIntervalBySemitoneNumber(intervalSemitone);
    }

    private static boolean isArraySizeValid(String[] args){
        if(args == null){
            return false;
        }
        if(args.length != MIN_SIZE && args.length != MAX_SIZE ){
            return false;
        }

        return true;
    }
    private static boolean isArrayValid(String[] args){
        if(!isArraySizeValid(args)){
            return false;
        }

        if(args.length == MAX_SIZE && !DIRECTION_INDICATORS.contains(args[MAX_SIZE - 1])){
            return false;
        }
        for(String str:args){
            if(str == null){
                return false;
            }
        }
        return true;
    }

    private static boolean isIntervalConstructionArgsValid(String[] args){
        if(!isArrayValid(args)){
            return false;
        }

        if(!INTERVALS_DATA.keySet().contains(args[0])){
            return false;
        }

        if(!CONSTRUCTION_INPUT_NOTES.contains(args[1])){
           return false;
        }

        return true;
    }

    private static boolean isIntervalIdentificationArgsValid(String[] args){
        if(!isArrayValid(args)){
            return false;
        }
        if(!IDENTIFICATION_INPUT_NOTES.contains(args[0])){
            return false;
        }
        if(!IDENTIFICATION_INPUT_NOTES.contains(args[1])){
            return false;
        }

        return true;
    }

    private static String getNotePostfix(int semitones){
        return SEMITONES_WITH_NOTE_POSTFIX.get(semitones);
    }

    private static boolean isIntervalDescending(String[] args){
        return args.length == 3 && args[2].equals(DESCENDING_INDICATOR);
    }

    private static int getNoteSemitone(String note){
        if(note.length() == 1){
            return 0;
        }
        int semitone = 0;
        String notePostfix = note.substring(1);
        semitone = SEMITONES_WITH_NOTE_POSTFIX.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), notePostfix))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
        return semitone;
    }

    private static String getIntervalBySemitoneNumber(int semitoneNum){
        return INTERVALS_DATA.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue()[0], semitoneNum))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
    }

    private static String getNoteWithoutSemitones(String note){
        if(note == null || note.isEmpty()){
            return note;
        }
        return String.valueOf(note.charAt(0));
    }
}
