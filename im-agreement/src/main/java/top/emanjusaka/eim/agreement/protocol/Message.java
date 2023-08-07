package top.emanjusaka.eim.agreement.protocol;

import lombok.Data;

@Data
public class Message {
    private MessageHeader messageHeader;

    private Object messagePack;
}
