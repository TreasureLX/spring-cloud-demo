package com.lx.springcloudconsumercustom.loadBalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LoadBalanceRequestInterceptor implements ClientHttpRequestInterceptor {

    private static Map<String, Set<String>> services = new ConcurrentHashMap<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    //更新所有的缓存
    @Scheduled(fixedRate = 10 * 1000)
    private void updateServices() {
        Map<String, Set<String>> oldService = services;
        Map<String, Set<String>> newService = new ConcurrentHashMap<>();
        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
            Set<String> newTartUrls = instances.stream()
                    .map(s -> s.isSecure() ?
                            "https://" + s.getHost() + ":" + s.getPort() :
                            "http://" + s.getHost() + ":" + s.getPort()
                    )
                    .collect(Collectors.toSet());
            newService.put(serviceName, newTartUrls);
        });
        services = newService;
        oldService.clear();
        System.out.println(services);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //获取URL
        URI requestUri = request.getURI();
        String path = requestUri.getPath();
        String paths[] = StringUtils.split(path.substring(1), "/");
        String serviceName = paths[0];
        String uri = paths[1];
        //获取真实的URL
        List<String> currentTargetUrls = new ArrayList<>(services.get(serviceName));
        int size = currentTargetUrls.size();
        int next = new Random().nextInt(size);
        String selectService = currentTargetUrls.get(next);
        String targetUrl = selectService + "/" + uri + "?" + requestUri.getQuery();
//        List<HttpMessageConverter<?>> list = new ArrayList<>(Arrays.asList(
//                new StringHttpMessageConverter(),
//                new ByteArrayHttpMessageConverter())
//        );
//        RestTemplate restTemplate = new RestTemplate(list);
//        ResponseEntity<InputStream> responseEntity = restTemplate.getForEntity(targetUrl, InputStream.class);
//        InputStream responseBody = responseEntity.getBody();
//        HttpHeaders httpHeaders = responseEntity.getHeaders();
        URL url=new URL(targetUrl);
        URLConnection urlConnection=url.openConnection();
        SimpleClientHttpResponse httpResponse = new SimpleClientHttpResponse(urlConnection.getInputStream(), new HttpHeaders());
        return httpResponse;
    }


    private static class SimpleClientHttpResponse implements ClientHttpResponse {
        private InputStream responseBody;
        private HttpHeaders httpHeaders;

        public SimpleClientHttpResponse(InputStream responseBody, HttpHeaders httpHeaders) {
            this.responseBody = responseBody;
            this.httpHeaders = httpHeaders;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 200;
        }

        @Override
        public String getStatusText() throws IOException {
            return "OK";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return responseBody;
        }

        @Override
        public HttpHeaders getHeaders() {
            return httpHeaders;
        }
    }
}
