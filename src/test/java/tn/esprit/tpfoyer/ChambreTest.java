package tn.esprit.tpfoyer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tn.esprit.tpfoyer.control.*;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class ChambreTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    public ChambreServiceImpl ChambreServices;

    @InjectMocks
    private ChambreRestController chambreRestController;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPiste() throws Exception {
        // Create a sample Piste object (as JSON)
        Bloc b = new Bloc();
        b.setNomBloc("Bloc A");
        b.setCapaciteBloc(200);
        // Mock the service layer to return the created Piste
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(10);
        chambre.setTypeC(TypeChambre.SIMPLE);
       // chambre.setBloc(b);



        // Corrected pisteJson with namePiste included
        String chambreJson = "{\"numeroChambre\": \"" + chambre.getNumeroChambre() + "\", \"typeC\": \"" + chambre.getTypeC() + "\"}";

        when(chambreRestController.addChambre(any(Chambre.class))).thenReturn(chambre);

        // Use MockMvc to simulate HTTP POST request
        mockMvc.perform(post("/chambre/add-chambre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(chambreJson))
        ;


    }

    @Test
    void testGetAllPistes() throws Exception {
        // Arrange: Mock the response
        List<Chambre> chambres = new ArrayList<>();
        when(chambreRestController.getChambres()).thenReturn(chambres);

        // Act: Perform the GET request
        MvcResult result = mockMvc.perform(get("/chambre/retrieve-all-chambres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Print size of the mocked response
       // System.out.println("Size: " + chambres.size());

        // Get the JSON response
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println("Response: " + jsonResponse);

        // Convert JSON response to List<Chambre>
        ObjectMapper objectMapper = new ObjectMapper();
        List<Chambre> retrievedChambres = objectMapper.readValue(jsonResponse,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Chambre.class));

        // Print size and contents of retrievedChambres
        System.out.println("Retrieved Size: " + retrievedChambres.size());
        for (Chambre c : retrievedChambres) {
            System.out.println(c); // Adjust as needed based on Chambre's toString implementation
        }
    }


    @Test
    void testGetById() throws Exception {
        // Mock the service layer to return a specific Piste
        Chambre c = new Chambre();
        c.setIdChambre(1L);


        when(chambreRestController.retrieveChambre(c.getIdChambre())).thenReturn(c);

        // Perform the GET request with path variable
        mockMvc.perform(get("/chambre/retrieve-chambre/"+c.getIdChambre())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
    @Test
    void testDeleteById() throws Exception {
        Chambre c =new Chambre();
        List<Chambre> pistes = new ArrayList<>();

        Chambre p =ChambreServices.retrieveChambre(12L);

        //when(ChambreServices.retrieveAllPistes()).thenReturn();
       // pistes = ChambreServices.retrieveAllPistes();
        System.out.println("size liste des pistes : " + p.getNumeroChambre());

        // Perform the DELETE request with path variable
        /*mockMvc.perform(delete("/piste/delete/"+ p.getNumPiste())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Verify that the service method was called
*/
    }

}
