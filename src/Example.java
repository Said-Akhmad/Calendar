import java.time.chrono.HijrahChronology;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] array = new String[5];
        int last_empty_cell = 0;
        while (true) {
            while (true) {
                System.out.println("Enter 2 or 1");
                String value = scanner.nextLine();
                if (value.equals("1")) {
                    System.out.println("Enter name:");

                    array[last_empty_cell] = scanner.nextLine();

                    last_empty_cell++;
                    System.out.println(Arrays.toString(array));
                } else if (value.equals("2")) {

                    System.out.println("Enter index:");
                    int index = Integer.parseInt(scanner.nextLine());
                    for (int i = index; i < array.length - 1; i++) {
                        array[i] = array[i + 1];
                    }
                    array[array.length - 1] = null;
                    last_empty_cell--;


                    System.out.println(Arrays.toString(array));
                }


            }


        }
    }
}




