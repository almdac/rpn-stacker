import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

class RpnStacker {
    public static void main(String[] args) {
        RpnStacker rpnStacker = new RpnStacker();
        ArrayList<String> lines = rpnStacker.readInstance();
        Stack<Float> stk = new Stack<Float>();
        String line;
        Float a;
        Float b;

        for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                switch(line) {
                    case "+":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a+b);
                        break;
                    case "-":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a-b);
                        break;
                    case "*":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a*b);
                        break;
                    case "/":
                        a = stk.pop();
                        b = stk.pop();
                        stk.push(a/b);
                        break;
                    default:
                        stk.push(Float.parseFloat(line));
                        break;
                }
        }
       float result = stk.pop();
       System.out.println(result);
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
