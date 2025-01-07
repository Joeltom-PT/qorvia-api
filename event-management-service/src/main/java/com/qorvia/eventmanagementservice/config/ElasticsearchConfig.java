//package com.qorvia.eventmanagementservice.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean
//    public RestHighLevelClient restHighLevelClient() {
//        // Create the credentials provider here
//        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope("camco-sample-7679798411.ap-southeast-2.bonsaisearch.net", 443),
//                new UsernamePasswordCredentials("pbzcycn9fq", "8qh5rx6ekr"));
//
//        // Create the RestClientBuilder
//        RestClientBuilder restClientBuilder = RestClient.builder(
//                        new HttpHost("camco-sample-7679798411.ap-southeast-2.bonsaisearch.net", 443, "https"))
//                .setRequestConfigCallback(requestConfigBuilder ->
//                        requestConfigBuilder.setConnectTimeout(10000).setSocketTimeout(10000))
//                .setHttpClientConfigCallback(httpClientBuilder -> {
//                    // Pass the credentialsProvider to the HttpClient
//                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                    return httpClientBuilder;
//                });
//
//        return new RestHighLevelClient(restClientBuilder);
//    }
//
//    @Bean
//    public CloseableHttpClient httpClient() {
//        // You don't need to define the credentialsProvider again here, it's already in restHighLevelClient
//        return HttpClients.custom().build();
//    }
//}
