package sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
/*
* This class is the main class of the program.
* It is used to parse the command line and to process the sorting of the data.
* It takes in command line arguments and parses them to set flags.
* It then reads the data from a file or from the console and processes the sorting.
* It then prints the sorted data to the console or to a file.
*/
public class Main {
    public static void main(final String[] args) {

        // Command line options that can be parsed:
        boolean nums = false;
        boolean lines = false;
        boolean words = false;
        boolean byCount = false;
        boolean natural = true;
        String inputFileName = null;
        String outputFileName = null;

        // Parsing the command line and setting flags.
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-dataType":
                    if (i + 1 < args.length) {
                        switch (args[++i]) {
                            case "long" -> nums = true;
                            case "line" -> lines = true;
                            case "word" -> words = true;
                            default -> {
                                System.out.println("No data type defined!");
                                return;
                            }
                        }
                    } else {
                        System.out.println("No data type defined!");
                        return;
                    }
                    break;
                case "-sortingType":
                    if (i + 1 < args.length) {
                        switch (args[++i]) {
                            case "byCount" -> {
                                byCount = true;
                                natural = false;
                            }
                            case "natural" -> {
                                natural = true;
                                byCount = false;
                            }
                            default -> {
                                System.out.println("No sorting type defined!");
                                return;
                            }
                        }
                    } else {
                        System.out.println("No sorting type defined!");
                        return;
                    }
                    break;
                case "-inputFile":
                    if (i + 1 < args.length) {
                        inputFileName = args[++i];
                    } else {
                        System.out.println("No input file defined!");
                        return;
                    }
                    break;
                case "-outputFile":
                    if (i + 1 < args.length) {
                        outputFileName = args[++i];
                    } else {
                        System.out.println("No output file defined!");
                        return;
                    }
                    break;
                default:
                    System.out.printf("\"%s\" is not a valid parameter. It will be skipped.%n", args[i]);
            }
        }
        // Default case for command line.
        if (!nums && !words && !lines) {
            words = true;
        }

        // initializing a Scanner object for use in file reading / input reading
        Scanner scanner;
        // null check
        if (inputFileName != null) {
            try {
                scanner = new Scanner(new File(inputFileName));
            } catch (FileNotFoundException e) {
                System.out.printf("File not found: %s%n", inputFileName);
                return;
            }
        } else {
            scanner = new Scanner(System.in);
        }

        PrintWriter printWriter = null;
        // null check
        if (outputFileName != null) {
            try {
                printWriter = new PrintWriter(new File(outputFileName));
            } catch (FileNotFoundException e) {
                System.out.printf("File not found: %s%n", outputFileName);
                return;
            }
        }
        // Numbers Flag
        if (nums) {
            ArrayList<Long> list = new ArrayList<>();
            while (scanner.hasNext()) {
                if (scanner.hasNextLong()) {
                    list.add(scanner.nextLong());
                } else {
                    System.out.printf("\"%s\" is not a long. It will be skipped.%n", scanner.next());
                }
            }
            processSorting(list, byCount, printWriter);
        } else if (lines) {
            ArrayList<String> list = new ArrayList<>();
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            processSorting(list, byCount, printWriter);
        } else {
            ArrayList<String> list = new ArrayList<>();
            while (scanner.hasNext()) {
                list.add(scanner.next());
            }
            processSorting(list, byCount, printWriter);
        }
        // Closing the scanner and the printWriter
        if (printWriter != null) {
            printWriter.close();
        }
    }
    /*
    * This method is used to process the sorting of the data.
    * It takes in an ArrayList of type T, a boolean for sorting by count, and a PrintWriter object.
    * It then sorts the data and prints it to the console or to a file.
    */
    public static <T extends Comparable<T>> void processSorting(ArrayList<T> list, boolean byCount, PrintWriter printWriter) {
        if (byCount) {
            Map<T, Integer> countMap = new HashMap<>();
            for (T item : list) {
                countMap.put(item, countMap.getOrDefault(item, 0) + 1);
            }

            // Sorting the data by count
            List<Map.Entry<T, Integer>> entryList = new ArrayList<>(countMap.entrySet());
            entryList.sort((e1, e2) -> {
                int cmp = e1.getValue().compareTo(e2.getValue());
                if (cmp != 0) {
                    return cmp;
                } else {
                    return e1.getKey().compareTo(e2.getKey());
                }
            });

            // Printing the data to the console or to a file.
            String output = String.format("Total %ss: %d.%n", list.get(0) instanceof String ? "words" : "numbers", list.size());
            if (printWriter == null) {
                System.out.print(output);
            } else {
                printWriter.print(output);
            }
            // Printing the data to the console or to a file.
            for (Map.Entry<T, Integer> entry : entryList) {
                double percentage = 100.0 * entry.getValue() / list.size();
                output = String.format("%s: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                if (printWriter == null) {
                    System.out.print(output);
                } else {
                    printWriter.print(output);
                }
            }
        } else {

            // Sorting the data naturally
            list.sort(null);
            String output = String.format("Total %ss: %d.%n", list.get(0) instanceof String ? "words" : "numbers", list.size());
            if (printWriter == null) {
                System.out.print(output);
            } else {
                printWriter.print(output);
            }

            // Printing the data to the console or to a file.
            output = "Sorted data: ";
            for (T item : list) {
                output += item.toString() + " ";
            }
            output += System.lineSeparator();
            if (printWriter == null) {
                System.out.print(output);
            } else {
                printWriter.print(output);
            }
        }
    }
}



