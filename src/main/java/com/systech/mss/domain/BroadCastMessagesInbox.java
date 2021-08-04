package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = BroadCastMessagesInbox.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BroadCastMessagesInbox implements Serializable {

    @Transient
    public static final String TB_NAME = "broadcastinbox";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Embedded
    BroadCastMessages messages;

    public static BroadCastMessagesInbox from(BroadCastMessages broadCastMessages) {
        BroadCastMessagesInbox broadCastMessagesInbox = new BroadCastMessagesInbox();
        broadCastMessagesInbox.setMessages(broadCastMessages);
        return broadCastMessagesInbox;
    }
}
