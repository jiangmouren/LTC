package Finished.design;

import Finished.design.ReadNCharactersGivenRead4;
import org.junit.Test;
import Finished.design.ReadNCharactersGivenRead4.*;

/**
 * Created by eljian on 6/30/2017.
 */
public class ReadNCharactersGivenRead4Test {
    ReadNCharactersGivenRead4 objectUnderTest = new ReadNCharactersGivenRead4();
    ReadNCharactersGivenRead4 objectUnderTest2 = new ReadNCharactersGivenRead4();
    //This is a very interesting experiment to understand the what static entries really means in a class.
    //Like in the following case, I have two different object, objectUnderTest1 and objectUnderTest2.
    //They share the same file, basically static entries are per type, unlike those instance entries.
    File file = new File("HelloWorld!HelloWorld!HelloWorld!");
    @Test
    public void read() throws Exception {
        char[] buf = new char[15];
        int result = objectUnderTest.read(buf, 15);
        System.out.println(result);
        for(int i=0; i<result; i++){
            System.out.print(buf[i]);
        }

        result = objectUnderTest2.read(buf, 15);
        System.out.println(result);
        for(int i=0; i<result; i++){
            System.out.print(buf[i]);
        }

        //System.out.println();
        //File file = new File("change change change change");
        //char[] buf2 = new char[15];
        //int result2 = objectUnderTest.read(buf2, 15);
        //System.out.println(result2);
        //for(int i=0; i<result2; i++){
        //    System.out.print(buf2[i]);
        //}
    }
}