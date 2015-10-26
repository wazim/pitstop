package net.wazim.pitstop.controller;

import net.wazim.pitstop.domain.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@SuppressWarnings("unused")
public class RaceResultsController {

    private Map<Integer, RaceResult> allRaceResults = new HashMap<>();

    @RequestMapping(value = "raceResults", method = RequestMethod.POST)
    public ResponseEntity<String> addRaceResults(@RequestBody List<LinkedHashMap<String, Object>> raceResults) {
        allRaceResults.clear();
        for (LinkedHashMap<String, Object> raceResult : raceResults) {
            HashMap<String, Object> race = (HashMap<String, Object>) raceResult.get("race");
            HashMap<String, Object> circuit = (HashMap<String, Object>) race.get("circuit");
            HashMap<String, Object> location = (HashMap<String, Object>) circuit.get("location");
            ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) raceResult.get("results");
            ArrayList<Result> theResults = new ArrayList<>();
            Race theRace = getRace(race, circuit, location);
            for (HashMap<String, Object> result : results) {
                HashMap<String, Object> driver = (HashMap<String, Object>) result.get("driver");
                HashMap<String, Object> constructor = (HashMap<String, Object>) result.get("constructor");
                theResults.add(
                        new Result(
                                (Integer) result.get("number"),
                                (Integer) result.get("position"),
                                (String) result.get("positionText"),
                                (Integer) result.get("points"),
                                new Driver(
                                        (String) driver.get("driverId"),
                                        (Integer) driver.get("permanentNumber"),
                                        (String) driver.get("code"),
                                        (String) driver.get("url"),
                                        (String) driver.get("givenName"),
                                        (String) driver.get("familyName"),
                                        (String) driver.get("nationality")
                                ),
                                new Constructor(
                                        (String) constructor.get("constructorId"),
                                        (String) constructor.get("url"),
                                        (String) constructor.get("name"),
                                        (String) constructor.get("nationality")
                                ),
                                (Integer) result.get("grid"),
                                (Integer) result.get("laps"),
                                (String) result.get("status")
                        )
                );
            }


            allRaceResults.put((Integer) race.get("round"), new RaceResult(
                    theRace,
                    theResults
            ));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = {"f1/2015/{round}/results.json"}, method = RequestMethod.GET)
    public ResponseEntity<String> getRaceResultsByRound(@PathVariable Integer round) {
        JSONObject jsonResponse = createJsonResponse(allRaceResults.get(round));
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    private JSONObject createJsonResponse(RaceResult raceResult) {
        JSONObject rootObject = new JSONObject();
        Race race = raceResult.race();

        JSONObject raceObject = createRaceObject(new JSONObject(), race);
        JSONArray results = new JSONArray();
        for (Result rr : raceResult.results()) {
            JSONObject driver = new JSONObject();
            driver.put("driverId", rr.driver().driverId());
            driver.put("permanentNumber", rr.driver().permanentNumber());
            driver.put("code", rr.driver().code());
            driver.put("url", rr.driver().url());
            driver.put("givenName", rr.driver().givenName());
            driver.put("familyName", rr.driver().familyName());
            driver.put("nationality", rr.driver().nationality());

            JSONObject constructor = new JSONObject();
            driver.put("constructorId", rr.constructor().constructorId());
            driver.put("name", rr.constructor().name());
            driver.put("url", rr.constructor().url());
            driver.put("nationality", rr.constructor().nationality());

            JSONObject result = new JSONObject();
            result.put("number", rr.number());
            result.put("position", rr.position());
            result.put("positionText", rr.positionText());
            result.put("points", rr.points());
            result.put("Driver", driver);
            result.put("Constructor", constructor);
            result.put("grid", rr.grid());
            result.put("laps", rr.laps());
            result.put("status", rr.status());
            results.put(result);
        }
        raceObject.put("Results", results);

        JSONObject raceTable = new JSONObject();
        raceTable.put("season", "2015");
        raceTable.put("Races", raceObject);

        JSONObject data = new JSONObject();
        data.put("xmlns", "http://ergast.com/mrd/1.4");
        data.put("series", "f1");
        data.put("url", String.format("http://ergast.com/api/f1/2015/%s/results.json", race.round()));
        data.put("limit", "30");
        data.put("offset", "0");
        data.put("total", allRaceResults.size());
        data.put("RaceTable", raceTable);
        rootObject.put("MRData", data);
        return rootObject;
    }

    private JSONObject createRaceObject(JSONObject racesJson, Race race) {
        JSONObject locationJson = new JSONObject();
        locationJson.put("lat", race.circuit().location().latitude());
        locationJson.put("long", race.circuit().location().longitude());
        locationJson.put("locality", race.circuit().location().locality());
        locationJson.put("country", race.circuit().location().country());

        JSONObject circuitJson = new JSONObject();
        circuitJson.put("circuitId", race.circuit().circuitId());
        circuitJson.put("url", race.circuit().url());
        circuitJson.put("circuitName", race.circuit().circuitName());
        circuitJson.put("Location", locationJson);

        JSONObject raceJson = new JSONObject();
        raceJson.put("season", race.season());
        raceJson.put("round", race.round());
        raceJson.put("url", race.url());
        raceJson.put("raceName", race.raceName());
        raceJson.put("Circuit", circuitJson);

        raceJson.put("date", race.dateTime().toLocalDate());
        raceJson.put("time", race.dateTime().toLocalTime().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_TIME));
        return raceJson;
    }

    private Race getRace(HashMap<String, Object> race, HashMap<String, Object> circuit, HashMap<String, Object> location) {
        return new Race(
                (Integer) race.get("season"),
                (Integer) race.get("round"),
                (String) race.get("url"),
                (String) race.get("raceName"),
                new Circuit(
                        (String) circuit.get("circuitId"),
                        (String) circuit.get("url"),
                        (String) circuit.get("circuitName"),
                        new Location(
                                (String) location.get("latitude"),
                                (String) location.get("longitude"),
                                (String) location.get("locality"),
                                (String) location.get("country")
                        )
                ),
                getDateFrom(race)
        );
    }

    private static LocalDateTime getDateFrom(HashMap<String, Object> race) {
        Map<String, Integer> dateTime = (Map<String, Integer>) race.get("dateTime");
        return LocalDateTime.of(
                dateTime.get("year"),
                dateTime.get("monthValue"),
                dateTime.get("dayOfMonth"),
                dateTime.get("hour"),
                dateTime.get("minute"),
                dateTime.get("second")
        );
    }
}
