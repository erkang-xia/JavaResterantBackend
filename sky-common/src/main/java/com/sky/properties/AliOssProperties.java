package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.alioss") //springboot
@Data
public class AliOssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}

//Step 1: Component Scanning
//@Component Annotation: This annotation marks the AliOssProperties class as a candidate for Spring's component scanning, which allows Spring Boot to detect and register it as a bean in the ApplicationContext. Essentially, Spring will manage instances of this class.
//Step 2: Configuration Properties Binding
//@ConfigurationProperties(prefix = "sky.alioss"): This annotation tells Spring Boot to bind external configuration properties to the fields of the AliOssProperties class. Properties in your external configuration files (application.properties or application.yml) that start with the prefix sky.alioss will be mapped to the fields in this class. For example:
//sky.alioss.endpoint will be mapped to the endpoint field.
//sky.alioss.accessKeyId will be mapped to the accessKeyId field.
//sky.alioss.accessKeySecret will be mapped to the accessKeySecret field.
//sky.alioss.bucketName will be mapped to the bucketName field.
//Step 3: Property Values Injection
//Relaxed Binding: Spring Boot's relaxed binding feature allows for a bit of flexibility in how properties are named in the configuration files relative to the field names in the Java class. For example, sky.alioss.access_key_id in a properties file can still map to accessKeyId in the Java class.
//Type Conversion: If necessary, Spring Boot will handle the conversion of properties from the source configuration files to the appropriate data types required by the AliOssProperties fields.
//Step 4: Use of @Data Annotation
//        @Data Annotation (from Lombok): This is a Lombok annotation that at compile-time will automatically generate getters and setters for all fields, along with toString(), equals(), and hashCode() methods. This simplifies the class by removing boilerplate code and is particularly handy when dealing with configuration property classes which primarily store data.