package com.zhangaoo.elasticsearchdemo;

import com.zhangaoo.elasticsearchdemo.service.RandomMaterialGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ElasticsearchDemoApplication.class)
class ElasticsearchDemoApplicationTests {

	@Autowired
	private  RandomMaterialGeneratorService generatorService;
	
	@Test
	public void generateData(){
		generatorService.sendTestMaterialData(1);
	}


	@Test
	public void generateHistoryData(){
		generatorService.sendTestMaterialHistoryData(10000);
	}
}
