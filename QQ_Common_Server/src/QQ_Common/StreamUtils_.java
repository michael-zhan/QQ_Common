package QQ_Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils_ {
    /*
    功能：用于将输入流转化为字节数组
    即可以把文件的内容读取到一个字节数组
     */

    public static byte[] streamToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buf=new byte[1024];
        int readLength=0;
        while((readLength=in.read(buf))!=-1){
            byteArrayOutputStream.write(buf,0,readLength);
        }
        byte[] res=byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return res;
    }
}
