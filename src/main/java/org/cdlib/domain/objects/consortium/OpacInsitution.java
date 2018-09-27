package org.cdlib.domain.objects.consortium;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the OPAC used by OCLC WorldCat Local instances.
 *
 * A single enumeration can be used either for a production or parallel WorldCat
 Local instance, as both instances uses the same library OPAC.

 The OpacInsitution regId value is present in the AQ service response that represents the
 OPAC or set of OPACs used by the WorlCat Local instance. The ones enumerated
 here correspond to lending institutions who participate in the UC consortium,
 and whose shelving locations are maintained by CDL.

 Note that not all lenders participating in the consortium have their own OPAC
 regId, as some "institutions" share the same OPAC (specifically NRLF shares
 UCB, and SRLF shares UCLA).
 *
 * @author jff after mjt
 */
public enum OpacInsitution {
    CRL(93175, 93175),
    UCB(5689, 110115),
    UCD(5697, 110113),
    UCI(5656, 110112),
    UCLA(207, 110106),
    UCM(4691, 110111),
    UCR(208, 110107),
    UCSB(211, 110109),
    UCSC(4688, 110110),
    UCSD(30059, 110114),
    UCSF(210, 110108);

    private final int wclOpacRegId;
    private final int wclParallelInstanceRegId;
    private static final Logger logger = Logger.getLogger(OpacInsitution.class.getName());

    OpacInsitution(int prodWclOpacRegId, int parallelWclOpacRegId) {
        this.wclOpacRegId = prodWclOpacRegId;
        this.wclParallelInstanceRegId = parallelWclOpacRegId;
    }

    private int wclProductionRegId() {
        return wclOpacRegId;
    }

    private int wclParallelIRegId() {
        return wclParallelInstanceRegId;
    }

    public static Optional<OpacInsitution> byAnyRegId(int regId) {
        for (OpacInsitution campus : OpacInsitution.values()) {
            if (campus.wclProductionRegId() == regId || campus.wclParallelIRegId() == regId) {
                return Optional.of(campus);
            }
        }
        return Optional.empty();
    }

    public static Optional<OpacInsitution> byAnyRegId(String regIdStr) {
        int regId;
        try {
            regId = Integer.parseInt(regIdStr);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Could not parse string " + regIdStr + " as int.", e);
            return Optional.empty();
        }
        return byAnyRegId(regId);
    }

    public static Optional<OpacInsitution> fromString(String code) {
        for (OpacInsitution enumCode : OpacInsitution.values()) {
            if (enumCode.toString().equalsIgnoreCase(code)) {
                return Optional.of(enumCode);
            }
        }
        return Optional.empty();
    }

}
