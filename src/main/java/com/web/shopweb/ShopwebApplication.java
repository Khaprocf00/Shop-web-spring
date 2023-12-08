package com.web.shopweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.web.shopweb.service.BrandService;
import com.web.shopweb.service.ImageService;
import com.web.shopweb.service.ProductService;
import com.web.shopweb.service.SliderService;
import com.web.shopweb.service.StorageService;
import com.web.shopweb.storage.StorageProperties;

@SpringBootApplication()
@EnableConfigurationProperties(StorageProperties.class)
public class ShopwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopwebApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService,
			BrandService brandService, ImageService imageService,
			ProductService productService, SliderService sliderService) {
		return (args) -> {
			storageService.init();
			brandService.init();
			imageService.init();
			productService.init();
			sliderService.init();
			// storageService.deleteAll();
		};
	}
}
