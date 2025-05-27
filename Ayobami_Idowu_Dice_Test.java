import java.util.*;

public class DiceGameSimulation {

    public static void main(String[] args) {
        int diceNoToRow = 2;
        int numberOfSimulations = 100;

        simulateGame(diceNoToRow, numberOfSimulations);
    }

    public static void simulateGame(int diceNoToRow, int simulations) {
        Map<Integer, Integer> scoreTrack = new TreeMap<>();
        Random random = new Random();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < simulations; i++) {
            List<Integer> dice = rollDice(diceNoToRow, random);
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

            scoreTrack.put(totalScore, scoreTrack.getOrDefault(totalScore, 0) + 1);
        }

        long endTime = System.currentTimeMillis();
        double durationSeconds = (endTime - startTime) / 1000.0;

        System.out.println("Number of simulations was " + simulations + " using " + diceNoToRow + " dice.");
		
        for (Map.Entry<Integer, Integer> entry : scoreTrack.entrySet()) {
            int score = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / simulations;
            System.out.printf("Total %d occurs %.2f occurred %.1f times.%n", score, percentage, (double) count);
        }

        System.out.printf("Total simulation took %.1f seconds.%n", durationSeconds);
    }

    public static List<Integer> rollDice(int rollCount, Random random) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < rollCount; i++) {
            result.add(random.nextInt(6) + 1);
        }
        return result;
    }
}