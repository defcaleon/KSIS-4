
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Main {
    public static int PORT = 80;
    public static String requestStr;
    public static String hostStr;
    public static String connectionStr;
    public static String contentTypeStr;
    public static String contentLengthStr;
    public static String postRequestStr;
    public static boolean type = false;

    public static void main (String [] args) throws IOException {
        Scanner in = new Scanner(System.in);

// GET / HTTP/1.1
// POST /post HTTP/1.1
// HEAD / HTTP/1.1
// Host: httpbin.org

        System.out.println("Введите запрос:");
        requestStr = in.nextLine();
        System.out.println("Введите хост:");
        hostStr = in.nextLine();
        if (requestStr.charAt(0) == 'P') {
            type = true;
            connectionStr = "Connection: close";
            contentTypeStr = "Content-type: application/json";
            contentLengthStr = "Content-length: 14";
            postRequestStr = "{\"text\":true}";
        }

        Socket socket = new Socket(hostStr,PORT);
        InputStream response = socket.getInputStream();
        OutputStream request = socket.getOutputStream();

        byte [] data;
        if (type){
            data = (requestStr+"\n"+"Host: "+hostStr+"\n"+connectionStr+"\n"+contentTypeStr+"\n"+contentLengthStr+"\r\n\r\n"+postRequestStr+"\n\n").getBytes();
        } else {
            data = (requestStr+"\n"+"Host: "+hostStr+"\n\n").getBytes();
        }

        request.write(data);

        int c;

        while ((c = response.read()) != -1){
            System.out.print((char) c);
        }
        socket.close();
    }
}