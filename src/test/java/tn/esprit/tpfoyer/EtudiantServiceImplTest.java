package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("John");

        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(1L);

        assertNotNull(retrievedEtudiant);
        assertEquals("John", retrievedEtudiant.getNomEtudiant());
    }

    @Test
    public void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Jane");

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant addedEtudiant = etudiantService.addEtudiant(etudiant);

        assertNotNull(addedEtudiant);
        assertEquals("Jane", addedEtudiant.getNomEtudiant());
    }

    @Test
    public void testRemoveEtudiant() {
        Long etudiantId = 1L;

        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }
}
