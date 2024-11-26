// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package feup.sdle.message;

public final class Message {
  private Message() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MessageFormatOrBuilder extends
      // @@protoc_insertion_point(interface_extends:feup.sdle.message.MessageFormat)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
     * @return The enum numeric value on the wire for messageType.
     */
    int getMessageTypeValue();
    /**
     * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
     * @return The messageType.
     */
    feup.sdle.message.Message.MessageFormat.MessageType getMessageType();

    /**
     * <code>bytes message = 2;</code>
     * @return The message.
     */
    com.google.protobuf.ByteString getMessage();

    /**
     * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
     * @return Whether the nodeIdentifier field is set.
     */
    boolean hasNodeIdentifier();
    /**
     * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
     * @return The nodeIdentifier.
     */
    feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodeIdentifier();
    /**
     * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
     */
    feup.sdle.message.NodeIdentifierMessage.NodeIdentifierOrBuilder getNodeIdentifierOrBuilder();
  }
  /**
   * Protobuf type {@code feup.sdle.message.MessageFormat}
   */
  public static final class MessageFormat extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:feup.sdle.message.MessageFormat)
      MessageFormatOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MessageFormat.newBuilder() to construct.
    private MessageFormat(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MessageFormat() {
      messageType_ = 0;
      message_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new MessageFormat();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return feup.sdle.message.Message.internal_static_feup_sdle_message_MessageFormat_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return feup.sdle.message.Message.internal_static_feup_sdle_message_MessageFormat_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              feup.sdle.message.Message.MessageFormat.class, feup.sdle.message.Message.MessageFormat.Builder.class);
    }

    /**
     * Protobuf enum {@code feup.sdle.message.MessageFormat.MessageType}
     */
    public enum MessageType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>HASH_RING_LOG = 0;</code>
       */
      HASH_RING_LOG(0),
      /**
       * <code>REPLICATION = 1;</code>
       */
      REPLICATION(1),
      /**
       * <code>HASHRING_GET = 2;</code>
       */
      HASHRING_GET(2),
      /**
       * <code>HASHRING_JOIN = 3;</code>
       */
      HASHRING_JOIN(3),
      /**
       * <code>HASHRING_LOG_HASH_CHECK = 4;</code>
       */
      HASHRING_LOG_HASH_CHECK(4),
      /**
       * <code>DOCUMENT_REQUEST = 5;</code>
       */
      DOCUMENT_REQUEST(5),
      /**
       * <code>DOCUMENT_REPLICATION = 6;</code>
       */
      DOCUMENT_REPLICATION(6),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>HASH_RING_LOG = 0;</code>
       */
      public static final int HASH_RING_LOG_VALUE = 0;
      /**
       * <code>REPLICATION = 1;</code>
       */
      public static final int REPLICATION_VALUE = 1;
      /**
       * <code>HASHRING_GET = 2;</code>
       */
      public static final int HASHRING_GET_VALUE = 2;
      /**
       * <code>HASHRING_JOIN = 3;</code>
       */
      public static final int HASHRING_JOIN_VALUE = 3;
      /**
       * <code>HASHRING_LOG_HASH_CHECK = 4;</code>
       */
      public static final int HASHRING_LOG_HASH_CHECK_VALUE = 4;
      /**
       * <code>DOCUMENT_REQUEST = 5;</code>
       */
      public static final int DOCUMENT_REQUEST_VALUE = 5;
      /**
       * <code>DOCUMENT_REPLICATION = 6;</code>
       */
      public static final int DOCUMENT_REPLICATION_VALUE = 6;


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
      public static MessageType valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static MessageType forNumber(int value) {
        switch (value) {
          case 0: return HASH_RING_LOG;
          case 1: return REPLICATION;
          case 2: return HASHRING_GET;
          case 3: return HASHRING_JOIN;
          case 4: return HASHRING_LOG_HASH_CHECK;
          case 5: return DOCUMENT_REQUEST;
          case 6: return DOCUMENT_REPLICATION;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<MessageType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          MessageType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<MessageType>() {
              public MessageType findValueByNumber(int number) {
                return MessageType.forNumber(number);
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
        return feup.sdle.message.Message.MessageFormat.getDescriptor().getEnumTypes().get(0);
      }

      private static final MessageType[] VALUES = values();

      public static MessageType valueOf(
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

      private MessageType(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:feup.sdle.message.MessageFormat.MessageType)
    }

    public static final int MESSAGETYPE_FIELD_NUMBER = 1;
    private int messageType_;
    /**
     * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
     * @return The enum numeric value on the wire for messageType.
     */
    @java.lang.Override public int getMessageTypeValue() {
      return messageType_;
    }
    /**
     * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
     * @return The messageType.
     */
    @java.lang.Override public feup.sdle.message.Message.MessageFormat.MessageType getMessageType() {
      @SuppressWarnings("deprecation")
      feup.sdle.message.Message.MessageFormat.MessageType result = feup.sdle.message.Message.MessageFormat.MessageType.valueOf(messageType_);
      return result == null ? feup.sdle.message.Message.MessageFormat.MessageType.UNRECOGNIZED : result;
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString message_;
    /**
     * <code>bytes message = 2;</code>
     * @return The message.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getMessage() {
      return message_;
    }

    public static final int NODEIDENTIFIER_FIELD_NUMBER = 3;
    private feup.sdle.message.NodeIdentifierMessage.NodeIdentifier nodeIdentifier_;
    /**
     * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
     * @return Whether the nodeIdentifier field is set.
     */
    @java.lang.Override
    public boolean hasNodeIdentifier() {
      return nodeIdentifier_ != null;
    }
    /**
     * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
     * @return The nodeIdentifier.
     */
    @java.lang.Override
    public feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodeIdentifier() {
      return nodeIdentifier_ == null ? feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.getDefaultInstance() : nodeIdentifier_;
    }
    /**
     * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
     */
    @java.lang.Override
    public feup.sdle.message.NodeIdentifierMessage.NodeIdentifierOrBuilder getNodeIdentifierOrBuilder() {
      return getNodeIdentifier();
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
      if (messageType_ != feup.sdle.message.Message.MessageFormat.MessageType.HASH_RING_LOG.getNumber()) {
        output.writeEnum(1, messageType_);
      }
      if (!message_.isEmpty()) {
        output.writeBytes(2, message_);
      }
      if (nodeIdentifier_ != null) {
        output.writeMessage(3, getNodeIdentifier());
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (messageType_ != feup.sdle.message.Message.MessageFormat.MessageType.HASH_RING_LOG.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, messageType_);
      }
      if (!message_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, message_);
      }
      if (nodeIdentifier_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, getNodeIdentifier());
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
      if (!(obj instanceof feup.sdle.message.Message.MessageFormat)) {
        return super.equals(obj);
      }
      feup.sdle.message.Message.MessageFormat other = (feup.sdle.message.Message.MessageFormat) obj;

      if (messageType_ != other.messageType_) return false;
      if (!getMessage()
          .equals(other.getMessage())) return false;
      if (hasNodeIdentifier() != other.hasNodeIdentifier()) return false;
      if (hasNodeIdentifier()) {
        if (!getNodeIdentifier()
            .equals(other.getNodeIdentifier())) return false;
      }
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
      hash = (37 * hash) + MESSAGETYPE_FIELD_NUMBER;
      hash = (53 * hash) + messageType_;
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      if (hasNodeIdentifier()) {
        hash = (37 * hash) + NODEIDENTIFIER_FIELD_NUMBER;
        hash = (53 * hash) + getNodeIdentifier().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static feup.sdle.message.Message.MessageFormat parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.Message.MessageFormat parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static feup.sdle.message.Message.MessageFormat parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.Message.MessageFormat parseFrom(
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
    public static Builder newBuilder(feup.sdle.message.Message.MessageFormat prototype) {
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
     * Protobuf type {@code feup.sdle.message.MessageFormat}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:feup.sdle.message.MessageFormat)
        feup.sdle.message.Message.MessageFormatOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return feup.sdle.message.Message.internal_static_feup_sdle_message_MessageFormat_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return feup.sdle.message.Message.internal_static_feup_sdle_message_MessageFormat_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                feup.sdle.message.Message.MessageFormat.class, feup.sdle.message.Message.MessageFormat.Builder.class);
      }

      // Construct using feup.sdle.message.Message.MessageFormat.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        messageType_ = 0;

        message_ = com.google.protobuf.ByteString.EMPTY;

        if (nodeIdentifierBuilder_ == null) {
          nodeIdentifier_ = null;
        } else {
          nodeIdentifier_ = null;
          nodeIdentifierBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return feup.sdle.message.Message.internal_static_feup_sdle_message_MessageFormat_descriptor;
      }

      @java.lang.Override
      public feup.sdle.message.Message.MessageFormat getDefaultInstanceForType() {
        return feup.sdle.message.Message.MessageFormat.getDefaultInstance();
      }

      @java.lang.Override
      public feup.sdle.message.Message.MessageFormat build() {
        feup.sdle.message.Message.MessageFormat result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public feup.sdle.message.Message.MessageFormat buildPartial() {
        feup.sdle.message.Message.MessageFormat result = new feup.sdle.message.Message.MessageFormat(this);
        result.messageType_ = messageType_;
        result.message_ = message_;
        if (nodeIdentifierBuilder_ == null) {
          result.nodeIdentifier_ = nodeIdentifier_;
        } else {
          result.nodeIdentifier_ = nodeIdentifierBuilder_.build();
        }
        onBuilt();
        return result;
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
        if (other instanceof feup.sdle.message.Message.MessageFormat) {
          return mergeFrom((feup.sdle.message.Message.MessageFormat)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(feup.sdle.message.Message.MessageFormat other) {
        if (other == feup.sdle.message.Message.MessageFormat.getDefaultInstance()) return this;
        if (other.messageType_ != 0) {
          setMessageTypeValue(other.getMessageTypeValue());
        }
        if (other.getMessage() != com.google.protobuf.ByteString.EMPTY) {
          setMessage(other.getMessage());
        }
        if (other.hasNodeIdentifier()) {
          mergeNodeIdentifier(other.getNodeIdentifier());
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
              case 8: {
                messageType_ = input.readEnum();

                break;
              } // case 8
              case 18: {
                message_ = input.readBytes();

                break;
              } // case 18
              case 26: {
                input.readMessage(
                    getNodeIdentifierFieldBuilder().getBuilder(),
                    extensionRegistry);

                break;
              } // case 26
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

      private int messageType_ = 0;
      /**
       * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
       * @return The enum numeric value on the wire for messageType.
       */
      @java.lang.Override public int getMessageTypeValue() {
        return messageType_;
      }
      /**
       * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
       * @param value The enum numeric value on the wire for messageType to set.
       * @return This builder for chaining.
       */
      public Builder setMessageTypeValue(int value) {
        
        messageType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
       * @return The messageType.
       */
      @java.lang.Override
      public feup.sdle.message.Message.MessageFormat.MessageType getMessageType() {
        @SuppressWarnings("deprecation")
        feup.sdle.message.Message.MessageFormat.MessageType result = feup.sdle.message.Message.MessageFormat.MessageType.valueOf(messageType_);
        return result == null ? feup.sdle.message.Message.MessageFormat.MessageType.UNRECOGNIZED : result;
      }
      /**
       * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
       * @param value The messageType to set.
       * @return This builder for chaining.
       */
      public Builder setMessageType(feup.sdle.message.Message.MessageFormat.MessageType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        messageType_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.feup.sdle.message.MessageFormat.MessageType messageType = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessageType() {
        
        messageType_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString message_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes message = 2;</code>
       * @return The message.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getMessage() {
        return message_;
      }
      /**
       * <code>bytes message = 2;</code>
       * @param value The message to set.
       * @return This builder for chaining.
       */
      public Builder setMessage(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes message = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessage() {
        
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }

      private feup.sdle.message.NodeIdentifierMessage.NodeIdentifier nodeIdentifier_;
      private com.google.protobuf.SingleFieldBuilderV3<
          feup.sdle.message.NodeIdentifierMessage.NodeIdentifier, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.Builder, feup.sdle.message.NodeIdentifierMessage.NodeIdentifierOrBuilder> nodeIdentifierBuilder_;
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       * @return Whether the nodeIdentifier field is set.
       */
      public boolean hasNodeIdentifier() {
        return nodeIdentifierBuilder_ != null || nodeIdentifier_ != null;
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       * @return The nodeIdentifier.
       */
      public feup.sdle.message.NodeIdentifierMessage.NodeIdentifier getNodeIdentifier() {
        if (nodeIdentifierBuilder_ == null) {
          return nodeIdentifier_ == null ? feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.getDefaultInstance() : nodeIdentifier_;
        } else {
          return nodeIdentifierBuilder_.getMessage();
        }
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      public Builder setNodeIdentifier(feup.sdle.message.NodeIdentifierMessage.NodeIdentifier value) {
        if (nodeIdentifierBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          nodeIdentifier_ = value;
          onChanged();
        } else {
          nodeIdentifierBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      public Builder setNodeIdentifier(
          feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.Builder builderForValue) {
        if (nodeIdentifierBuilder_ == null) {
          nodeIdentifier_ = builderForValue.build();
          onChanged();
        } else {
          nodeIdentifierBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      public Builder mergeNodeIdentifier(feup.sdle.message.NodeIdentifierMessage.NodeIdentifier value) {
        if (nodeIdentifierBuilder_ == null) {
          if (nodeIdentifier_ != null) {
            nodeIdentifier_ =
              feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.newBuilder(nodeIdentifier_).mergeFrom(value).buildPartial();
          } else {
            nodeIdentifier_ = value;
          }
          onChanged();
        } else {
          nodeIdentifierBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      public Builder clearNodeIdentifier() {
        if (nodeIdentifierBuilder_ == null) {
          nodeIdentifier_ = null;
          onChanged();
        } else {
          nodeIdentifier_ = null;
          nodeIdentifierBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      public feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.Builder getNodeIdentifierBuilder() {
        
        onChanged();
        return getNodeIdentifierFieldBuilder().getBuilder();
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      public feup.sdle.message.NodeIdentifierMessage.NodeIdentifierOrBuilder getNodeIdentifierOrBuilder() {
        if (nodeIdentifierBuilder_ != null) {
          return nodeIdentifierBuilder_.getMessageOrBuilder();
        } else {
          return nodeIdentifier_ == null ?
              feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.getDefaultInstance() : nodeIdentifier_;
        }
      }
      /**
       * <code>.feup.sdle.message.NodeIdentifier nodeIdentifier = 3;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          feup.sdle.message.NodeIdentifierMessage.NodeIdentifier, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.Builder, feup.sdle.message.NodeIdentifierMessage.NodeIdentifierOrBuilder> 
          getNodeIdentifierFieldBuilder() {
        if (nodeIdentifierBuilder_ == null) {
          nodeIdentifierBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              feup.sdle.message.NodeIdentifierMessage.NodeIdentifier, feup.sdle.message.NodeIdentifierMessage.NodeIdentifier.Builder, feup.sdle.message.NodeIdentifierMessage.NodeIdentifierOrBuilder>(
                  getNodeIdentifier(),
                  getParentForChildren(),
                  isClean());
          nodeIdentifier_ = null;
        }
        return nodeIdentifierBuilder_;
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


      // @@protoc_insertion_point(builder_scope:feup.sdle.message.MessageFormat)
    }

    // @@protoc_insertion_point(class_scope:feup.sdle.message.MessageFormat)
    private static final feup.sdle.message.Message.MessageFormat DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new feup.sdle.message.Message.MessageFormat();
    }

    public static feup.sdle.message.Message.MessageFormat getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MessageFormat>
        PARSER = new com.google.protobuf.AbstractParser<MessageFormat>() {
      @java.lang.Override
      public MessageFormat parsePartialFrom(
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

    public static com.google.protobuf.Parser<MessageFormat> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MessageFormat> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public feup.sdle.message.Message.MessageFormat getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_feup_sdle_message_MessageFormat_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_feup_sdle_message_MessageFormat_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\021feup.sdle.message\032\024node" +
      "identifier.proto\"\304\002\n\rMessageFormat\022A\n\013me" +
      "ssageType\030\001 \001(\0162,.feup.sdle.message.Mess" +
      "ageFormat.MessageType\022\017\n\007message\030\002 \001(\014\0229" +
      "\n\016nodeIdentifier\030\003 \001(\0132!.feup.sdle.messa" +
      "ge.NodeIdentifier\"\243\001\n\013MessageType\022\021\n\rHAS" +
      "H_RING_LOG\020\000\022\017\n\013REPLICATION\020\001\022\020\n\014HASHRIN" +
      "G_GET\020\002\022\021\n\rHASHRING_JOIN\020\003\022\033\n\027HASHRING_L" +
      "OG_HASH_CHECK\020\004\022\024\n\020DOCUMENT_REQUEST\020\005\022\030\n" +
      "\024DOCUMENT_REPLICATION\020\006B\034\n\021feup.sdle.mes" +
      "sageB\007Messageb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          feup.sdle.message.NodeIdentifierMessage.getDescriptor(),
        });
    internal_static_feup_sdle_message_MessageFormat_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_feup_sdle_message_MessageFormat_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_feup_sdle_message_MessageFormat_descriptor,
        new java.lang.String[] { "MessageType", "Message", "NodeIdentifier", });
    feup.sdle.message.NodeIdentifierMessage.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
