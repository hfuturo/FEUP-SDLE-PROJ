// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: hashcheck.proto
// Protobuf Java Version: 4.28.2

package feup.sdle.message;

public final class Hashcheck {
  private Hashcheck() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      Hashcheck.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HashCheckOrBuilder extends
      // @@protoc_insertion_point(interface_extends:HashCheck)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string hash = 1;</code>
     * @return The hash.
     */
    java.lang.String getHash();
    /**
     * <code>string hash = 1;</code>
     * @return The bytes for hash.
     */
    com.google.protobuf.ByteString
        getHashBytes();

    /**
     * <code>.HashCheck.ContextType type = 2;</code>
     * @return The enum numeric value on the wire for type.
     */
    int getTypeValue();
    /**
     * <code>.HashCheck.ContextType type = 2;</code>
     * @return The type.
     */
    feup.sdle.message.Hashcheck.HashCheck.ContextType getType();
  }
  /**
   * Protobuf type {@code HashCheck}
   */
  public static final class HashCheck extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:HashCheck)
      HashCheckOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 28,
        /* patch= */ 2,
        /* suffix= */ "",
        HashCheck.class.getName());
    }
    // Use HashCheck.newBuilder() to construct.
    private HashCheck(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private HashCheck() {
      hash_ = "";
      type_ = 0;
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return feup.sdle.message.Hashcheck.internal_static_HashCheck_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return feup.sdle.message.Hashcheck.internal_static_HashCheck_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              feup.sdle.message.Hashcheck.HashCheck.class, feup.sdle.message.Hashcheck.HashCheck.Builder.class);
    }

    /**
     * Protobuf enum {@code HashCheck.ContextType}
     */
    public enum ContextType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>HASHRINGLOG = 0;</code>
       */
      HASHRINGLOG(0),
      /**
       * <code>DOCUMENTCRDT = 1;</code>
       */
      DOCUMENTCRDT(1),
      UNRECOGNIZED(-1),
      ;

      static {
        com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
          com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
          /* major= */ 4,
          /* minor= */ 28,
          /* patch= */ 2,
          /* suffix= */ "",
          ContextType.class.getName());
      }
      /**
       * <code>HASHRINGLOG = 0;</code>
       */
      public static final int HASHRINGLOG_VALUE = 0;
      /**
       * <code>DOCUMENTCRDT = 1;</code>
       */
      public static final int DOCUMENTCRDT_VALUE = 1;


      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static ContextType valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static ContextType forNumber(int value) {
        switch (value) {
          case 0: return HASHRINGLOG;
          case 1: return DOCUMENTCRDT;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<ContextType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          ContextType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<ContextType>() {
              public ContextType findValueByNumber(int number) {
                return ContextType.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalStateException(
              "Can't get the descriptor of an unrecognized enum value.");
        }
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return feup.sdle.message.Hashcheck.HashCheck.getDescriptor().getEnumTypes().get(0);
      }

      private static final ContextType[] VALUES = values();

      public static ContextType valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
          return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private ContextType(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:HashCheck.ContextType)
    }

    public static final int HASH_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile java.lang.Object hash_ = "";
    /**
     * <code>string hash = 1;</code>
     * @return The hash.
     */
    @java.lang.Override
    public java.lang.String getHash() {
      java.lang.Object ref = hash_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        hash_ = s;
        return s;
      }
    }
    /**
     * <code>string hash = 1;</code>
     * @return The bytes for hash.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getHashBytes() {
      java.lang.Object ref = hash_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        hash_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TYPE_FIELD_NUMBER = 2;
    private int type_ = 0;
    /**
     * <code>.HashCheck.ContextType type = 2;</code>
     * @return The enum numeric value on the wire for type.
     */
    @java.lang.Override public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.HashCheck.ContextType type = 2;</code>
     * @return The type.
     */
    @java.lang.Override public feup.sdle.message.Hashcheck.HashCheck.ContextType getType() {
      feup.sdle.message.Hashcheck.HashCheck.ContextType result = feup.sdle.message.Hashcheck.HashCheck.ContextType.forNumber(type_);
      return result == null ? feup.sdle.message.Hashcheck.HashCheck.ContextType.UNRECOGNIZED : result;
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
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(hash_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 1, hash_);
      }
      if (type_ != feup.sdle.message.Hashcheck.HashCheck.ContextType.HASHRINGLOG.getNumber()) {
        output.writeEnum(2, type_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(hash_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(1, hash_);
      }
      if (type_ != feup.sdle.message.Hashcheck.HashCheck.ContextType.HASHRINGLOG.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(2, type_);
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
      if (!(obj instanceof feup.sdle.message.Hashcheck.HashCheck)) {
        return super.equals(obj);
      }
      feup.sdle.message.Hashcheck.HashCheck other = (feup.sdle.message.Hashcheck.HashCheck) obj;

      if (!getHash()
          .equals(other.getHash())) return false;
      if (type_ != other.type_) return false;
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
      hash = (37 * hash) + HASH_FIELD_NUMBER;
      hash = (53 * hash) + getHash().hashCode();
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + type_;
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static feup.sdle.message.Hashcheck.HashCheck parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static feup.sdle.message.Hashcheck.HashCheck parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.Hashcheck.HashCheck parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(feup.sdle.message.Hashcheck.HashCheck prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code HashCheck}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:HashCheck)
        feup.sdle.message.Hashcheck.HashCheckOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return feup.sdle.message.Hashcheck.internal_static_HashCheck_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return feup.sdle.message.Hashcheck.internal_static_HashCheck_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                feup.sdle.message.Hashcheck.HashCheck.class, feup.sdle.message.Hashcheck.HashCheck.Builder.class);
      }

      // Construct using feup.sdle.message.Hashcheck.HashCheck.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        hash_ = "";
        type_ = 0;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return feup.sdle.message.Hashcheck.internal_static_HashCheck_descriptor;
      }

      @java.lang.Override
      public feup.sdle.message.Hashcheck.HashCheck getDefaultInstanceForType() {
        return feup.sdle.message.Hashcheck.HashCheck.getDefaultInstance();
      }

      @java.lang.Override
      public feup.sdle.message.Hashcheck.HashCheck build() {
        feup.sdle.message.Hashcheck.HashCheck result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public feup.sdle.message.Hashcheck.HashCheck buildPartial() {
        feup.sdle.message.Hashcheck.HashCheck result = new feup.sdle.message.Hashcheck.HashCheck(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(feup.sdle.message.Hashcheck.HashCheck result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.hash_ = hash_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.type_ = type_;
        }
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof feup.sdle.message.Hashcheck.HashCheck) {
          return mergeFrom((feup.sdle.message.Hashcheck.HashCheck)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(feup.sdle.message.Hashcheck.HashCheck other) {
        if (other == feup.sdle.message.Hashcheck.HashCheck.getDefaultInstance()) return this;
        if (!other.getHash().isEmpty()) {
          hash_ = other.hash_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (other.type_ != 0) {
          setTypeValue(other.getTypeValue());
        }
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
                hash_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 16: {
                type_ = input.readEnum();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
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

      private java.lang.Object hash_ = "";
      /**
       * <code>string hash = 1;</code>
       * @return The hash.
       */
      public java.lang.String getHash() {
        java.lang.Object ref = hash_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          hash_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string hash = 1;</code>
       * @return The bytes for hash.
       */
      public com.google.protobuf.ByteString
          getHashBytes() {
        java.lang.Object ref = hash_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          hash_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string hash = 1;</code>
       * @param value The hash to set.
       * @return This builder for chaining.
       */
      public Builder setHash(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        hash_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string hash = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearHash() {
        hash_ = getDefaultInstance().getHash();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string hash = 1;</code>
       * @param value The bytes for hash to set.
       * @return This builder for chaining.
       */
      public Builder setHashBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        hash_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private int type_ = 0;
      /**
       * <code>.HashCheck.ContextType type = 2;</code>
       * @return The enum numeric value on the wire for type.
       */
      @java.lang.Override public int getTypeValue() {
        return type_;
      }
      /**
       * <code>.HashCheck.ContextType type = 2;</code>
       * @param value The enum numeric value on the wire for type to set.
       * @return This builder for chaining.
       */
      public Builder setTypeValue(int value) {
        type_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.HashCheck.ContextType type = 2;</code>
       * @return The type.
       */
      @java.lang.Override
      public feup.sdle.message.Hashcheck.HashCheck.ContextType getType() {
        feup.sdle.message.Hashcheck.HashCheck.ContextType result = feup.sdle.message.Hashcheck.HashCheck.ContextType.forNumber(type_);
        return result == null ? feup.sdle.message.Hashcheck.HashCheck.ContextType.UNRECOGNIZED : result;
      }
      /**
       * <code>.HashCheck.ContextType type = 2;</code>
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(feup.sdle.message.Hashcheck.HashCheck.ContextType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000002;
        type_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.HashCheck.ContextType type = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearType() {
        bitField0_ = (bitField0_ & ~0x00000002);
        type_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:HashCheck)
    }

    // @@protoc_insertion_point(class_scope:HashCheck)
    private static final feup.sdle.message.Hashcheck.HashCheck DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new feup.sdle.message.Hashcheck.HashCheck();
    }

    public static feup.sdle.message.Hashcheck.HashCheck getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<HashCheck>
        PARSER = new com.google.protobuf.AbstractParser<HashCheck>() {
      @java.lang.Override
      public HashCheck parsePartialFrom(
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

    public static com.google.protobuf.Parser<HashCheck> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<HashCheck> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public feup.sdle.message.Hashcheck.HashCheck getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HashCheck_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_HashCheck_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017hashcheck.proto\"q\n\tHashCheck\022\014\n\004hash\030\001" +
      " \001(\t\022$\n\004type\030\002 \001(\0162\026.HashCheck.ContextTy" +
      "pe\"0\n\013ContextType\022\017\n\013HASHRINGLOG\020\000\022\020\n\014DO" +
      "CUMENTCRDT\020\001B\023\n\021feup.sdle.messageb\006proto" +
      "3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_HashCheck_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HashCheck_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_HashCheck_descriptor,
        new java.lang.String[] { "Hash", "Type", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
