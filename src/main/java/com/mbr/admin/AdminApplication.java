package com.mbr.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.mbr.admin")
@MapperScan("com.mbr.admin.dao")
@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
public class AdminApplication {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            String[] args_ = {"--spring.profiles.active=dev"};
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("=======@@@@@启动参数不能为空，默认将以开发环境启动@@@@@============");
            System.out.println("============如需启动其它环境，请参考至少添加以下两个参数：=========");
            System.out.println("--spring.profiles.active=dev --spring.cloud.config.profile=dev     ");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            SpringApplication.run(AdminApplication.class, args_);
        } else {
            SpringApplication.run(AdminApplication.class, args);
        }
    }

}
