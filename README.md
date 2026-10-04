# SWE 307 – Big Data Project 1

A Java **Spring Boot** web app that reads **one value per second** from
**MongoDB** (in Java), passes that value to an **R** plotting function running on
**GraalVM**, and shows the live chart in a browser that **refreshes every second**.

Our group's column is **`Col-1`**.

> 📘 **Full study guide:** see [`EXPLANATION.md`](EXPLANATION.md) for a detailed,
> line-by-line explanation of every file, the architecture, setup steps, key
> concepts, likely exam questions, and a glossary.

---

## What it does

```
Browser ──GET /plot──> PlotController ──findByRow──> MongoDB
   ▲                        │                           │
   │                        └── reads Col-1 (a double) ──┘
   │                        │
   │                        ▼
   │                 RPlotFunction (GraalVM) ──> plot.R (xyplot ──> SVG)
   │                        │
   └──── SVG + "Refresh: 1" ─┘   (browser reloads after 1s → next value)
```

- Line chart via R's `xyplot()` (lattice): x-axis 0–99, dark-brown line, grid.
- The page auto-refreshes every second using the HTTP `Refresh` header.

## Tech stack

| Part | Choice |
|------|--------|
| Language / JDK | Java 17 on **GraalVM CE 22.3.0** |
| Framework | Spring Boot 4.1.1 (webmvc + data-mongodb) |
| Database | MongoDB 7 (in Docker) |
| Plotting | R (**FastR**) + lattice `xyplot`, over GraalVM polyglot |
| Build | Maven (`./mvnw`) |

## Requirements to run

- **WSL2 Ubuntu** (FastR has no Windows build).
- **GraalVM CE 22.3.0 (Java 17)** with the **R** component (`gu install R`),
  and `JAVA_HOME` / `PATH` pointing at it.
- **Docker** with a MongoDB container named `mongo` holding database `swe307`,
  collection `data` (100 documents with fields `Row` and `Col-1`…`Col-21`).

## How to run

```bash
# 1. start the database
docker start mongo

# 2. start the app (from the project folder, in WSL)
./mvnw spring-boot:run

# 3. open in a browser
#    http://localhost:8080/plot
```

Stop with **Ctrl+C**.

## Change to another group's column

Edit one line in `src/main/java/com/swe307/pro1/DataRow.java`:

```java
@Field("Col-1")   // change "Col-1" to e.g. "Col-7"
private double col1;
```

Then restart the app. (Optionally update the chart title in
`src/main/resources/plot.R`.)

## Project layout

```
src/main/java/com/swe307/pro1/
  Pro1Application.java      # Spring Boot entry point
  DataRow.java              # maps one MongoDB document (uses Col-1)
  DataRowRepository.java    # findByRow("Row-N") lookup
  RPlotFunction.java        # GraalVM polyglot: Java -> R function
  PlotController.java       # GET /plot: read value -> R -> SVG + Refresh header
src/main/resources/
  application.properties    # MongoDB connection (Spring Boot 4 property name)
  plot.R                    # xyplot drawing, returns SVG string
EXPLANATION.md              # detailed study guide
```
