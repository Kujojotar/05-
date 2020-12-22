package Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.lang.Runnable;

public class PlayerService implements Runnable {
    Socket firstPlayerSocket;
    Socket secondPlayerSocket;
    private int numberOfFirstWin=0;
    private int numberOfSecondWin=0;

    public PlayerService(){
        super();
    }

    public PlayerService(Socket socket){
        this.firstPlayerSocket=socket;
    }

    public void join(Socket socket){
        this.secondPlayerSocket=socket;
    }

    @Override
    public void run(){
        while(secondPlayerSocket==null){
        }
        System.out.println("用户均已上线");
        int res;
        try {
            DataInputStream fdis = new DataInputStream(firstPlayerSocket.getInputStream());
            DataInputStream sdis=new DataInputStream(secondPlayerSocket.getInputStream());

            DataOutputStream fdos=new DataOutputStream(firstPlayerSocket.getOutputStream());
            DataOutputStream sdos=new DataOutputStream(secondPlayerSocket.getOutputStream());
            fdos.writeUTF("----------游戏已经开始----------");
            sdos.writeUTF("----------游戏已经开始----------");
            while(true) {
                res = fdis.readInt()-sdis.readInt();
                if (res == 1 || res==-2) {
                    numberOfFirstWin++;
                    fdos.writeUTF("恭喜您赢了,目前您赢了" + numberOfFirstWin + "局");
                    sdos.writeUTF("很遗憾您输了，再接再厉。");
                } else if (res == -1 || res==2) {
                    numberOfSecondWin++;
                    fdos.writeUTF("很遗憾您输了，再接再厉。");
                    sdos.writeUTF("恭喜您赢了,目前您赢了" + numberOfSecondWin + "局");
                }else{
                    fdos.writeUTF("平局");
                    sdos.writeUTF("平局");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Socket getFirstPlayerSocket(){
        return firstPlayerSocket;
    }

    public boolean isEmpty(){
        if(secondPlayerSocket!=null){
            return true;
        }
        else{
            return false;
        }
    }
}
