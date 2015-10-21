package net.wazim.pitstop.controller;

import net.wazim.pitstop.domain.Circuit;
import net.wazim.pitstop.domain.Location;
import net.wazim.pitstop.domain.Race;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@SuppressWarnings("unused")
public class RaceScheduleController {

    private Map<Integer, Race> allRaces = new HashMap<>();

    @RequestMapping(value = "races", method = RequestMethod.POST)
    public ResponseEntity<String> addRaces(@RequestBody List<LinkedHashMap<String, Object>> races) {
        allRaces.clear();
        for (LinkedHashMap<String, Object> race : races) {
            HashMap<String, Object> circuit = (HashMap<String, Object>) race.get("circuit");
            HashMap<String, Object> location = (HashMap<String, Object>) circuit.get("location");
            allRaces.put((Integer) race.get("round"),
                    new Race(
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
                            )
                    )
            );
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = {"f1/current.json"}, method = RequestMethod.GET)
    public ResponseEntity<String> getRaces() {
        JSONObject jsonResponse = createJsonResponse(allRaces.values());
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = {"f1/current/{round}.json"}, method = RequestMethod.GET)
    public ResponseEntity<String> getRaceByRound(@PathVariable Integer round) {
        JSONObject jsonResponse = createJsonResponse(Collections.singletonList(allRaces.get(round)));
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    private JSONObject createJsonResponse(Collection<Race> races) {
        JSONObject rootObject = new JSONObject();

        JSONArray racesJson = new JSONArray();
        for (Race race : races) {
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
            raceJson.put("Circuit", circuitJson);
            racesJson.put(raceJson);
        }

        JSONObject raceTable = new JSONObject();
        raceTable.put("season", "2015");
        raceTable.put("Races", racesJson);

        JSONObject data = new JSONObject();
        data.put("xmlns", "http://ergast.com/mrd/1.4");
        data.put("series", "f1");
        data.put("url", "http://ergast.com/api/f1/2015/drivers.json");
        data.put("limit", "30");
        data.put("offset", "0");
        data.put("total", allRaces.size());
        data.put("RaceTable", raceTable);
        rootObject.put("MRData", data);
        return rootObject;
    }
}
