package Client;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try {
            String t;
            Socket socket = new Socket("localhost", 1234);
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
            Scanner in=new Scanner(System.in);
            t=in.next();
            dos.writeUTF(t);
            dos.flush();
            if(t.equals("a")) {
                System.out.println("等待用户连接.......");
            }
            else{
                System.out.println(dis.readUTF());
                dos.writeUTF(in.next());
                dos.flush();
            }
            t=dis.readUTF();
            if(t.equals("end")){
                System.out.println("抱歉，没找到您想要进入的房间.");
                return;
            }
            System.out.println(t + "\n");
            while(true){
                System.out.println("------------------------------------------------------");
                System.out.println("请输入一个数字表示剪刀石头布:1--剪刀 2--石头 3--布");
                if(socket.isClosed()){
                    break;
                }
                dos.writeInt(in.nextInt());
                dos.flush();
                System.out.println(dis.readUTF());
                System.out.println("------------------------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
