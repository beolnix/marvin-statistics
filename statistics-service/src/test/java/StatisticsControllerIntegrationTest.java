import com.beolnix.marvin.statistics.Application;
import com.beolnix.marvin.statistics.api.model.ChatDTO;
import com.beolnix.marvin.statistics.chats.domain.dao.ChatDAO;
import com.beolnix.marvin.statistics.statistics.domain.dao.UserSpecificMetricDAO;
import com.beolnix.marvin.statistics.statistics.domain.model.AggregatedUserSpecificMetric;
import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import com.beolnix.marvin.statistics.statistics.mongo.MetricsAggregationService;
import com.beolnix.marvin.utils.RestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest(randomPort = true)
@ActiveProfiles("integration")
public class StatisticsControllerIntegrationTest {

    @Value("${local.server.port}")
    private Integer port;

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private UserSpecificMetricDAO metricDAO;

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    MongoOperations mongoOps;

    @Autowired
    MetricsAggregationService aggregationService;

    private String CHAT_NAME = "testChat";
    private RestHelper restHelper;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port + "/api/v1/";
        chatDAO.deleteAll();
        restHelper = new RestHelper(chatDAO, port);

        metricDAO.deleteAll();
    }

    @Test
    public void aggregationSmokeTest() {

        LocalDateTime start = LocalDateTime.now()
                .withDayOfMonth(4)
                .withHour(1)
                .withMinute(0);

        LocalDateTime end = start
                .withDayOfMonth(4)
                .withHour(8);

        createTestData(start);

        List<AggregatedUserSpecificMetric> result = aggregationService.aggregateForTimePeriod(start, end , 2);
        assertNotNull(result);
        assertTrue(result.size() > 1);

        List<AggregatedUserSpecificMetric> userSpecific = result.stream()
                .filter(entry -> entry.getUsername().equals("testUser9"))
                .collect(Collectors.toList());

        assertEquals(4, userSpecific.size());
    }

    private void createTestData(LocalDateTime start) {
        ChatDTO chatDTO = restHelper.createChat(CHAT_NAME);

        List<UserSpecificMetric> metrics = new LinkedList<>();
        String userName = "testUser";

        IntStream.range(1, 10).forEach(userNum -> {
            IntStream.range(1, 10).forEach(num -> {
                UserSpecificMetric m = new UserSpecificMetric();
                m.setUsername(userName + userNum);
                m.setMetricName("msgCount");
                m.setValue(num);
                m.setDateTime(start.plusHours(num));
                m.setChatId(chatDTO.getId());

                metrics.add(m);
            });
        });

        metricDAO.save(metrics);
    }
}