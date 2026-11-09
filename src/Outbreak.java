/**
 * Mission 5. Dynamic programming on the tree of infections. Root the tree in person 0.
 * Input.Tree.neighbours() gives the neighbours of every person. The number of people is at most about 100, so a recursive DFS is fine here.
 */
public class Outbreak {

    /**
     * Choose people to test so that for every link (somebody infected somebody) at least one of the two is tested,
     * and the total cost of testing is as small as possible. cost[v] is the price of testing person v.
     * Returns tested[v] = true if person v is tested.
     *
     * State for every person v (looking only at v and everything below it): the smallest cost when v IS tested, and
     * the smallest cost when v is NOT tested. What must then be true about the children of v in each case?
     * Do not forget to rebuild the answer (who exactly is tested), not only the cost: the picture needs it.
     * Main calls this twice: with all costs equal to 1 (the line "Tests needed") and with the real costs ("Cheapest testing").
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        long[] take = new long[n];
        long[] skip = new long[n];
        testingCosts(0, -1, nb, cost, take, skip);

        boolean[] tested = new boolean[n];
        chooseTests(0, -1, take[0] < skip[0], nb, take, skip, tested);
        return tested;
    }

    private static void testingCosts(int person, int parent, int[][] nb, int[] cost, long[] take, long[] skip) {
        take[person] = cost[person];
        for (int child : nb[person]) {
            if (child == parent) {
                continue;
            }
            testingCosts(child, person, nb, cost, take, skip);
            take[person] += Math.min(take[child], skip[child]);
            skip[person] += take[child];
        }
    }

    private static void chooseTests(int person, int parent, boolean testPerson, int[][] nb,
                                    long[] take, long[] skip, boolean[] tested) {
        tested[person] = testPerson;
        for (int child : nb[person]) {
            if (child == parent) {
                continue;
            }
            boolean testChild = !testPerson || take[child] < skip[child];
            chooseTests(child, person, testChild, nb, take, skip, tested);
        }
    }

    /**
     * The number of links on the longest chain of infections, that is the longest path in the tree (counted in links).
     * A path goes up from one person and down to another; for every person you need the two longest ways down.
     * Example: a line 0-1-2-3 has chain 3; a star with centre 0 and leaves 1, 2, 3 has chain 2.
     */
    public static int longestChain(int n, int[][] nb) {
        return longestInSubtree(0, -1, nb, new int[n]);
    }

    private static int longestInSubtree(int person, int parent, int[][] nb, int[] down) {
        int best = 0;
        int first = 0;
        int second = 0;
        for (int child : nb[person]) {
            if (child == parent) {
                continue;
            }
            best = Math.max(best, longestInSubtree(child, person, nb, down));
            int branch = down[child] + 1;
            if (branch > first) {
                second = first;
                first = branch;
            } else if (branch > second) {
                second = branch;
            }
        }
        down[person] = first;
        return Math.max(best, first + second);
    }
}
