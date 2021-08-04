package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = BroadCastMessagesOutbox.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BroadCastMessagesOutbox implements Serializable {

    @Transient
    public static final String TB_NAME = "broadcastoutbox";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Embedded
    BroadCastMessages messages;

    public static BroadCastMessagesOutbox from(BroadCastMessages broadCastMessages) {
        BroadCastMessagesOutbox outbox = new BroadCastMessagesOutbox();
        outbox.setMessages(broadCastMessages);
        return outbox;
    }
}
