package com.bootcamp.demo.data.save;

import lombok.Getter;

@Getter
public enum Stat {
    HP(Type.ADDITIVE),
    ATK(Type.ADDITIVE),
    DODGE(Type.MULTIPLICATIVE),
    COMBO(Type.MULTIPLICATIVE),
    CRIT(Type.MULTIPLICATIVE),
    STUN(Type.MULTIPLICATIVE),
    REGEN(Type.MULTIPLICATIVE),
    STEAL(Type.MULTIPLICATIVE),
    POISON(Type.MULTIPLICATIVE);

    @Getter
    final Type type;

    Stat(Type type) {
        this.type = type;
    }

    public enum Type {
        ADDITIVE,
        MULTIPLICATIVE;
    }
}
