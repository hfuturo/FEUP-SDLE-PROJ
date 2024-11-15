// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: document.proto

package feup.sdle.message;

public final class DocumentProto {
  private DocumentProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface DocumentMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:DocumentMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>bytes message = 1;</code>
     * @return The message.
     */
    com.google.protobuf.ByteString getMessage();
  }
  /**
   * Protobuf type {@code DocumentMessage}
   */
  public static final class DocumentMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:DocumentMessage)
      DocumentMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use DocumentMessage.newBuilder() to construct.
    private DocumentMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private DocumentMessage() {
      message_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new DocumentMessage();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return feup.sdle.message.DocumentProto.internal_static_DocumentMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return feup.sdle.message.DocumentProto.internal_static_DocumentMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              feup.sdle.message.DocumentProto.DocumentMessage.class, feup.sdle.message.DocumentProto.DocumentMessage.Builder.class);
    }

    /**
     * Protobuf enum {@code DocumentMessage.type}
     */
    public enum type
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>SHOPPINGLIST = 0;</code>
       */
      SHOPPINGLIST(0),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>SHOPPINGLIST = 0;</code>
       */
      public static final int SHOPPINGLIST_VALUE = 0;


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
      public static type valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static type forNumber(int value) {
        switch (value) {
          case 0: return SHOPPINGLIST;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<type>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          type> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<type>() {
              public type findValueByNumber(int number) {
                return type.forNumber(number);
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
        return feup.sdle.message.DocumentProto.DocumentMessage.getDescriptor().getEnumTypes().get(0);
      }

      private static final type[] VALUES = values();

      public static type valueOf(
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

      private type(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:DocumentMessage.type)
    }

    public static final int MESSAGE_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString message_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes message = 1;</code>
     * @return The message.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getMessage() {
      return message_;
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
      if (!message_.isEmpty()) {
        output.writeBytes(1, message_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!message_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, message_);
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
      if (!(obj instanceof feup.sdle.message.DocumentProto.DocumentMessage)) {
        return super.equals(obj);
      }
      feup.sdle.message.DocumentProto.DocumentMessage other = (feup.sdle.message.DocumentProto.DocumentMessage) obj;

      if (!getMessage()
          .equals(other.getMessage())) return false;
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
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.DocumentProto.DocumentMessage parseFrom(
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
    public static Builder newBuilder(feup.sdle.message.DocumentProto.DocumentMessage prototype) {
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
     * Protobuf type {@code DocumentMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:DocumentMessage)
        feup.sdle.message.DocumentProto.DocumentMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return feup.sdle.message.DocumentProto.internal_static_DocumentMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return feup.sdle.message.DocumentProto.internal_static_DocumentMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                feup.sdle.message.DocumentProto.DocumentMessage.class, feup.sdle.message.DocumentProto.DocumentMessage.Builder.class);
      }

      // Construct using feup.sdle.message.DocumentProto.DocumentMessage.newBuilder()
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
        message_ = com.google.protobuf.ByteString.EMPTY;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return feup.sdle.message.DocumentProto.internal_static_DocumentMessage_descriptor;
      }

      @java.lang.Override
      public feup.sdle.message.DocumentProto.DocumentMessage getDefaultInstanceForType() {
        return feup.sdle.message.DocumentProto.DocumentMessage.getDefaultInstance();
      }

      @java.lang.Override
      public feup.sdle.message.DocumentProto.DocumentMessage build() {
        feup.sdle.message.DocumentProto.DocumentMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public feup.sdle.message.DocumentProto.DocumentMessage buildPartial() {
        feup.sdle.message.DocumentProto.DocumentMessage result = new feup.sdle.message.DocumentProto.DocumentMessage(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(feup.sdle.message.DocumentProto.DocumentMessage result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.message_ = message_;
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
        if (other instanceof feup.sdle.message.DocumentProto.DocumentMessage) {
          return mergeFrom((feup.sdle.message.DocumentProto.DocumentMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(feup.sdle.message.DocumentProto.DocumentMessage other) {
        if (other == feup.sdle.message.DocumentProto.DocumentMessage.getDefaultInstance()) return this;
        if (other.getMessage() != com.google.protobuf.ByteString.EMPTY) {
          setMessage(other.getMessage());
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
                message_ = input.readBytes();
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

      private com.google.protobuf.ByteString message_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes message = 1;</code>
       * @return The message.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getMessage() {
        return message_;
      }
      /**
       * <code>bytes message = 1;</code>
       * @param value The message to set.
       * @return This builder for chaining.
       */
      public Builder setMessage(com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        message_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>bytes message = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000001);
        message_ = getDefaultInstance().getMessage();
        onChanged();
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


      // @@protoc_insertion_point(builder_scope:DocumentMessage)
    }

    // @@protoc_insertion_point(class_scope:DocumentMessage)
    private static final feup.sdle.message.DocumentProto.DocumentMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new feup.sdle.message.DocumentProto.DocumentMessage();
    }

    public static feup.sdle.message.DocumentProto.DocumentMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DocumentMessage>
        PARSER = new com.google.protobuf.AbstractParser<DocumentMessage>() {
      @java.lang.Override
      public DocumentMessage parsePartialFrom(
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

    public static com.google.protobuf.Parser<DocumentMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DocumentMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public feup.sdle.message.DocumentProto.DocumentMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DocumentMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DocumentMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016document.proto\"<\n\017DocumentMessage\022\017\n\007m" +
      "essage\030\001 \001(\014\"\030\n\004type\022\020\n\014SHOPPINGLIST\020\000B\"" +
      "\n\021feup.sdle.messageB\rDocumentProtob\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_DocumentMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_DocumentMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DocumentMessage_descriptor,
        new java.lang.String[] { "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
