import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class BlocUnitTests {
    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllBlocs() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, "Bloc A", 100L, null)); // Use 100L and 200L for long values
        blocs.add(new Bloc(2L, "Bloc B", 200L, null));
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(blocs, result);
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Long blocId = 1L;
        Bloc bloc = new Bloc(blocId, "Bloc A", 100, null);
        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.retrieveBloc(blocId);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc(1, "Bloc A", 100, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
    }



    @Test
    void testModifyBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc A", 100, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.modifyBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        Long blocId = 1L;
        doNothing().when(blocRepository).deleteById(blocId);

        // Act
        blocService.removeBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    void testTrouverBlocsSansFoyer() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, "Bloc A", 100, null));
        blocs.add(new Bloc(2L, "Bloc B", 200, null));
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(blocs, result);
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
        // Arrange
        String nomBloc = "Bloc A";
        long capacite = 100;
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, nomBloc, capacite, null));
        when(blocRepository.findAllByNomBlocAndCapaciteBloc(nomBloc, capacite)).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsParNomEtCap(nomBloc, capacite);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(blocs, result);
    }

}
