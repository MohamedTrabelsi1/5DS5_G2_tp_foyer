package tn.esprit.tpfoyer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Subscription;
import tn.esprit.tpfoyer.entity.TypeSubscription;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.ISubscriptionRepository;
import tn.esprit.tpfoyer.service.SubscriptionServicesImp;

import java.time.LocalDate;
import java.util.*;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@AllArgsConstructor
public class SubscriptionServiceImplMock {

    @Mock
    ISubscriptionRepository SubscriptionRepository;

    @InjectMocks
    SubscriptionServicesImp SubscriptionService;


    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusMonths(12);
    Subscription subscription = new Subscription((long)1, startDate, endDate, (float)10.3, TypeSubscription.ANNUAL);


    List<Subscription> listSubscriptions = new ArrayList<Subscription>() {
        {
            add(new Subscription((long)2, startDate, endDate, (float)22.4, TypeSubscription.ANNUAL));
            add(new Subscription((long)3, startDate, endDate, (float)14.5, TypeSubscription.ANNUAL));
        }
    };


    @Test
    public void testRetrieveBloc() {
        Mockito.when(SubscriptionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(subscription));
        Subscription subscription1 = SubscriptionService.retrieveSubscriptionById((long)1);
        Assertions.assertNotNull(subscription1);
    }
}
