package dsdbahw2.controller;


import dsdbahw2.model.ComputeLog;
import dsdbahw2.repository.IgniteRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DSDBAHW2Controller {
    /**
     * Get data from cassandra, round datetime to scale and group by Key and Date
     * number.
     *
     * @param scale
     *            scale to round datetime.
     */
    @RequestMapping(value="/fetch/cassandra/ignite/{scale}")
    public List<String> getCassandraDataUsingIgnite(@PathVariable("scale") String scale) throws ParseException {
        List<String> logs =  new ArrayList<>();
        List<ComputeLog> logsList = IgniteRepository.selectFromIgnite(scale);
        Map<Integer, Map<Date, List<ComputeLog>>> map1 = logsList.stream()
                .collect(Collectors.groupingBy(ComputeLog::getKey,
                        Collectors.groupingBy(ComputeLog::getTime)));
        for(Map.Entry<Integer,Map<Date, List<ComputeLog>>> item : map1.entrySet()){
            Integer key = item.getKey();
            Date date = new Date();
            float avgValue = 0.0F;
            Map<Date, List<ComputeLog>> map2 = item.getValue();
            for(Map.Entry<Date, List<ComputeLog>> map3 : map2.entrySet()){
                Integer sum = 0;
                for(ComputeLog cLog:map3.getValue()){
                    sum +=cLog.getValue();
                    date = cLog.getTime();
                }
                avgValue = sum / map3.getValue().size();
                logs.add(key+","+date+","+scale+","+avgValue);
            }
        }
        return logs;

    }
}
