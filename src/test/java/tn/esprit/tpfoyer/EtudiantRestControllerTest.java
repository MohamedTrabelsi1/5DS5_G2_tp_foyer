package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.service.IEtudiantService;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import java.util.Collections;
import java.util.Date;

import tn.esprit.tpfoyer.entity.Etudiant;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantServices;

    @Test
    void testAddEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean",  new Date(),12345678L, null);
        when(etudiantServices.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomEtudiant\":\"Dupont\",\"prenomEtudiant\":\"Jean\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$.prenomEtudiant").value(etudiant.getPrenomEtudiant()));
    }

    @Test
    void testGetAllEtudiants() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Alice", "Smith", null, 99,null);
        when(etudiantServices.retrieveAllEtudiants()).thenReturn(Collections.singletonList(etudiant));

        mockMvc.perform(get("/etudiant/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$[0].lastName").value(etudiant.getNomEtudiant()));
    }

    @Test
    void testUpdateEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(109, "Alice", "Smith", null, 88888888, null);
        when(etudiantServices.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"Alice\",\"lastName\":\"Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$.lastName").value(etudiant.getNomEtudiant()));
    }

    @Test
    void testGetEtudiantById() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Alice", "Smith", null, 99,null);
        when(etudiantServices.retrieveEtudiant(Long.valueOf(1L))).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$.lastName").value(etudiant.getNomEtudiant()));
    }
}
