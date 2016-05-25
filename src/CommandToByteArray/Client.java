package CommandToByteArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by idalov on 18.05.16.
 * test Version 0.01
 */
public class Client {
    public static void main(String args[]){
        Socket socket;
        BufferedOutputStream out;
        BufferedInputStream in;
        try {
        //  Scanner scan = new Scanner(System.in);
            socket = new Socket(InetAddress.getByName("192.168.127.3"), 54321);

            out = new BufferedOutputStream(socket.getOutputStream());
            in = new BufferedInputStream(socket.getInputStream());

            //C00CommandATV<Float> com = new C00CommandATV<>((byte)1,123,2,50,(byte)1,(float)5.5);
            C00CommandSI com = new C00CommandSI((byte)2, 123, 2, (byte)1, (byte)-1);
            out.write(com.getByteArray());
            out.flush();
            C00CommandsReader.getState(in);
            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
