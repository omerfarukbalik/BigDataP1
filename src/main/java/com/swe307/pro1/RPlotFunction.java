package com.swe307.pro1;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Component
public class RPlotFunction implements Function<Double, String> {

    private Context context;
    private Value plotFunction;

    @PostConstruct
    public void init() throws IOException {
        context = Context.newBuilder("R").allowAllAccess(true).build();

        String rCode;
        try (InputStream in = new ClassPathResource("plot.R").getInputStream()) {
            rCode = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }

        plotFunction = context.eval("R", rCode);
    }

    @Override
    public synchronized String apply(Double value) {
        Value result = plotFunction.execute(value);
        return result.asString();
    }

    @PreDestroy
    public void close() {
        if (context != null) {
            context.close();
        }
    }
}
