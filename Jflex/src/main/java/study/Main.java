package study;

import java_cup.runtime.Symbol;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter file path: ");
            String filePath = scanner.nextLine();
            scanner.close();

            FileInputStream inputStream = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(inputStream);
            UPLLexer lexer = new UPLLexer(reader);

            Symbol token;
            while ((token = lexer.next_token()) != null && token.sym != sym.EOF) {
                String tokenName = sym.getTokenName(token.sym);
                String tokenValue = (token.value != null) ? token.value.toString() : "null";
                System.out.println("Token{type=" + tokenName + ", value='" + tokenValue + "'}");
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
