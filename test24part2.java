import java.util.ArrayList;


// version 2: returns whether solution exists
public class test24part2 {

    private static final int SOL = 24;

    private static boolean solve(Iterable<Double> input) {
        ArrayList<Double> nums = (ArrayList) input;
        int len = nums.size();
        if (len == 0) return false;
        if (len == 2) {
            double a = nums.get(0);
            double b = nums.get(1);
            if (a + b == SOL || Math.abs(a - b) == SOL || a * b == SOL || b != 0 && a / b == SOL || a != 0 && b / a == SOL) {
                System.out.println(nums);
                return true;
            }
            return false;
        }

        int sum = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                ArrayList<Double> temp = (ArrayList) nums.clone();
                double a = temp.remove(j);
                double b = temp.remove(i);
                temp.add(0, a + b);
                if (solve(temp)) return true;
                temp.remove(0);

                temp.add(0, Math.abs(a - b));
                if (solve(temp)) return true;
                temp.remove(0);

                temp.add(0, a * b);
                if (solve(temp)) return true;
                temp.remove(0);

                if (b != 0) {
                    temp.add(0, a / b);
                    if (solve(temp)) return true;
                    temp.remove(0);
                }

                if (a != 0) {
                    temp.add(0, b / a);
                    if (solve(temp)) return true;
                    temp.remove(0);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

        ArrayList test = new ArrayList<Double>();
        test.add(1.0);
        test.add(5.0);
        test.add(13.0);
        test.add(4.0);

        System.out.println(solve(test));
    }

}
