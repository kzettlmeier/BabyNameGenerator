package main;

import data.DataLoader;
import models.GenerateName;
import models.MarkovChain;
import models.NextCharacter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Ask if male or female
        System.out.println("Male or Female name? ");
        String maleOrFemale = input.next().trim();

        // Validate the gender is male or female
        if (!maleOrFemale.toLowerCase().equals("male") && !maleOrFemale.toLowerCase().equals("female")) {
            System.out.println("Answer must be male or female");
            System.out.println("Exiting...");
            System.exit(-1);
        }

        // Ask for order of the model
        System.out.println("What should the order of the model be? ");
        int orderSize = input.nextInt();

        // Ask for minimum length
        System.out.println("What should the minimum name length be? ");
        int minimumLength = input.nextInt();

        // Ask for maximum length
        System.out.println("What should the maximum name length be? ");
        int maximumLength = input.nextInt();

        // Ask for the number of names to generate
        System.out.println("Number of names to generate? ");
        int numberOfNames = input.nextInt();

        // Validate that min < max
        if (minimumLength > maximumLength) {
            System.out.println("Minimum length must be less than or equal to maximum length");
            System.out.println("Exiting...");
            System.exit(-1);
        }

        // Print out parameters
        System.out.println("Generating " + numberOfNames + " " + maleOrFemale + " name(s) of at least " + minimumLength +
                " characters" + " and maximum of " + maximumLength + " characters...");

        System.out.println("Loading datasets...");

        // Load in data sets
        List names;
        if (maleOrFemale.toLowerCase().equals("male")) {
            names = DataLoader.loadDataFromFileIntoList("namesBoys.txt", orderSize);
        } else {
            names = DataLoader.loadDataFromFileIntoList("namesGirls.txt", orderSize);
        }

        // Generate markov model
        HashMap<String, HashMap<String, NextCharacter>> markovChain = MarkovChain.createMarkovModel(names, orderSize);

        // Loop for how ever many names to generate
        List<String> generatedNames = new ArrayList<>();
        String startEndChar = "_";
        String startEndChars = startEndChar.repeat(orderSize);
        while (generatedNames.size() != numberOfNames) {
            // Generate a random name
            String generatedName = GenerateName.generateName(markovChain, orderSize);

            // Validate name matches parameters and does not exist in list
            String trimmedName = generatedName.substring(orderSize, generatedName.length() - orderSize);
            int nameLength = trimmedName.length();
            if (nameLength >= minimumLength && nameLength <= maximumLength && generatedName.startsWith(startEndChars) && generatedName.endsWith(startEndChars) && !names.contains(generatedName)) {
                generatedNames.add(trimmedName);
            }
        }

        // Print out names
        System.out.println("The names that were generated are:");
        for (String name : generatedNames) {
            System.out.println(name);
        }
    }
}
