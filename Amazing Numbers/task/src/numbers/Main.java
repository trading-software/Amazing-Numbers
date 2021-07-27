package numbers;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        //menu();
        IO.start();
    }

    private static void menu() {
        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameters show how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and a property to search for;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
        loop();
    }

    private static void loop() {
        long n;

        do {
            long[] inputArray = new long[0];
            try {
                inputArray = input();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                loop();
                break;

            }
            n = inputArray[1];

            long numOfNums = inputArray[0];

          if (numOfNums == -1) {
                if (n < 0) {
                    System.out.println("The first parameter should be a natural number or zero.");
                } else {
                    printProperties(n);
                }
            } else { // range input
                for (long i = n; i < n + numOfNums; i++) {
                   // printPropertiesInLine(i);
                }
            }
        } while (n != 0);

        System.out.println("Goodbye!");
    }

    private static long[] input() throws IllegalArgumentException {
        System.out.println("Enter a request:");
        String line = sc.nextLine();





        if (isDoubleInput(line)) {
            String[] splitted = line.split(" ");
            long n = Long.parseLong(splitted[0]);
            long numOfNums = Long.parseLong(splitted[1]);
            if (numOfNums < 1)
                throw new IllegalArgumentException("second parameter should be a natural number");
            return new long[]{numOfNums, n};
        } else {
            long n = Long.parseLong(line);
            return new long[]{-1, n};
        }
    }

    private static boolean containsAnyCommand(String line) {
        String[] commands = new String[] { "EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY"};
        for (int i = 0; i < commands.length; i++) {
            if (line.contains(commands[i])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDoubleInput(String str) {
        return str.contains(" ");
    }

    private static void printProperties(long n) {
        System.out.println("Properties of " + n);
        System.out.println("even: " + isEven(n));
        System.out.println("odd: " + !isEven(n));
        System.out.println("buzz: " + isBuzz(n));
        System.out.println("duck: " + isDuck(n));
        System.out.println("palindromic: " + isPalindromic(n));
        System.out.println("gapful: " + isGapful(n));
        System.out.println("spy: " + isSpy(n));
        System.out.println();
    }





    private static boolean isSpy(long n) {
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

    private static boolean isGapful(long n) {
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

    private static boolean isPalindromic(long n) {
        StringBuilder sb = new StringBuilder("" + n);
        String s1 = sb.toString();
        sb.reverse(); // StringBuilder is mutable
        String s2 = sb.toString();
        return s1.equals(s2);
    }

    private static boolean isDuck(long n) {
        return (""+n).contains("0");
    }

    private static void explanation(long n) {
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

    private static boolean isBuzz(long n) {
        if (n % 7 == 0 || n % 10 == 7)
            return true;
        else return false;
    }

    private static boolean isNatural(long n) {
        return n > 0;
    }
    private static boolean isEven(long n) {
        return n % 2 == 0;
    }
}
