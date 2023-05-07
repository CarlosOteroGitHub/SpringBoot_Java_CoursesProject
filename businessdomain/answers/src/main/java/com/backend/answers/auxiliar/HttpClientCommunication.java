package com.backend.answers.auxiliar;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class HttpClientCommunication {

    private static HttpClientCommunication instance = null;
    private final WebClient.Builder webClientBuilder = WebClient.builder();

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    public WebClient.Builder getWebClientBuilder() {
        return webClientBuilder;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static HttpClientCommunication getInstance() {
        if (instance == null) {
            instance = new HttpClientCommunication();
        }
        return instance;
    }
}