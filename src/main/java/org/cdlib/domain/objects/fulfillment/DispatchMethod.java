package org.cdlib.domain.objects.fulfillment;

public enum DispatchMethod {

    /**
     * <p>
     * Fulfills requests for library resources by borrowing physical items, or
     * by receiving copy-non-returnables (electronic copies of photocopied or
     * electronic material) from other institutions.
     * </p>
     */
    ILL,
    /**
     * <p>
     * Fulfills requests for library resources by lending physical items, or by
     * sending copy-non-returnables from an institution library. This service is
     * supplemental to the normal library circulation system, and is often
     * restricted to specific classes of patron.
     * </p>
     *
     * <p>
     * Normally a document delivery service is used for borrowing from the
     * library with which the patron is affiliated. For example, a UCSF faculty
     * member might use a UCSF document delivery service to have photocopied
     * articles sent to her email address.
     * </p>
     *
     * <p>
     * In some cases the document delivery service can be used for interlibrary
     * borrowing, depending on campus policy. For example, UCB Baker can be used
     * for interlibrary borrowing, but the UCD service can only be used for
     * borrowing local library resources.
     * </p>
     */
    DDS;

}
