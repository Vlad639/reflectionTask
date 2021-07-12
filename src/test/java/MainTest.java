import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

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
        Main.publicPrintFieldsNames(new WithArrayData());
        String consoleOut;
        String rez = """      
                --------------------------------------------
                Parents fileds:
                   intData as int
                   byteData as byte
                   objectData as java.lang.Object
                   stringData as java.lang.String
                Parents methods:
                   getByteData return byte
                   getIntData return int
                   getObjectData return java.lang.Object
                   setByteData return void
                   setIntData return void
                   setObjectData return void
                Self fields:
                   stringArray as java.lang.String[]
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
                   setByteData return void
                   setIntData return void
                   setObjectData return void
                """;
        consoleOut = out.toString();
        consoleOut = consoleOut.replaceAll("\n", "");
        consoleOut = consoleOut.replaceAll("\r", "\n");

        assertEquals(consoleOut, rez);
    }


    @Test
    public void copyTest(){
        SimpleData a = new SimpleData(32, (byte)0x63, "--a--", true);
        SimpleData b = new SimpleData(54, (byte)0x53, "--b--", false);

        a = (SimpleData) Main.copyOf(new SimpleData(54, (byte)0x53, "--b--", false));
        assertEquals(a, b);
    }



}