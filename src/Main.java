import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    private static String filePath1 = "text1.txt";
    private static String filePath2 = "text2.txt";


    public static void main(String[] args) {
        search();
        seacrhMaxLengthStr();
        arrayNumberFile();
        writeToFile();
    }

    public static void writeToFile(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите путь к файлу:");
        String filePath = scanner.nextLine();

        System.out.println("Введите целые числа через пробел:");
        String[] input = scanner.nextLine().split("\\s+");
        int[] array = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            array[i] = Integer.parseInt(input[i]);
        }

        List<Integer> evenNumbers = new ArrayList<>();
        List<Integer> oddNumbers = new ArrayList<>();

        for (int num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            } else {
                oddNumbers.add(num);
            }
        }

        int[] reversedArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(arrayToString(array));
            writer.newLine();

            writer.write(listToString(evenNumbers));
            writer.newLine();

            writer.write(listToString(oddNumbers));
            writer.newLine();

            writer.write(arrayToString(reversedArray));
            writer.newLine();

            System.out.println("Данные успешно записаны в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }

        scanner.close();
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }

    private static String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int num : list) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }

    public static void arrayNumberFile(){
        String fileName = "arrayInt.txt"; // Имя файла с данными
        List<int[]> arrays = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.trim().split("\\s+");
                int[] array = Arrays.stream(elements).mapToInt(Integer::parseInt).toArray();
                arrays.add(array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int globalMax = Integer.MIN_VALUE;
        int globalMin = Integer.MAX_VALUE;
        int totalSum = 0;

        for (int i = 0; i < arrays.size(); i++) {
            int[] array = arrays.get(i);
            System.out.println("Массив " + (i + 1) + ": " + Arrays.toString(array));

            int max = Arrays.stream(array).max().getAsInt();
            int min = Arrays.stream(array).min().getAsInt();
            int sum = Arrays.stream(array).sum();

            System.out.println("Максимум: " + max);
            System.out.println("Минимум: " + min);
            System.out.println("Сумма: " + sum);
            System.out.println();

            globalMax = Math.max(globalMax, max);
            globalMin = Math.min(globalMin, min);
            totalSum += sum;
        }

        System.out.println("Глобальный максимум: " + globalMax);
        System.out.println("Глобальный минимум: " + globalMin);
        System.out.println("Общая сумма всех массивов: " + totalSum);
    }


    public static void seacrhMaxLengthStr(){
        try {
            Stream<String> stream1 = Files.lines(Paths.get(filePath2));
            List<String> list1 = stream1.collect(Collectors.toList());
            int myIndex = 0;


            for (int i = 0; i < list1.size(); i++) {


                if (list1.get(i).length() > list1.get(myIndex).length()) {
                    myIndex = i;
                }
            }
            System.out.println("Length: " + list1.get(myIndex).length() + "  Str : " + list1.get(myIndex));
        }
        catch (Exception e){
            System.out.println("Ошибка с файлами: " + e.getMessage());
        }



    }

    public static void search() {
        try {
            Stream<String> stream1 = Files.lines(Paths.get(filePath1));
            List<String> list1 = stream1.collect(Collectors.toList());

            Stream<String> stream2 = Files.lines(Paths.get(filePath2));
            List<String> list2 = stream2.collect(Collectors.toList());

            for (String line1 : list1) {
                boolean matchFound = false;
                for (String line2 : list2) {
                    if (line1.equals(line2)) {
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    System.out.println("Несовпадающая строка из первого файла: " + line1);
                }
            }

            for (String line2 : list2) {
                boolean matchFound = false;
                for (String line1 : list1) {
                    if (line2.equals(line1)) {
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    System.out.println("Несовпадающая строка из второго файла: " + line2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}






