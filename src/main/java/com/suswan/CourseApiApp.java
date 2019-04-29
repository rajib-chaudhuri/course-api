package com.suswan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.suswan")
@EnableSwagger2
@EnableHystrixDashboard
@EnableCircuitBreaker
public class CourseApiApp {

	public static void main(String[] args) {
		SpringApplication.run(CourseApiApp.class,args);
	}
	static final Counter requests = Counter.build().name("requests_total").help("Total number of requests.").register();
	// Define a histogram metric for /prometheus
	static final Histogram requestLatency = Histogram.build().name("requests_latency_seconds").help("Request latency in seconds.").register();
}
