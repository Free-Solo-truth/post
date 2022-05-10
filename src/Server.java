import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Servlet implementation class ImageServlet
 */

//这里填写自己的响应请求地址
@WebServlet("/ImageServlet")
public class Server extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Server() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        BufferedReader reader = request.getReader();
        String json = reader.readLine();
        //ImageStringImpl dao=new ImageStringImpl();
        ImageList ImageList = new ImageList();
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ImageList>(){}.getType();
         ImageList= gson.fromJson(json, type);
        System.out.print(ImageList);
//        Base64Util.GenerateImage(image.getImage(),"/home/roo/upload/"+ image.getImagename());
        Decoder decoder = Base64.getMimeDecoder();
        for(int i=0;i<ImageList.Array_image.size();i++){
            byte[] b = decoder.decode(ImageList.Array_image.get(i).image);
            ByteArrayInputStream baisArrayInputStream=new ByteArrayInputStream(b);
            BufferedImage bi1=ImageIO.read(baisArrayInputStream);
            //此处为linux下存储图片的路径
            String imgFilePath = "/root/photo/"+ ImageList.Array_image.get(i).imagename;//新生成的图片
            File file=new File(imgFilePath);
            ImageIO.write(bi1, "jpg", file);

        }
        //验证是否发送到服务器
        //linux下笔者之后利用文件流来验证是否上传了，，呜呜呜
        File mFile=new File("/root/photo/test.txt");
        try (PrintWriter output = new PrintWriter(mFile);) {
            output.print("test");
            output.print(ImageList.Array_image.get(0).image);}
        int i=1;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(i);
    }

}
