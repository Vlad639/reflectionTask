import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class MainTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @BeforeEach
    public void setStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    public void printFieldsNamesCheck() {
        Main.publicPrintFieldsNames(new WithCollectionData());
        String consoleOut;
        String rez = """      
                --------------------------------------------
                Parents fileds:
                   stringArray as java.lang.String[]
                Parents methods:
                   equals return boolean
                   getStringArray return java.lang.String[]
                   setStringArray return void
                Self fields:
                   listString as java.util.List
                   mapStringSimpleData as java.util.Map
                """;
        consoleOut = out.toString();
        consoleOut = consoleOut.replaceAll("\n", "");
        consoleOut = consoleOut.replaceAll("\r", "\n");

        assertEquals(consoleOut, rez);
    }

    @Test
    public void printMethodsNames() {
        Main.publicPrintMethodsNames(new SimpleData());
        String consoleOut;
        String rez = """      
                --------------------------------------------
                Parents fileds:
                Parents methods:
                   clone return java.lang.Object
                   equals return boolean
                   finalize return void
                   getClass return java.lang.Class
                   hashCode return int
                   notify return void
                   notifyAll return void
                   toString return java.lang.String
                   wait return void
                   wait return void
                   wait return void
                Self methods:
                   getByteData return byte
                   getIntData return int
                   getObjectData return java.lang.Object
                   getStringData return java.lang.String
                   isBoolData return boolean
                   setBoolData return void
                   setByteData return void
                   setIntData return void
                   setObjectData return void
                   setStringData return void
                """;
        consoleOut = out.toString();
        consoleOut = consoleOut.replaceAll("\n", "");
        consoleOut = consoleOut.replaceAll("\r", "\n");

        assertEquals(consoleOut, rez);
    }

    @Test
    public void simpleDataCopyTest(){
        SimpleData a = new SimpleData(10, (byte)0x34, "str a", null, true);
        SimpleData b = new SimpleData(20, (byte)0x75, "str b", a, true);
        SimpleData c = new SimpleData(30, (byte)0x91, "str c", b, true);

        SimpleData d = (SimpleData) Main.copyOf(c);

        a.setStringData("Changed");

        assertEquals(((SimpleData) ((SimpleData) d.getObjectData()).getObjectData()).getStringData(), "str a");
    }

    @Test
    public void withArrayDataCopyTest(){
        String[] arr = {"1", "2", "3", "4", "5"};
        WithArrayData a = new WithArrayData(10, (byte)0x34, "str a", null, true, arr);

        WithArrayData test = (WithArrayData) Main.copyOf(a);
        a.setStringArray(new String[]{"0", "0", "0", "0", "0"});
        assertEquals(test.getStringArray()[2], "3");
    }

    @Test
    public void withCollectionDataListCopyTest(){
        String[] arr = {"1", "2", "3", "4", "5"};
        ArrayList<String> list = new ArrayList<>(Arrays.asList(arr));
        SimpleData a = new SimpleData(10, (byte)0x34, "str a", null, true);
        SimpleData b = new SimpleData(20, (byte)0x75, "str b", a, true);

        Map<String, SimpleData> dataMap = new HashMap<>();
        dataMap.put("1", a);
        dataMap.put("2", b);

        WithCollectionData withCollectionData = new WithCollectionData(10, (byte)0x34, "str a", null, true, arr, list, dataMap);

        WithCollectionData test = (WithCollectionData) Main.copyOf(withCollectionData);
        withCollectionData.setListString(Arrays.asList(new String[]{"0", "0", "0", "0", "0"}));
        assertEquals(test.getListString().get(2), "3");

    }

    @Test
    public void withCollectionDataMapCopyTest(){
        String[] arr = {"1", "2", "3", "4", "5"};
        ArrayList<String> list = new ArrayList<>(Arrays.asList(arr));
        SimpleData a = new SimpleData(10, (byte)0x34, "str a", null, true);
        SimpleData b = new SimpleData(20, (byte)0x75, "str b", a, true);

        Map<String, SimpleData> dataMap = new HashMap<>();
        dataMap.put("1", a);
        dataMap.put("2", b);
        WithCollectionData withCollectionData = new WithCollectionData(10, (byte)0x34, "str a", null, true, arr, list, dataMap);

        WithCollectionData test = (WithCollectionData) Main.copyOf(withCollectionData);
        dataMap.remove("1");

        withCollectionData.setMapStringSimpleData(dataMap);
        assertEquals(test.getMapStringSimpleData().get("1").getStringData(), "str a");

    }



}