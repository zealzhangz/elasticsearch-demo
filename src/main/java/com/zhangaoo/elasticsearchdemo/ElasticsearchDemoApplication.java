package com.zhangaoo.elasticsearchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
@SpringBootApplication
public class ElasticsearchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchDemoApplication.class, args);
	}

}
