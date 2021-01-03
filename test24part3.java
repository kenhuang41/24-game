import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

// version 3: returns all final steps (no duplicates but may not account for order of adding)
public class test24part3 {

    // value to solve for
    private static final int SOL = 163;

    // card values to work with
    private ArrayList<Double> vals;

    // hashset of final steps (aka solutions)
    private HashSet<String> solutions;

    // constructs object (pardon the lack of camelCase)
    public test24part3(Iterable<Double> input) {
        vals = (ArrayList) input;
        solutions = new HashSet<String>();
        solve(vals);
    }

    // recursive method to populate solutions hashset
    private boolean solve(Iterable<Double> input) {
        ArrayList<Double> nums = (ArrayList) input;
        Collections.sort(nums, Collections.reverseOrder());
        int len = nums.size();

        // checks whether solution is one step away
        if (len == 2) {
            double a = nums.get(0);
            double b = nums.get(1);
            if (a + b == SOL) {
                solutions.add(a + " + " + b);
                return true;
            }
            if (a - b == SOL) {
                solutions.add(a + " - " + b);
                return true;
            }
            if (b - a == SOL) {
                solutions.add(b + " - " + a);
                return true;
            }
            if (a * b == SOL) {
                solutions.add(a + " * " + b);
                return true;
            }
            if (b != 0 && a / b == SOL) {
                solutions.add(a + " / " + b);
                return true;
            }
            if (a != 0 && b / a == SOL) {
                solutions.add(b + " / " + a);
                return true;
            }
            return false;
        }

        // recursive calls for each pair of remaining numbers
        // returns whether any branches return true
        boolean output = false;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {

                // remove two values and add back value after operation
                ArrayList<Double> temp = (ArrayList) nums.clone();
                double a = temp.remove(j);
                double b = temp.remove(i);

                temp.add(a + b);
                if (solve(temp)) output = true;
                temp.remove(a + b);

                temp.add(Math.abs(a - b));
                if (solve(temp)) output = true;
                temp.remove(Math.abs(a - b));

                temp.add(a * b);
                if (solve(temp)) output = true;
                temp.remove(a * b);

                if (b != 0) {
                    temp.add(a / b);
                    if (solve(temp)) output = true;
                    temp.remove(a / b);
                }

                if (a != 0) {
                    temp.add(b / a);
                    if (solve(temp)) output = true;
                    temp.remove(b / a);
                }
            }
        }
        return output;
    }

    // access solutions
    public HashSet<String> getSolutions() {
        return solutions;
    }

    // main method for testing
    public static void main(String[] args) {

        long start = System.nanoTime();
        ArrayList test = new ArrayList<Double>();
        for (String s : args)
            test.add(Double.parseDouble(s));
        System.out.println("Cards: " + test + "\n");

        test24part3 testObject = new test24part3(test);
        int numSols = testObject.getSolutions().size();
        switch (numSols) {
            case 0:
                System.out.println("No solution exists.");
                break;
            case 1:
                System.out.println("The following solution exists.");
                break;
            default:
                System.out.println("The following " + numSols + " solutions exists.");
        }

        for (String s : testObject.getSolutions())
            System.out.println(s);

        long finish = System.nanoTime();
        System.out.println("\n(" + (finish - start) / 1.0e6 + " milliseconds elapsed)");
    }

}
