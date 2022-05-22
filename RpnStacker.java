import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;

import stacker.rpn.lexer.TokenType;
import stacker.rpn.lexer.Token;

class UnexpectedCharacter extends Exception { 
    public UnexpectedCharacter(String element) {
        super("Unexpected character: " + element);
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
        } catch(UnexpectedCharacter e) {
            System.out.println(e);
            return;
        }

        for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                switch(token.type) {
                    case PLUS:
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a+b);
                        break;
                    case MINUS:
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a-b);
                        break;
                    case STAR:
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a*b);
                        break;
                    case SLASH:
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a/b);
                        break;
                    case NUM:
                        stk.push(Float.parseFloat(token.lexeme));
                        break;
                    case EOF:
                        break;
                }
        }
       float result = stk.pop();
       System.out.println(result);
    }

    private ArrayList<Token> scanner(ArrayList<String> input) throws UnexpectedCharacter {
        ArrayList<Token> tokens = new ArrayList<Token>();
        
        for (int i = 0; i < input.size(); i++) {
            String element = input.get(i);

            if (Pattern.matches("[0-9]{1,}", element)) {
                Token token = new Token(TokenType.NUM, element);
                System.out.println(token.toString());
                tokens.add(token);
            }
            else if (Pattern.matches("\\+", element)) {
                Token token = new Token(TokenType.PLUS, element);
                System.out.println(token.toString());
                tokens.add(token);
            }
            else if (Pattern.matches("\\-", element)) {
                Token token = new Token(TokenType.MINUS, element);
                System.out.println(token.toString());
                tokens.add(token);
            }
            else if (Pattern.matches("\\/", element)) {
                Token token = new Token(TokenType.SLASH, element);
                System.out.println(token.toString());
                tokens.add(token);
            }
            else if (Pattern.matches("\\*", element)) {
                Token token = new Token(TokenType.STAR, element);
                System.out.println(token.toString());
                tokens.add(token);
            }
            else {
                throw new UnexpectedCharacter(element);
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
