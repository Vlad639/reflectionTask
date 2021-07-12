import java.util.Arrays;

/* Объект с предком и массивом*/
public class WithArrayData extends SimpleData {
    private String[] stringArray;

    public WithArrayData() {
    }

    public WithArrayData(int intData, byte byteData, boolean boolData, String stringData, String[] stringArray) {
        super(intData, byteData, stringData, boolData);
        this.stringArray = stringArray;
    }



    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithArrayData that = (WithArrayData) o;
        return Arrays.equals(stringArray, that.stringArray);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(stringArray);
        return result;
    }
}
