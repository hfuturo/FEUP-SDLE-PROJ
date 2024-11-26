// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: shoppinglistitem.proto

package feup.sdle.message;

public final class ShoppingListItemProto {
  private ShoppingListItemProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ShoppingListItemOrBuilder extends
      // @@protoc_insertion_point(interface_extends:feup.sdle.message.ShoppingListItem)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
     * @return Whether the ccounter field is set.
     */
    boolean hasCcounter();
    /**
     * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
     * @return The ccounter.
     */
    feup.sdle.message.CCounterProto.CCounter getCcounter();
    /**
     * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
     */
    feup.sdle.message.CCounterProto.CCounterOrBuilder getCcounterOrBuilder();
  }
  /**
   * Protobuf type {@code feup.sdle.message.ShoppingListItem}
   */
  public static final class ShoppingListItem extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:feup.sdle.message.ShoppingListItem)
      ShoppingListItemOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ShoppingListItem.newBuilder() to construct.
    private ShoppingListItem(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ShoppingListItem() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ShoppingListItem();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return feup.sdle.message.ShoppingListItemProto.internal_static_feup_sdle_message_ShoppingListItem_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return feup.sdle.message.ShoppingListItemProto.internal_static_feup_sdle_message_ShoppingListItem_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              feup.sdle.message.ShoppingListItemProto.ShoppingListItem.class, feup.sdle.message.ShoppingListItemProto.ShoppingListItem.Builder.class);
    }

    public static final int CCOUNTER_FIELD_NUMBER = 1;
    private feup.sdle.message.CCounterProto.CCounter ccounter_;
    /**
     * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
     * @return Whether the ccounter field is set.
     */
    @java.lang.Override
    public boolean hasCcounter() {
      return ccounter_ != null;
    }
    /**
     * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
     * @return The ccounter.
     */
    @java.lang.Override
    public feup.sdle.message.CCounterProto.CCounter getCcounter() {
      return ccounter_ == null ? feup.sdle.message.CCounterProto.CCounter.getDefaultInstance() : ccounter_;
    }
    /**
     * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
     */
    @java.lang.Override
    public feup.sdle.message.CCounterProto.CCounterOrBuilder getCcounterOrBuilder() {
      return getCcounter();
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
      if (ccounter_ != null) {
        output.writeMessage(1, getCcounter());
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (ccounter_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getCcounter());
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
      if (!(obj instanceof feup.sdle.message.ShoppingListItemProto.ShoppingListItem)) {
        return super.equals(obj);
      }
      feup.sdle.message.ShoppingListItemProto.ShoppingListItem other = (feup.sdle.message.ShoppingListItemProto.ShoppingListItem) obj;

      if (hasCcounter() != other.hasCcounter()) return false;
      if (hasCcounter()) {
        if (!getCcounter()
            .equals(other.getCcounter())) return false;
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
      if (hasCcounter()) {
        hash = (37 * hash) + CCOUNTER_FIELD_NUMBER;
        hash = (53 * hash) + getCcounter().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem parseFrom(
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
    public static Builder newBuilder(feup.sdle.message.ShoppingListItemProto.ShoppingListItem prototype) {
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
     * Protobuf type {@code feup.sdle.message.ShoppingListItem}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:feup.sdle.message.ShoppingListItem)
        feup.sdle.message.ShoppingListItemProto.ShoppingListItemOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return feup.sdle.message.ShoppingListItemProto.internal_static_feup_sdle_message_ShoppingListItem_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return feup.sdle.message.ShoppingListItemProto.internal_static_feup_sdle_message_ShoppingListItem_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                feup.sdle.message.ShoppingListItemProto.ShoppingListItem.class, feup.sdle.message.ShoppingListItemProto.ShoppingListItem.Builder.class);
      }

      // Construct using feup.sdle.message.ShoppingListItemProto.ShoppingListItem.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        if (ccounterBuilder_ == null) {
          ccounter_ = null;
        } else {
          ccounter_ = null;
          ccounterBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return feup.sdle.message.ShoppingListItemProto.internal_static_feup_sdle_message_ShoppingListItem_descriptor;
      }

      @java.lang.Override
      public feup.sdle.message.ShoppingListItemProto.ShoppingListItem getDefaultInstanceForType() {
        return feup.sdle.message.ShoppingListItemProto.ShoppingListItem.getDefaultInstance();
      }

      @java.lang.Override
      public feup.sdle.message.ShoppingListItemProto.ShoppingListItem build() {
        feup.sdle.message.ShoppingListItemProto.ShoppingListItem result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public feup.sdle.message.ShoppingListItemProto.ShoppingListItem buildPartial() {
        feup.sdle.message.ShoppingListItemProto.ShoppingListItem result = new feup.sdle.message.ShoppingListItemProto.ShoppingListItem(this);
        if (ccounterBuilder_ == null) {
          result.ccounter_ = ccounter_;
        } else {
          result.ccounter_ = ccounterBuilder_.build();
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
        if (other instanceof feup.sdle.message.ShoppingListItemProto.ShoppingListItem) {
          return mergeFrom((feup.sdle.message.ShoppingListItemProto.ShoppingListItem)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(feup.sdle.message.ShoppingListItemProto.ShoppingListItem other) {
        if (other == feup.sdle.message.ShoppingListItemProto.ShoppingListItem.getDefaultInstance()) return this;
        if (other.hasCcounter()) {
          mergeCcounter(other.getCcounter());
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
                input.readMessage(
                    getCcounterFieldBuilder().getBuilder(),
                    extensionRegistry);

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

      private feup.sdle.message.CCounterProto.CCounter ccounter_;
      private com.google.protobuf.SingleFieldBuilderV3<
          feup.sdle.message.CCounterProto.CCounter, feup.sdle.message.CCounterProto.CCounter.Builder, feup.sdle.message.CCounterProto.CCounterOrBuilder> ccounterBuilder_;
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       * @return Whether the ccounter field is set.
       */
      public boolean hasCcounter() {
        return ccounterBuilder_ != null || ccounter_ != null;
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       * @return The ccounter.
       */
      public feup.sdle.message.CCounterProto.CCounter getCcounter() {
        if (ccounterBuilder_ == null) {
          return ccounter_ == null ? feup.sdle.message.CCounterProto.CCounter.getDefaultInstance() : ccounter_;
        } else {
          return ccounterBuilder_.getMessage();
        }
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      public Builder setCcounter(feup.sdle.message.CCounterProto.CCounter value) {
        if (ccounterBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ccounter_ = value;
          onChanged();
        } else {
          ccounterBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      public Builder setCcounter(
          feup.sdle.message.CCounterProto.CCounter.Builder builderForValue) {
        if (ccounterBuilder_ == null) {
          ccounter_ = builderForValue.build();
          onChanged();
        } else {
          ccounterBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      public Builder mergeCcounter(feup.sdle.message.CCounterProto.CCounter value) {
        if (ccounterBuilder_ == null) {
          if (ccounter_ != null) {
            ccounter_ =
              feup.sdle.message.CCounterProto.CCounter.newBuilder(ccounter_).mergeFrom(value).buildPartial();
          } else {
            ccounter_ = value;
          }
          onChanged();
        } else {
          ccounterBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      public Builder clearCcounter() {
        if (ccounterBuilder_ == null) {
          ccounter_ = null;
          onChanged();
        } else {
          ccounter_ = null;
          ccounterBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      public feup.sdle.message.CCounterProto.CCounter.Builder getCcounterBuilder() {
        
        onChanged();
        return getCcounterFieldBuilder().getBuilder();
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      public feup.sdle.message.CCounterProto.CCounterOrBuilder getCcounterOrBuilder() {
        if (ccounterBuilder_ != null) {
          return ccounterBuilder_.getMessageOrBuilder();
        } else {
          return ccounter_ == null ?
              feup.sdle.message.CCounterProto.CCounter.getDefaultInstance() : ccounter_;
        }
      }
      /**
       * <code>.feup.sdle.message.CCounter ccounter = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          feup.sdle.message.CCounterProto.CCounter, feup.sdle.message.CCounterProto.CCounter.Builder, feup.sdle.message.CCounterProto.CCounterOrBuilder> 
          getCcounterFieldBuilder() {
        if (ccounterBuilder_ == null) {
          ccounterBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              feup.sdle.message.CCounterProto.CCounter, feup.sdle.message.CCounterProto.CCounter.Builder, feup.sdle.message.CCounterProto.CCounterOrBuilder>(
                  getCcounter(),
                  getParentForChildren(),
                  isClean());
          ccounter_ = null;
        }
        return ccounterBuilder_;
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


      // @@protoc_insertion_point(builder_scope:feup.sdle.message.ShoppingListItem)
    }

    // @@protoc_insertion_point(class_scope:feup.sdle.message.ShoppingListItem)
    private static final feup.sdle.message.ShoppingListItemProto.ShoppingListItem DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new feup.sdle.message.ShoppingListItemProto.ShoppingListItem();
    }

    public static feup.sdle.message.ShoppingListItemProto.ShoppingListItem getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ShoppingListItem>
        PARSER = new com.google.protobuf.AbstractParser<ShoppingListItem>() {
      @java.lang.Override
      public ShoppingListItem parsePartialFrom(
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

    public static com.google.protobuf.Parser<ShoppingListItem> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ShoppingListItem> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public feup.sdle.message.ShoppingListItemProto.ShoppingListItem getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_feup_sdle_message_ShoppingListItem_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_feup_sdle_message_ShoppingListItem_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026shoppinglistitem.proto\022\021feup.sdle.mess" +
      "age\032\016ccounter.proto\"A\n\020ShoppingListItem\022" +
      "-\n\010ccounter\030\001 \001(\0132\033.feup.sdle.message.CC" +
      "ounterB*\n\021feup.sdle.messageB\025ShoppingLis" +
      "tItemProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          feup.sdle.message.CCounterProto.getDescriptor(),
        });
    internal_static_feup_sdle_message_ShoppingListItem_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_feup_sdle_message_ShoppingListItem_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_feup_sdle_message_ShoppingListItem_descriptor,
        new java.lang.String[] { "Ccounter", });
    feup.sdle.message.CCounterProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
