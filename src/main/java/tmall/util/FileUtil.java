package tmall.util;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmall.service.ConfigService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Map;
@Component
public class FileUtil {
    @Autowired
    public ConfigService configService;
    private static FileUtil fileUtil;
    @PostConstruct
    public void init() {
        fileUtil = this;
        fileUtil.configService = this.configService;
    }


    public static void saveFile(File temp,String dir,String fileName){
        File fileDir = new File(ServletActionContext.getServletContext().getRealPath(dir));
        File file = new File(fileDir,fileName);
        file.getParentFile().mkdirs();
        try{
            FileUtils.copyFile(temp,file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveImg(File temp,String type,int imgId){
        Map<String,String> config = fileUtil.configService.map();
        String dir = config.get("path_"+type+"_img");
        saveFile(temp,dir,imgId+".jpg");
    }
}
