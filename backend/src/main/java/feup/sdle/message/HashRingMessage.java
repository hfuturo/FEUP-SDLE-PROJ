// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hashringmsg.proto

package feup.sdle.message;

public final class HashRingMessage {
  private HashRingMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HashRingOrBuilder extends
      // @@protoc_insertion_point(interface_extends:feup.sdle.message.HashRing)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    int getNodesCount();
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    boolean containsNodes(
        java.lang.String key);
    /**
     * Use {@link #getNodesMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
    getNodes();
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
    getNodesMap();
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    /* nullable */
feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodesOrDefault(
        java.lang.String key,
        /* nullable */
feup.sdle.message.NodeIdentifierMessage.NodeIdentifier defaultValue);
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodesOrThrow(
        java.lang.String key);
  }
  /**
   * Protobuf type {@code feup.sdle.message.HashRing}
   */
  public static final class HashRing extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:feup.sdle.message.HashRing)
      HashRingOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use HashRing.newBuilder() to construct.
    private HashRing(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private HashRing() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new HashRing();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return feup.sdle.message.HashRingMessage.internal_static_feup_sdle_message_HashRing_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    @java.lang.Override
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 1:
          return internalGetNodes();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return feup.sdle.message.HashRingMessage.internal_static_feup_sdle_message_HashRing_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              feup.sdle.message.HashRingMessage.HashRing.class, feup.sdle.message.HashRingMessage.HashRing.Builder.class);
    }

    public static final int NODES_FIELD_NUMBER = 1;
    private static final class NodesDefaultEntryHolder {
      static final com.google.protobuf.MapEntry<
          java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> defaultEntry =
              com.google.protobuf.MapEntry
              .<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>newDefaultInstance(
                  feup.sdle.message.HashRingMessage.internal_static_feup_sdle_message_HashRing_NodesEntry_descriptor, 
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "",
                  com.google.protobuf.WireFormat.FieldType.MESSAGE,
                  feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.getDefaultInstance());
    }
    @SuppressWarnings("serial")
    private com.google.protobuf.MapField<
        java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> nodes_;
    private com.google.protobuf.MapField<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
    internalGetNodes() {
      if (nodes_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            NodesDefaultEntryHolder.defaultEntry);
      }
      return nodes_;
    }
    public int getNodesCount() {
      return internalGetNodes().getMap().size();
    }
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    @java.lang.Override
    public boolean containsNodes(
        java.lang.String key) {
      if (key == null) { throw new NullPointerException("map key"); }
      return internalGetNodes().getMap().containsKey(key);
    }
    /**
     * Use {@link #getNodesMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> getNodes() {
      return getNodesMap();
    }
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    @java.lang.Override
    public java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> getNodesMap() {
      return internalGetNodes().getMap();
    }
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    @java.lang.Override
    public /* nullable */
feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodesOrDefault(
        java.lang.String key,
        /* nullable */
feup.sdle.message.NodeIdentifierMessage.NodeIdentifier defaultValue) {
      if (key == null) { throw new NullPointerException("map key"); }
      java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> map =
          internalGetNodes().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
     */
    @java.lang.Override
    public feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodesOrThrow(
        java.lang.String key) {
      if (key == null) { throw new NullPointerException("map key"); }
      java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> map =
          internalGetNodes().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      com.google.protobuf.GeneratedMessageV3
        .serializeStringMapTo(
          output,
          internalGetNodes(),
          NodesDefaultEntryHolder.defaultEntry,
          1);
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      for (java.util.Map.Entry<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> entry
           : internalGetNodes().getMap().entrySet()) {
        com.google.protobuf.MapEntry<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
        nodes__ = NodesDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(1, nodes__);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof feup.sdle.message.HashRingMessage.HashRing)) {
        return super.equals(obj);
      }
      feup.sdle.message.HashRingMessage.HashRing other = (feup.sdle.message.HashRingMessage.HashRing) obj;

      if (!internalGetNodes().equals(
          other.internalGetNodes())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (!internalGetNodes().getMap().isEmpty()) {
        hash = (37 * hash) + NODES_FIELD_NUMBER;
        hash = (53 * hash) + internalGetNodes().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static feup.sdle.message.HashRingMessage.HashRing parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static feup.sdle.message.HashRingMessage.HashRing parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.HashRingMessage.HashRing parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(feup.sdle.message.HashRingMessage.HashRing prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code feup.sdle.message.HashRing}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:feup.sdle.message.HashRing)
        feup.sdle.message.HashRingMessage.HashRingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return feup.sdle.message.HashRingMessage.internal_static_feup_sdle_message_HashRing_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMapField(
          int number) {
        switch (number) {
          case 1:
            return internalGetNodes();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMutableMapField(
          int number) {
        switch (number) {
          case 1:
            return internalGetMutableNodes();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return feup.sdle.message.HashRingMessage.internal_static_feup_sdle_message_HashRing_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                feup.sdle.message.HashRingMessage.HashRing.class, feup.sdle.message.HashRingMessage.HashRing.Builder.class);
      }

      // Construct using feup.sdle.message.HashRingMessage.HashRing.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        internalGetMutableNodes().clear();
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return feup.sdle.message.HashRingMessage.internal_static_feup_sdle_message_HashRing_descriptor;
      }

      @java.lang.Override
      public feup.sdle.message.HashRingMessage.HashRing getDefaultInstanceForType() {
        return feup.sdle.message.HashRingMessage.HashRing.getDefaultInstance();
      }

      @java.lang.Override
      public feup.sdle.message.HashRingMessage.HashRing build() {
        feup.sdle.message.HashRingMessage.HashRing result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public feup.sdle.message.HashRingMessage.HashRing buildPartial() {
        feup.sdle.message.HashRingMessage.HashRing result = new feup.sdle.message.HashRingMessage.HashRing(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(feup.sdle.message.HashRingMessage.HashRing result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.nodes_ = internalGetNodes();
          result.nodes_.makeImmutable();
        }
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof feup.sdle.message.HashRingMessage.HashRing) {
          return mergeFrom((feup.sdle.message.HashRingMessage.HashRing)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(feup.sdle.message.HashRingMessage.HashRing other) {
        if (other == feup.sdle.message.HashRingMessage.HashRing.getDefaultInstance()) return this;
        internalGetMutableNodes().mergeFrom(
            other.internalGetNodes());
        bitField0_ |= 0x00000001;
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                com.google.protobuf.MapEntry<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
                nodes__ = input.readMessage(
                    NodesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                internalGetMutableNodes().getMutableMap().put(
                    nodes__.getKey(), nodes__.getValue());
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.MapField<
          java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> nodes_;
      private com.google.protobuf.MapField<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
          internalGetNodes() {
        if (nodes_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              NodesDefaultEntryHolder.defaultEntry);
        }
        return nodes_;
      }
      private com.google.protobuf.MapField<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
          internalGetMutableNodes() {
        if (nodes_ == null) {
          nodes_ = com.google.protobuf.MapField.newMapField(
              NodesDefaultEntryHolder.defaultEntry);
        }
        if (!nodes_.isMutable()) {
          nodes_ = nodes_.copy();
        }
        bitField0_ |= 0x00000001;
        onChanged();
        return nodes_;
      }
      public int getNodesCount() {
        return internalGetNodes().getMap().size();
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      @java.lang.Override
      public boolean containsNodes(
          java.lang.String key) {
        if (key == null) { throw new NullPointerException("map key"); }
        return internalGetNodes().getMap().containsKey(key);
      }
      /**
       * Use {@link #getNodesMap()} instead.
       */
      @java.lang.Override
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> getNodes() {
        return getNodesMap();
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      @java.lang.Override
      public java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> getNodesMap() {
        return internalGetNodes().getMap();
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      @java.lang.Override
      public /* nullable */
feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodesOrDefault(
          java.lang.String key,
          /* nullable */
feup.sdle.message.NodeIdentifierMessage.NodeIdentifier defaultValue) {
        if (key == null) { throw new NullPointerException("map key"); }
        java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> map =
            internalGetNodes().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      @java.lang.Override
      public feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodesOrThrow(
          java.lang.String key) {
        if (key == null) { throw new NullPointerException("map key"); }
        java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> map =
            internalGetNodes().getMap();
        if (!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
      }
      public Builder clearNodes() {
        bitField0_ = (bitField0_ & ~0x00000001);
        internalGetMutableNodes().getMutableMap()
            .clear();
        return this;
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      public Builder removeNodes(
          java.lang.String key) {
        if (key == null) { throw new NullPointerException("map key"); }
        internalGetMutableNodes().getMutableMap()
            .remove(key);
        return this;
      }
      /**
       * Use alternate mutation accessors instead.
       */
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier>
          getMutableNodes() {
        bitField0_ |= 0x00000001;
        return internalGetMutableNodes().getMutableMap();
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      public Builder putNodes(
          java.lang.String key,
          feup.sdle.message.NodeIdentifierMessage.NodeIdentifier value) {
        if (key == null) { throw new NullPointerException("map key"); }
        if (value == null) { throw new NullPointerException("map value"); }
        internalGetMutableNodes().getMutableMap()
            .put(key, value);
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>map&lt;string, .feup.sdle.message.NodeIdentifier&gt; nodes = 1;</code>
       */
      public Builder putAllNodes(
          java.util.Map<java.lang.String, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier> values) {
        internalGetMutableNodes().getMutableMap()
            .putAll(values);
        bitField0_ |= 0x00000001;
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:feup.sdle.message.HashRing)
    }

    // @@protoc_insertion_point(class_scope:feup.sdle.message.HashRing)
    private static final feup.sdle.message.HashRingMessage.HashRing DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new feup.sdle.message.HashRingMessage.HashRing();
    }

    public static feup.sdle.message.HashRingMessage.HashRing getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<HashRing>
        PARSER = new com.google.protobuf.AbstractParser<HashRing>() {
      @java.lang.Override
      public HashRing parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<HashRing> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<HashRing> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public feup.sdle.message.HashRingMessage.HashRing getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_feup_sdle_message_HashRing_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_feup_sdle_message_HashRing_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_feup_sdle_message_HashRing_NodesEntry_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_feup_sdle_message_HashRing_NodesEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021hashringmsg.proto\022\021feup.sdle.message\032\024" +
      "nodeidentifier.proto\"\222\001\n\010HashRing\0225\n\005nod" +
      "es\030\001 \003(\0132&.feup.sdle.message.HashRing.No" +
      "desEntry\032O\n\nNodesEntry\022\013\n\003key\030\001 \001(\t\0220\n\005v" +
      "alue\030\002 \001(\0132!.feup.sdle.message.NodeIdent" +
      "ifier:\0028\001B$\n\021feup.sdle.messageB\017HashRing" +
      "Messageb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          feup.sdle.message.NodeIdentifierMessage.getDescriptor(),
        });
    internal_static_feup_sdle_message_HashRing_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_feup_sdle_message_HashRing_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_feup_sdle_message_HashRing_descriptor,
        new java.lang.String[] { "Nodes", });
    internal_static_feup_sdle_message_HashRing_NodesEntry_descriptor =
      internal_static_feup_sdle_message_HashRing_descriptor.getNestedTypes().get(0);
    internal_static_feup_sdle_message_HashRing_NodesEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_feup_sdle_message_HashRing_NodesEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    feup.sdle.message.NodeIdentifierMessage.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
