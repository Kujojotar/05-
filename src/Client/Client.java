package Client;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
            char c='a';
            dos.writeChar(c);
            dos.flush();
            Scanner in=new Scanner(System.in);

            while(true){
                System.out.println("------------------------------------------------------");
                System.out.println("请输入一个数字表示剪刀石头布:1--剪刀 2--石头 3--布");
                dos.writeInt(in.nextInt());
                System.out.println(dis.readUTF());
                System.out.println("------------------------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
