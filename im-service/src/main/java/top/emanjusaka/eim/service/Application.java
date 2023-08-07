package top.emanjusaka.eim.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"top.emanjusaka.eim.service",
        "top.emanjusaka.eim.common"})
@MapperScan("top.emanjusaka.eim.service.*.dao.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
