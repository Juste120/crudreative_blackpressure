package crudreative.backpressure;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public class Util {
    public static void main(String[] args){
        // This is a pseudocode representation for generating SQL statements.
// Use a scripting language or SQL generator to create these statements.

        for (int i = 1; i <= 10000; i++) {
            System.out.println("INSERT INTO users (id, name, email) VALUES (" + i + ", 'User" + i + "', 'user" + i + "@example.com');");
        }

    }
}