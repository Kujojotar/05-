package Server;

import Service.PlayerService;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
     private static final Integer PORT=1234;
     private static List<PlayerService> gameList=new ArrayList<>();
     public static void main(String[] args) {
            try {
                String choice;
                ServerSocket server = new ServerSocket(PORT);
                System.out.println("服务器已启动");
                while(true){
                    Socket socket= server.accept();
                    System.out.println("用户"+socket.getLocalPort()+"已经连接到服务器");
                    DataInputStream dis=new DataInputStream(socket.getInputStream());
                    choice=dis.readUTF();
                    if(choice.equals("a")){
                        PlayerService s=new PlayerService(socket);
                        gameList.add(s);
                    }
                    else{
                        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("请输入您要加入用户的ip地址:");
                        String tmp=dis.readUTF();
                        for(PlayerService e:gameList){
                            if(tmp.equals(e.getFirstPlayerSocket().getInetAddress().getHostAddress())){
                                e.join(socket);
                                new DataOutputStream(e.getFirstPlayerSocket().getOutputStream()).writeUTF("用户已成功连接.");
                                e.run();
                                break;
                            }
                        }
                    }

                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

}
