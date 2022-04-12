package QQ_Common;

import java.util.HashMap;
import java.util.Set;

public class ManageServerConnectClientThread {
    private static HashMap<String,ServerConnectClientThread> threadHashMap=new HashMap<>();

    public static void addManageServerConnectClientThread(String userId,ServerConnectClientThread thread){
        if(thread!=null){
            threadHashMap.put(userId,thread);
        }
    }

    public static void removeManageServerConnectClientThread(String userId){
        if(threadHashMap.containsKey(userId)){
            threadHashMap.remove(userId);
        }else{
            System.out.println("用户("+userId+")进程不存在");
        }
    }
    public static ServerConnectClientThread getServerConnectClient(String userId){
        if(threadHashMap.containsKey(userId)){
            return threadHashMap.get(userId);
        }else{
            return null;
        }
    }

    public static boolean isOnline(String userId){
        if(threadHashMap.containsKey(userId)){
            return true;
        }
        return false;
    }

    public static String getOnlineFriendList(){
        String friends=" ";
        Set<String> set =threadHashMap.keySet();
        for(String friend:set){
            friends+=friend+" ";
        }
        return friends;
    }

    public static HashMap<String, ServerConnectClientThread> getThreadHashMap() {
        return threadHashMap;
    }
}
