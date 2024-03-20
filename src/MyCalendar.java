import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class MyCalendar {
    final static int EXTSIZE = 20;
    final static int TOTALCOLUMNS = 8;

    final static int DAY = 0;
    final static int MONTH = 1;
    final static int YEAR = 2;
    final static int HIJRAH = 3;
    final static int NAME = 4;
    final static int TYPE = 5;
    final static int PLACE = 6;
    final static int PARTICIPANTS = 7;
    static int LASTEMPTYCELL = 0;
    static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        Scanner scan = new Scanner(System.in);

        String[][] calendar_events = new String[100][TOTALCOLUMNS];
        String[][] array_to_sort;


        System.out.println("Добро пожаловать в Календарь важных событий!");
        while (true) {
            System.out.println("Выберите операцию:");
            System.out.println("Введите (add) для добавления события");
            System.out.println("Введите (delete) для удаления события");
            System.out.println("Введите (show) для вывода всех событий");
            System.out.println("Введите (search) для поиска события");
            System.out.println("Введите (update) для изменения" +
                    " события");
            String operation = scan.nextLine();
            switch (operation) {
                case "add":
                    add_or_update_event(calendar_events, 0, scan, false);
                    LASTEMPTYCELL++;
                    break;

                case "delete":
                    show(calendar_events);
                    System.out.println("Введите индекс:");
                    int index = Integer.parseInt(scan.nextLine());
                    for (int i = index; i < LASTEMPTYCELL - 1; i++) {
                        for (int j = 0; j < calendar_events[0].length; j++) {
                            calendar_events[i][j] = calendar_events[i + 1][j];
                        }
                    }
                    calendar_events[LASTEMPTYCELL - 1][DAY] = null;
                    calendar_events[LASTEMPTYCELL - 1][MONTH] = null;
                    calendar_events[LASTEMPTYCELL - 1][YEAR] = null;
                    calendar_events[LASTEMPTYCELL - 1][HIJRAH] = null;
                    calendar_events[LASTEMPTYCELL - 1][NAME] = null;
                    calendar_events[LASTEMPTYCELL - 1][TYPE] = null;
                    calendar_events[LASTEMPTYCELL - 1][PLACE] = null;
                    calendar_events[LASTEMPTYCELL - 1][PARTICIPANTS] = null;
                    LASTEMPTYCELL--;
                    show(calendar_events);

                    break;

                case "show":
                    array_to_sort = copyOneContactToAnother(calendar_events);
                    sortedOutput(array_to_sort);


                    break;
                case "search":
                    System.out.println("Введите (1) для поиска события по названию");
                    System.out.println("Введите (2) для поиска событий по промежутку дат");
                    operation = scan.nextLine();
                    switch (operation) {
                        case "1": {
                            System.out.println("Введите название событие:");
                            String value_1 = scan.nextLine();
                            for (int i = 0; i < LASTEMPTYCELL; i++) {
                                for (int j = 0; j < calendar_events[0].length; j++) {
                                    if (calendar_events[i][j].contains(value_1)) {
                                        System.out.println("[" + calendar_events[i][DAY] + "]");
                                        System.out.println("[" + calendar_events[i][MONTH] + "]");
                                        System.out.println("[" + calendar_events[i][YEAR] + "]");
                                        System.out.println("[" + calendar_events[i][HIJRAH] + "]");
                                        System.out.println("[" + calendar_events[i][NAME] + "]");
                                        System.out.println("[" + calendar_events[i][TYPE] + "]");
                                        System.out.println("[" + calendar_events[i][PLACE] + "]");
                                        System.out.println("[" + calendar_events[i][PARTICIPANTS] + "]");
                                    }
                                }
                            }
                        }
                        case "2": {
                            array_to_sort = copyOneContactToAnother(calendar_events);
                            String[][] sorted_by_month = sort(array_to_sort, MONTH);

                            //   Arrays.stream(sorted_by_month).filter(row -> row[0] != null).forEach(arr -> System.out.println(Arrays.toString(arr)));
                            show(sorted_by_month);
                            int index_from = 0;
                            int index_to = 0;
                            System.out.println("Введите дату поиска от:");
                            System.out.println("День:");
                            int day_from = Integer.parseInt(scan.nextLine());
                            System.out.println("Месяц:");
                            int month_from = Integer.parseInt(scan.nextLine()) - 1;
                            System.out.println("Введите дату поиска до:");
                            System.out.println("День:");
                            int day_to = Integer.parseInt(scan.nextLine());
                            System.out.println("Месяц:");
                            int month_to = Integer.parseInt(scan.nextLine()) - 1;

                            for (int i = 0; i < LASTEMPTYCELL; i++) {
                                System.out.print(i + ": ");
                                for (int j = 0; j < sorted_by_month[0].length; j++) {
                                    System.out.print("[" + sorted_by_month[i][j] + "]");
                                }
                                System.out.println();
                            }


                            for (int i = 0; i < LASTEMPTYCELL; i++) {
                                for (int j = 0; j < sorted_by_month[0].length; j++) {
                                    if (day_from == Integer.parseInt(sorted_by_month[i][DAY]) && month_from == Integer.parseInt(sorted_by_month[i][MONTH])) {
                                        index_from = i;
                                    }
                                }
                            }

                            for (int i = 0; i < LASTEMPTYCELL; i++) {
                                for (int j = 0; j < sorted_by_month[0].length; j++) {
                                    if (day_to == Integer.parseInt(sorted_by_month[i][DAY]) && month_to == Integer.parseInt(sorted_by_month[i][MONTH])) {
                                        index_to = i;
                                    }
                                }
                            }
                            System.out.println("-----------------------------------------------------------------");
                            for (int i = index_from; i <= index_to; i++) {

                                System.out.print("[" + sorted_by_month[i][DAY] + "]");
                                System.out.print("[" + (Integer.parseInt(sorted_by_month[i][MONTH]) + 1) + "]");
                                System.out.print("[" + sorted_by_month[i][YEAR] + "]");
                                System.out.println("[" + sorted_by_month[i][HIJRAH] + "]");

                            }


                        }
                    }


                    break;
                case "update":
                    show(calendar_events);
                    System.out.println("Введите индекс:");
                    int index_to_update = Integer.parseInt(scan.nextLine());
                    add_or_update_event(calendar_events, index_to_update, scan, true);

                    break;
                case "exit":
                    return;
                default:

            }

            calendar_events_for_tomorrow(calendar_events);


        }
    }

    private static void add_or_update_event(String[][] calendar_events, int index_to_update, Scanner scan, boolean flag) {
        int index;
        if (flag) {
            index = index_to_update;
        } else {
            index = LASTEMPTYCELL;
        }
        Calendar calendar = Calendar.getInstance();

        System.out.println("Ведите год:");
        int value;
        int year;
        while (true) {
            value = Integer.parseInt(scan.nextLine());
            if (value >= calendar.get(Calendar.YEAR)) {
                year = value;
                break;
            } else {
                System.out.println("Некорректные данные!");
            }
        }
        boolean leap_year_flag = leapyear(year);


        System.out.println("Ведите месяц:");
        int month;
        while (true) {
            value = Integer.parseInt(scan.nextLine());
            if (value <= 12 && value > 0) {
                month = value - 1;
                break;
            } else {
                System.out.println("Некорректные данные!");
            }
        }

        System.out.println("Ведите день:");
        int day;

        while (true) {
            value = Integer.parseInt(scan.nextLine());
            if (leap_year_flag && month == 1) {
                if (value <= 29 && value >= 0) {
                    day = value;
                    break;
                } else {
                    System.out.println("Некорректные данные!");
                }
            } else if (month == 1) {
                if (value <= 28 && value >= 0) {
                    day = value;
                    break;
                } else {
                    System.out.println("Некорректные данные!");
                }
            } else {
                if (value <= 31 && value >= 0) {
                    day = value;
                    break;
                } else {
                    System.out.println("Некорректные данные!");
                }
            }


        }

















        calendar.set(year, month, day);
        calendar_events[index][DAY] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        calendar_events[index][MONTH] = String.valueOf(calendar.get(Calendar.MONTH));
        calendar_events[index][YEAR] = String.valueOf(calendar.get(Calendar.YEAR));


        LocalDate gregorianDate = LocalDate.of(calendar.get(Calendar.YEAR), month, day);
        HijrahDate hijrahDate = HijrahDate.from(gregorianDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        String hijriDateString = hijrahDate.format(formatter);
        calendar_events[index][HIJRAH] = hijriDateString;

//                    System.out.println("Ведите названия:");
//                    calendar_events[index][NAME] = scan.nextLine();
//                    System.out.println("Ведите тип:");
//                    calendar_events[index][TYPE] = scan.nextLine();
//                    System.out.println("Введите место:");
//                    calendar_events[index][PLACE] = scan.nextLine();
//                    System.out.println("Ведите участников:");
//                    calendar_events[index][PARTICIPANTS] = scan.nextLine();


    }


    private static String[][] sort(String[][] calendar_events, int CONST) {

        for (int i = 0; i < LASTEMPTYCELL - 1; i++) {
            for (int j = 0; j < LASTEMPTYCELL - 1; j++) {
                if (Integer.parseInt(calendar_events[j][CONST]) > Integer.parseInt(calendar_events[j + 1][CONST])) {
                    for (int k = 0; k < calendar_events[0].length; k++) {
                        String tmp = calendar_events[j][k];
                        calendar_events[j][k] = calendar_events[j + 1][k];
                        calendar_events[j + 1][k] = tmp;
                    }
                }
            }
        }
        return calendar_events;
    }

    private static void calendar_events_for_tomorrow(String[][] calendar_events)
    {
        Calendar calendar = Calendar.getInstance();
        int counter = 0;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int data = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        for (int i = 0; i < LASTEMPTYCELL; i++) {
            if (Integer.parseInt(calendar_events[i][DAY]) == data &&
                    Integer.parseInt(calendar_events[i][MONTH]) == month && Integer.parseInt(calendar_events[i][YEAR]) == year) {
                counter++;
            }

        }
        System.out.println("События на завтра: " + counter);

    }

    private static void sortedOutput(String[][] array_to_sort) {
        System.out.println("Изначальный массив:");
        Arrays.stream(array_to_sort).filter(row -> row[0] != null).forEach(arr -> System.out.println(Arrays.toString(arr)));


        System.out.println("Сортировка по месяцам:");
        String[][] sorted_by_month = sort(array_to_sort, MONTH);
        Arrays.stream(array_to_sort).filter(row -> row[0] != null).forEach(arr -> System.out.println(Arrays.toString(arr)));

        int[] count_of_months = getAllMonth(sorted_by_month);




        //System.out.println(Arrays.toString(months_2));
        //System.out.println(Arrays.toString(months_1));


        System.out.println("Сортировка по дням:");
        String[][] sorted_by_day = sort(array_to_sort, DAY);
        Arrays.stream(sorted_by_day).filter(row -> row[0] != null).forEach(arr -> System.out.println(Arrays.toString(arr)));
        for (int i = 0; i < count_of_months.length; i++) {
            if (count_of_months[i] == 0) {
                break;
            }
            for (int j = 0; j < months.length; j++) {
                if (count_of_months[i] == j) {
                    System.out.println("/" + months[j] + "/");
                }
            }
            for (int j = 0; j < LASTEMPTYCELL; j++) {
                if (count_of_months[i] == Integer.parseInt(sorted_by_day[j][MONTH])) {

                    System.out.print("[" + sorted_by_day[j][DAY] + "]");
                    System.out.print("[" + (Integer.parseInt(sorted_by_day[j][MONTH]) + 1) + "]");
                    System.out.print("[" + sorted_by_day[j][YEAR] + "]");
                    System.out.println("[" + sorted_by_day[j][HIJRAH] + "]");
                }
            }
        }
    }

    private static int[] getAllMonth(String[][] sorted_by_month) {
        int counter = 0;
        int[] months_1 = new int[LASTEMPTYCELL];
        int[] months_2 = new int[LASTEMPTYCELL];
        for (int i = 0; i < months_1.length; i++) {
            months_1[i] = Integer.parseInt(sorted_by_month[i][1]);
        }

        boolean flag = true;
        for (int i = 0; i < months_1.length; i++) {


            for (int j = 0; j < months_1.length; j++) {
                if (months_1[i] == months_2[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                months_2[counter] = months_1[i];
                counter++;
            }
            flag = true;
        }
        return months_2;
    }

    static boolean leapyear(int year) {
        boolean leapyear = (year > 1584 && ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)));
        if (leapyear)
            System.out.println("Год " + year + " високосный");
        else
            System.out.println("Год " + year + " не високосный");
        return leapyear;
    }

    static String[][] copyOneContactToAnother(String[][] calendar_events) {
        String[][] sorted_array = new String[LASTEMPTYCELL][TOTALCOLUMNS];
        for (int i = 0; i < LASTEMPTYCELL; i++) {
            for (int j = 0; j < calendar_events[0].length; j++) {
                sorted_array[i][j] = calendar_events[i][j];
            }
        }

        return sorted_array;

    }

    static void show(String[][] calendar_events) {
        for (int i = 0; i < LASTEMPTYCELL; i++) {
            System.out.print(i + ": [" + calendar_events[i][DAY] + "]");
            System.out.print("[" + (Integer.parseInt(calendar_events[i][MONTH]) + 1) + "]");
            System.out.print("[" + calendar_events[i][YEAR] + "]");
            System.out.println("[" + calendar_events[i][HIJRAH] + "]");

        }
    }


}





