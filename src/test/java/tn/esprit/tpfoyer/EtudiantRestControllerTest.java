package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.service.IEtudiantService;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantRestController.class)
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantServices;

    @Test
    void testAddEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean", 12345678L, new Date(), null);
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomEtudiant\":\"Dupont\",\"prenomEtudiant\":\"Jean\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()))
                .andExpect(jsonPath("$.nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$.prenomEtudiant").value(etudiant.getPrenomEtudiant()));
    }
    @Test
    void testAssignEtudiantToClass() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Alice", "Smith", null, null);
        when(etudiantServices.assignEtudiantToClass(any(Etudiant.class), eq(1L))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/assignToClass/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Alice\",\"lastName\":\"Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(etudiant.getId()))
                .andExpect(jsonPath("$.firstName").value(etudiant.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(etudiant.getLastName()));
    }

    @Test
    void testGetAllEtudiants() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Alice", "Smith", null, null);
        when(etudiantServices.retrieveAllEtudiants()).thenReturn(Collections.singletonList(etudiant));

        mockMvc.perform(get("/etudiant/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(etudiant.getId()))
                .andExpect(jsonPath("$[0].firstName").value(etudiant.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(etudiant.getLastName()));
    }

    @Test
    void testUpdateEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Alice", "Smith", null, null);
        when(etudiantServices.updateEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"Alice\",\"lastName\":\"Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(etudiant.getId()))
                .andExpect(jsonPath("$.firstName").value(etudiant.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(etudiant.getLastName()));
    }

    @Test
    void testGetEtudiantById() throws Exception {
        Etudiant etudiant = new Etudiant(1L, "Alice", "Smith", null, null);
        when(etudiantServices.retrieveEtudiant(1L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(etudiant.getId()))
                .andExpect(jsonPath("$.firstName").value(etudiant.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(etudiant.getLastName()));
    }
}
