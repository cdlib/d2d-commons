package org.cdlib.mail;

import java.util.Map;
import org.cdlib.util.CollectionUtil;

/**
 * Produces the message that is passed into a mail service method.
 *
 *
 * @author jferrie
 *
 */
public abstract class MailMessage {

  private Map<String, String> model;

  protected static final String NL = "\n";

  public MailMessage() {
  }

  public MailMessage(Map<String, Object> model) {
    this.model = CollectionUtil.toStringMap(model);
  }

  protected Map<String, String> getMessageMap() {
    return model;
  }

  public abstract String getBody();

  public abstract String getToAddress();

  public abstract String getFromAddress();

  public abstract String getSubject();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("to= " + getToAddress() + NL);
    sb.append("from=" + getFromAddress() + NL);
    sb.append("subject=" + getSubject() + NL);
    sb.append("body=" + getBody() + NL);
    return sb.toString();
  }

}
