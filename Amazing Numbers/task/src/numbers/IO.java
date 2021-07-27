package numbers;

import java.util.*;

public class IO {

    public static void start() {
        IO io = new IO();
        io.printMenu();
        String input = null;
        while(input != "0") {
            input = io.loadInput();
            if (input.equals("0")) break;
            //InputType type = io.determineInputType(input);
            try {
                io.checkCorrectness(input);
                io.executeCommand(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //e.printStackTrace();
            }


        }
        System.out.println("Goodbye!");
    }

    static Scanner sc = new Scanner(System.in);

    public void executeCommand(String line) {
        switch (determineInputType(line)) {
            case SINGLE_NUMBER:
                Numbers.printProperties(Long.parseLong(line));
                break;
            case TWO_NUMBERS:
                String[] splitted = line.split(" ");
                long n = Long.parseLong(splitted[0]);
                long numOfNums = Long.parseLong(splitted[1]);

                for (long i = n ; i < n + numOfNums ; i++) {
                    Numbers.printPropertiesInLine(i);
                }
                break;
            case TWO_NUMBERS_COMMAND:
                splitted = line.split(" ");
                n = Long.parseLong(splitted[0]);
                numOfNums = Long.parseLong(splitted[1]);
                String searchedProperty = splitted[2];
                //Property[] tab = Property.values();

                long counter = 0;
                for (long i = n ; counter < numOfNums ; i++) {
                    if (isProperty(searchedProperty, i)) {
                        Numbers.printPropertiesInLine(i);
                        counter++;
                    }
                }
                break;
            case MULTIPLE_PROPERTIES:
                splitted = line.split(" ");
                n = Long.parseLong(splitted[0]);
                numOfNums = Long.parseLong(splitted[1]);
                HashSet<String> inputCommands = new HashSet<String>();
                // multiple the same input properties reduced due set
                long cnt = 0;
                for (int i = 2; i < splitted.length; i++) {
                    inputCommands.add(splitted[i].toUpperCase());
                }
                for (long i = n ; cnt < numOfNums ; i++) {
                    if (isMultipleProperty(inputCommands, i)) {
                        Numbers.printPropertiesInLine(i);
                        cnt++;
                    }
                }
                break;

        }
    }

    public boolean isMultipleProperty(HashSet<String> multipleProperties, long n) {
        String[] properties = multipleProperties.toArray(new String[multipleProperties.size()]);
        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            if (!isProperty(properties[i], n)) {
                return false;
            }

        }
        return true;
    }

    public boolean isProperty(String property, long n) {
        property = property.toUpperCase();
        if (property.charAt(0) == '-')
            return !isProperty(property.substring(1), n);

        switch (property) {
            case "ODD" :
                return !Numbers.isEven(n);
            case "EVEN":
                return Numbers.isEven(n);
            case "BUZZ":
                return Numbers.isBuzz(n);
            case "DUCK":
                return Numbers.isDuck(n);
            case "PALINDROMIC":
                return Numbers.isPalindromic(n);
            case "GAPFUL":
                return Numbers.isGapful(n);
            case "SPY":
                return Numbers.isSpy(n);
            case "SQUARE":
                return Numbers.isSquare(n);
            case "SUNNY":
                return Numbers.isSunny(n);
            case "JUMPING":
                return Numbers.isJumping(n);
            case "HAPPY":
                return Numbers.isHappy(n);
            case "SAD":
                return !Numbers.isHappy(n);
        }
        return false;
    }



    public void checkCorrectness(String line) throws Exception {
        switch (determineInputType(line)) {
            case SINGLE_NUMBER:
                checkSingleNum(line);
                break;
            case TWO_NUMBERS:
                checkDoubleNum(line);
                break;
            case TWO_NUMBERS_COMMAND:
                checkTwoNumbersCommand(line);
                break;
            case MULTIPLE_PROPERTIES:
                checkMultipleProperties(line);

        }
    }

