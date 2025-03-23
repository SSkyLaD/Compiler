import java_cup.runtime.Symbol;

%%

// Định nghĩa tùy chọn cho JFlex
%class UPLLexer
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
%}

// Định nghĩa biểu thức chính quy
WHITESPACE = [ \t\r\n]+  
COMMENT_BLOCK = "/*" [^*]* ("*" [^/][^*]*)* "*/"
COMMENT_LINE = "//"[^\r\n]*  
ID = [a-zA-Z]([a-zA-Z0-9]*)?
NUMBER = [0-9]+
BOOL_LITERAL = "true" | "false"

%%

// Bỏ qua khoảng trắng
{WHITESPACE}     { /* Bỏ qua */ }

// Nhận diện comment và trả về giá trị của nó
{COMMENT_LINE}   { return symbol(sym.COMMENT, yytext()); }
{COMMENT_BLOCK}  { return symbol(sym.COMMENT, yytext()); }

// Từ khóa
"begin"          { return symbol(sym.BEGIN); }
"end"            { return symbol(sym.END); }
"int"            { return symbol(sym.INT); }
"bool"           { return symbol(sym.BOOL); }
"if"             { return symbol(sym.IF); }
"then"           { return symbol(sym.THEN); }
"else"           { return symbol(sym.ELSE); }
"do"             { return symbol(sym.DO); }
"while"          { return symbol(sym.WHILE); }
"for"            { return symbol(sym.FOR); }
"print"          { return symbol(sym.PRINT); }

// Toán tử và ký hiệu
">"              { return symbol(sym.GT); }
">="             { return symbol(sym.GTE); }
"=="             { return symbol(sym.EQ); }
"+"              { return symbol(sym.PLUS); }
"*"              { return symbol(sym.MULT); }
"="              { return symbol(sym.ASSIGN); }
";"              { return symbol(sym.SEMI); }
","              { return symbol(sym.COMMA); }
"("              { return symbol(sym.LPAREN); }
")"              { return symbol(sym.RPAREN); }
"{"              { return symbol(sym.LBRACE); }
"}"              { return symbol(sym.RBRACE); }

// Nhận diện biến, số và giá trị boolean
{BOOL_LITERAL}   { return symbol(sym.BOOL_LITERAL, Boolean.parseBoolean(yytext())); }
{ID}             { return symbol(sym.ID, yytext()); }
{NUMBER}         { return symbol(sym.NUMBER, Integer.parseInt(yytext())); }

// Xử lý lỗi
. { 
    System.err.println("Lexical error at line " + (yyline+1) + ", column " + (yycolumn+1) + ": " + yytext());
    return symbol(sym.UNKNOWN, yytext()); // Trả về UNKNOWN thay vì null để tránh lỗi NullPointerException
}

<<EOF>> { return symbol(sym.EOF); }
