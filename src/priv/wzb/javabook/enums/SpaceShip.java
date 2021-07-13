package priv.wzb.javabook.enums;

import java.util.stream.Stream;

public enum SpaceShip {
    SCOUT, CARGO, TRANSPORT,
    CRUISER, BATTLESHIP, MOTHERSHIP;

    @Override
    public String toString() {
        String id = name();
        String lower = id.substring(1).toLowerCase();
        return id.charAt(0) + lower;
    }

    public static void main(String[] args) {
        Stream.of(values())
                .forEach(System.out::println);
    }
}
