package net.wazim.pitstop.controller;

import net.wazim.pitstop.domain.Driver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@SuppressWarnings("unused")
public class DriverController {

    private Map<String, Driver> allDrivers = new HashMap<>();

    @RequestMapping(value = "drivers", method = RequestMethod.POST)
    public ResponseEntity<String> addDrivers(@RequestBody List<LinkedHashMap<String, String>> drivers) {
        allDrivers.clear();
        for (LinkedHashMap<String, String> driver : drivers) {
            allDrivers.put(driver.get("familyName").toLowerCase(),
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

    @RequestMapping(value = "f1/**/drivers/{familyName}.json")
    public ResponseEntity<String> getDriver(@PathVariable String familyName) {
        Driver driver = allDrivers.get(familyName.toLowerCase());
        JSONObject jsonResponse = createJsonResponse(Collections.singletonList(driver));
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "f1/**/drivers.json", method = RequestMethod.GET)
    public ResponseEntity<String> getDrivers() {
        JSONObject jsonResponse = createJsonResponse(allDrivers.values());
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    private JSONObject createJsonResponse(Collection<Driver> driver) {
        JSONObject rootObject = new JSONObject();

        JSONArray drivers = new JSONArray();
        for (Driver theDriver : driver) {
            JSONObject driverJson = new JSONObject();
            driverJson.put("driverId", theDriver.driverId());
            driverJson.put("permanentNumber", theDriver.permanentNumber());
            driverJson.put("code", theDriver.code());
            driverJson.put("url", theDriver.url());
            driverJson.put("givenName", theDriver.givenName());
            driverJson.put("familyName", theDriver.familyName());
            driverJson.put("dateOfBirth", "1990-12-10");
            driverJson.put("nationality", theDriver.nationality());
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
        return rootObject;
    }

}
