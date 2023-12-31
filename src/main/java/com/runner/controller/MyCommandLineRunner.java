package com.runner.controller;


import com.runner.dao.model.Card;
import com.runner.service.CardService;
import com.runner.service.ProductService;
import com.runner.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private final ProductService productService;
    private final CardService cardService;

    @Autowired
    public MyCommandLineRunner(ProductService productService, CardService cardService) {
        this.productService = productService;
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) throws ServiceException {
        if (args.length > 0) {
            Map<Long, Long> argsMap = new HashMap<>();
            String cardName = "";
            for (String arg : args) {
                String[] parts = arg.split("-");
                if (!parts[0].equals("card")) {
                    argsMap.put(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                } else {
                    cardName = parts[1];
                }
            }
            Card card = cardService.find(cardName);
            productService.createReceipt(argsMap, card);
        } else {
            System.out.println("No command line arguments were provided.");
        }

    }

}