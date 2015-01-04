package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by amaliujia on 15-1-3.
 */

public class SDClient {

    public static void main(String argv[]){

        SDClientDriver driver = new SDClientDriver();

        //read command
        BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
        promptPrinter("help");

        String inputString;
        String[] args;

        while(true) {
            try {
                inputString = stdReader.readLine();
                args = inputString.split(" ");

                if (args.length == 0) {
                    continue;
                }else if(args[0] == "create" && args.length >= 3){
                    int re = Integer.parseInt(args[2]);
                    driver.lookup("create", new Object[] {args[1], re});
                }else{
                    promptPrinter("help");
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Print appropriate messages in console.
     * @param message
     *          a String, used to print different messages into console.
     */
    private static void promptPrinter(String message){
        if(message.equals("help")){
            System.out.println("Instruction: Please input your command based on following format");
            System.out.println("     create [file path]. Create a file into Archer distributed file system");
        }
    }
}