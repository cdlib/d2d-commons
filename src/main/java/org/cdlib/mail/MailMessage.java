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
        sb.append("to= " + to + "\n");
        sb.append("from=" + from + "\n");
        sb.append("subject=" + subject + "\n");
        sb.append("body=" + body + "\n");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.body);
        hash = 61 * hash + Objects.hashCode(this.to);
        hash = 61 * hash + Objects.hashCode(this.from);
        hash = 61 * hash + Objects.hashCode(this.subject);
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MailMessage other = (MailMessage) obj;
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }

}
