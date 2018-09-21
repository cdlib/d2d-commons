package org.cdlib.domain.objects.fulfillment;

public enum DeliveryMethod {

    /**
     * Loan a physical item, typically a monograph.
     */
    LOAN,
    
    /**
     * Post or send an electronic copy, typically of an article.
     */
    COPY_NON_RETURNABLE;
}
