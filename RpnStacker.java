import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;

class UnexpectedElement extends Exception { 
    public UnexpectedElement(String element) {
        super("Unexpected character: " + element);
    }
}
class Token {
    String type;
    String lexeme;

    public Token(String t, String l) {
        type = t;
        lexeme = l;
    }

    public void print() {
        System.out.printf("Token[type=%s, lexeme=%s]\n", type, lexeme);
    }
}

class RpnStacker {
    public static void main(String[] args) {
        RpnStacker rpnStacker = new RpnStacker();
        ArrayList<String> input = rpnStacker.readInstance();
        Stack<Float> stk = new Stack<Float>();
        ArrayList<Token> tokens;
        Float a;
        Float b;

        try{
            tokens = rpnStacker.scanner(input);
        } catch(UnexpectedElement e) {
            System.out.println(e);
            return;
        }

        for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                switch(token.type) {
                    case "PLUS":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a+b);
                        break;
                    case "MIN":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a-b);
                        break;
                    case "MULT":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a*b);
                        break;
                    case "DIV":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a/b);
                        break;
                    case "NUM":
                        stk.push(Float.parseFloat(token.lexeme));
                        break;
                }
        }
       float result = stk.pop();
       System.out.println(result);
    }

    private ArrayList<Token> scanner(ArrayList<String> input) throws UnexpectedElement {
        ArrayList<Token> tokens = new ArrayList<Token>();
        
        for (int i = 0; i < input.size(); i++) {
            String element = input.get(i);

            if (Pattern.matches("[0-9]{1,}", element)) {
                Token token = new Token("NUM", element);
                token.print();
                tokens.add(token);
            }
            if (Pattern.matches("\\+", element)) {
                Token token = new Token("PLUS", element);
                token.print();
                tokens.add(token);
            }
            if (Pattern.matches("\\-", element)) {
                Token token = new Token("MIN", element);
                token.print();
                tokens.add(token);
            }
            if (Pattern.matches("\\/", element)) {
                Token token = new Token("DIV", element);
                token.print();
                tokens.add(token);
            }
            if (Pattern.matches("\\*", element)) {
                Token token = new Token("MULT", element);
                token.print();
                tokens.add(token);
            }
        }
        
        return tokens;
    }

    private ArrayList<String> readInstance() {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            File f = new File("instance.txt");
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                lines.add(line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }
}
