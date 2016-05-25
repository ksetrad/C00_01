package CommandToByteArray;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by idalov on 18.05.16.
 * Version 0.01
 */
class C00CommandsReader {
    static void getState(BufferedInputStream in) throws IOException{
        int i = 0;
        while(in.available()==0 ) {
            if (i == 100000) {System.out.println(in.available());return;}
            i++;
        }

        byte [] array = new byte[in.available()];
        in.read(array);
        for(byte b : array)
            System.out.print(b+" ");
        System.out.println();
    }

}
