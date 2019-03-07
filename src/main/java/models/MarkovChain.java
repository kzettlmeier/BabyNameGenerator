package models;

import java.util.HashMap;
import java.util.List;

public class MarkovChain {
    public static HashMap<String, HashMap<String, NextCharacter>> createMarkovModel(List<String> names) {
        HashMap<String, HashMap<String, NextCharacter>> markovChain = new HashMap<>();

        // Loop through each name and generate stats
        for (String name : names) {
            int nameLength = name.length();

            for (int i = 0; i < nameLength - 2; i++) {
                // 2nd order key
                String twoChars = name.substring(i, i + 2);
                // 3rd char
                String newChar = name.substring(i + 2, i + 3);

                // Check if already a key
                if (markovChain.containsKey(twoChars)) {
                    HashMap<String, NextCharacter> obj = markovChain.get(twoChars);
                    // Check if new character already in here
                    if (obj.containsKey(newChar)) {
                        // Increment
                        NextCharacter newCharIncObj = obj.get(newChar);
                        newCharIncObj.incrementCount();
                        obj.put(newChar, newCharIncObj);
                    } else {
                        // New and create a new object for it
                        NextCharacter newCharObj = new NextCharacter(newChar);
                        obj.put(newChar, newCharObj);
                    }
                    markovChain.put(twoChars, obj);
                } else {
                    // Didn't already exist so add it
                    HashMap<String, NextCharacter> newObj = new HashMap<>();
                    NextCharacter newCharObj = new NextCharacter(newChar);
                    newObj.put(newChar, newCharObj);
                    markovChain.put(twoChars, newObj);
                }
            }
        }

        return markovChain;
    }
}
