package numbers;

import java.util.ArrayList;
import java.util.List;

public class Numbers {


    public static void printProperties(long n) {
        System.out.println("Properties of " + n);
        System.out.println("even: " + isEven(n));
        System.out.println("odd: " + !isEven(n));
        System.out.println("buzz: " + isBuzz(n));
        System.out.println("duck: " + isDuck(n));
        System.out.println("palindromic: " + isPalindromic(n));
        System.out.println("gapful: " + isGapful(n));
        System.out.println("spy: " + isSpy(n));
        System.out.println("square: " + isSquare(n));
        System.out.println("sunny: " + isSunny(n));
        System.out.println("jumping: " + isJumping(n));
        System.out.println("happy: " + isHappy(n));
        System.out.println("sad: " + !isHappy(n));

        System.out.println();
    }
    public static void printPropertiesInLine(long n) {
        System.out.print(n + " is ");
        String properties = "";
        properties += isEven(n) ? "even, " : "odd, ";
        if (isBuzz(n)) properties += "buzz, ";
        if (isDuck(n)) properties += "duck, ";
        if (isPalindromic(n)) properties += "palindromic, ";
        if (isGapful(n)) properties += "gapeful, ";
        if (isSpy(n)) properties += "spy, ";
        if (isSquare(n)) properties += "square, ";
        if (isSunny(n)) properties += "sunny, ";
        if (isJumping(n)) properties += "jumping, ";
        if (isHappy(n)) properties += "happy, ";
        if (!isHappy(n)) properties += "sad, ";
        System.out.println(properties.substring(0, properties.length() - 2));
    }

    public static boolean isHappy(long n) {
        return isHappy(n, new ArrayList<Long>());
    }

    private static boolean isHappy(long n, List<Long> visited) {
        if (n == 0) return false;
        if (n == 1) return true;

        if (visited.contains(n))
            return false;
        visited.add(n);

        String str = "" + n;
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int x = Integer.parseInt(""  + str.charAt(i));
            sum += x * x;
        }
        return isHappy(sum, visited);
    }

    public static boolean isJumping(long n) {
        String str = "" + n;
        if (n < 10) {
            return true;
        }
        for (int i = 1; i < str.length(); i++) {
            int x = Integer.parseInt("" + str.charAt(i));
            int previous = Integer.parseInt("" + str.charAt(i - 1));
            int abs = Math.abs(x - previous);
            if (Math.abs(x - previous) != 1)
                return false;
        }
        return true;
    }

    public static boolean isSunny(long n) {
        return isSquare(n + 1);
    }

    public static boolean isSquare(long n) {
        double x = Math.sqrt(n);
        int integerPart = (int) x;
        double fractionalPart = x - integerPart;
        return fractionalPart == 0;

    }

    public static boolean isSpy(long n) {
        String str = "" + n;
        long sum = 0;
        long product = 1;
        for (int i = 0; i < str.length(); i++) {
            long value = (long) str.charAt(i) - 48;
            sum += value;
            product *= value;
        }
        return sum == product;
    }

    public static boolean isGapful(long n) {
        String str = "" + n;
        if (str.length() < 3) {
            return false;
        } else {
            char first = str.charAt(0);
            char last = str.charAt(str.length() - 1);
            String fl = "" + first + last;
            long fll = Long.parseLong(fl);
            if (n % fll != 0)
                return false;
        }
        return true;
    }

    public static boolean isPalindromic(long n) {
        StringBuilder sb = new StringBuilder("" + n);
        String s1 = sb.toString();
        sb.reverse(); // StringBuilder is mutable
        String s2 = sb.toString();
        return s1.equals(s2);
    }

    public static boolean isDuck(long n) {
        return (""+n).contains("0");
    }

    public static void explanation(long n) {
        System.out.println("Explanation:");
        if (n % 7 != 0 && n % 10 != 7) {
            System.out.println(n + " is neither divisible by 7 nor does it end with 7.");
        } else if (n % 7 == 0 && n % 10 != 7) {
            System.out.println(n + " is divisible by 7");
        } else if (n % 7 != 0 && n % 10 == 7) {
            System.out.println(n + " ends with 7");
        } else if (isBuzz(n)) {
            System.out.println(n + " is divisible by 7 and ends with 7");
        }
    }

    public static boolean isBuzz(long n) {
        if (n % 7 == 0 || n % 10 == 7)
            return true;
        else return false;
    }

    public static boolean isNatural(long n) {
        return n > 0;
    }
    public static boolean isEven(long n) {
        return n % 2 == 0;
    }
}
