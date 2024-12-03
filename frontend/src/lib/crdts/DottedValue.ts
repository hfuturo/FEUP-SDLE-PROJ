export class DottedValue<I, E, V> {
    constructor(
      public identifier: I,
      public event: E,
      public value: V
    ) {}
  }