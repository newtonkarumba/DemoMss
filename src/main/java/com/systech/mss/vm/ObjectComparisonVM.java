package com.systech.mss.vm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ObjectComparisonVM{
    String key;
    String old;
    String current;

    public ObjectComparisonVM(String key, String old, String current) {
        this.key = key;
        this.old = old;
        this.current = current;
    }

    String readableKey;
}
