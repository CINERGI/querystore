package edu.sdsc.cinergi.querystore;


import edu.sdsc.cinergi.querystore.model.PortalCollectionItem;
import edu.sdsc.cinergi.querystore.repository.elasticsearch.PortalCollectionRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

    /**
     * Test case to show Spring Data Elasticsearch functionality.
     *
     * @author Artur Konczak
     * @author Oliver Gierke
     * @author Christoph Strobl
     */
    @RunWith(SpringRunner.class)
    @SpringBootTest(classes = QuerystoreApplication.class)
    public class RepsitoryTests {

        private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @Autowired
        PortalCollectionRepository repository;
        @Autowired  ElasticsearchOperations operations;
        List<PortalCollectionItem> addedList ;

        @Before
        public  void insertDataSample() {

            // Remove all documents
            repository.deleteAll();
            operations.refresh(PortalCollectionItem.class);
//            DatasetReference result1 = DatasetReference.builder().fileId("test1").url("http://example.com").build();
//            DatasetReference result2 = DatasetReference.builder().fileId("test2").url("http://example.com").build();
//            DatasetReference result3 = DatasetReference.builder().fileId("test3").url("http://example.com").build();
//            DatasetReference result4 = DatasetReference.builder().fileId("test4").url("http://example.com").build();
            // Save data sample
             repository.save(PortalCollectionItem.builder().id("1").created("2014-11-06").title("Test 1")
                    .collections(Arrays.asList("c1", "c2")).build());
            repository.save(PortalCollectionItem.builder().id("2").created("2014-12-07").title("Test 1")
                    .collections(Arrays.asList("c1", "c2")).build());
            repository.save(PortalCollectionItem.builder().id("3").created("2014-11-20").title("Test 2")
                    .collections(Arrays.asList("c1", "c2")).build());

            repository.save(PortalCollectionItem.builder().id("4").created("2014-11-12").title("Test 3")
                    .collections(Arrays.asList("c1", "c2")).build());

//            repository.save(PortalCollectionItem.builder().created("2014-10-04").title("JDD14 - Cracow")
//                    .datasetReferences(Arrays.asList("java", "spring")).location(new GeoPoint(50.0646501D, 19.9449799)).build());
        }

        @Test
        public void textSearch() throws ParseException {

            String expectedDate = "2014-10-29";
            String expectedWord = "Test 1";
            CriteriaQuery query = new CriteriaQuery(
                    new Criteria("title").is(expectedWord).and(new Criteria("created").greaterThanEqual(expectedDate)));

            List<PortalCollectionItem> result = operations.queryForList(query, PortalCollectionItem.class);

            assertThat(result, hasSize(2));

            for (PortalCollectionItem conference : result) {
                assertThat(conference.getTitle(), is(expectedWord));
                assertThat(format.parse(conference.getCreated()), greaterThan(format.parse(expectedDate)));
            }
        }
        @Test
        public void collectionSearch() throws ParseException {

            String expectedCollection = "c1";
            String expectedWord = "Test 1";
            CriteriaQuery query = new CriteriaQuery(
                    new Criteria("collections").is(expectedCollection));

            List<PortalCollectionItem> result = operations.queryForList(query, PortalCollectionItem.class);

            assertThat(result, hasSize(4));


        }

        @Ignore
        @Test
        public void geoSpatialSearch() {

            GeoPoint startLocation = new GeoPoint(50.0646501D, 19.9449799D);
            String range = "330mi"; // or 530km
            CriteriaQuery query = new CriteriaQuery(new Criteria("location").within(startLocation, range));

            List<PortalCollectionItem> result = operations.queryForList(query, PortalCollectionItem.class);

            assertThat(result, hasSize(2));
        }
    }
