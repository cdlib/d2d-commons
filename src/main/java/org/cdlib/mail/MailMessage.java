package org.cdlib.mail;

import java.util.Objects;

public class MailMessage {

    protected String body;
    protected String to;
    protected String from;
    protected String subject;

    public MailMessage() {
    }

    public MailMessage(String body, String to, String from, String subject) {
        this.body = body;
        this.to = to;
        this.from = from;
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("to=").append(getTo()).append("\n");
        sb.append("from=").append(getFrom()).append("\n");
        sb.append("subject=").append(getSubject()).append("\n");
        sb.append("body=").append(getBody()).append("\n");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.getBody());
        hash = 61 * hash + Objects.hashCode(this.getTo());
        hash = 61 * hash + Objects.hashCode(this.getFrom());
        hash = 61 * hash + Objects.hashCode(this.getSubject());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final MailMessage other = (MailMessage) obj;
        if (!Objects.equals(this.getBody(), other.getBody())) {
            return false;
        }
        if (!Objects.equals(this.getTo(), other.getTo())) {
            return false;
        }
        if (!Objects.equals(this.getFrom(), other.getFrom())) {
            return false;
        }
        if (!Objects.equals(this.getSubject(), other.getSubject())) {
            return false;
        }
        return true;
    }

}
