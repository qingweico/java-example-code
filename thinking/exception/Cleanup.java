package thinking.exception;


import util.Print;

/**
 * @author zqw
 * @date 2021/1/18
 */
public class Cleanup {
    public static void main(String[] args) throws Exception {
        try {
            InputFile in = new InputFile("exception/Cleanup.java");
            try {
                String s;
                while ((s = in.getLine()) != null){
                    Print.println(s);
                }
            } catch (Exception e){
                Print.println("Caught Exception in main");
                e.printStackTrace(System.out);
            }finally {
                in.dispose();
            }
        }catch (Exception e){
            Print.println("InputFile construction failed");
        }
    }
}
