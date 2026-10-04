package com.swe307.pro1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlotController {

    private final DataRowRepository repository;
    private final RPlotFunction plotFunction;

    private int index = 0;

    public PlotController(DataRowRepository repository, RPlotFunction plotFunction) {
        this.repository = repository;
        this.plotFunction = plotFunction;
    }

    @GetMapping(value = "/plot", produces = "image/svg+xml")
    public ResponseEntity<String> plot() {
        index = index % 100 + 1;

        DataRow document = repository.findByRow("Row-" + index);
        double value = document.getCol1();

        String svg = plotFunction.apply(value);

        return ResponseEntity.ok()
                .header("Refresh", "1")
                .body(svg);
    }
}
