package com.cosine.mysql;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThread {
    static final ExecutorService service = Executors.newFixedThreadPool(1);
}
