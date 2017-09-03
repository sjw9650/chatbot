package kr.or.connect.chatbotserver.lost;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageGet {

    private String strUrl;
    private String index;

    public ImageGet(String strUrl, String index) {
        this.strUrl = strUrl;
        this.index = index;
    }
    public ImageGet() {}
    public String getIndex() {
        return index;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setStrUrl(String strUrl) throws IOException {

        this.strUrl = strUrl;
    }


    public void saveImage() throws IOException {

        URL url = null;

        InputStream in = null;
        OutputStream out = null;

        try {
            url = new URL(strUrl);
            in = url.openStream();

            String reposit = "/apps/lost/" + index + ".jpg";
            out = new FileOutputStream(reposit);

            while(true){
                //이미지를 읽어온다.
                int data = in.read();
                if(data == -1){
                    break;
                }
                //이미지를 쓴다.
                out.write(data);
            }
            in.close();
            out.close();

        } catch (Exception e) {

            e.printStackTrace();

        }finally{

            if(in != null){in.close();}
            if(out != null){out.close();}

        }
    }
}
