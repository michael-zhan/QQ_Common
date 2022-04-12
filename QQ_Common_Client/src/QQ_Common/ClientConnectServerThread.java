package QQ_Common;

import java.io.*;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    private String userId=null;
    private Socket socket=null;
    private boolean loop=true;

    public ClientConnectServerThread(String userId,Socket socket){
        this.userId=userId;
        this.socket=socket;
    }

    @Override
    public void run() {
        while (loop) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                //如果没有读取到消息，则会一直阻塞在这里
                Message message = (Message) objectInputStream.readObject();
                if (message.getMessageType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    System.out.println("========当前用户列表========");
                    String[] friends = message.getContent().split(" ");
                    for (String friend : friends) {
                        System.out.println(friend);
                    }
                }else if(message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)){
                    String content=message.getContent();
                    String sender=message.getSender();
                    System.out.print("\n"+sender+" 对 你 说: "+content);
                }else if(message.getMessageType().equals(MessageType.MESSAGE_FILE_MES)){
                    String path="d:\\test1.jpg";
                    BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(path));
                    bufferedOutputStream.write(message.getBytes());
                    bufferedOutputStream.close();
                }else if(message.getMessageType().equals(MessageType.MESSAGE_EXIT)){
                    loop=false;
                }else if(message.getMessageType().equals(MessageType.MESSAGE_NEWS_MES)){
                    String content=message.getContent();
                    System.out.println("收到服务器的推送:"+content);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Socket getSocket() {
        return socket;
    }
}
