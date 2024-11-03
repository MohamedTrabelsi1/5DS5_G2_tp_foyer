package tn.esprit.tpfoyer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EtudiantServiceImplTest {

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @MockBean
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void testAddEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("John");
        etudiant.setPrenomEtudiant("Doe");
        etudiant.setDateNaissance(new Date());
        etudiant.setCinEtudiant(12345678L);

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addEtudiant(etudiant);
        System.out.println("Added Etudiant: " + result);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        assertEquals("Doe", result.getPrenomEtudiant());
        verify(etudiantRepository).save(any(Etudiant.class));
    }

    @Test
    @Order(2)
    void testRetrieveAllEtudiants() {
        // Arrange
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant(1L, "Alice", "Smith", 88, null, null));
        etudiants.add(new Etudiant(2L, "Bob", "Brown", 888, null, null));

        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        System.out.println("Retrieved Etudiants: " + result);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getNomEtudiant());
        assertEquals("Brown", result.get(1).getPrenomEtudiant());
        verify(etudiantRepository).findAll();
    }

    @Test
    @Order(3)
    void testRetrieveEtudiant() {
        // Arrange
        Long etudiantId = 1L;
        Etudiant etudiant = new Etudiant(etudiantId, "John", "Doe", 12345678, null, null);

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(etudiantId);
        System.out.println("Retrieved Etudiant: " + result);

        // Assert
        assertNotNull(result);
        assertEquals(etudiantId, result.getIdEtudiant());
        verify(etudiantRepository).findById(etudiantId);
    }

    @Test
    @Order(4)
    void testModifyEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.modifyEtudiant(etudiant);
        System.out.println("Modified Etudiant: " + result);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository).save(any(Etudiant.class));
    }

    @Test
    @Order(5)
    void testRemoveEtudiant() {
        // Arrange
        Long etudiantId = 1L;

        doNothing().when(etudiantRepository).deleteById(etudiantId);

        // Act
        etudiantService.removeEtudiant(etudiantId);
        System.out.println("Removed Etudiant with ID: " + etudiantId);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }

    @Test
    @Order(6)
    void testRecupererEtudiantParCin() {
        // Arrange
        long cin = 12345678L;
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);

        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.recupererEtudiantParCin(cin);
        System.out.println("Retrieved Etudiant by CIN: " + result);

        // Assert
        assertNotNull(result);
        assertEquals(cin, result.getCinEtudiant());
        verify(etudiantRepository).findEtudiantByCinEtudiant(cin);
    }
}
