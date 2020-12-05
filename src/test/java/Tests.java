import dsdbahw2.model.ComputeLog;
import org.junit.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class Tests {
    private String testScale = "5m";
    private Date date = new Timestamp(1601546604);
    private ComputeLog log = new ComputeLog(10, date, 100);

    @Test
    public void testCheckIP() throws IOException {
        log.scaleTimeStamp("1m");

        assertEquals(1601520000, log.getTime().getTime());
    }


}
