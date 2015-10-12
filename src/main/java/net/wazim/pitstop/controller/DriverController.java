package net.wazim.pitstop.controller;

import net.wazim.pitstop.domain.Driver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@SuppressWarnings("unused")
public class DriverController {

    private List<Driver> allDrivers = new ArrayList<>();

    @RequestMapping(value = "drivers", method = RequestMethod.POST)
    public ResponseEntity<String> addDrivers(@RequestBody List<LinkedHashMap<String, String>> drivers) {
        for (LinkedHashMap<String, String> driver : drivers) {
            allDrivers.add(
                    new Driver(
                            driver.get("driverId"),
                            Integer.parseInt(driver.get("permanentNumber")),
                            driver.get("code"),
                            driver.get("url"),
                            driver.get("givenName"),
                            driver.get("familyName"),
                            driver.get("nationality")
                    )
            );
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "drivers", method = RequestMethod.GET)
    public ResponseEntity<String> getDrivers() {
        JSONObject rootObject = new JSONObject();

        JSONArray drivers = new JSONArray();
        for (Driver driver : allDrivers) {
            JSONObject driverJson = new JSONObject();
            driverJson.put("driverId", driver.driverId());
            driverJson.put("permanentNumber", driver.permanentNumber());
            driverJson.put("code", driver.code());
            driverJson.put("url", driver.url());
            driverJson.put("givenName", driver.givenName());
            driverJson.put("familyName", driver.familyName());
            driverJson.put("dateOfBirth", "1990-12-10");
            driverJson.put("nationality", driver.nationality());
            drivers.put(driverJson);
        }

        JSONObject driverTable = new JSONObject();
        driverTable.put("season", "2015");
        driverTable.put("Drivers", drivers);

        JSONObject data = new JSONObject();
        data.put("xmlns", "http://ergast.com/mrd/1.4");
        data.put("series", "f1");
        data.put("url", "http://ergast.com/api/f1/2015/drivers.json");
        data.put("limit", "30");
        data.put("offset", "0");
        data.put("total", allDrivers.size());
        data.put("DriverTable", driverTable);
        rootObject.put("MRData", data);

        return new ResponseEntity<>(rootObject.toString(), HttpStatus.OK);
    }

}
