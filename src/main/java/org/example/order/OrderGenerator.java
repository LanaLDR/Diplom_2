package org.example.order;

import java.util.List;

public class OrderGenerator {
    public static Order getDefaultOrder() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa7a", "61c0c5a71d1f82001bdaaa6c"));
    }
}
