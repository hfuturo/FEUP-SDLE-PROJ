package feup.sdle.crdts;

public record DottedValue<I, E, V>(I identifier, E event, V value) {}
