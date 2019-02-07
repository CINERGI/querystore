package edu.sdsc.cinergi.querystore;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sdsc.cinergi.querystore.model.PortalCollectionItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    /**
     * Integration test to run the application.
     *
     * @author Oliver Gierke
     */
    @RunWith(SpringJUnit4ClassRunner.class)
    //@SpringApplicationConfiguration(classes = QuerystoreApplicationApplication.class)
    @SpringBootTest(classes = QuerystoreApplication.class)
    @WebAppConfiguration
    @ActiveProfiles("estest")
// Separate profile for web tests to avoid clashing databases
    public class RepositoryRestTests {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mvc;
//        @MockBean
//       private PortalCollectionRepository portalCollectionRepository;

        @Before
        public void setUp() {
            this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        }

        @Test
        public void testApi() throws Exception {

            this.mvc.perform(get("/api")).andExpect(status().isOk())
                    .andExpect(content().string(containsString("metadatacollection")));
        }
        @Test
        public void testCollectionController () throws Exception {
            ResultMatcher ok = MockMvcResultMatchers.status()
                    .isOk();

            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/item");
            this.mvc.perform(builder)
                    .andExpect(ok);

        }

        @Test
        public void testPost() throws Exception {
            PortalCollectionItem item = PortalCollectionItem.builder().title("test-rest").type("item")
                    .id("test999").user("user999").build();
            ObjectMapper mapper = new ObjectMapper();

            String json =mapper.writeValueAsString(item);

            MvcResult mvcResult = this.mvc.perform(post("/api/item").content(json).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                    .andExpect(content().string(containsString("test-rest"))).andReturn();
           // verify(portalCollectionRepository   , times(1)).save(any(PortalCollectionItem.class));
            mvcResult.toString();
        }

        @Test
        public void testCollectionSearch () throws Exception {
            ResultMatcher ok = MockMvcResultMatchers.status()
                    .isOk();

            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/item/search/collections")
                    .param("collection","c1");
            this.mvc.perform(builder)
                    .andExpect(ok);

        }

    }

