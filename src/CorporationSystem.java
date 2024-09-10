import java.io.*;
import java.util.*;

class Employee {
    String lastName;
    int age;

    Employee(String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return lastName + " " + age;
    }
}

public class CorporationSystem {
    private static final String FILE_NAME = "employees.txt";
    private static List<Employee> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadEmployeesFromFile();
        while (true) {
            System.out.println("1. Ввод данных");
            System.out.println("2. Редактирование данных");
            System.out.println("3. Удаление сотрудника");
            System.out.println("4. Поиск по фамилии");
            System.out.println("5. Вывод информации по возрасту или первой букве фамилии");
            System.out.println("6. Сохранить в файл и выйти");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    editEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    filterEmployees();
                    break;
                case 6:
                    saveEmployeesToFile();
                    System.out.println("Программа завершена.");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void loadEmployeesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String lastName = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    employees.add(new Employee(lastName, age));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    private static void saveEmployeesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                bw.write(emp.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    private static void addEmployee() {
        System.out.print("Введите фамилию сотрудника: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите возраст сотрудника: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        employees.add(new Employee(lastName, age));
    }

    private static void editEmployee() {
        System.out.print("Введите фамилию сотрудника для редактирования: ");
        String lastName = scanner.nextLine();
        Employee emp = findEmployeeByLastName(lastName);
        if (emp != null) {
            System.out.print("Введите новый возраст: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            emp.age = age;
        } else {
            System.out.println("Сотрудник не найден.");
        }
    }

    private static void deleteEmployee() {
        System.out.print("Введите фамилию сотрудника для удаления: ");
        String lastName = scanner.nextLine();
        Employee emp = findEmployeeByLastName(lastName);
        if (emp != null) {
            employees.remove(emp);
        } else {
            System.out.println("Сотрудник не найден.");
        }
    }

    private static void searchEmployee() {
        System.out.print("Введите фамилию для поиска: ");
        String lastName = scanner.nextLine();
        Employee emp = findEmployeeByLastName(lastName);
        if (emp != null) {
            System.out.println(emp);
        } else {
            System.out.println("Сотрудник не найден.");
        }
    }

    private static void filterEmployees() {
        System.out.println("1. По возрасту");
        System.out.println("2. По первой букве фамилии");
        System.out.print("Выберите фильтр: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                System.out.print("Введите возраст: ");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                for (Employee emp : employees) {
                    if (emp.age == age) {
                        System.out.println(emp);
                    }
                }
                break;
            case 2:
                System.out.print("Введите первую букву фамилии: ");
                char letter = scanner.nextLine().charAt(0);
                for (Employee emp : employees) {
                    if (emp.lastName.charAt(0) == letter) {
                        System.out.println(emp);
                    }
                }
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static Employee findEmployeeByLastName(String lastName) {
        for (Employee emp : employees) {
            if (emp.lastName.equals(lastName)) {
                return emp;
            }
        }
        return null;
    }
}
