package study;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum TokenType {
    COMMENT_BLOCK, COMMENT_LINE, WHITESPACE,
    BEGIN, END, INT, BOOL, IF, THEN, ELSE, DO, WHILE, FOR, PRINT,
    GT, GTE, EQ, PLUS, MULT, ASSIGN, SEMI, COMMA, LPAREN, RPAREN, LBRACE, RBRACE,
    BOOL_LITERAL, ID, NUMBER, UNKNOWN, EOF
}

class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + "'}";
    }
}

class Lexer {
    private static final String TOKEN_REGEX =
            "/\\*([\\s\\S]*?)\\*/|" +        // COMMENT_BLOCK
                    "//[^\r\n]*|" +                  // COMMENT_LINE
                    "\\s+|" +                         // WHITESPACE
                    "\\bbegin\\b|\\bend\\b|" +        // BEGIN, END
                    "\\bint\\b|\\bbool\\b|" +         // INT, BOOL
                    "\\bif\\b|\\bthen\\b|\\belse\\b|" +  // IF, THEN, ELSE
                    "\\bdo\\b|\\bwhile\\b|\\bfor\\b|" + // DO, WHILE, FOR
                    "\\bprint\\b|" +                 // PRINT
                    ">=?|==|\\+|\\*|=|;|,|\\(|\\)|\\{|\\}|" + // Operators and symbols
                    "\\btrue\\b|\\bfalse\\b|" +       // BOOL_LITERAL
                    "[a-zA-Z][a-zA-Z0-9]*|" +         // ID
                    "\\d+";                           // NUMBER

    private static final Pattern TOKEN_PATTERN = Pattern.compile(TOKEN_REGEX);

    private final String input;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {
            String value = matcher.group();
            TokenType type = identifyTokenType(value);

            if (type == TokenType.WHITESPACE) {
                continue;
            }

            tokens.add(new Token(type, value));
        }

        return tokens;
    }

    private TokenType identifyTokenType(String value) {
        if (value.matches("/\\*([\\s\\S]*?)\\*/")) return TokenType.COMMENT_BLOCK;
        if (value.matches("//[^\r\n]*")) return TokenType.COMMENT_LINE;
        if (value.matches("\\s+")) return TokenType.WHITESPACE;
        if (value.matches("\\bbegin\\b")) return TokenType.BEGIN;
        if (value.matches("\\bend\\b")) return TokenType.END;
        if (value.matches("\\bint\\b")) return TokenType.INT;
        if (value.matches("\\bbool\\b")) return TokenType.BOOL;
        if (value.matches("\\bif\\b")) return TokenType.IF;
        if (value.matches("\\bthen\\b")) return TokenType.THEN;
        if (value.matches("\\belse\\b")) return TokenType.ELSE;
        if (value.matches("\\bdo\\b")) return TokenType.DO;
        if (value.matches("\\bwhile\\b")) return TokenType.WHILE;
        if (value.matches("\\bfor\\b")) return TokenType.FOR;
        if (value.matches("\\bprint\\b")) return TokenType.PRINT;
        if (value.matches(">")) return TokenType.GT;
        if (value.matches(">=")) return TokenType.GTE;
        if (value.matches("==")) return TokenType.EQ;
        if (value.matches("\\+")) return TokenType.PLUS;
        if (value.matches("\\*")) return TokenType.MULT;
        if (value.matches("=")) return TokenType.ASSIGN;
        if (value.matches(";")) return TokenType.SEMI;
        if (value.matches(",")) return TokenType.COMMA;
        if (value.matches("\\(")) return TokenType.LPAREN;
        if (value.matches("\\)")) return TokenType.RPAREN;
        if (value.matches("\\{")) return TokenType.LBRACE;
        if (value.matches("\\}")) return TokenType.RBRACE;
        if (value.matches("\\btrue\\b|\\bfalse\\b")) return TokenType.BOOL_LITERAL;
        if (value.matches("[a-zA-Z][a-zA-Z0-9]*")) return TokenType.ID;
        if (value.matches("\\d+")) return TokenType.NUMBER;
        return TokenType.UNKNOWN;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            Lexer lexer = new Lexer(content);
            List<Token> tokens = lexer.tokenize();

            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (IOException e) {
            System.err.println("Error when reading file " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
