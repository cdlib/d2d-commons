package org.cdlib.domain.objects.consortium;

import java.util.Optional;

/**
 * One of ten UC consortium members: UCB, UCD, UCI, UCLA, UCM, UCR, UCSB, UCSC, UCSD,
 * UCSF.
 * 
 * This list comprises the borrowing institutions with which a requesting patron can be affiliated.
 */
public enum MemberCode {

    UCB,
    UCD,
    UCI,
    UCLA,
    UCM,
    UCR,
    UCSB,
    UCSC,
    UCSD,
    UCSF;

    public static Optional<MemberCode> fromString(String code) {
        for (MemberCode enumCode : MemberCode.values()) {
            if (enumCode.toString().equalsIgnoreCase(code)) {
                return Optional.of(enumCode);
            }
        }
        return Optional.empty();
    }
    
}
