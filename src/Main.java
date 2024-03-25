import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), " +
                "номер телефона (число без разделителей) " +
                "и пол(символ латиницей f или m), разделенные пробелом");
        String userData = scanner.nextLine();

        try {
            processUserData(userData);
            System.out.println("Данные сохранены.");
        } catch (IllegalArgumentException | IOException e){
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void processUserData(String userData) throws IllegalArgumentException, IOException {
        String[] dataParts = userData.split(" ");
        if (dataParts.length > 6){
            throw new IllegalArgumentException("Вы ввели больше данных, чем требуется");
        }
        if (dataParts.length < 6){
            throw new IllegalArgumentException("Вы ввели меньше данных, чем требуется");
        }

        String surname = dataParts[0];
        String name = dataParts[1];
        String middleName = dataParts[2];
        String dateOfBirth = dataParts[3];
        String phone = dataParts[4];
        String gender = dataParts[5];

        if (!Pattern.matches("\\d{2}.\\d{2}.\\d{4}", dateOfBirth)){
            throw new IllegalArgumentException("Неправильно введена дата рождения");
        }
        if (!Pattern.matches("\\d+", phone)){
            throw new IllegalArgumentException("Неправильно введен номер телефона");
        }
        if (!gender.equals("f") && !gender.equals("m")){
            throw new IllegalArgumentException("Неправильно введен пол");
        }

        String fileName = surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write("\n");
            }
            fileWriter.write(surname + " " + name + " " + middleName + " " + dateOfBirth + " " + phone + " " + gender);
        } catch (IOException e){
            throw new FileSystemException("Ошибка при работе с файлом");
        }
    }
}
