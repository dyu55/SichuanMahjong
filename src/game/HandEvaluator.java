package game; /**
 * HandEvaluator.java
 *
 * Function:
 *   - Provides methods to evaluate if a player's hand forms a winning hand (Ping Hu).
 *   - Uses a recursive algorithm to check if the hand can be divided into a pair and valid sets (triplets or sequences).
 *
 * Contains:
 *   - isPingHu: Checks if the hand is a winning hand.
 *   - canFormSets: Recursively checks if remaining tiles can form valid sets.
 */
import java.util.*;
public class HandEvaluator {
    public static boolean isPingHu(List<Tile> tiles) {
        if ((tiles.size() - 2) % 3 != 0) return false;
        Map<Tile, Integer> counts = new HashMap<>();
        for (Tile t : tiles) counts.put(t, counts.getOrDefault(t, 0) + 1);
        for (Tile tile : counts.keySet()) {
            if (counts.get(tile) >= 2) {
                Map<Tile, Integer> tmp = new HashMap<>(counts);
                tmp.put(tile, tmp.get(tile) - 2);
                if (canFormSets(tmp)) return true;
            }
        }
        return false;
    }

    public static boolean isPingHu2(List<Tile> tiles) {
        if ((tiles.size() - 2) % 3 != 0) return false;
        Map<Tile, Integer> counts = new HashMap<>();
        for (Tile t : tiles) counts.put(t, counts.getOrDefault(t, 0) + 1);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Boolean>> futures = new ArrayList<>();

        for (Tile tile : counts.keySet()) {
            if (counts.get(tile) >= 2) {
                Map<Tile, Integer> tmp = new HashMap<>(counts);
                tmp.put(tile, tmp.get(tile) - 2);

                futures.add(executorService.submit(() -> canFormSets(tmp)));
            }
        }

        executorService.shutdown();
        try {
            for (Future<Boolean> future : futures) {
                if (future.get()) return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean canFormSets(Map<Tile, Integer> counts) {
        if (counts.values().stream().allMatch(c -> c == 0)) return true;
        Tile tile = counts.keySet().stream().filter(t -> counts.get(t) > 0).findFirst().orElse(null);
        if (tile == null) return false;
        if (counts.get(tile) >= 3) {
            counts.put(tile, counts.get(tile) - 3);
            if (canFormSets(counts)) return true;
            counts.put(tile, counts.get(tile) + 3);
        }
        if (tile.rank <= 7) {
            Tile t2 = new Tile(tile.suit, tile.rank + 1);
            Tile t3 = new Tile(tile.suit, tile.rank + 2);
            if (counts.getOrDefault(t2, 0) > 0 && counts.getOrDefault(t3, 0) > 0) {
                counts.put(tile, counts.get(tile) - 1);
                counts.put(t2, counts.get(t2) - 1);
                counts.put(t3, counts.get(t3) - 1);
                if (canFormSets(counts)) return true;
                counts.put(tile, counts.get(tile) + 1);
                counts.put(t2, counts.get(t2) + 1);
                counts.put(t3, counts.get(t3) + 1);
            }
        }
        return false;
    }
}