    private void checkMultipleProperties(String line) throws Exception{
        String[] spl = line.split(" ");
        long n = Long.parseLong(spl[0]);
        long numOfNums = Long.parseLong(spl[1]);
        if (numOfNums < 1)
            throw new Exception("The second parameter should be a natural number.");
        if (n < 0)
            throw new Exception("The first parameter should be a natural number or zero.");

        HashSet<String> inputCommands = new HashSet<String>();
        // multiple the same input properties reduced due set
        for (int i = 2; i < spl.length; i++) {
            inputCommands.add(spl[i].toUpperCase());
        }
        //check if all commands entered by user are allowed
        String[] commands = new String[] { "EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD"};
        String[] impCmd = inputCommands.toArray(new String[inputCommands.size()]);
        ArrayList<String> wrongProperties = new ArrayList<String>();
        for (int i = 0; i < inputCommands.size(); i++) {
            String command = impCmd[i];

            //stage 8 : add minus before command to exclude
            String coreCmd = command;
            if (command.charAt(0) == '-')
                coreCmd = command.substring(1, command.length());

            if (!Arrays.asList(commands).contains(coreCmd)) {
                wrongProperties.add(command);

            }
        }

        if (wrongProperties.size() == 1) {
            throw new Exception("The property [" + wrongProperties.get(0) + "] is wrong.\n" +
                    "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY, JUMPING, HAPPY, SAD ]");
        }
        if (wrongProperties.size() > 1) {
            String msg = "The properties [ ";
            for (int i = 0; i < wrongProperties.size(); i++) {
                msg += wrongProperties.get(i) + ", ";
            }
            msg = msg.substring(0, msg.length() - 2);
            msg += "] are wrong.\n" +
                    "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY, JUMPING, HAPPY, SAD ]";
            throw new Exception(msg);
        }


        //check for exclusive properties
        checkForExclusiveProperties(inputCommands);

    }
    private void checkForExclusiveProperties(HashSet<String> set) throws Exception {
        //check minus properties
        checkMinuses(set);

        if (set.contains("EVEN") && set.contains("ODD")) {
            throw new Exception("The request contains mutually exclusive properties: [ODD, EVEN]\n" +
                    "There are no numbers with these properties.");
        }
        if (set.contains("DUCK") && set.contains("SPY")) {
            throw new Exception("The request contains mutually exclusive properties: [DUCK, SPY]\n" +
                    "There are no numbers with these properties.");
        }
        if (set.contains("SUNNY") && set.contains("SQUARE")) {
            throw new Exception("The request contains mutually exclusive properties: [SUNNY, SQUARE]\n" +
                    "There are no numbers with these properties.");
        }


        //stage 8
        if (set.contains("-EVEN") && set.contains("-ODD")) {
            throw new Exception("The request contains mutually exclusive properties: [-ODD, -EVEN]\n" +
                    "There are no numbers with these properties.");
        }
        if (set.contains("HAPPY") && set.contains("SAD"))
            throw new Exception("The request contains mutually exclusive properties: [HAPPY, SAD]\n" +
                    "There are no numbers with these properties.");
        if (set.contains("-HAPPY") && set.contains("-SAD"))
            throw new Exception("The request contains mutually exclusive properties: [-HAPPY, -SAD]\n" +
                    "There are no numbers with these properties.");


    }

    private void checkMinuses(HashSet<String> set) throws Exception {
        Property[] properties = Property.values();
        for (int i = 0; i < properties.length; i++) {
            String p = properties[i].toString();
            String minus = "-" + p;
            if (set.contains(minus) && set.contains(p))
                throw new Exception("The request contains mutually exclusive properties: ["+p+", "+ minus+"]\n" +
                        "There are no numbers with these properties.");
        }
    }

    private void checkTwoNumbersCommand(String line) throws Exception {
        String[] spl = line.split(" ");
        long n = Long.parseLong(spl[0]);
        long numOfNums = Long.parseLong(spl[1]);

        if (numOfNums < 1)
            throw new Exception("The second parameter should be a natural number.");

        if (n < 0)
            throw new Exception("The first parameter should be a natural number or zero.");

        String command = spl[2];
        command = command.toUpperCase();

        //stage 8 : add minus before command to exclude
        String coreCmd = command;
        if (command.charAt(0) == '-')
            coreCmd = command.substring(1, command.length());

        String[] commands = new String[] { "EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD"};
        if (!Arrays.asList(commands).contains(coreCmd)) {
            throw new Exception("The property [" + command + "] is wrong.\n" +
                    "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY, JUMPING, HAPPY, SAD ]");
        }
    }
    private void checkDoubleNum(String line) throws Exception {
        String[] spl = line.split(" ");
        long n = Long.parseLong(spl[0]);
        long numOfNums = Long.parseLong(spl[1]);
        if (numOfNums < 1)
            throw new Exception("The second parameter should be a natural number.");

        if (n < 0)
            throw new Exception("The first parameter should be a natural number or zero.");
    }

    private void checkSingleNum(String line) throws Exception {
        long n = Long.parseLong(line);
        if (n < 0)
            throw new Exception("The first parameter should be a natural number or zero.");
    }

    private String[] splitInput(String line) {
        return line.split(" ");
    }

    public InputType determineInputType(String line) {
        String[] splitted = line.split(" ");
        if (splitted.length > 4)
            return InputType.MULTIPLE_PROPERTIES;
        switch (splitted.length) {
            case 1:
                return InputType.SINGLE_NUMBER;
            case 2:
                return InputType.TWO_NUMBERS;
            case 3:
                return InputType.TWO_NUMBERS_COMMAND;
            case 4:
                return InputType.MULTIPLE_PROPERTIES;
            default:
                throw new IllegalArgumentException("input command not recognized");

        }
    }
    public String loadInput() {
        System.out.println("Enter a request:");
        System.out.println();
        String line = sc.nextLine();
        return line;
    }

    public void printMenu() {
        System.out.println("Welcome to Amazing Numbers!\nSupported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
    }



}

enum InputType {
    SINGLE_NUMBER, TWO_NUMBERS, TWO_NUMBERS_COMMAND, MULTIPLE_PROPERTIES
}

enum Property {
    BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY, JUMPING, HAPPY, SAD
}