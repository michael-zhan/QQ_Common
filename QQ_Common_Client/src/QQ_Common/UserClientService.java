package QQ_Common;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {

    private User user=null;
    Socket socket=null;

    public boolean checkUser(String userId,String password) throws IOException {
        boolean res=false;
        user=new User(userId,password);

        socket=new Socket(InetAddress.getLocalHost(),9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(user);

        //读取服务端验证信息
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message=null;
        try {
            message=(Message) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //验证成功则创建新的线程
        if(message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
            res=true;
            ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(userId,socket);
            clientConnectServerThread.start();
            ManageClientConnectServerThread.addClientConnectServerThread(userId,clientConnectServerThread);
        }else{
            socket.close();
        }
        return res;
    }

    public void onlineFriendList(){
        Message message=new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userExit(){
        Message message=new Message();
        message.setSender(user.getUserId());
        message.setMessageType(MessageType.MESSAGE_EXIT);
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
            ManageClientConnectServerThread.removeManageServerConnectClientThread(user.getUserId());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        System.out.println("退出系统");
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void sendMessageToOne(String receiver,String content){
        Message message=new Message();
        message.setSender(user.getUserId());
        message.setReceiver(receiver);
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        message.setContent(content);
        try {
            ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
            System.out.println("你对 "+receiver+" 说:"+content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToSome(String[] receivers,String content){
        for(int i=0;i<receivers.length;i++){
            String receiver=receivers[i];
            Message message=new Message();
            message.setSender(user.getUserId());
            message.setReceiver(receiver);
            message.setMessageType(MessageType.MESSAGE_COMM_MES);
            message.setContent(content);
            try {
                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
                objectOutputStream.writeObject(message);
                System.out.println("你对 "+receiver+" 说:"+content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFileToOne(String receiver){
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setCurrentDirectory(new File("d:\\"));
        JFrame jFrame=new JFrame();
        fileChooser.showOpenDialog(jFrame);
        File file=fileChooser.getSelectedFile();
        String path=file.getPath();
        try {
            BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(path));
            byte[] bytes=StreamUtils_.streamToByteArray(bufferedInputStream);
            Message message=new Message();
            message.setSender(user.getUserId());
            message.setReceiver(receiver);
            message.setMessageType(MessageType.MESSAGE_FILE_MES);
            message.setBytes(bytes);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
