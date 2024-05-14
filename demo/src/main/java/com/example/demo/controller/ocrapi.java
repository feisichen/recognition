package com.example.demo.controller;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Iterator;
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ocr")
public class ocrapi {
    //设置APPID/AK/SK
    private static String APP_ID = "26840924";
    private static String API_KEY ="jmQGOM1HZMmhhBdD5ERuRHF8";
    private static String SECRET_KEY = "S37LccLEux2e8167WaIosG9LOx0FZuaj";


    @PutMapping
    public static void main(String[] args) {

        // 初始化一个AipOcr
        AipOcr aipOcr = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        //设置本地图片地址
        String path = "E:/code/springboot+vue/image_recognition/vue/public/image";
        //通用文字识别
        basicGeneral(aipOcr,path);

    }

    /*
     * 通用文字识别
     * */
    public static void basicGeneral(AipOcr aipOcr,String path){
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");//识别语言类型，默认为中英文混合
        options.put("detect_direction", "true");//是否检查图片朝向，默认false不检查
        options.put("detect_language", "true");//是否检查语言，默认false不检查
        options.put("probability", "true");//是否返回识别结果中每一行的置信度

        // 调用接口，返回JSON格式数据
        JSONObject jsonObject = aipOcr.basicGeneral(path, options);
        //获取JSON对象里提取图片文字信息数组
        JSONArray jsonArray = jsonObject.getJSONArray("words_result");
        //循环打印信息
        for(int i = 0;i<jsonArray.length();i++){
            System.out.println(jsonArray.getJSONObject(i).get("words"));
        }
    }


}
