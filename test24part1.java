import java.util.ArrayList;

// version 1: returns number of paths that end at 24
public class test24part1 {

    private static int solve(Iterable<Double> input) {
        ArrayList<Double> nums = (ArrayList) input;
        int len = nums.size();
        if (len == 0) return 0;
        if (len == 1) return nums.get(0) == 24 ? 1 : 0;

        int sum = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                ArrayList<Double> temp = (ArrayList) nums.clone();
                double a = temp.remove(j);
                double b = temp.remove(i);
                temp.add(0, a + b);
                System.out.println(temp);
                sum += solve(temp);
                temp.remove(0);

                temp.add(0, Math.abs(a - b));
                sum += solve(temp);
                temp.remove(0);

                temp.add(0, a * b);
                sum += solve(temp);
                temp.remove(0);

                if (b != 0) temp.add(0, a / b);
                sum += solve(temp);
                temp.remove(0);

                if (a != 0) temp.add(0, b / a);
                sum += solve(temp);
                temp.remove(0);
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        ArrayList test = new ArrayList<Double>();
        test.add(15.0);
        test.add(2.0);
        test.add(3.0);
        test.add(4.0);

        System.out.println(solve(test));
    }

}
