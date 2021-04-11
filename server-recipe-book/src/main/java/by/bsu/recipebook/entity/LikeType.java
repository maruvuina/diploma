package by.bsu.recipebook.entity;

import by.bsu.recipebook.exception.EnumException;

import java.util.Arrays;

public enum LikeType {
    LIKE(1),
    UNLIKE(-1),
    ;

    private int direction;

    LikeType(int direction) {}

    public static LikeType lookup(Integer direction) throws EnumException {
        return Arrays.stream(LikeType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new EnumException("Like not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
