package tn.esprit.tpfoyer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import tn.esprit.tpfoyer.entity.Subscription;


public interface ISubscriptionServices {

    Subscription addSubscription(Subscription subscription);

    Subscription updateSubscription(Subscription subscription);

    Subscription retrieveSubscriptionById(Long numSubscription);

   // Set<Subscription> getSubscriptionByType(TypeSubscription type);


}
