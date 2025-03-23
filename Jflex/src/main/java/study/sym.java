package study;

public class sym {
    public static final int EOF = 0;
    public static final int BEGIN = 1;
    public static final int END = 2;
    public static final int INT = 3;
    public static final int BOOL = 4;
    public static final int IF = 5;
    public static final int THEN = 6;
    public static final int ELSE = 7;
    public static final int DO = 8;
    public static final int WHILE = 9;
    public static final int FOR = 10;
    public static final int PRINT = 11;
    public static final int GT = 12;
    public static final int GTE = 13;
    public static final int EQ = 14;
    public static final int PLUS = 15;
    public static final int MULT = 16;
    public static final int ASSIGN = 17;
    public static final int SEMI = 18;
    public static final int COMMA = 19;
    public static final int LPAREN = 20;
    public static final int RPAREN = 21;
    public static final int LBRACE = 22;
    public static final int RBRACE = 23;
    public static final int ID = 24;
    public static final int NUMBER = 25;
    public static final int BOOL_LITERAL = 26;
    public static final int COMMENT = 27;
    public static final int UNKNOWN = -999;

    public static String getTokenName(int token) {
        for (var field : sym.class.getDeclaredFields()) {
            try {
                if (field.getInt(null) == token) {
                    return field.getName();
                }
            } catch (IllegalAccessException ignored) {}
        }
        return "UNKNOWN";
    }
}
