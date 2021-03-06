/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.swing.data.JFileDataStoreChooser;

/**
 *
 * @author 冯雨宁
 */
public class FProject {
    private String FName;//工程名
    //private int FLayerNum;//图层数
    private List<String> FLayers;//图层名称列表
    //private MapContent FMapContent;//图层类

    //public MapContent getFMapContent() {

        //return FMapContent;
   // }

    public void setFMapContent(MapContent FMapContent) {
        //this.FMapContent = FMapContent;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }   

    public void setFLayerID(List<String> FLayers) {
        this.FLayers = FLayers;
    }

    public int getFLayerNum() {
        return FLayers.size();
    }

    public List<String> getFLayers() {
        return FLayers;
    }
    
    public String getFName()
    {
        return this.FName;
    }
    
    public String getFilePath()//默认保存在当前目录下，json格式，文件名为工程名
    {
        String path="./";
        String fileNameTemp = path+FName+".json";//文件路径+名称+文件类型
        return fileNameTemp;
    }
    
    
     public FProject(){
        FName="";
        FLayers= new ArrayList<>();
        //FMapContent=new MapContent();
    }
    
    public FProject(String name){
        FName=name;
        FLayers= new ArrayList<>();
       // FMapContent=new MapContent();
    }
      
    static FProject openFromFile(File file) throws FileNotFoundException, IOException{//从json文件打开
        BufferedReader fr = new BufferedReader(new FileReader(file));
        String json = fr.readLine();
        JSONObject jsonObject=JSONObject.fromObject(json);
        FProject fp=(FProject)JSONObject.toBean(jsonObject, FProject.class); 
        System.out.println("success open file,the file is "+fp.getFName());
        return fp;
    }
    
    public boolean saveFProject(){
        JSONObject jsonObject=JSONObject.fromObject(this);
        System.out.println(jsonObject);
          
        Boolean bool = false;
        String fileNameTemp = this.getFilePath();
        File file = new File(fileNameTemp);
        
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                System.out.println("success create file,the file is "+fileNameTemp);
                //创建文件成功后，写入内容到文件里
            }
            FileWriter fw = new FileWriter(file); 
            fw.write(jsonObject.toString());
            fw.close();
            System.out.println("success save file,the file is "+fileNameTemp);
            bool = true;
        } catch (IOException e) {
        }
        
        return bool;    
    }
    
    public void addLayer(String name){//先把图层存进数据库，再把数据库id作为参数传入
        FLayers.add(name);
        //FMapContent.addLayer(mLayer);
    }
    
    public void deleteLayer(int index){//先获取要删除图层的id，作为参数传入
   
        FLayers.remove(index);
        //FMapContent.layers().remove(index);
    }
      
    public void moveLayer(int sourcePos, int desPos){//移动图层顺序
        String name=FLayers.get(sourcePos);
        FLayers.remove(name);
        if(sourcePos>desPos)
        {
            FLayers.add(desPos, name);
        }
        else
        {
            FLayers.add(desPos-1,name);
        }
       //FMapContent.moveLayer(sourcePos, desPos);
    }
    
    public static void main(String[] args) throws IOException {
        String ss="fb";      
        FProject fp = new FProject(ss);
        fp.saveFProject();
        
        File file = JFileDataStoreChooser.showOpenFile("json", null);
        if (file == null) {
            return;
        }
        
        FProject a=openFromFile(file);
        System.out.println(a.getFName());   
    } 
}
