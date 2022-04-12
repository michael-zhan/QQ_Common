package QQ_Common;

import java.util.Scanner;

public class Utility_ {

    private static Scanner in=new Scanner(System.in);

    /**
     * 读取键盘输入的一个字符
     * @return 一个字符
     */
    public static char readChar(){
        String str=readKeyBoard(1,false);
        return str.charAt(0);
    }

    /**
     * 读取键盘的一个输入，若输入为空，则返回默认值
     * @param defaultValue
     * @return 默认或输出的字符
     */
    public static char readChar(char defaultValue){
        String str=readKeyBoard(1,true);
        return (str.length()==0)?defaultValue:str.charAt(0);
    }

    /**
     *  读取一个整数
     * @return 整数或默认值
     */

    public static int readInt(){
        int n;
        for(;;){
            String str=readKeyBoard(2,false);
            try{
                n=Integer.parseInt(str);
                break;
            }catch (NumberFormatException e){
                System.out.print("数字输入错误，请重新输入： ");
            }
        }
        return n;
    }

    /**
     * 读取一个整数或默认值
     * @param defaultValue
     * @return 输入或默认值
     */
    public static int raedInt(int defaultValue){
        int n;
        for(;;){
            String str=readKeyBoard(2,true);
            if(str.equals("")){
                return defaultValue;
            }

            try{
                n=Integer.parseInt(str);
                break;
            }catch (NumberFormatException e){
                System.out.print("数字输入错误，请重新输入： ");
            }
        }
        return n;
    }

    /**
     * 读取键盘输入的限制长度的字符串
     * @param limit
     * @return 指定长度的字符串
     */

    public static String readString(int limit){
        return readKeyBoard(limit,false);
    }

    /**
     * 读取键盘输入的限制长度的字符串，输入为空则返回默认值
     * @param limit
     * @param defaulValue
     * @return
     */

    public static String readString(int limit,String defaulValue){
        String str=readKeyBoard(limit,true);
        return str.equals("")?defaulValue:str;
    }

    /**
     * 读取键盘输入的确认选项，Y/N
     * @return
     */
    public static char readConfirmSelection() {
        System.out.println("请输入你的选择（Y/N）");
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("选择错误，请重新输入： ");
            }
        }
        return c;

    }

    /**
     * 从键盘获取一个限制长度的字符串，并说明是否允许返回空值
     * @param limit
     * @param blankReturn
     * @return
     */

    public static String readKeyBoard(int limit,boolean blankReturn){
        String line=null;

        while(in.hasNextLine()){
            line=in.nextLine();
            if(line.length()==0){
                if(blankReturn)return line;
                else continue;
            }

            if(line.length()<1||line.length()>limit){
                System.out.print("输入长度（不能大于"+limit+")错误，请重新输入");
                continue;
            }
            break;
        }
        return line;
    }
}
