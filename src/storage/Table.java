package storage;
import users.User;

import java.lang.reflect.Field;
import java.util.List;

public class Table {

    public static void printTable(List<?> objects) throws IllegalAccessException {

        if (objects == null || objects.isEmpty()) {
            System.out.println("Empty list");
            return;
        }

        Class<?> clazz = objects.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        // доступ к private полям
        for (Field field : fields) {
            field.setAccessible(true);
        }

        // ширина колонок
        int[] widths = new int[fields.length];

        // ширина = max(header, values)
        for (int i = 0; i < fields.length; i++) {
            widths[i] = fields[i].getName().length();
        }

        for (Object obj : objects) {
            for (int i = 0; i < fields.length; i++) {

                Object value = fields[i].get(obj);

                int len = String.valueOf(value).length();

                widths[i] = Math.max(widths[i], len);
            }
        }

        // header
        for (int i = 0; i < fields.length; i++) {
            System.out.print("| " +
                    center(fields[i].getName().toUpperCase(), widths[i]) +
                    " ");
        }
        System.out.println("|");

        // rows
        for (Object obj : objects) {

            for (int i = 0; i < fields.length; i++) {

                Object value = fields[i].get(obj);

                System.out.print("| " +
                        center(String.valueOf(value), widths[i]) +
                        " ");
            }

            System.out.println("|");
        }
    }

    public static String center(String text, int width) {
        int padding = width - text.length();
        if(padding < 0) padding = text.length();

        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }

}
