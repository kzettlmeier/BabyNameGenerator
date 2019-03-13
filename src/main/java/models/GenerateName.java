package models;

import java.util.HashMap;
import java.util.Set;

public class GenerateName {
    public static String generateName(HashMap<String, HashMap<String, NextCharacter>> markovChain, int orderSize) {
        String startEndChar = "_";
        String name = startEndChar.repeat(orderSize);
        String startingChars = startEndChar.repeat(orderSize);
        String nextChar = "";
        while (!nextChar.equals("_")) {
            if (markovChain.get(startingChars) == null) {
                break;
            }
            HashMap<String, NextCharacter> nextCharHashMap = markovChain.get(startingChars);
            // Loop through all keys to get count
            int totalCount = 0;
            Set<String> keys = nextCharHashMap.keySet();
            for (String key : keys) {
                NextCharacter nextCharObj = nextCharHashMap.get(key);
                totalCount += nextCharObj.getCount();
            }

            // Loop through again to get percentage
            RandomCollection<String> charWeightedList = new RandomCollection<>();
            for (String key : keys) {
                NextCharacter nextCharObj = nextCharHashMap.get(key);
                double weight = (nextCharObj.getCount()*100) / totalCount;
                charWeightedList.add(weight, nextCharObj.getCharacter());
            }

            // Choose a character based upon weight;
            nextChar = charWeightedList.next();
            name += nextChar;

            // Set starting character to the new value
            startingChars = startingChars.substring(1, orderSize) + nextChar;
        }

        return name + startEndChar.repeat(orderSize - 1);
    }
}
