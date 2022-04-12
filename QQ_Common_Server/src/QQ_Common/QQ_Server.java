package QQ_Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class QQ_Server {

    private Socket socket=null;
    private static HashMap<String ,User> userHashMap=new HashMap<>();
    static {
        userHashMap.put("zmx",new User("zmx","123456"));
        userHashMap.put("zjm",new User("zjm","123456"));
    }

    public QQ_Server(){}

    public void go() throws IOException {
        System.out.println("服务器开始运行");
        new SendNewsThread().start();

        ServerSocket serverSocket = new ServerSocket(9999);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                User user = (User) objectInputStream.readObject();
                Message message = new Message();
                if (checkUser(user)) {
                    //验证成功启动新线程
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(user.getUserId(),socket);
                    serverConnectClientThread.start();
                    ManageServerConnectClientThread.addManageServerConnectClientThread(user.getUserId(), serverConnectClientThread);

                    //发送验证成功信息给客户端
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    objectOutputStream.writeObject(message);
                } else {
                    System.out.println("验证失败");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    objectOutputStream.writeObject(message);
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    public boolean checkUser(User user){
        boolean res=false;
        if(userHashMap.get(user.getUserId())==null){
            System.out.println("用户("+ user.getUserId()+")不存在");
            return res;
        }
        if(!userHashMap.get((user.getUserId())).getPassWord().equals(user.getPassWord())){
            System.out.println("密码错误");
            return res;
        }
        if(ManageServerConnectClientThread.isOnline(user.getUserId())){
            System.out.println("重复用户登录");
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        QQ_Server qq_server = new QQ_Server();
        qq_server.go();
    }
}
