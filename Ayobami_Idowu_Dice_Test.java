import java.util.*;

public class DiceGameSimulation {

    public static void main(String[] args) {
        // Default values
        int numberOfDice = 5;
        int numberOfSimulations = 10000;

        // 1. Command-line arguments (highest priority)
        if (args.length >= 1) {
            try {
                numberOfDice = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid command-line NUMBER_OF_DICE, using fallback...");
            }
        } else {
            // 2. Environment variable fallback
            String envDice = System.getenv("NUMBER_OF_DICE");
            if (envDice != null) {
                try {
                    numberOfDice = Integer.parseInt(envDice);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid env NUMBER_OF_DICE, using default: " + numberOfDice);
                }
            }
        }

        if (args.length >= 2) {
            try {
                numberOfSimulations = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid command-line NUMBER_OF_SIMULATIONS, using fallback...");
            }
        } else {
            // 2. Environment variable fallback
            String envSimulations = System.getenv("NUMBER_OF_SIMULATIONS");
            if (envSimulations != null) {
                try {
                    numberOfSimulations = Integer.parseInt(envSimulations);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid env NUMBER_OF_SIMULATIONS, using default: " + numberOfSimulations);
                }
            }
        }

        simulateGame(numberOfDice, numberOfSimulations);
    }

    public static void simulateGame(int numberOfDice, int simulations) {
        Map<Integer, Integer> scoreCounts = new TreeMap<>();
        Random random = new Random();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < simulations; i++) {
            List<Integer> dice = rollDice(numberOfDice, random);
            int totalScore = 0;

            while (!dice.isEmpty()) {
                if (dice.contains(3)) {
                    dice.removeIf(d -> d == 3);
                } else {
                    int min = Collections.min(dice);
                    totalScore += min;
                    dice.remove(Integer.valueOf(min));
                }
                dice = rollDice(dice.size(), random);
            }

            scoreCounts.put(totalScore, scoreCounts.getOrDefault(totalScore, 0) + 1);
        }

        long endTime = System.currentTimeMillis();
        double durationSeconds = (endTime - startTime) / 1000.0;

        System.out.println("Number of simulations was " + simulations + " using " + numberOfDice + " dice.");
        for (Map.Entry<Integer, Integer> entry : scoreCounts.entrySet()) {
            int score = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / simulations;
            System.out.printf("Total %d occurs %.2f occurred %.1f times.%n", score, percentage, (double) count);
        }

        System.out.printf("Total simulation took %.1f seconds.%n", durationSeconds);
    }

    public static List<Integer> rollDice(int count, Random random) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(random.nextInt(6) + 1); // 1 to 6
        }
        return result;
    }
}
