package com.gruppometa.metaoaicat;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.culturaitalia.admin.objects.OaiSet;
import com.gruppometa.culturaitalia.admin.objects.OaicatConfigItem;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;
import com.gruppometa.metaoaicat.MyApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ActiveProfiles("local")
//@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ContextConfiguration(classes = { MyApplication.class})
public class OaisetControllerTest {

	@Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;

    private RestDocumentationResultHandler document;
    
    @Before
    public void setUp() {
        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listOaisets() throws Exception {
        //createSamplePerson("George", "King");
        //createSamplePerson("Mary", "Queen");

        this.document.snippets(
                responseFields(
                        fieldWithPath("[]").description("La lista degli oaiset"),
                        fieldWithPath("[].id").description("ID dell'oaiset"),
                        fieldWithPath("[].name").description("Il nome dell'oaiset"),
                        fieldWithPath("[].spec").description("il spec del set"),
                        fieldWithPath("[].servletName").description("il servlet che serve il set")
                )
        );

        this.mockMvc.perform(
                get("/metaoaicat/rest/oaiset").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getConfiguration() throws Exception {
    	this.document.snippets(
    			pathParameters( 
                parameterWithName("servletName").description("servlet, di norma 'OAIHandler'")
                		)                
                );
        this.mockMvc.perform(
        		RestDocumentationRequestBuilders.get("/metaoaicat/rest/configuration/{servletName}","OAIHandler")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getStatus() throws Exception {
    	this.document.snippets(
    			requestParameters( 
    					parameterWithName("getstatus").description("ottiene lo stato del OAI provider (offline o online)")
                		)
    			//,
    			//responseFields(fieldWithPath("").description("online o offline"))
                );
        this.mockMvc.perform(
        		RestDocumentationRequestBuilders.get("/metaoaicat/OAIHandler?getstatus=true")
                .accept(MediaType.APPLICATION_XML)
        ).andExpect(status().is4xxClientError());
    }
    
    @Test
    public void changeStatus() throws Exception {
    	this.document.snippets(
    			requestParameters( 
    					parameterWithName("changestatus").description("cambia lo stato del OAI provider (offline o online)")
                		)
    			//,
    			//responseFields(fieldWithPath("status").description("online o offline"))
                );
        this.mockMvc.perform(
        		RestDocumentationRequestBuilders.get("/metaoaicat/OAIHandler?changestatus=true")
                .accept(MediaType.APPLICATION_XML)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void updateConfiguration() throws Exception {
        //createSamplePerson("George", "King");
        //createSamplePerson("Mary", "Queen");
    	OaicatConfigItem item = new OaicatConfigItem();
    	ConstrainedFields fields = new ConstrainedFields(OaicatConfigItem.class);
    	item.setName("OAIHandler.styleSheet");
    	item.setValue("/metaoaicat/oai2.xsl");
    	this.document.snippets(
    	        requestFields(
    	        		fields.withPath("id").description("id, può mancare.'"),
                		fields.withPath("name").description("Nome proprieta' (viene utilizzato come chiave.)"),
                		fields.withPath("value").description("valore della proprieta' da assegnare"),
                		fields.withPath("formName").description("nome della proprieta' (in formato HTML, non serve al momento) ")
                	),
                responseFields(
                		fieldWithPath("message").description("Messaggio della risposta, nel caso di 'ko' l'errore"),
                        fieldWithPath("status").description("status della risposta (Ok o ko)")
                        //fieldWithPath("pippo").description("status della risposta (Ok o ko)")
                        )
                );

        this.mockMvc.perform(
                post("/metaoaicat/rest/configuration/{servletName}/save","OAIHandler")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                        this.objectMapper.writeValueAsString(item)
                        )
        ).andExpect(status().isOk());
    }

    @Test
    public void deleteOaiset() throws Exception {
    	
        /*this.document.snippets(
        		pathParameters( 
                		parameterWithName("id").description("L'oaiset da cancellare") )
        );*/
        
        this.document.snippets(
                responseFields(
                		fieldWithPath("message").description("Messaggio della risposta, nel caso di 'ko' l'errore"),
                        fieldWithPath("status").description("status della risposta (Ok o ko)")
                        )
                );
	
        this.mockMvc.perform(
        		RestDocumentationRequestBuilders.delete("/metaoaicat/rest/oaiset/{id}",19).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentation.document("{method-name}", pathParameters( 
        		parameterWithName("id").description("L'oaiset da cancellare") )
                ))
        ;
    }

    @Test
    public void updateOaiset() throws Exception {
    	OaiSet newOaiset = ObjectFactory.getOaiSet(1);
    	 ConstrainedFields fields = new ConstrainedFields(OaiSet.class);
        this.document.snippets(
                responseFields(
                		fieldWithPath("message").description("Messaggio della risposta, nel caso di 'ko' l'errore"),
                        fieldWithPath("status").description("status della risposta (Ok o ko)")
                ),   
        		
                requestFields(
                		fieldWithPath("id").description("ID dell'oaiset"),
                		fieldWithPath("spec").description("il spec del set"),
                		fieldWithPath("name").description("Il nome dell'oaiset"),
                		fieldWithPath("servletName").description("il servlet che serve il set"),
                		fieldWithPath("description_it").description("descrizione italiana"),
                		fieldWithPath("description_en").description("descrizione inglese"),
                		fieldWithPath("solrquery").description("query per solr (senza filtro per deleted)"),
                		fieldWithPath("project").description("progetto"),
                		fieldWithPath("type").description("tipo (non serve al momento)"),
                		fieldWithPath("profiles").description("la lista dei profili da attivare come p.e. mag, oai_dc o mets"),
                		fieldWithPath("limiters").description("non serve al momento"),
                		fieldWithPath("constants").description("Costanti da inserire er tutto il set"),
                		fieldWithPath("values").description("non serve"),
                		fieldWithPath("fields").description("non serve")                		
                        )               
                );
        this.mockMvc.perform(
        		RestDocumentationRequestBuilders.post("/metaoaicat/rest/oaiset")
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(newOaiset)
                        )
        ). andExpect(status().isOk())
        ;
    }
    
    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
