package tmall.action;
import java.io.File;

/**
 * 处理img上传的注入和取出
 */
public class Action4Upload {
 
     protected File img;
     protected String imgFileName;
     protected String imgContentType;
      
    public File getImg() {
        return img;
    }
    public void setImg(File img) {
        this.img = img;
    }
    public String getImgFileName() {
        return imgFileName;
    }
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    public String getImgContentType() {
        return imgContentType;
    }
    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }
      
}