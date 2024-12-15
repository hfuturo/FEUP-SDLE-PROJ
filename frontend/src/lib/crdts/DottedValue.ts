export class DottedValue<I, E, V> {
    constructor(
      public identifier: I,
      public event: E,
      public value: V
    ) {
      this.identifier = identifier;
      this.event = event;
      this.value = value;
    }

    toSerializable() {
      return {
        "identifier": this.identifier,
        "event": this.event,
        "value": this.value
      }
    }
  }