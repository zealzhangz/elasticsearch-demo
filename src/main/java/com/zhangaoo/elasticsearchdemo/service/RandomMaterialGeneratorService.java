package com.zhangaoo.elasticsearchdemo.service;

import com.alibaba.fastjson.JSON;
import com.zhangaoo.elasticsearchdemo.entity.Material;
import com.zhangaoo.elasticsearchdemo.entity.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/11 10:20:00<br/>
 */
@Service
public class RandomMaterialGeneratorService {
    @Autowired
    private MqService mqService;
    @Value("${es.rocketmq.topic}")
    private String sendTopic;

    String[] mantnr = {"1000370691", "1000368281", "1000059038"};
    Map<String, String> maktx = new HashMap<String, String>() {{
        put("1000370691", "轴承\\UC205(90505)");
        put("1000368281", "声光报警器\\SF-513\\AC 220V");
        put("1000059038", "开关电源\\S-200-24\\输入AC220V2.2A  输出DC24V8.5A");
    }};

    Map<String, String> meins = new HashMap<String, String>() {{
        put("1000370691", "SET");
        put("1000368281", "EA");
        put("1000059038", "EA");
    }};

    Map<String, String> meinsZh = new HashMap<String, String>() {{
        put("1000370691", "套");
        put("1000368281", "每一个");
        put("1000059038", "每一个");
    }};

    Map<String, String> matkl = new HashMap<String, String>() {{
        put("1000370691", "320111");
        put("1000368281", "279904");
        put("1000059038", "271310");
    }};

    Map<String, String> matklZh = new HashMap<String, String>() {{
        put("1000370691", "U系列外球面球轴承");
        put("1000368281", "报警系统及配件");
        put("1000059038", "电源箱");
    }};

    Map<String, String> werks = new HashMap<String, String>() {{
        put("1000370691", "551I,551J,551K,5640,5520,4310,551A,551B,551C,551D,551E,551F,551G,551H,550Y,550Z,4400,550I,5530,5510,550A,550B,550C,550E,550F,5500");
        put("1000368281", "4310");
        put("1000059038", "4310");
    }};

    public Material generate() {
        Material material = new Material();
        material.setMatnr(mantnr[new Random().nextInt(mantnr.length)]);
        material.setMaktx(maktx.get(material.getMatnr()));
        material.setMeins(meins.get(material.getMatnr()));
        material.setMeinsZh(meinsZh.get(material.getMatnr()));
        material.setMatkl(matkl.get(material.getMatnr()));
        material.setMatklZh(matklZh.get(material.getMatnr()));
        material.setWerks(werks.get(material.getMatnr()));
        material.setCreateTime(new Date());
        material.setNum(new Random().nextInt(100));
        BigDecimal bd = BigDecimal.valueOf(new Random().nextDouble());
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        material.setPrice(bd.doubleValue());
        return material;
    }

    public List<Material> batchGenerate(int size) {
        List<Material> batch = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            batch.add(generate());
        }
        return batch;
    }
    
    public void sendTestMaterialData(int count){
        for (int i = 0; i < count; i++) {
            Payload payload = new Payload();
            payload.setIdType("test");
            payload.setPayload(JSON.toJSONString(batchGenerate(10)));
            mqService.asyncSend(sendTopic, payload);
        }
    }
}
