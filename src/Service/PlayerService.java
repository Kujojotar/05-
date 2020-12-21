package Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.lang.Runnable;

public class PlayerService implements Runnable {
    Socket firstPlayerSocket;
    Socket secondPlayerSocket;
    private int totalgame=0;
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
        char res;
        try {
            DataInputStream fdis = new DataInputStream(firstPlayerSocket.getInputStream());
            DataInputStream sdis=new DataInputStream(secondPlayerSocket.getInputStream());

            DataOutputStream fdos=new DataOutputStream(firstPlayerSocket.getOutputStream());
            DataOutputStream sdos=new DataOutputStream(secondPlayerSocket.getOutputStream());
            while(true) {
                res = Exam(fdis.readInt(), sdis.readInt());
                if (res == 'w') {
                    totalgame++;
                    numberOfFirstWin++;
                    fdos.writeUTF("恭喜您赢了,目前您赢了" + numberOfFirstWin + "局");
                    sdos.writeUTF("很遗憾您输了，再接再厉。");
                } else if (res == 'f') {
                    totalgame++;
                    numberOfSecondWin++;
                    fdos.writeUTF("很遗憾您输了，再接再厉。");
                    sdos.writeUTF("恭喜您赢了,目前您赢了" + numberOfSecondWin + "局");
                }else{
                    totalgame++;
                    fdos.writeUTF("平局");
                    sdos.writeUTF("平局");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static char Exam(int fchoice,int schoice){
        if(fchoice==1&&schoice==2 || fchoice==2&&schoice==3 || fchoice==3&&schoice==1){
            return 'f';
        }
        else if(fchoice==2&&schoice==1 || fchoice==3&&schoice==2 || fchoice==1&&schoice==3){
            return 'w';
        }
        else{
            return 't';
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
