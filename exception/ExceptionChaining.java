package exception;

/**
 * Exception Chaining
 *
 * When one exception is caught and another exception is thrown, and the information about
 * the original exception is expected to be preserved, this is called the exception chain.
 *
 * @author:qiming
 * @date: 2021/1/8
 */
public class ExceptionChaining {
    public static void main(String[] args) {

    }
}
class DynamicFiledException extends Exception{
}
class DynamicField{
    private final Object[][] fields;
    public DynamicField(int initialSize){
        fields = new Object[initialSize][2];
        for (int i = 0; i < initialSize; i++) {
            fields[i] = new Object[]{null, null};
        }
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Object[] obj : fields){
            result.append(obj[0]);
            result.append(": ");
            result.append(obj[1]);
            result.append("\n");
        }
        return result.toString();
    }
    private int hasField(String id){
        for (int i = 0; i < fields.length; i++) {
            if(id.equals(fields[i][0])){
                return i;
            }
        }
        return -1;
    }
    private int getFieldNumber(String id) throws NoSuchFieldException{
        int fieldNumber = hasField(id);
        if(fieldNumber == -1){
            throw new NoSuchFieldException();
        }
        return fieldNumber;
    }
    private int makeField(String id){
        for (int i = 0; i < fields.length; i++) {
            if(fields[i][0] == null){
                fields[i][0] = id;
                return i;
            }
        }
        // No empty fields, Add one
        // TODO
        Object[][] tmp = new Object[fields.length + 1][2];
        return -1;
    }
}
