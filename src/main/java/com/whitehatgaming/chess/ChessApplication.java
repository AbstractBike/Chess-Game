package com.whitehatgaming.chess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(ChessApplication.class, args)));
    }
}
