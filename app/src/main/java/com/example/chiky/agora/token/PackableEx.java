package com.example.chiky.agora.token;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
