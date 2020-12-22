package Server;

import Service.PlayerService;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/*
* @classname:Server
* @description:客户端服务
* @author:James
* @version:0.0
* @param: PORT:服务端口号
*         gameList:储存创建完毕的游戏房间
*/
public class Server {
     private static final Integer PORT=1234;
     private static List<PlayerService> gameList=new ArrayList<>();

     public static void main(String[] args) {
            try {
                //用于记录用户的创建房间还是加入房间
                String choice;

                //启动服务器
                ServerSocket server = new ServerSocket(PORT);
                System.out.println("服务器已启动");
                while(true){
                    Socket socket= server.accept();
                    System.out.println("用户"+socket.getLocalPort()+"已经连接到服务器");
                    DataInputStream dis=new DataInputStream(socket.getInputStream());
                    choice=dis.readUTF();

                    //a表示用户选择创建房间，否则获取用户想加入的端口号，将用户加入到已创建的房间
                    //若没有用户指定的房间，关闭套接字并提示用户退出
                    if(choice.equals("a")){
                        PlayerService s=new PlayerService(socket);
                        gameList.add(s);
                    }
                    else{
                        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("请输入您要加入用户的ip地址:");
                        dos.flush();
                        String tmp=dis.readUTF();
                        for(PlayerService e:gameList){
                            if(tmp.equals(e.getFirstPlayerSocket().getInetAddress().getHostAddress())){
                                e.join(socket);
                                e.run();
                                break;
                            }
                        }
                        dos.writeUTF("end");
                        socket.close();
                    }

                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

}
