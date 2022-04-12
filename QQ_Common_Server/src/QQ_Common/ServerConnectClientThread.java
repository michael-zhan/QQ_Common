package QQ_Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerConnectClientThread extends Thread {
    private String userId;
    private Socket socket = null;

    public ServerConnectClientThread(String userId, Socket socket) {
        this.userId = userId;
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("服务器启动与" + userId + "的连接");
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                //没有收到消息则会一直阻塞在这里
                Message message = (Message) objectInputStream.readObject();
                if (message.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    String friendList = ManageServerConnectClientThread.getOnlineFriendList();
                    Message message1 = new Message();
                    message1.setContent(friendList);
                    message1.setReceiver(message.getSender());
                    message1.setMessageType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                } else if (message.getMessageType().equals(MessageType.MESSAGE_EXIT)) {
                    ObjectOutputStream objectOutputStream2 =
                            new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClient(message.getSender()).getSocket().getOutputStream());
                    objectOutputStream2.writeObject(message);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ManageServerConnectClientThread.removeManageServerConnectClientThread(userId);
                    socket.close();
                    break;
                }else if(message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)){
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClient(message.getReceiver()).getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                    System.out.println("已成功转发"+message.getSender()+"对"+message.getReceiver()+"的私聊信息");
                }else if(message.getMessageType().equals(MessageType.MESSAGE_FILE_MES)){
                    ObjectOutputStream objectOutputStream1 =
                            new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClient(message.getReceiver()).getSocket().getOutputStream());
                    objectOutputStream1.writeObject(message);
                    System.out.print("已成功转发文件");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (socket == null) {
                break;
            }
        }
        System.out.println("服务器断开与"+userId+"的连接");
    }

    public Socket getSocket() {
        return socket;
    }
}
