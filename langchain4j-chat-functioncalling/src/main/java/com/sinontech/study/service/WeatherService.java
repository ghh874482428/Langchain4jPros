package com.sinontech.study.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService
{
    //和风天气开发服务 https://dev.qweather.com/

    // 替换成你自己的和风天气API密钥
//    private static final String API_KEY = System.getenv("weatherAPI");
    private static final String API_KEY = "c6b3f4c92bcd4e24bdfc13ff8dc733dd";
    // 调用的url地址和指定的城市，本案例以北京为例
    private static final String BASE_URL = "https://pr7qqqwnhc.re.qweatherapi.com/v7/weather/now?location=%s&key=%s";

    public JsonNode getWeatherV2(String city)
    {
        //1 传入调用地址url和apikey
        String url = String.format(BASE_URL, city, API_KEY);

        //2 使用默认配置创建HttpClient实例
        var httpClient = HttpClients.createDefault();
        JsonNode jsonNode =null;
        //3 创建请求工厂并将其设置给RestTemplate，开启微服务调用和风天气开发服务
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        try {
            //4 RestTemplate微服务调用
            String response = new RestTemplate(factory).getForObject(url, String.class);
            //5 解析JSON响应获得第3方和风天气返回的天气预报信息
             jsonNode = new ObjectMapper().readTree(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        //6 想知道具体信息和结果请查看https://dev.qweather.com/docs/api/weather/weather-now/#response
        return jsonNode;
    }
}
