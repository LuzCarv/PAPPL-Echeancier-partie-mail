/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envoimail;

import java.time.LocalDate;

/**
 *
 * @author 96441
 */
public class DateMail {
      private LocalDate datedeadline;
    private String mailredevable;
    private String mailagent;

    public DateMail() {
    }

    public LocalDate getDatedeadline() {
        return datedeadline;
    }

    public String getMailredevable() {
        return mailredevable;
    }

    public String getMailagent() {
        return mailagent;
    }

    public void setDatedeadline(LocalDate datedeadline) {
        this.datedeadline = datedeadline;
    }

    public void setMailredevable(String mailredevable) {
        this.mailredevable = mailredevable;
    }

    public void setMailagent(String mailagent) {
        this.mailagent = mailagent;
    }
}
