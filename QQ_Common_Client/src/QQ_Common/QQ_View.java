package QQ_Common;

import java.io.IOException;

public class QQ_View {
    private boolean loop=true;
    private UserClientService userClientService=new UserClientService();

    public static void main(String[] args) throws IOException {
        new QQ_View().mainMenu();
    }
    private void mainMenu() throws IOException {
        System.out.println("登录系统");
        while(loop) {
            System.out.println("===========欢迎登录网络通信系统============");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");

            System.out.print("请输入你的选择：");
            String key=Utility_.readString(1);
            switch (key){
                case "1":
                    System.out.print("请输入用户名：");
                    String userId=Utility_.readString(20);
                    System.out.print("请输入密 码：");
                    String password=Utility_.readString(15);
                    if(userClientService.checkUser(userId,password)){
                        System.out.println("========欢迎用户（"+userId+"）登录系统=========");

                        //进入二级菜单
                        while(loop) {
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key=Utility_.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "2":
                                    System.out.println("========群发消息=========");
                                    System.out.print("请输入你的发送的人群(昵称请以一个空格隔开):");
                                    String receivers=Utility_.readString(100);
                                    String[] receiverNames=receivers.split(" ");
                                    System.out.print("请输入你要发送的内容:");
                                    String content1=Utility_.readString(100);
                                    userClientService.sendMessageToSome(receiverNames,content1);
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "3":
                                    System.out.println("==========私聊：==========");
                                    System.out.print("请输入你要私聊的用户:");
                                    String receiverName=Utility_.readString(20);
                                    System.out.print("请输入你要发送的内容:");
                                    String content=Utility_.readString(50);
                                    userClientService.sendMessageToOne(receiverName,content);
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "4":
                                    System.out.println("=========发送文件=========");
                                    System.out.print("请输入你要发送文件的用户:");
                                    String receiverName2=Utility_.readString(20);
                                    userClientService.sendFileToOne(receiverName2);
                                    break;
                                case "9":
                                    userClientService.userExit();
                                    loop=false;
                                    break;
                            }
                        }
                    }else{
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    System.out.println("退出系统");
                    loop=false;
                    break;
            }
        }
        System.exit(0);
    }
}
