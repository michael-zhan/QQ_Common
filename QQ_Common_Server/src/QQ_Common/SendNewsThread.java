package QQ_Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Set;

public class SendNewsThread extends Thread {
    @Override
    public void run() {
        while(true){
            Scanner in=new Scanner(System.in);

            System.out.print("请输入要发送的推送:");
            String news=Utility_.readString(50);
            Set<String> set=ManageServerConnectClientThread.getThreadHashMap().keySet();
            for(String userId:set){
                try {
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClient(userId).getSocket().getOutputStream());
                    Message message=new Message();
                    message.setContent(news);
                    message.setSender("服务器");
                    message.setReceiver(userId);
                    message.setMessageType(MessageType.MESSAGE_NEWS_MES);
                    objectOutputStream.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
