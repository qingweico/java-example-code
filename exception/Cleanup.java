package exception;


import static util.Print.print;

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
                    print(s);
                }
            } catch (Exception e){
                print("Caught Exception in main");
                e.printStackTrace(System.out);
            }finally {
                in.dispose();
            }
        }catch (Exception e){
            print("InputFile construction failed");
        }
    }
}
