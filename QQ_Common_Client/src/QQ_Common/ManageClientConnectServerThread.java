package QQ_Common;

import java.util.HashMap;

public class ManageClientConnectServerThread {

    private static HashMap<String,ClientConnectServerThread> threadHashMap=new HashMap<>();

    public static void addClientConnectServerThread(String userId,ClientConnectServerThread thread){
        if(thread!=null)
        threadHashMap.put(userId,thread);
    }

    public static void removeManageServerConnectClientThread(String userId){
        if(threadHashMap.containsKey(userId)){
            threadHashMap.remove(userId);
        }else{
            System.out.println("用户("+userId+")进程不存在");
        }
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        if(threadHashMap.containsKey(userId)){
            return threadHashMap.get(userId);
        }else{
            return null;
        }
    }

}
