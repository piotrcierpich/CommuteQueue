package com.piotrek.commitments;

import com.piotrek.Driver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;


/**
 * Created by Piotrek on 2016-10-24.
 */
public class Readiness {
    private final HashMap<LocalDate, Collection<Excuse>> excuses = new HashMap<>();

    public void add(Excuse excuse) {
        if(!excuses.containsKey(excuse.getDate())){
            excuses.put(excuse.getDate(), new ArrayList<>());
        }
        excuses.get(excuse.getDate()).add(excuse);
    }

    public Commitment getCommitment(LocalDate date, Driver driver) {
        if(!excuses.containsKey(date))
            return new ReadyToDrive(date, driver);

        Optional<Excuse> excuseOptional = excuses.get(date)
                                                 .stream()
                                                 .filter(e -> e.matches(date, driver))
                                                 .findFirst();
        if(!excuseOptional.isPresent())
            return new ReadyToDrive(date, driver);

        return excuseOptional.get().getCommitment();
    }
}
