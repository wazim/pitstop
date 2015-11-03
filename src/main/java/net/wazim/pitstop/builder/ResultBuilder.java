package net.wazim.pitstop.builder;

import net.wazim.pitstop.domain.Constructor;
import net.wazim.pitstop.domain.Driver;
import net.wazim.pitstop.domain.Result;

public class ResultBuilder {

    private int position;
    private int points;
    private Driver driver;
    private Constructor constructor;
    private int grid;
    private int laps;
    private String status;

    public ResultBuilder() {
        this.position = 1;
        this.points = 25;
        this.driver = Driver.LEWIS_HAMILTON;
        this.constructor = Constructor.MERCEDES;
        this.grid = 1;
        this.laps = 70;
        this.status = "Finished";
    }

    public ResultBuilder withPosition(int position) {
        this.position = position;
        return this;
    }

    public ResultBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public ResultBuilder withDriver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public ResultBuilder withConstructor(Constructor constructor) {
        this.constructor = constructor;
        return this;
    }

    public ResultBuilder withGrid(int grid) {
        this.grid = grid;
        return this;
    }

    public ResultBuilder withLaps(int laps) {
        this.laps = laps;
        return this;
    }

    public ResultBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public Result build() {
        return new Result(
                driver.permanentNumber(),
                position,
                String.valueOf(position),
                points,
                driver,
                constructor,
                grid,
                laps,
                status
        );
    }
}
