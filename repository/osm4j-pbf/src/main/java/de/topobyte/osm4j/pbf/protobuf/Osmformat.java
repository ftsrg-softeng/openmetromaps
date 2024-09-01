package de.topobyte.osm4j.pbf.protobuf;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.ByteString.Output;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Osmformat {
   private Osmformat() {
   }

   public static void registerAllExtensions(ExtensionRegistryLite registry) {
   }

   public static final class ChangeSet extends GeneratedMessageLite implements Osmformat.ChangeSetOrBuilder {
      private static final Osmformat.ChangeSet defaultInstance = new Osmformat.ChangeSet(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.ChangeSet> PARSER = new AbstractParser<Osmformat.ChangeSet>() {
         public Osmformat.ChangeSet parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.ChangeSet(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int ID_FIELD_NUMBER = 1;
      private long id_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private ChangeSet(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private ChangeSet(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.ChangeSet getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.ChangeSet getDefaultInstanceForType() {
         return defaultInstance;
      }

      private ChangeSet(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 8:
                     this.bitField0_ |= 1;
                     this.id_ = input.readInt64();
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var35) {
            throw var35.setUnfinishedMessage(this);
         } catch (IOException var36) {
            throw new InvalidProtocolBufferException(var36.getMessage()).setUnfinishedMessage(this);
         } finally {
            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var33) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.ChangeSet> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasId() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public long getId() {
         return this.id_;
      }

      private void initFields() {
         this.id_ = 0L;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasId()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeInt64(1, this.id_);
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeInt64Size(1, this.id_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.ChangeSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(data);
      }

      public static Osmformat.ChangeSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.ChangeSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(data);
      }

      public static Osmformat.ChangeSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.ChangeSet parseFrom(InputStream input) throws IOException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(input);
      }

      public static Osmformat.ChangeSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.ChangeSet parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.ChangeSet)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.ChangeSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.ChangeSet)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.ChangeSet parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(input);
      }

      public static Osmformat.ChangeSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.ChangeSet)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.ChangeSet.Builder newBuilder() {
         return Osmformat.ChangeSet.Builder.create();
      }

      public Osmformat.ChangeSet.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.ChangeSet.Builder newBuilder(Osmformat.ChangeSet prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.ChangeSet.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.ChangeSet, Osmformat.ChangeSet.Builder>
         implements Osmformat.ChangeSetOrBuilder {
         private int bitField0_;
         private long id_;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.ChangeSet.Builder create() {
            return new Osmformat.ChangeSet.Builder();
         }

         public Osmformat.ChangeSet.Builder clear() {
            super.clear();
            this.id_ = 0L;
            this.bitField0_ &= -2;
            return this;
         }

         public Osmformat.ChangeSet.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.ChangeSet getDefaultInstanceForType() {
            return Osmformat.ChangeSet.getDefaultInstance();
         }

         public Osmformat.ChangeSet build() {
            Osmformat.ChangeSet result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.ChangeSet buildPartial() {
            Osmformat.ChangeSet result = new Osmformat.ChangeSet(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.id_ = this.id_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.ChangeSet.Builder mergeFrom(Osmformat.ChangeSet other) {
            if (other == Osmformat.ChangeSet.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasId()) {
                  this.setId(other.getId());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return this.hasId();
         }

         public Osmformat.ChangeSet.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.ChangeSet parsedMessage = null;

            try {
               parsedMessage = (Osmformat.ChangeSet)Osmformat.ChangeSet.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.ChangeSet)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public long getId() {
            return this.id_;
         }

         public Osmformat.ChangeSet.Builder setId(long value) {
            this.bitField0_ |= 1;
            this.id_ = value;
            return this;
         }

         public Osmformat.ChangeSet.Builder clearId() {
            this.bitField0_ &= -2;
            this.id_ = 0L;
            return this;
         }
      }
   }

   public interface ChangeSetOrBuilder extends MessageLiteOrBuilder {
      boolean hasId();

      long getId();
   }

   public static final class DenseInfo extends GeneratedMessageLite implements Osmformat.DenseInfoOrBuilder {
      private static final Osmformat.DenseInfo defaultInstance = new Osmformat.DenseInfo(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.DenseInfo> PARSER = new AbstractParser<Osmformat.DenseInfo>() {
         public Osmformat.DenseInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.DenseInfo(input, extensionRegistry);
         }
      };
      public static final int VERSION_FIELD_NUMBER = 1;
      private List<Integer> version_;
      private int versionMemoizedSerializedSize = -1;
      public static final int TIMESTAMP_FIELD_NUMBER = 2;
      private List<Long> timestamp_;
      private int timestampMemoizedSerializedSize = -1;
      public static final int CHANGESET_FIELD_NUMBER = 3;
      private List<Long> changeset_;
      private int changesetMemoizedSerializedSize = -1;
      public static final int UID_FIELD_NUMBER = 4;
      private List<Integer> uid_;
      private int uidMemoizedSerializedSize = -1;
      public static final int USER_SID_FIELD_NUMBER = 5;
      private List<Integer> userSid_;
      private int userSidMemoizedSerializedSize = -1;
      public static final int VISIBLE_FIELD_NUMBER = 6;
      private List<Boolean> visible_;
      private int visibleMemoizedSerializedSize = -1;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private DenseInfo(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private DenseInfo(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.DenseInfo getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.DenseInfo getDefaultInstanceForType() {
         return defaultInstance;
      }

      private DenseInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               int limit;
               switch (tag) {
                  case 0:
                     done = true;
                     continue;
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                  case 9:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 17:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 25:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 33:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 41:
                  case 43:
                  case 44:
                  case 45:
                  case 46:
                  case 47:
                  case 49:
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
                     continue;
                  case 8:
                     if ((mutable_bitField0_ & 1) != 1) {
                        this.version_ = new ArrayList<>();
                        mutable_bitField0_ |= 1;
                     }

                     this.version_.add(input.readInt32());
                     continue;
                  case 10:
                     int length = input.readRawVarint32();
                     limit = input.pushLimit(length);
                     if ((mutable_bitField0_ & 1) != 1 && input.getBytesUntilLimit() > 0) {
                        this.version_ = new ArrayList<>();
                        mutable_bitField0_ |= 1;
                     }
                     break;
                  case 16:
                     if ((mutable_bitField0_ & 2) != 2) {
                        this.timestamp_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }

                     this.timestamp_.add(input.readSInt64());
                     continue;
                  case 18:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                        this.timestamp_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.timestamp_.add(input.readSInt64());
                     }

                     input.popLimit(limit);
                     continue;
                  case 24:
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.changeset_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     this.changeset_.add(input.readSInt64());
                     continue;
                  case 26:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                        this.changeset_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.changeset_.add(input.readSInt64());
                     }

                     input.popLimit(limit);
                     continue;
                  case 32:
                     if ((mutable_bitField0_ & 8) != 8) {
                        this.uid_ = new ArrayList<>();
                        mutable_bitField0_ |= 8;
                     }

                     this.uid_.add(input.readSInt32());
                     continue;
                  case 34:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 8) != 8 && input.getBytesUntilLimit() > 0) {
                        this.uid_ = new ArrayList<>();
                        mutable_bitField0_ |= 8;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.uid_.add(input.readSInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  case 40:
                     if ((mutable_bitField0_ & 16) != 16) {
                        this.userSid_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     this.userSid_.add(input.readSInt32());
                     continue;
                  case 42:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 16) != 16 && input.getBytesUntilLimit() > 0) {
                        this.userSid_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.userSid_.add(input.readSInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  case 48:
                     if ((mutable_bitField0_ & 32) != 32) {
                        this.visible_ = new ArrayList<>();
                        mutable_bitField0_ |= 32;
                     }

                     this.visible_.add(input.readBool());
                     continue;
                  case 50:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 32) != 32 && input.getBytesUntilLimit() > 0) {
                        this.visible_ = new ArrayList<>();
                        mutable_bitField0_ |= 32;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.visible_.add(input.readBool());
                     }

                     input.popLimit(limit);
                     continue;
               }

               while (input.getBytesUntilLimit() > 0) {
                  this.version_.add(input.readInt32());
               }

               input.popLimit(limit);
            }
         } catch (InvalidProtocolBufferException var37) {
            throw var37.setUnfinishedMessage(this);
         } catch (IOException var38) {
            throw new InvalidProtocolBufferException(var38.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 1) == 1) {
               this.version_ = Collections.unmodifiableList(this.version_);
            }

            if ((mutable_bitField0_ & 2) == 2) {
               this.timestamp_ = Collections.unmodifiableList(this.timestamp_);
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.changeset_ = Collections.unmodifiableList(this.changeset_);
            }

            if ((mutable_bitField0_ & 8) == 8) {
               this.uid_ = Collections.unmodifiableList(this.uid_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.userSid_ = Collections.unmodifiableList(this.userSid_);
            }

            if ((mutable_bitField0_ & 32) == 32) {
               this.visible_ = Collections.unmodifiableList(this.visible_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var35) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.DenseInfo> getParserForType() {
         return PARSER;
      }

      @Override
      public List<Integer> getVersionList() {
         return this.version_;
      }

      @Override
      public int getVersionCount() {
         return this.version_.size();
      }

      @Override
      public int getVersion(int index) {
         return this.version_.get(index);
      }

      @Override
      public List<Long> getTimestampList() {
         return this.timestamp_;
      }

      @Override
      public int getTimestampCount() {
         return this.timestamp_.size();
      }

      @Override
      public long getTimestamp(int index) {
         return this.timestamp_.get(index);
      }

      @Override
      public List<Long> getChangesetList() {
         return this.changeset_;
      }

      @Override
      public int getChangesetCount() {
         return this.changeset_.size();
      }

      @Override
      public long getChangeset(int index) {
         return this.changeset_.get(index);
      }

      @Override
      public List<Integer> getUidList() {
         return this.uid_;
      }

      @Override
      public int getUidCount() {
         return this.uid_.size();
      }

      @Override
      public int getUid(int index) {
         return this.uid_.get(index);
      }

      @Override
      public List<Integer> getUserSidList() {
         return this.userSid_;
      }

      @Override
      public int getUserSidCount() {
         return this.userSid_.size();
      }

      @Override
      public int getUserSid(int index) {
         return this.userSid_.get(index);
      }

      @Override
      public List<Boolean> getVisibleList() {
         return this.visible_;
      }

      @Override
      public int getVisibleCount() {
         return this.visible_.size();
      }

      @Override
      public boolean getVisible(int index) {
         return this.visible_.get(index);
      }

      private void initFields() {
         this.version_ = Collections.emptyList();
         this.timestamp_ = Collections.emptyList();
         this.changeset_ = Collections.emptyList();
         this.uid_ = Collections.emptyList();
         this.userSid_ = Collections.emptyList();
         this.visible_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if (this.getVersionList().size() > 0) {
            output.writeRawVarint32(10);
            output.writeRawVarint32(this.versionMemoizedSerializedSize);
         }

         for (int i = 0; i < this.version_.size(); i++) {
            output.writeInt32NoTag(this.version_.get(i));
         }

         if (this.getTimestampList().size() > 0) {
            output.writeRawVarint32(18);
            output.writeRawVarint32(this.timestampMemoizedSerializedSize);
         }

         for (int i = 0; i < this.timestamp_.size(); i++) {
            output.writeSInt64NoTag(this.timestamp_.get(i));
         }

         if (this.getChangesetList().size() > 0) {
            output.writeRawVarint32(26);
            output.writeRawVarint32(this.changesetMemoizedSerializedSize);
         }

         for (int i = 0; i < this.changeset_.size(); i++) {
            output.writeSInt64NoTag(this.changeset_.get(i));
         }

         if (this.getUidList().size() > 0) {
            output.writeRawVarint32(34);
            output.writeRawVarint32(this.uidMemoizedSerializedSize);
         }

         for (int i = 0; i < this.uid_.size(); i++) {
            output.writeSInt32NoTag(this.uid_.get(i));
         }

         if (this.getUserSidList().size() > 0) {
            output.writeRawVarint32(42);
            output.writeRawVarint32(this.userSidMemoizedSerializedSize);
         }

         for (int i = 0; i < this.userSid_.size(); i++) {
            output.writeSInt32NoTag(this.userSid_.get(i));
         }

         if (this.getVisibleList().size() > 0) {
            output.writeRawVarint32(50);
            output.writeRawVarint32(this.visibleMemoizedSerializedSize);
         }

         for (int i = 0; i < this.visible_.size(); i++) {
            output.writeBoolNoTag(this.visible_.get(i));
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            int var4 = 0;
            int dataSize = 0;

            for (int i = 0; i < this.version_.size(); i++) {
               dataSize += CodedOutputStream.computeInt32SizeNoTag(this.version_.get(i));
            }

            var4 += dataSize;
            if (!this.getVersionList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.versionMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.timestamp_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.timestamp_.get(i));
            }

            var4 += dataSize;
            if (!this.getTimestampList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.timestampMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.changeset_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.changeset_.get(i));
            }

            var4 += dataSize;
            if (!this.getChangesetList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.changesetMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.uid_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt32SizeNoTag(this.uid_.get(i));
            }

            var4 += dataSize;
            if (!this.getUidList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.uidMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.userSid_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt32SizeNoTag(this.userSid_.get(i));
            }

            var4 += dataSize;
            if (!this.getUserSidList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.userSidMemoizedSerializedSize = dataSize;
            dataSize = 0;
            dataSize = 1 * this.getVisibleList().size();
            var4 += dataSize;
            if (!this.getVisibleList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.visibleMemoizedSerializedSize = dataSize;
            var4 += this.unknownFields.size();
            this.memoizedSerializedSize = var4;
            return var4;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.DenseInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(data);
      }

      public static Osmformat.DenseInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.DenseInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(data);
      }

      public static Osmformat.DenseInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.DenseInfo parseFrom(InputStream input) throws IOException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(input);
      }

      public static Osmformat.DenseInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.DenseInfo parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.DenseInfo)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.DenseInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.DenseInfo)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.DenseInfo parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(input);
      }

      public static Osmformat.DenseInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.DenseInfo)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.DenseInfo.Builder newBuilder() {
         return Osmformat.DenseInfo.Builder.create();
      }

      public Osmformat.DenseInfo.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.DenseInfo.Builder newBuilder(Osmformat.DenseInfo prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.DenseInfo.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.DenseInfo, Osmformat.DenseInfo.Builder>
         implements Osmformat.DenseInfoOrBuilder {
         private int bitField0_;
         private List<Integer> version_ = Collections.emptyList();
         private List<Long> timestamp_ = Collections.emptyList();
         private List<Long> changeset_ = Collections.emptyList();
         private List<Integer> uid_ = Collections.emptyList();
         private List<Integer> userSid_ = Collections.emptyList();
         private List<Boolean> visible_ = Collections.emptyList();

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.DenseInfo.Builder create() {
            return new Osmformat.DenseInfo.Builder();
         }

         public Osmformat.DenseInfo.Builder clear() {
            super.clear();
            this.version_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.timestamp_ = Collections.emptyList();
            this.bitField0_ &= -3;
            this.changeset_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.uid_ = Collections.emptyList();
            this.bitField0_ &= -9;
            this.userSid_ = Collections.emptyList();
            this.bitField0_ &= -17;
            this.visible_ = Collections.emptyList();
            this.bitField0_ &= -33;
            return this;
         }

         public Osmformat.DenseInfo.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.DenseInfo getDefaultInstanceForType() {
            return Osmformat.DenseInfo.getDefaultInstance();
         }

         public Osmformat.DenseInfo build() {
            Osmformat.DenseInfo result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.DenseInfo buildPartial() {
            Osmformat.DenseInfo result = new Osmformat.DenseInfo(this);
            int from_bitField0_ = this.bitField0_;
            if ((this.bitField0_ & 1) == 1) {
               this.version_ = Collections.unmodifiableList(this.version_);
               this.bitField0_ &= -2;
            }

            result.version_ = this.version_;
            if ((this.bitField0_ & 2) == 2) {
               this.timestamp_ = Collections.unmodifiableList(this.timestamp_);
               this.bitField0_ &= -3;
            }

            result.timestamp_ = this.timestamp_;
            if ((this.bitField0_ & 4) == 4) {
               this.changeset_ = Collections.unmodifiableList(this.changeset_);
               this.bitField0_ &= -5;
            }

            result.changeset_ = this.changeset_;
            if ((this.bitField0_ & 8) == 8) {
               this.uid_ = Collections.unmodifiableList(this.uid_);
               this.bitField0_ &= -9;
            }

            result.uid_ = this.uid_;
            if ((this.bitField0_ & 16) == 16) {
               this.userSid_ = Collections.unmodifiableList(this.userSid_);
               this.bitField0_ &= -17;
            }

            result.userSid_ = this.userSid_;
            if ((this.bitField0_ & 32) == 32) {
               this.visible_ = Collections.unmodifiableList(this.visible_);
               this.bitField0_ &= -33;
            }

            result.visible_ = this.visible_;
            return result;
         }

         public Osmformat.DenseInfo.Builder mergeFrom(Osmformat.DenseInfo other) {
            if (other == Osmformat.DenseInfo.getDefaultInstance()) {
               return this;
            } else {
               if (!other.version_.isEmpty()) {
                  if (this.version_.isEmpty()) {
                     this.version_ = other.version_;
                     this.bitField0_ &= -2;
                  } else {
                     this.ensureVersionIsMutable();
                     this.version_.addAll(other.version_);
                  }
               }

               if (!other.timestamp_.isEmpty()) {
                  if (this.timestamp_.isEmpty()) {
                     this.timestamp_ = other.timestamp_;
                     this.bitField0_ &= -3;
                  } else {
                     this.ensureTimestampIsMutable();
                     this.timestamp_.addAll(other.timestamp_);
                  }
               }

               if (!other.changeset_.isEmpty()) {
                  if (this.changeset_.isEmpty()) {
                     this.changeset_ = other.changeset_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureChangesetIsMutable();
                     this.changeset_.addAll(other.changeset_);
                  }
               }

               if (!other.uid_.isEmpty()) {
                  if (this.uid_.isEmpty()) {
                     this.uid_ = other.uid_;
                     this.bitField0_ &= -9;
                  } else {
                     this.ensureUidIsMutable();
                     this.uid_.addAll(other.uid_);
                  }
               }

               if (!other.userSid_.isEmpty()) {
                  if (this.userSid_.isEmpty()) {
                     this.userSid_ = other.userSid_;
                     this.bitField0_ &= -17;
                  } else {
                     this.ensureUserSidIsMutable();
                     this.userSid_.addAll(other.userSid_);
                  }
               }

               if (!other.visible_.isEmpty()) {
                  if (this.visible_.isEmpty()) {
                     this.visible_ = other.visible_;
                     this.bitField0_ &= -33;
                  } else {
                     this.ensureVisibleIsMutable();
                     this.visible_.addAll(other.visible_);
                  }
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return true;
         }

         public Osmformat.DenseInfo.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.DenseInfo parsedMessage = null;

            try {
               parsedMessage = (Osmformat.DenseInfo)Osmformat.DenseInfo.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.DenseInfo)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         private void ensureVersionIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
               this.version_ = new ArrayList<>(this.version_);
               this.bitField0_ |= 1;
            }
         }

         @Override
         public List<Integer> getVersionList() {
            return Collections.unmodifiableList(this.version_);
         }

         @Override
         public int getVersionCount() {
            return this.version_.size();
         }

         @Override
         public int getVersion(int index) {
            return this.version_.get(index);
         }

         public Osmformat.DenseInfo.Builder setVersion(int index, int value) {
            this.ensureVersionIsMutable();
            this.version_.set(index, value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addVersion(int value) {
            this.ensureVersionIsMutable();
            this.version_.add(value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addAllVersion(Iterable<? extends Integer> values) {
            this.ensureVersionIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.version_);
            return this;
         }

         public Osmformat.DenseInfo.Builder clearVersion() {
            this.version_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
         }

         private void ensureTimestampIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
               this.timestamp_ = new ArrayList<>(this.timestamp_);
               this.bitField0_ |= 2;
            }
         }

         @Override
         public List<Long> getTimestampList() {
            return Collections.unmodifiableList(this.timestamp_);
         }

         @Override
         public int getTimestampCount() {
            return this.timestamp_.size();
         }

         @Override
         public long getTimestamp(int index) {
            return this.timestamp_.get(index);
         }

         public Osmformat.DenseInfo.Builder setTimestamp(int index, long value) {
            this.ensureTimestampIsMutable();
            this.timestamp_.set(index, value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addTimestamp(long value) {
            this.ensureTimestampIsMutable();
            this.timestamp_.add(value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addAllTimestamp(Iterable<? extends Long> values) {
            this.ensureTimestampIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.timestamp_);
            return this;
         }

         public Osmformat.DenseInfo.Builder clearTimestamp() {
            this.timestamp_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
         }

         private void ensureChangesetIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.changeset_ = new ArrayList<>(this.changeset_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public List<Long> getChangesetList() {
            return Collections.unmodifiableList(this.changeset_);
         }

         @Override
         public int getChangesetCount() {
            return this.changeset_.size();
         }

         @Override
         public long getChangeset(int index) {
            return this.changeset_.get(index);
         }

         public Osmformat.DenseInfo.Builder setChangeset(int index, long value) {
            this.ensureChangesetIsMutable();
            this.changeset_.set(index, value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addChangeset(long value) {
            this.ensureChangesetIsMutable();
            this.changeset_.add(value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addAllChangeset(Iterable<? extends Long> values) {
            this.ensureChangesetIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.changeset_);
            return this;
         }

         public Osmformat.DenseInfo.Builder clearChangeset() {
            this.changeset_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
         }

         private void ensureUidIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
               this.uid_ = new ArrayList<>(this.uid_);
               this.bitField0_ |= 8;
            }
         }

         @Override
         public List<Integer> getUidList() {
            return Collections.unmodifiableList(this.uid_);
         }

         @Override
         public int getUidCount() {
            return this.uid_.size();
         }

         @Override
         public int getUid(int index) {
            return this.uid_.get(index);
         }

         public Osmformat.DenseInfo.Builder setUid(int index, int value) {
            this.ensureUidIsMutable();
            this.uid_.set(index, value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addUid(int value) {
            this.ensureUidIsMutable();
            this.uid_.add(value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addAllUid(Iterable<? extends Integer> values) {
            this.ensureUidIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uid_);
            return this;
         }

         public Osmformat.DenseInfo.Builder clearUid() {
            this.uid_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
         }

         private void ensureUserSidIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.userSid_ = new ArrayList<>(this.userSid_);
               this.bitField0_ |= 16;
            }
         }

         @Override
         public List<Integer> getUserSidList() {
            return Collections.unmodifiableList(this.userSid_);
         }

         @Override
         public int getUserSidCount() {
            return this.userSid_.size();
         }

         @Override
         public int getUserSid(int index) {
            return this.userSid_.get(index);
         }

         public Osmformat.DenseInfo.Builder setUserSid(int index, int value) {
            this.ensureUserSidIsMutable();
            this.userSid_.set(index, value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addUserSid(int value) {
            this.ensureUserSidIsMutable();
            this.userSid_.add(value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addAllUserSid(Iterable<? extends Integer> values) {
            this.ensureUserSidIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.userSid_);
            return this;
         }

         public Osmformat.DenseInfo.Builder clearUserSid() {
            this.userSid_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }

         private void ensureVisibleIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
               this.visible_ = new ArrayList<>(this.visible_);
               this.bitField0_ |= 32;
            }
         }

         @Override
         public List<Boolean> getVisibleList() {
            return Collections.unmodifiableList(this.visible_);
         }

         @Override
         public int getVisibleCount() {
            return this.visible_.size();
         }

         @Override
         public boolean getVisible(int index) {
            return this.visible_.get(index);
         }

         public Osmformat.DenseInfo.Builder setVisible(int index, boolean value) {
            this.ensureVisibleIsMutable();
            this.visible_.set(index, value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addVisible(boolean value) {
            this.ensureVisibleIsMutable();
            this.visible_.add(value);
            return this;
         }

         public Osmformat.DenseInfo.Builder addAllVisible(Iterable<? extends Boolean> values) {
            this.ensureVisibleIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.visible_);
            return this;
         }

         public Osmformat.DenseInfo.Builder clearVisible() {
            this.visible_ = Collections.emptyList();
            this.bitField0_ &= -33;
            return this;
         }
      }
   }

   public interface DenseInfoOrBuilder extends MessageLiteOrBuilder {
      List<Integer> getVersionList();

      int getVersionCount();

      int getVersion(int var1);

      List<Long> getTimestampList();

      int getTimestampCount();

      long getTimestamp(int var1);

      List<Long> getChangesetList();

      int getChangesetCount();

      long getChangeset(int var1);

      List<Integer> getUidList();

      int getUidCount();

      int getUid(int var1);

      List<Integer> getUserSidList();

      int getUserSidCount();

      int getUserSid(int var1);

      List<Boolean> getVisibleList();

      int getVisibleCount();

      boolean getVisible(int var1);
   }

   public static final class DenseNodes extends GeneratedMessageLite implements Osmformat.DenseNodesOrBuilder {
      private static final Osmformat.DenseNodes defaultInstance = new Osmformat.DenseNodes(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.DenseNodes> PARSER = new AbstractParser<Osmformat.DenseNodes>() {
         public Osmformat.DenseNodes parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.DenseNodes(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int ID_FIELD_NUMBER = 1;
      private List<Long> id_;
      private int idMemoizedSerializedSize = -1;
      public static final int DENSEINFO_FIELD_NUMBER = 5;
      private Osmformat.DenseInfo denseinfo_;
      public static final int LAT_FIELD_NUMBER = 8;
      private List<Long> lat_;
      private int latMemoizedSerializedSize = -1;
      public static final int LON_FIELD_NUMBER = 9;
      private List<Long> lon_;
      private int lonMemoizedSerializedSize = -1;
      public static final int KEYS_VALS_FIELD_NUMBER = 10;
      private List<Integer> keysVals_;
      private int keysValsMemoizedSerializedSize = -1;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private DenseNodes(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private DenseNodes(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.DenseNodes getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.DenseNodes getDefaultInstanceForType() {
         return defaultInstance;
      }

      private DenseNodes(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               int limit;
               switch (tag) {
                  case 0:
                     done = true;
                     continue;
                  case 8:
                     if ((mutable_bitField0_ & 1) != 1) {
                        this.id_ = new ArrayList<>();
                        mutable_bitField0_ |= 1;
                     }

                     this.id_.add(input.readSInt64());
                     continue;
                  case 10:
                     int length = input.readRawVarint32();
                     limit = input.pushLimit(length);
                     if ((mutable_bitField0_ & 1) != 1 && input.getBytesUntilLimit() > 0) {
                        this.id_ = new ArrayList<>();
                        mutable_bitField0_ |= 1;
                     }
                     break;
                  case 42:
                     Osmformat.DenseInfo.Builder subBuilder = null;
                     if ((this.bitField0_ & 1) == 1) {
                        subBuilder = this.denseinfo_.toBuilder();
                     }

                     this.denseinfo_ = (Osmformat.DenseInfo)input.readMessage(Osmformat.DenseInfo.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.denseinfo_);
                        this.denseinfo_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 1;
                     continue;
                  case 64:
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.lat_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     this.lat_.add(input.readSInt64());
                     continue;
                  case 66:
                     int lengthx = input.readRawVarint32();
                     limit = input.pushLimit(lengthx);
                     if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                        this.lat_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.lat_.add(input.readSInt64());
                     }

                     input.popLimit(limit);
                     continue;
                  case 72:
                     if ((mutable_bitField0_ & 8) != 8) {
                        this.lon_ = new ArrayList<>();
                        mutable_bitField0_ |= 8;
                     }

                     this.lon_.add(input.readSInt64());
                     continue;
                  case 74:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 8) != 8 && input.getBytesUntilLimit() > 0) {
                        this.lon_ = new ArrayList<>();
                        mutable_bitField0_ |= 8;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.lon_.add(input.readSInt64());
                     }

                     input.popLimit(limit);
                     continue;
                  case 80:
                     if ((mutable_bitField0_ & 16) != 16) {
                        this.keysVals_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     this.keysVals_.add(input.readInt32());
                     continue;
                  case 82:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 16) != 16 && input.getBytesUntilLimit() > 0) {
                        this.keysVals_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.keysVals_.add(input.readInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
                     continue;
               }

               while (input.getBytesUntilLimit() > 0) {
                  this.id_.add(input.readSInt64());
               }

               input.popLimit(limit);
            }
         } catch (InvalidProtocolBufferException var37) {
            throw var37.setUnfinishedMessage(this);
         } catch (IOException var38) {
            throw new InvalidProtocolBufferException(var38.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 1) == 1) {
               this.id_ = Collections.unmodifiableList(this.id_);
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.lat_ = Collections.unmodifiableList(this.lat_);
            }

            if ((mutable_bitField0_ & 8) == 8) {
               this.lon_ = Collections.unmodifiableList(this.lon_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.keysVals_ = Collections.unmodifiableList(this.keysVals_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var35) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.DenseNodes> getParserForType() {
         return PARSER;
      }

      @Override
      public List<Long> getIdList() {
         return this.id_;
      }

      @Override
      public int getIdCount() {
         return this.id_.size();
      }

      @Override
      public long getId(int index) {
         return this.id_.get(index);
      }

      @Override
      public boolean hasDenseinfo() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public Osmformat.DenseInfo getDenseinfo() {
         return this.denseinfo_;
      }

      @Override
      public List<Long> getLatList() {
         return this.lat_;
      }

      @Override
      public int getLatCount() {
         return this.lat_.size();
      }

      @Override
      public long getLat(int index) {
         return this.lat_.get(index);
      }

      @Override
      public List<Long> getLonList() {
         return this.lon_;
      }

      @Override
      public int getLonCount() {
         return this.lon_.size();
      }

      @Override
      public long getLon(int index) {
         return this.lon_.get(index);
      }

      @Override
      public List<Integer> getKeysValsList() {
         return this.keysVals_;
      }

      @Override
      public int getKeysValsCount() {
         return this.keysVals_.size();
      }

      @Override
      public int getKeysVals(int index) {
         return this.keysVals_.get(index);
      }

      private void initFields() {
         this.id_ = Collections.emptyList();
         this.denseinfo_ = Osmformat.DenseInfo.getDefaultInstance();
         this.lat_ = Collections.emptyList();
         this.lon_ = Collections.emptyList();
         this.keysVals_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if (this.getIdList().size() > 0) {
            output.writeRawVarint32(10);
            output.writeRawVarint32(this.idMemoizedSerializedSize);
         }

         for (int i = 0; i < this.id_.size(); i++) {
            output.writeSInt64NoTag(this.id_.get(i));
         }

         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(5, this.denseinfo_);
         }

         if (this.getLatList().size() > 0) {
            output.writeRawVarint32(66);
            output.writeRawVarint32(this.latMemoizedSerializedSize);
         }

         for (int i = 0; i < this.lat_.size(); i++) {
            output.writeSInt64NoTag(this.lat_.get(i));
         }

         if (this.getLonList().size() > 0) {
            output.writeRawVarint32(74);
            output.writeRawVarint32(this.lonMemoizedSerializedSize);
         }

         for (int i = 0; i < this.lon_.size(); i++) {
            output.writeSInt64NoTag(this.lon_.get(i));
         }

         if (this.getKeysValsList().size() > 0) {
            output.writeRawVarint32(82);
            output.writeRawVarint32(this.keysValsMemoizedSerializedSize);
         }

         for (int i = 0; i < this.keysVals_.size(); i++) {
            output.writeInt32NoTag(this.keysVals_.get(i));
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            int var4 = 0;
            int dataSize = 0;

            for (int i = 0; i < this.id_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.id_.get(i));
            }

            var4 += dataSize;
            if (!this.getIdList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.idMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 1) == 1) {
               var4 += CodedOutputStream.computeMessageSize(5, this.denseinfo_);
            }

            dataSize = 0;

            for (int i = 0; i < this.lat_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.lat_.get(i));
            }

            var4 += dataSize;
            if (!this.getLatList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.latMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.lon_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.lon_.get(i));
            }

            var4 += dataSize;
            if (!this.getLonList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.lonMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.keysVals_.size(); i++) {
               dataSize += CodedOutputStream.computeInt32SizeNoTag(this.keysVals_.get(i));
            }

            var4 += dataSize;
            if (!this.getKeysValsList().isEmpty()) {
               var4 = ++var4 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.keysValsMemoizedSerializedSize = dataSize;
            var4 += this.unknownFields.size();
            this.memoizedSerializedSize = var4;
            return var4;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.DenseNodes parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(data);
      }

      public static Osmformat.DenseNodes parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.DenseNodes parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(data);
      }

      public static Osmformat.DenseNodes parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.DenseNodes parseFrom(InputStream input) throws IOException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(input);
      }

      public static Osmformat.DenseNodes parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.DenseNodes parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.DenseNodes)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.DenseNodes parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.DenseNodes)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.DenseNodes parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(input);
      }

      public static Osmformat.DenseNodes parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.DenseNodes)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.DenseNodes.Builder newBuilder() {
         return Osmformat.DenseNodes.Builder.create();
      }

      public Osmformat.DenseNodes.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.DenseNodes.Builder newBuilder(Osmformat.DenseNodes prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.DenseNodes.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.DenseNodes, Osmformat.DenseNodes.Builder>
         implements Osmformat.DenseNodesOrBuilder {
         private int bitField0_;
         private List<Long> id_ = Collections.emptyList();
         private Osmformat.DenseInfo denseinfo_ = Osmformat.DenseInfo.getDefaultInstance();
         private List<Long> lat_ = Collections.emptyList();
         private List<Long> lon_ = Collections.emptyList();
         private List<Integer> keysVals_ = Collections.emptyList();

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.DenseNodes.Builder create() {
            return new Osmformat.DenseNodes.Builder();
         }

         public Osmformat.DenseNodes.Builder clear() {
            super.clear();
            this.id_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.denseinfo_ = Osmformat.DenseInfo.getDefaultInstance();
            this.bitField0_ &= -3;
            this.lat_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.lon_ = Collections.emptyList();
            this.bitField0_ &= -9;
            this.keysVals_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }

         public Osmformat.DenseNodes.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.DenseNodes getDefaultInstanceForType() {
            return Osmformat.DenseNodes.getDefaultInstance();
         }

         public Osmformat.DenseNodes build() {
            Osmformat.DenseNodes result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.DenseNodes buildPartial() {
            Osmformat.DenseNodes result = new Osmformat.DenseNodes(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((this.bitField0_ & 1) == 1) {
               this.id_ = Collections.unmodifiableList(this.id_);
               this.bitField0_ &= -2;
            }

            result.id_ = this.id_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 1;
            }

            result.denseinfo_ = this.denseinfo_;
            if ((this.bitField0_ & 4) == 4) {
               this.lat_ = Collections.unmodifiableList(this.lat_);
               this.bitField0_ &= -5;
            }

            result.lat_ = this.lat_;
            if ((this.bitField0_ & 8) == 8) {
               this.lon_ = Collections.unmodifiableList(this.lon_);
               this.bitField0_ &= -9;
            }

            result.lon_ = this.lon_;
            if ((this.bitField0_ & 16) == 16) {
               this.keysVals_ = Collections.unmodifiableList(this.keysVals_);
               this.bitField0_ &= -17;
            }

            result.keysVals_ = this.keysVals_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.DenseNodes.Builder mergeFrom(Osmformat.DenseNodes other) {
            if (other == Osmformat.DenseNodes.getDefaultInstance()) {
               return this;
            } else {
               if (!other.id_.isEmpty()) {
                  if (this.id_.isEmpty()) {
                     this.id_ = other.id_;
                     this.bitField0_ &= -2;
                  } else {
                     this.ensureIdIsMutable();
                     this.id_.addAll(other.id_);
                  }
               }

               if (other.hasDenseinfo()) {
                  this.mergeDenseinfo(other.getDenseinfo());
               }

               if (!other.lat_.isEmpty()) {
                  if (this.lat_.isEmpty()) {
                     this.lat_ = other.lat_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureLatIsMutable();
                     this.lat_.addAll(other.lat_);
                  }
               }

               if (!other.lon_.isEmpty()) {
                  if (this.lon_.isEmpty()) {
                     this.lon_ = other.lon_;
                     this.bitField0_ &= -9;
                  } else {
                     this.ensureLonIsMutable();
                     this.lon_.addAll(other.lon_);
                  }
               }

               if (!other.keysVals_.isEmpty()) {
                  if (this.keysVals_.isEmpty()) {
                     this.keysVals_ = other.keysVals_;
                     this.bitField0_ &= -17;
                  } else {
                     this.ensureKeysValsIsMutable();
                     this.keysVals_.addAll(other.keysVals_);
                  }
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return true;
         }

         public Osmformat.DenseNodes.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.DenseNodes parsedMessage = null;

            try {
               parsedMessage = (Osmformat.DenseNodes)Osmformat.DenseNodes.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.DenseNodes)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         private void ensureIdIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
               this.id_ = new ArrayList<>(this.id_);
               this.bitField0_ |= 1;
            }
         }

         @Override
         public List<Long> getIdList() {
            return Collections.unmodifiableList(this.id_);
         }

         @Override
         public int getIdCount() {
            return this.id_.size();
         }

         @Override
         public long getId(int index) {
            return this.id_.get(index);
         }

         public Osmformat.DenseNodes.Builder setId(int index, long value) {
            this.ensureIdIsMutable();
            this.id_.set(index, value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addId(long value) {
            this.ensureIdIsMutable();
            this.id_.add(value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addAllId(Iterable<? extends Long> values) {
            this.ensureIdIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.id_);
            return this;
         }

         public Osmformat.DenseNodes.Builder clearId() {
            this.id_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
         }

         @Override
         public boolean hasDenseinfo() {
            return (this.bitField0_ & 2) == 2;
         }

         @Override
         public Osmformat.DenseInfo getDenseinfo() {
            return this.denseinfo_;
         }

         public Osmformat.DenseNodes.Builder setDenseinfo(Osmformat.DenseInfo value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.denseinfo_ = value;
               this.bitField0_ |= 2;
               return this;
            }
         }

         public Osmformat.DenseNodes.Builder setDenseinfo(Osmformat.DenseInfo.Builder builderForValue) {
            this.denseinfo_ = builderForValue.build();
            this.bitField0_ |= 2;
            return this;
         }

         public Osmformat.DenseNodes.Builder mergeDenseinfo(Osmformat.DenseInfo value) {
            if ((this.bitField0_ & 2) == 2 && this.denseinfo_ != Osmformat.DenseInfo.getDefaultInstance()) {
               this.denseinfo_ = Osmformat.DenseInfo.newBuilder(this.denseinfo_).mergeFrom(value).buildPartial();
            } else {
               this.denseinfo_ = value;
            }

            this.bitField0_ |= 2;
            return this;
         }

         public Osmformat.DenseNodes.Builder clearDenseinfo() {
            this.denseinfo_ = Osmformat.DenseInfo.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
         }

         private void ensureLatIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.lat_ = new ArrayList<>(this.lat_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public List<Long> getLatList() {
            return Collections.unmodifiableList(this.lat_);
         }

         @Override
         public int getLatCount() {
            return this.lat_.size();
         }

         @Override
         public long getLat(int index) {
            return this.lat_.get(index);
         }

         public Osmformat.DenseNodes.Builder setLat(int index, long value) {
            this.ensureLatIsMutable();
            this.lat_.set(index, value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addLat(long value) {
            this.ensureLatIsMutable();
            this.lat_.add(value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addAllLat(Iterable<? extends Long> values) {
            this.ensureLatIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.lat_);
            return this;
         }

         public Osmformat.DenseNodes.Builder clearLat() {
            this.lat_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
         }

         private void ensureLonIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
               this.lon_ = new ArrayList<>(this.lon_);
               this.bitField0_ |= 8;
            }
         }

         @Override
         public List<Long> getLonList() {
            return Collections.unmodifiableList(this.lon_);
         }

         @Override
         public int getLonCount() {
            return this.lon_.size();
         }

         @Override
         public long getLon(int index) {
            return this.lon_.get(index);
         }

         public Osmformat.DenseNodes.Builder setLon(int index, long value) {
            this.ensureLonIsMutable();
            this.lon_.set(index, value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addLon(long value) {
            this.ensureLonIsMutable();
            this.lon_.add(value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addAllLon(Iterable<? extends Long> values) {
            this.ensureLonIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.lon_);
            return this;
         }

         public Osmformat.DenseNodes.Builder clearLon() {
            this.lon_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
         }

         private void ensureKeysValsIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.keysVals_ = new ArrayList<>(this.keysVals_);
               this.bitField0_ |= 16;
            }
         }

         @Override
         public List<Integer> getKeysValsList() {
            return Collections.unmodifiableList(this.keysVals_);
         }

         @Override
         public int getKeysValsCount() {
            return this.keysVals_.size();
         }

         @Override
         public int getKeysVals(int index) {
            return this.keysVals_.get(index);
         }

         public Osmformat.DenseNodes.Builder setKeysVals(int index, int value) {
            this.ensureKeysValsIsMutable();
            this.keysVals_.set(index, value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addKeysVals(int value) {
            this.ensureKeysValsIsMutable();
            this.keysVals_.add(value);
            return this;
         }

         public Osmformat.DenseNodes.Builder addAllKeysVals(Iterable<? extends Integer> values) {
            this.ensureKeysValsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.keysVals_);
            return this;
         }

         public Osmformat.DenseNodes.Builder clearKeysVals() {
            this.keysVals_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }
      }
   }

   public interface DenseNodesOrBuilder extends MessageLiteOrBuilder {
      List<Long> getIdList();

      int getIdCount();

      long getId(int var1);

      boolean hasDenseinfo();

      Osmformat.DenseInfo getDenseinfo();

      List<Long> getLatList();

      int getLatCount();

      long getLat(int var1);

      List<Long> getLonList();

      int getLonCount();

      long getLon(int var1);

      List<Integer> getKeysValsList();

      int getKeysValsCount();

      int getKeysVals(int var1);
   }

   public static final class HeaderBBox extends GeneratedMessageLite implements Osmformat.HeaderBBoxOrBuilder {
      private static final Osmformat.HeaderBBox defaultInstance = new Osmformat.HeaderBBox(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.HeaderBBox> PARSER = new AbstractParser<Osmformat.HeaderBBox>() {
         public Osmformat.HeaderBBox parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.HeaderBBox(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int LEFT_FIELD_NUMBER = 1;
      private long left_;
      public static final int RIGHT_FIELD_NUMBER = 2;
      private long right_;
      public static final int TOP_FIELD_NUMBER = 3;
      private long top_;
      public static final int BOTTOM_FIELD_NUMBER = 4;
      private long bottom_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private HeaderBBox(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private HeaderBBox(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.HeaderBBox getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.HeaderBBox getDefaultInstanceForType() {
         return defaultInstance;
      }

      private HeaderBBox(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 8:
                     this.bitField0_ |= 1;
                     this.left_ = input.readSInt64();
                     break;
                  case 16:
                     this.bitField0_ |= 2;
                     this.right_ = input.readSInt64();
                     break;
                  case 24:
                     this.bitField0_ |= 4;
                     this.top_ = input.readSInt64();
                     break;
                  case 32:
                     this.bitField0_ |= 8;
                     this.bottom_ = input.readSInt64();
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var35) {
            throw var35.setUnfinishedMessage(this);
         } catch (IOException var36) {
            throw new InvalidProtocolBufferException(var36.getMessage()).setUnfinishedMessage(this);
         } finally {
            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var33) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.HeaderBBox> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasLeft() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public long getLeft() {
         return this.left_;
      }

      @Override
      public boolean hasRight() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public long getRight() {
         return this.right_;
      }

      @Override
      public boolean hasTop() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public long getTop() {
         return this.top_;
      }

      @Override
      public boolean hasBottom() {
         return (this.bitField0_ & 8) == 8;
      }

      @Override
      public long getBottom() {
         return this.bottom_;
      }

      private void initFields() {
         this.left_ = 0L;
         this.right_ = 0L;
         this.top_ = 0L;
         this.bottom_ = 0L;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasLeft()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasRight()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasTop()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasBottom()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeSInt64(1, this.left_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeSInt64(2, this.right_);
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeSInt64(3, this.top_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeSInt64(4, this.bottom_);
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeSInt64Size(1, this.left_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeSInt64Size(2, this.right_);
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeSInt64Size(3, this.top_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeSInt64Size(4, this.bottom_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.HeaderBBox parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(data);
      }

      public static Osmformat.HeaderBBox parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.HeaderBBox parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(data);
      }

      public static Osmformat.HeaderBBox parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.HeaderBBox parseFrom(InputStream input) throws IOException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(input);
      }

      public static Osmformat.HeaderBBox parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.HeaderBBox parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.HeaderBBox)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.HeaderBBox parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.HeaderBBox)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.HeaderBBox parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(input);
      }

      public static Osmformat.HeaderBBox parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.HeaderBBox)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.HeaderBBox.Builder newBuilder() {
         return Osmformat.HeaderBBox.Builder.create();
      }

      public Osmformat.HeaderBBox.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.HeaderBBox.Builder newBuilder(Osmformat.HeaderBBox prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.HeaderBBox.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.HeaderBBox, Osmformat.HeaderBBox.Builder>
         implements Osmformat.HeaderBBoxOrBuilder {
         private int bitField0_;
         private long left_;
         private long right_;
         private long top_;
         private long bottom_;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.HeaderBBox.Builder create() {
            return new Osmformat.HeaderBBox.Builder();
         }

         public Osmformat.HeaderBBox.Builder clear() {
            super.clear();
            this.left_ = 0L;
            this.bitField0_ &= -2;
            this.right_ = 0L;
            this.bitField0_ &= -3;
            this.top_ = 0L;
            this.bitField0_ &= -5;
            this.bottom_ = 0L;
            this.bitField0_ &= -9;
            return this;
         }

         public Osmformat.HeaderBBox.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.HeaderBBox getDefaultInstanceForType() {
            return Osmformat.HeaderBBox.getDefaultInstance();
         }

         public Osmformat.HeaderBBox build() {
            Osmformat.HeaderBBox result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.HeaderBBox buildPartial() {
            Osmformat.HeaderBBox result = new Osmformat.HeaderBBox(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.left_ = this.left_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.right_ = this.right_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            result.top_ = this.top_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 8;
            }

            result.bottom_ = this.bottom_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.HeaderBBox.Builder mergeFrom(Osmformat.HeaderBBox other) {
            if (other == Osmformat.HeaderBBox.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasLeft()) {
                  this.setLeft(other.getLeft());
               }

               if (other.hasRight()) {
                  this.setRight(other.getRight());
               }

               if (other.hasTop()) {
                  this.setTop(other.getTop());
               }

               if (other.hasBottom()) {
                  this.setBottom(other.getBottom());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasLeft()) {
               return false;
            } else if (!this.hasRight()) {
               return false;
            } else {
               return !this.hasTop() ? false : this.hasBottom();
            }
         }

         public Osmformat.HeaderBBox.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.HeaderBBox parsedMessage = null;

            try {
               parsedMessage = (Osmformat.HeaderBBox)Osmformat.HeaderBBox.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.HeaderBBox)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasLeft() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public long getLeft() {
            return this.left_;
         }

         public Osmformat.HeaderBBox.Builder setLeft(long value) {
            this.bitField0_ |= 1;
            this.left_ = value;
            return this;
         }

         public Osmformat.HeaderBBox.Builder clearLeft() {
            this.bitField0_ &= -2;
            this.left_ = 0L;
            return this;
         }

         @Override
         public boolean hasRight() {
            return (this.bitField0_ & 2) == 2;
         }

         @Override
         public long getRight() {
            return this.right_;
         }

         public Osmformat.HeaderBBox.Builder setRight(long value) {
            this.bitField0_ |= 2;
            this.right_ = value;
            return this;
         }

         public Osmformat.HeaderBBox.Builder clearRight() {
            this.bitField0_ &= -3;
            this.right_ = 0L;
            return this;
         }

         @Override
         public boolean hasTop() {
            return (this.bitField0_ & 4) == 4;
         }

         @Override
         public long getTop() {
            return this.top_;
         }

         public Osmformat.HeaderBBox.Builder setTop(long value) {
            this.bitField0_ |= 4;
            this.top_ = value;
            return this;
         }

         public Osmformat.HeaderBBox.Builder clearTop() {
            this.bitField0_ &= -5;
            this.top_ = 0L;
            return this;
         }

         @Override
         public boolean hasBottom() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public long getBottom() {
            return this.bottom_;
         }

         public Osmformat.HeaderBBox.Builder setBottom(long value) {
            this.bitField0_ |= 8;
            this.bottom_ = value;
            return this;
         }

         public Osmformat.HeaderBBox.Builder clearBottom() {
            this.bitField0_ &= -9;
            this.bottom_ = 0L;
            return this;
         }
      }
   }

   public interface HeaderBBoxOrBuilder extends MessageLiteOrBuilder {
      boolean hasLeft();

      long getLeft();

      boolean hasRight();

      long getRight();

      boolean hasTop();

      long getTop();

      boolean hasBottom();

      long getBottom();
   }

   public static final class HeaderBlock extends GeneratedMessageLite implements Osmformat.HeaderBlockOrBuilder {
      private static final Osmformat.HeaderBlock defaultInstance = new Osmformat.HeaderBlock(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.HeaderBlock> PARSER = new AbstractParser<Osmformat.HeaderBlock>() {
         public Osmformat.HeaderBlock parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.HeaderBlock(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int BBOX_FIELD_NUMBER = 1;
      private Osmformat.HeaderBBox bbox_;
      public static final int REQUIRED_FEATURES_FIELD_NUMBER = 4;
      private LazyStringList requiredFeatures_;
      public static final int OPTIONAL_FEATURES_FIELD_NUMBER = 5;
      private LazyStringList optionalFeatures_;
      public static final int WRITINGPROGRAM_FIELD_NUMBER = 16;
      private Object writingprogram_;
      public static final int SOURCE_FIELD_NUMBER = 17;
      private Object source_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private HeaderBlock(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private HeaderBlock(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.HeaderBlock getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.HeaderBlock getDefaultInstanceForType() {
         return defaultInstance;
      }

      private HeaderBlock(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 10:
                     Osmformat.HeaderBBox.Builder subBuilder = null;
                     if ((this.bitField0_ & 1) == 1) {
                        subBuilder = this.bbox_.toBuilder();
                     }

                     this.bbox_ = (Osmformat.HeaderBBox)input.readMessage(Osmformat.HeaderBBox.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.bbox_);
                        this.bbox_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 1;
                     break;
                  case 34: {
                     ByteString bs = input.readBytes();
                     if ((mutable_bitField0_ & 2) != 2) {
                        this.requiredFeatures_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 2;
                     }

                     this.requiredFeatures_.add(bs);
                     break;
                  }
                  case 42: {
                     ByteString bs = input.readBytes();
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.optionalFeatures_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 4;
                     }

                     this.optionalFeatures_.add(bs);
                     break;
                  }
                  case 130: {
                     ByteString bs = input.readBytes();
                     this.bitField0_ |= 2;
                     this.writingprogram_ = bs;
                     break;
                  }
                  case 138: {
                     ByteString bs = input.readBytes();
                     this.bitField0_ |= 4;
                     this.source_ = bs;
                     break;
                  }
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var36) {
            throw var36.setUnfinishedMessage(this);
         } catch (IOException var37) {
            throw new InvalidProtocolBufferException(var37.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 2) == 2) {
               this.requiredFeatures_ = this.requiredFeatures_.getUnmodifiableView();
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.optionalFeatures_ = this.optionalFeatures_.getUnmodifiableView();
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var34) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.HeaderBlock> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasBbox() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public Osmformat.HeaderBBox getBbox() {
         return this.bbox_;
      }

      @Override
      public ProtocolStringList getRequiredFeaturesList() {
         return this.requiredFeatures_;
      }

      @Override
      public int getRequiredFeaturesCount() {
         return this.requiredFeatures_.size();
      }

      @Override
      public String getRequiredFeatures(int index) {
         return (String)this.requiredFeatures_.get(index);
      }

      @Override
      public ByteString getRequiredFeaturesBytes(int index) {
         return this.requiredFeatures_.getByteString(index);
      }

      @Override
      public ProtocolStringList getOptionalFeaturesList() {
         return this.optionalFeatures_;
      }

      @Override
      public int getOptionalFeaturesCount() {
         return this.optionalFeatures_.size();
      }

      @Override
      public String getOptionalFeatures(int index) {
         return (String)this.optionalFeatures_.get(index);
      }

      @Override
      public ByteString getOptionalFeaturesBytes(int index) {
         return this.optionalFeatures_.getByteString(index);
      }

      @Override
      public boolean hasWritingprogram() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public String getWritingprogram() {
         Object ref = this.writingprogram_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.writingprogram_ = s;
            }

            return s;
         }
      }

      @Override
      public ByteString getWritingprogramBytes() {
         Object ref = this.writingprogram_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.writingprogram_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      @Override
      public boolean hasSource() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public String getSource() {
         Object ref = this.source_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.source_ = s;
            }

            return s;
         }
      }

      @Override
      public ByteString getSourceBytes() {
         Object ref = this.source_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.source_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      private void initFields() {
         this.bbox_ = Osmformat.HeaderBBox.getDefaultInstance();
         this.requiredFeatures_ = LazyStringArrayList.EMPTY;
         this.optionalFeatures_ = LazyStringArrayList.EMPTY;
         this.writingprogram_ = "";
         this.source_ = "";
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (this.hasBbox() && !this.getBbox().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.bbox_);
         }

         for (int i = 0; i < this.requiredFeatures_.size(); i++) {
            output.writeBytes(4, this.requiredFeatures_.getByteString(i));
         }

         for (int i = 0; i < this.optionalFeatures_.size(); i++) {
            output.writeBytes(5, this.optionalFeatures_.getByteString(i));
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeBytes(16, this.getWritingprogramBytes());
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeBytes(17, this.getSourceBytes());
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.bbox_);
            }

            int dataSize = 0;

            for (int i = 0; i < this.requiredFeatures_.size(); i++) {
               dataSize += CodedOutputStream.computeBytesSizeNoTag(this.requiredFeatures_.getByteString(i));
            }

            size += dataSize;
            size += 1 * this.getRequiredFeaturesList().size();
            dataSize = 0;

            for (int i = 0; i < this.optionalFeatures_.size(); i++) {
               dataSize += CodedOutputStream.computeBytesSizeNoTag(this.optionalFeatures_.getByteString(i));
            }

            size += dataSize;
            size += 1 * this.getOptionalFeaturesList().size();
            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeBytesSize(16, this.getWritingprogramBytes());
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeBytesSize(17, this.getSourceBytes());
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.HeaderBlock parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(data);
      }

      public static Osmformat.HeaderBlock parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.HeaderBlock parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(data);
      }

      public static Osmformat.HeaderBlock parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.HeaderBlock parseFrom(InputStream input) throws IOException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(input);
      }

      public static Osmformat.HeaderBlock parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.HeaderBlock parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.HeaderBlock)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.HeaderBlock parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.HeaderBlock)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.HeaderBlock parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(input);
      }

      public static Osmformat.HeaderBlock parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.HeaderBlock)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.HeaderBlock.Builder newBuilder() {
         return Osmformat.HeaderBlock.Builder.create();
      }

      public Osmformat.HeaderBlock.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.HeaderBlock.Builder newBuilder(Osmformat.HeaderBlock prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.HeaderBlock.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.HeaderBlock, Osmformat.HeaderBlock.Builder>
         implements Osmformat.HeaderBlockOrBuilder {
         private int bitField0_;
         private Osmformat.HeaderBBox bbox_ = Osmformat.HeaderBBox.getDefaultInstance();
         private LazyStringList requiredFeatures_ = LazyStringArrayList.EMPTY;
         private LazyStringList optionalFeatures_ = LazyStringArrayList.EMPTY;
         private Object writingprogram_ = "";
         private Object source_ = "";

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.HeaderBlock.Builder create() {
            return new Osmformat.HeaderBlock.Builder();
         }

         public Osmformat.HeaderBlock.Builder clear() {
            super.clear();
            this.bbox_ = Osmformat.HeaderBBox.getDefaultInstance();
            this.bitField0_ &= -2;
            this.requiredFeatures_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= -3;
            this.optionalFeatures_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= -5;
            this.writingprogram_ = "";
            this.bitField0_ &= -9;
            this.source_ = "";
            this.bitField0_ &= -17;
            return this;
         }

         public Osmformat.HeaderBlock.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.HeaderBlock getDefaultInstanceForType() {
            return Osmformat.HeaderBlock.getDefaultInstance();
         }

         public Osmformat.HeaderBlock build() {
            Osmformat.HeaderBlock result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.HeaderBlock buildPartial() {
            Osmformat.HeaderBlock result = new Osmformat.HeaderBlock(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.bbox_ = this.bbox_;
            if ((this.bitField0_ & 2) == 2) {
               this.requiredFeatures_ = this.requiredFeatures_.getUnmodifiableView();
               this.bitField0_ &= -3;
            }

            result.requiredFeatures_ = this.requiredFeatures_;
            if ((this.bitField0_ & 4) == 4) {
               this.optionalFeatures_ = this.optionalFeatures_.getUnmodifiableView();
               this.bitField0_ &= -5;
            }

            result.optionalFeatures_ = this.optionalFeatures_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 2;
            }

            result.writingprogram_ = this.writingprogram_;
            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 4;
            }

            result.source_ = this.source_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.HeaderBlock.Builder mergeFrom(Osmformat.HeaderBlock other) {
            if (other == Osmformat.HeaderBlock.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasBbox()) {
                  this.mergeBbox(other.getBbox());
               }

               if (!other.requiredFeatures_.isEmpty()) {
                  if (this.requiredFeatures_.isEmpty()) {
                     this.requiredFeatures_ = other.requiredFeatures_;
                     this.bitField0_ &= -3;
                  } else {
                     this.ensureRequiredFeaturesIsMutable();
                     this.requiredFeatures_.addAll(other.requiredFeatures_);
                  }
               }

               if (!other.optionalFeatures_.isEmpty()) {
                  if (this.optionalFeatures_.isEmpty()) {
                     this.optionalFeatures_ = other.optionalFeatures_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureOptionalFeaturesIsMutable();
                     this.optionalFeatures_.addAll(other.optionalFeatures_);
                  }
               }

               if (other.hasWritingprogram()) {
                  this.bitField0_ |= 8;
                  this.writingprogram_ = other.writingprogram_;
               }

               if (other.hasSource()) {
                  this.bitField0_ |= 16;
                  this.source_ = other.source_;
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return !this.hasBbox() || this.getBbox().isInitialized();
         }

         public Osmformat.HeaderBlock.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.HeaderBlock parsedMessage = null;

            try {
               parsedMessage = (Osmformat.HeaderBlock)Osmformat.HeaderBlock.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.HeaderBlock)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasBbox() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public Osmformat.HeaderBBox getBbox() {
            return this.bbox_;
         }

         public Osmformat.HeaderBlock.Builder setBbox(Osmformat.HeaderBBox value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bbox_ = value;
               this.bitField0_ |= 1;
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder setBbox(Osmformat.HeaderBBox.Builder builderForValue) {
            this.bbox_ = builderForValue.build();
            this.bitField0_ |= 1;
            return this;
         }

         public Osmformat.HeaderBlock.Builder mergeBbox(Osmformat.HeaderBBox value) {
            if ((this.bitField0_ & 1) == 1 && this.bbox_ != Osmformat.HeaderBBox.getDefaultInstance()) {
               this.bbox_ = Osmformat.HeaderBBox.newBuilder(this.bbox_).mergeFrom(value).buildPartial();
            } else {
               this.bbox_ = value;
            }

            this.bitField0_ |= 1;
            return this;
         }

         public Osmformat.HeaderBlock.Builder clearBbox() {
            this.bbox_ = Osmformat.HeaderBBox.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
         }

         private void ensureRequiredFeaturesIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
               this.requiredFeatures_ = new LazyStringArrayList(this.requiredFeatures_);
               this.bitField0_ |= 2;
            }
         }

         @Override
         public ProtocolStringList getRequiredFeaturesList() {
            return this.requiredFeatures_.getUnmodifiableView();
         }

         @Override
         public int getRequiredFeaturesCount() {
            return this.requiredFeatures_.size();
         }

         @Override
         public String getRequiredFeatures(int index) {
            return (String)this.requiredFeatures_.get(index);
         }

         @Override
         public ByteString getRequiredFeaturesBytes(int index) {
            return this.requiredFeatures_.getByteString(index);
         }

         public Osmformat.HeaderBlock.Builder setRequiredFeatures(int index, String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureRequiredFeaturesIsMutable();
               this.requiredFeatures_.set(index, value);
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder addRequiredFeatures(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureRequiredFeaturesIsMutable();
               this.requiredFeatures_.add(value);
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder addAllRequiredFeatures(Iterable<String> values) {
            this.ensureRequiredFeaturesIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.requiredFeatures_);
            return this;
         }

         public Osmformat.HeaderBlock.Builder clearRequiredFeatures() {
            this.requiredFeatures_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= -3;
            return this;
         }

         public Osmformat.HeaderBlock.Builder addRequiredFeaturesBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureRequiredFeaturesIsMutable();
               this.requiredFeatures_.add(value);
               return this;
            }
         }

         private void ensureOptionalFeaturesIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.optionalFeatures_ = new LazyStringArrayList(this.optionalFeatures_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public ProtocolStringList getOptionalFeaturesList() {
            return this.optionalFeatures_.getUnmodifiableView();
         }

         @Override
         public int getOptionalFeaturesCount() {
            return this.optionalFeatures_.size();
         }

         @Override
         public String getOptionalFeatures(int index) {
            return (String)this.optionalFeatures_.get(index);
         }

         @Override
         public ByteString getOptionalFeaturesBytes(int index) {
            return this.optionalFeatures_.getByteString(index);
         }

         public Osmformat.HeaderBlock.Builder setOptionalFeatures(int index, String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureOptionalFeaturesIsMutable();
               this.optionalFeatures_.set(index, value);
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder addOptionalFeatures(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureOptionalFeaturesIsMutable();
               this.optionalFeatures_.add(value);
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder addAllOptionalFeatures(Iterable<String> values) {
            this.ensureOptionalFeaturesIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.optionalFeatures_);
            return this;
         }

         public Osmformat.HeaderBlock.Builder clearOptionalFeatures() {
            this.optionalFeatures_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= -5;
            return this;
         }

         public Osmformat.HeaderBlock.Builder addOptionalFeaturesBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureOptionalFeaturesIsMutable();
               this.optionalFeatures_.add(value);
               return this;
            }
         }

         @Override
         public boolean hasWritingprogram() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public String getWritingprogram() {
            Object ref = this.writingprogram_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.writingprogram_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         @Override
         public ByteString getWritingprogramBytes() {
            Object ref = this.writingprogram_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.writingprogram_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public Osmformat.HeaderBlock.Builder setWritingprogram(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 8;
               this.writingprogram_ = value;
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder clearWritingprogram() {
            this.bitField0_ &= -9;
            this.writingprogram_ = Osmformat.HeaderBlock.getDefaultInstance().getWritingprogram();
            return this;
         }

         public Osmformat.HeaderBlock.Builder setWritingprogramBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 8;
               this.writingprogram_ = value;
               return this;
            }
         }

         @Override
         public boolean hasSource() {
            return (this.bitField0_ & 16) == 16;
         }

         @Override
         public String getSource() {
            Object ref = this.source_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.source_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         @Override
         public ByteString getSourceBytes() {
            Object ref = this.source_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.source_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public Osmformat.HeaderBlock.Builder setSource(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 16;
               this.source_ = value;
               return this;
            }
         }

         public Osmformat.HeaderBlock.Builder clearSource() {
            this.bitField0_ &= -17;
            this.source_ = Osmformat.HeaderBlock.getDefaultInstance().getSource();
            return this;
         }

         public Osmformat.HeaderBlock.Builder setSourceBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 16;
               this.source_ = value;
               return this;
            }
         }
      }
   }

   public interface HeaderBlockOrBuilder extends MessageLiteOrBuilder {
      boolean hasBbox();

      Osmformat.HeaderBBox getBbox();

      ProtocolStringList getRequiredFeaturesList();

      int getRequiredFeaturesCount();

      String getRequiredFeatures(int var1);

      ByteString getRequiredFeaturesBytes(int var1);

      ProtocolStringList getOptionalFeaturesList();

      int getOptionalFeaturesCount();

      String getOptionalFeatures(int var1);

      ByteString getOptionalFeaturesBytes(int var1);

      boolean hasWritingprogram();

      String getWritingprogram();

      ByteString getWritingprogramBytes();

      boolean hasSource();

      String getSource();

      ByteString getSourceBytes();
   }

   public static final class Info extends GeneratedMessageLite implements Osmformat.InfoOrBuilder {
      private static final Osmformat.Info defaultInstance = new Osmformat.Info(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.Info> PARSER = new AbstractParser<Osmformat.Info>() {
         public Osmformat.Info parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.Info(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int VERSION_FIELD_NUMBER = 1;
      private int version_;
      public static final int TIMESTAMP_FIELD_NUMBER = 2;
      private long timestamp_;
      public static final int CHANGESET_FIELD_NUMBER = 3;
      private long changeset_;
      public static final int UID_FIELD_NUMBER = 4;
      private int uid_;
      public static final int USER_SID_FIELD_NUMBER = 5;
      private int userSid_;
      public static final int VISIBLE_FIELD_NUMBER = 6;
      private boolean visible_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private Info(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private Info(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.Info getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.Info getDefaultInstanceForType() {
         return defaultInstance;
      }

      private Info(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 8:
                     this.bitField0_ |= 1;
                     this.version_ = input.readInt32();
                     break;
                  case 16:
                     this.bitField0_ |= 2;
                     this.timestamp_ = input.readInt64();
                     break;
                  case 24:
                     this.bitField0_ |= 4;
                     this.changeset_ = input.readInt64();
                     break;
                  case 32:
                     this.bitField0_ |= 8;
                     this.uid_ = input.readInt32();
                     break;
                  case 40:
                     this.bitField0_ |= 16;
                     this.userSid_ = input.readUInt32();
                     break;
                  case 48:
                     this.bitField0_ |= 32;
                     this.visible_ = input.readBool();
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var35) {
            throw var35.setUnfinishedMessage(this);
         } catch (IOException var36) {
            throw new InvalidProtocolBufferException(var36.getMessage()).setUnfinishedMessage(this);
         } finally {
            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var33) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.Info> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasVersion() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public int getVersion() {
         return this.version_;
      }

      @Override
      public boolean hasTimestamp() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public long getTimestamp() {
         return this.timestamp_;
      }

      @Override
      public boolean hasChangeset() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public long getChangeset() {
         return this.changeset_;
      }

      @Override
      public boolean hasUid() {
         return (this.bitField0_ & 8) == 8;
      }

      @Override
      public int getUid() {
         return this.uid_;
      }

      @Override
      public boolean hasUserSid() {
         return (this.bitField0_ & 16) == 16;
      }

      @Override
      public int getUserSid() {
         return this.userSid_;
      }

      @Override
      public boolean hasVisible() {
         return (this.bitField0_ & 32) == 32;
      }

      @Override
      public boolean getVisible() {
         return this.visible_;
      }

      private void initFields() {
         this.version_ = -1;
         this.timestamp_ = 0L;
         this.changeset_ = 0L;
         this.uid_ = 0;
         this.userSid_ = 0;
         this.visible_ = false;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeInt32(1, this.version_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeInt64(2, this.timestamp_);
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeInt64(3, this.changeset_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeInt32(4, this.uid_);
         }

         if ((this.bitField0_ & 16) == 16) {
            output.writeUInt32(5, this.userSid_);
         }

         if ((this.bitField0_ & 32) == 32) {
            output.writeBool(6, this.visible_);
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeInt32Size(1, this.version_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeInt64Size(2, this.timestamp_);
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeInt64Size(3, this.changeset_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeInt32Size(4, this.uid_);
            }

            if ((this.bitField0_ & 16) == 16) {
               size += CodedOutputStream.computeUInt32Size(5, this.userSid_);
            }

            if ((this.bitField0_ & 32) == 32) {
               size += CodedOutputStream.computeBoolSize(6, this.visible_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.Info parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.Info)PARSER.parseFrom(data);
      }

      public static Osmformat.Info parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Info)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Info parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.Info)PARSER.parseFrom(data);
      }

      public static Osmformat.Info parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Info)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Info parseFrom(InputStream input) throws IOException {
         return (Osmformat.Info)PARSER.parseFrom(input);
      }

      public static Osmformat.Info parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Info)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Info parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.Info)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.Info parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Info)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.Info parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.Info)PARSER.parseFrom(input);
      }

      public static Osmformat.Info parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Info)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Info.Builder newBuilder() {
         return Osmformat.Info.Builder.create();
      }

      public Osmformat.Info.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.Info.Builder newBuilder(Osmformat.Info prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.Info.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.Info, Osmformat.Info.Builder>
         implements Osmformat.InfoOrBuilder {
         private int bitField0_;
         private int version_ = -1;
         private long timestamp_;
         private long changeset_;
         private int uid_;
         private int userSid_;
         private boolean visible_;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.Info.Builder create() {
            return new Osmformat.Info.Builder();
         }

         public Osmformat.Info.Builder clear() {
            super.clear();
            this.version_ = -1;
            this.bitField0_ &= -2;
            this.timestamp_ = 0L;
            this.bitField0_ &= -3;
            this.changeset_ = 0L;
            this.bitField0_ &= -5;
            this.uid_ = 0;
            this.bitField0_ &= -9;
            this.userSid_ = 0;
            this.bitField0_ &= -17;
            this.visible_ = false;
            this.bitField0_ &= -33;
            return this;
         }

         public Osmformat.Info.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.Info getDefaultInstanceForType() {
            return Osmformat.Info.getDefaultInstance();
         }

         public Osmformat.Info build() {
            Osmformat.Info result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.Info buildPartial() {
            Osmformat.Info result = new Osmformat.Info(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.version_ = this.version_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.timestamp_ = this.timestamp_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            result.changeset_ = this.changeset_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 8;
            }

            result.uid_ = this.uid_;
            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 16;
            }

            result.userSid_ = this.userSid_;
            if ((from_bitField0_ & 32) == 32) {
               to_bitField0_ |= 32;
            }

            result.visible_ = this.visible_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.Info.Builder mergeFrom(Osmformat.Info other) {
            if (other == Osmformat.Info.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasVersion()) {
                  this.setVersion(other.getVersion());
               }

               if (other.hasTimestamp()) {
                  this.setTimestamp(other.getTimestamp());
               }

               if (other.hasChangeset()) {
                  this.setChangeset(other.getChangeset());
               }

               if (other.hasUid()) {
                  this.setUid(other.getUid());
               }

               if (other.hasUserSid()) {
                  this.setUserSid(other.getUserSid());
               }

               if (other.hasVisible()) {
                  this.setVisible(other.getVisible());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return true;
         }

         public Osmformat.Info.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.Info parsedMessage = null;

            try {
               parsedMessage = (Osmformat.Info)Osmformat.Info.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.Info)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public int getVersion() {
            return this.version_;
         }

         public Osmformat.Info.Builder setVersion(int value) {
            this.bitField0_ |= 1;
            this.version_ = value;
            return this;
         }

         public Osmformat.Info.Builder clearVersion() {
            this.bitField0_ &= -2;
            this.version_ = -1;
            return this;
         }

         @Override
         public boolean hasTimestamp() {
            return (this.bitField0_ & 2) == 2;
         }

         @Override
         public long getTimestamp() {
            return this.timestamp_;
         }

         public Osmformat.Info.Builder setTimestamp(long value) {
            this.bitField0_ |= 2;
            this.timestamp_ = value;
            return this;
         }

         public Osmformat.Info.Builder clearTimestamp() {
            this.bitField0_ &= -3;
            this.timestamp_ = 0L;
            return this;
         }

         @Override
         public boolean hasChangeset() {
            return (this.bitField0_ & 4) == 4;
         }

         @Override
         public long getChangeset() {
            return this.changeset_;
         }

         public Osmformat.Info.Builder setChangeset(long value) {
            this.bitField0_ |= 4;
            this.changeset_ = value;
            return this;
         }

         public Osmformat.Info.Builder clearChangeset() {
            this.bitField0_ &= -5;
            this.changeset_ = 0L;
            return this;
         }

         @Override
         public boolean hasUid() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public int getUid() {
            return this.uid_;
         }

         public Osmformat.Info.Builder setUid(int value) {
            this.bitField0_ |= 8;
            this.uid_ = value;
            return this;
         }

         public Osmformat.Info.Builder clearUid() {
            this.bitField0_ &= -9;
            this.uid_ = 0;
            return this;
         }

         @Override
         public boolean hasUserSid() {
            return (this.bitField0_ & 16) == 16;
         }

         @Override
         public int getUserSid() {
            return this.userSid_;
         }

         public Osmformat.Info.Builder setUserSid(int value) {
            this.bitField0_ |= 16;
            this.userSid_ = value;
            return this;
         }

         public Osmformat.Info.Builder clearUserSid() {
            this.bitField0_ &= -17;
            this.userSid_ = 0;
            return this;
         }

         @Override
         public boolean hasVisible() {
            return (this.bitField0_ & 32) == 32;
         }

         @Override
         public boolean getVisible() {
            return this.visible_;
         }

         public Osmformat.Info.Builder setVisible(boolean value) {
            this.bitField0_ |= 32;
            this.visible_ = value;
            return this;
         }

         public Osmformat.Info.Builder clearVisible() {
            this.bitField0_ &= -33;
            this.visible_ = false;
            return this;
         }
      }
   }

   public interface InfoOrBuilder extends MessageLiteOrBuilder {
      boolean hasVersion();

      int getVersion();

      boolean hasTimestamp();

      long getTimestamp();

      boolean hasChangeset();

      long getChangeset();

      boolean hasUid();

      int getUid();

      boolean hasUserSid();

      int getUserSid();

      boolean hasVisible();

      boolean getVisible();
   }

   public static final class Node extends GeneratedMessageLite implements Osmformat.NodeOrBuilder {
      private static final Osmformat.Node defaultInstance = new Osmformat.Node(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.Node> PARSER = new AbstractParser<Osmformat.Node>() {
         public Osmformat.Node parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.Node(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int ID_FIELD_NUMBER = 1;
      private long id_;
      public static final int KEYS_FIELD_NUMBER = 2;
      private List<Integer> keys_;
      private int keysMemoizedSerializedSize = -1;
      public static final int VALS_FIELD_NUMBER = 3;
      private List<Integer> vals_;
      private int valsMemoizedSerializedSize = -1;
      public static final int INFO_FIELD_NUMBER = 4;
      private Osmformat.Info info_;
      public static final int LAT_FIELD_NUMBER = 8;
      private long lat_;
      public static final int LON_FIELD_NUMBER = 9;
      private long lon_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private Node(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private Node(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.Node getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.Node getDefaultInstanceForType() {
         return defaultInstance;
      }

      private Node(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               int limit;
               switch (tag) {
                  case 0:
                     done = true;
                     continue;
                  case 8:
                     this.bitField0_ |= 1;
                     this.id_ = input.readSInt64();
                     continue;
                  case 16:
                     if ((mutable_bitField0_ & 2) != 2) {
                        this.keys_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }

                     this.keys_.add(input.readUInt32());
                     continue;
                  case 18:
                     int length = input.readRawVarint32();
                     limit = input.pushLimit(length);
                     if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                        this.keys_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }
                     break;
                  case 24:
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.vals_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     this.vals_.add(input.readUInt32());
                     continue;
                  case 26:
                     int lengthx = input.readRawVarint32();
                     limit = input.pushLimit(lengthx);
                     if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                        this.vals_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.vals_.add(input.readUInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  case 34:
                     Osmformat.Info.Builder subBuilder = null;
                     if ((this.bitField0_ & 2) == 2) {
                        subBuilder = this.info_.toBuilder();
                     }

                     this.info_ = (Osmformat.Info)input.readMessage(Osmformat.Info.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.info_);
                        this.info_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 2;
                     continue;
                  case 64:
                     this.bitField0_ |= 4;
                     this.lat_ = input.readSInt64();
                     continue;
                  case 72:
                     this.bitField0_ |= 8;
                     this.lon_ = input.readSInt64();
                     continue;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
                     continue;
               }

               while (input.getBytesUntilLimit() > 0) {
                  this.keys_.add(input.readUInt32());
               }

               input.popLimit(limit);
            }
         } catch (InvalidProtocolBufferException var37) {
            throw var37.setUnfinishedMessage(this);
         } catch (IOException var38) {
            throw new InvalidProtocolBufferException(var38.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 2) == 2) {
               this.keys_ = Collections.unmodifiableList(this.keys_);
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.vals_ = Collections.unmodifiableList(this.vals_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var35) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.Node> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasId() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public long getId() {
         return this.id_;
      }

      @Override
      public List<Integer> getKeysList() {
         return this.keys_;
      }

      @Override
      public int getKeysCount() {
         return this.keys_.size();
      }

      @Override
      public int getKeys(int index) {
         return this.keys_.get(index);
      }

      @Override
      public List<Integer> getValsList() {
         return this.vals_;
      }

      @Override
      public int getValsCount() {
         return this.vals_.size();
      }

      @Override
      public int getVals(int index) {
         return this.vals_.get(index);
      }

      @Override
      public boolean hasInfo() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public Osmformat.Info getInfo() {
         return this.info_;
      }

      @Override
      public boolean hasLat() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public long getLat() {
         return this.lat_;
      }

      @Override
      public boolean hasLon() {
         return (this.bitField0_ & 8) == 8;
      }

      @Override
      public long getLon() {
         return this.lon_;
      }

      private void initFields() {
         this.id_ = 0L;
         this.keys_ = Collections.emptyList();
         this.vals_ = Collections.emptyList();
         this.info_ = Osmformat.Info.getDefaultInstance();
         this.lat_ = 0L;
         this.lon_ = 0L;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasId()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasLat()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasLon()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeSInt64(1, this.id_);
         }

         if (this.getKeysList().size() > 0) {
            output.writeRawVarint32(18);
            output.writeRawVarint32(this.keysMemoizedSerializedSize);
         }

         for (int i = 0; i < this.keys_.size(); i++) {
            output.writeUInt32NoTag(this.keys_.get(i));
         }

         if (this.getValsList().size() > 0) {
            output.writeRawVarint32(26);
            output.writeRawVarint32(this.valsMemoizedSerializedSize);
         }

         for (int i = 0; i < this.vals_.size(); i++) {
            output.writeUInt32NoTag(this.vals_.get(i));
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeMessage(4, this.info_);
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeSInt64(8, this.lat_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeSInt64(9, this.lon_);
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeSInt64Size(1, this.id_);
            }

            int dataSize = 0;

            for (int i = 0; i < this.keys_.size(); i++) {
               dataSize += CodedOutputStream.computeUInt32SizeNoTag(this.keys_.get(i));
            }

            size += dataSize;
            if (!this.getKeysList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.keysMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.vals_.size(); i++) {
               dataSize += CodedOutputStream.computeUInt32SizeNoTag(this.vals_.get(i));
            }

            size += dataSize;
            if (!this.getValsList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.valsMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeMessageSize(4, this.info_);
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeSInt64Size(8, this.lat_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeSInt64Size(9, this.lon_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.Node parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.Node)PARSER.parseFrom(data);
      }

      public static Osmformat.Node parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Node)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Node parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.Node)PARSER.parseFrom(data);
      }

      public static Osmformat.Node parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Node)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Node parseFrom(InputStream input) throws IOException {
         return (Osmformat.Node)PARSER.parseFrom(input);
      }

      public static Osmformat.Node parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Node)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Node parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.Node)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.Node parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Node)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.Node parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.Node)PARSER.parseFrom(input);
      }

      public static Osmformat.Node parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Node)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Node.Builder newBuilder() {
         return Osmformat.Node.Builder.create();
      }

      public Osmformat.Node.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.Node.Builder newBuilder(Osmformat.Node prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.Node.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.Node, Osmformat.Node.Builder>
         implements Osmformat.NodeOrBuilder {
         private int bitField0_;
         private long id_;
         private List<Integer> keys_ = Collections.emptyList();
         private List<Integer> vals_ = Collections.emptyList();
         private Osmformat.Info info_ = Osmformat.Info.getDefaultInstance();
         private long lat_;
         private long lon_;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.Node.Builder create() {
            return new Osmformat.Node.Builder();
         }

         public Osmformat.Node.Builder clear() {
            super.clear();
            this.id_ = 0L;
            this.bitField0_ &= -2;
            this.keys_ = Collections.emptyList();
            this.bitField0_ &= -3;
            this.vals_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.info_ = Osmformat.Info.getDefaultInstance();
            this.bitField0_ &= -9;
            this.lat_ = 0L;
            this.bitField0_ &= -17;
            this.lon_ = 0L;
            this.bitField0_ &= -33;
            return this;
         }

         public Osmformat.Node.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.Node getDefaultInstanceForType() {
            return Osmformat.Node.getDefaultInstance();
         }

         public Osmformat.Node build() {
            Osmformat.Node result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.Node buildPartial() {
            Osmformat.Node result = new Osmformat.Node(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.id_ = this.id_;
            if ((this.bitField0_ & 2) == 2) {
               this.keys_ = Collections.unmodifiableList(this.keys_);
               this.bitField0_ &= -3;
            }

            result.keys_ = this.keys_;
            if ((this.bitField0_ & 4) == 4) {
               this.vals_ = Collections.unmodifiableList(this.vals_);
               this.bitField0_ &= -5;
            }

            result.vals_ = this.vals_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 2;
            }

            result.info_ = this.info_;
            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 4;
            }

            result.lat_ = this.lat_;
            if ((from_bitField0_ & 32) == 32) {
               to_bitField0_ |= 8;
            }

            result.lon_ = this.lon_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.Node.Builder mergeFrom(Osmformat.Node other) {
            if (other == Osmformat.Node.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasId()) {
                  this.setId(other.getId());
               }

               if (!other.keys_.isEmpty()) {
                  if (this.keys_.isEmpty()) {
                     this.keys_ = other.keys_;
                     this.bitField0_ &= -3;
                  } else {
                     this.ensureKeysIsMutable();
                     this.keys_.addAll(other.keys_);
                  }
               }

               if (!other.vals_.isEmpty()) {
                  if (this.vals_.isEmpty()) {
                     this.vals_ = other.vals_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureValsIsMutable();
                     this.vals_.addAll(other.vals_);
                  }
               }

               if (other.hasInfo()) {
                  this.mergeInfo(other.getInfo());
               }

               if (other.hasLat()) {
                  this.setLat(other.getLat());
               }

               if (other.hasLon()) {
                  this.setLon(other.getLon());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasId()) {
               return false;
            } else {
               return !this.hasLat() ? false : this.hasLon();
            }
         }

         public Osmformat.Node.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.Node parsedMessage = null;

            try {
               parsedMessage = (Osmformat.Node)Osmformat.Node.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.Node)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public long getId() {
            return this.id_;
         }

         public Osmformat.Node.Builder setId(long value) {
            this.bitField0_ |= 1;
            this.id_ = value;
            return this;
         }

         public Osmformat.Node.Builder clearId() {
            this.bitField0_ &= -2;
            this.id_ = 0L;
            return this;
         }

         private void ensureKeysIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
               this.keys_ = new ArrayList<>(this.keys_);
               this.bitField0_ |= 2;
            }
         }

         @Override
         public List<Integer> getKeysList() {
            return Collections.unmodifiableList(this.keys_);
         }

         @Override
         public int getKeysCount() {
            return this.keys_.size();
         }

         @Override
         public int getKeys(int index) {
            return this.keys_.get(index);
         }

         public Osmformat.Node.Builder setKeys(int index, int value) {
            this.ensureKeysIsMutable();
            this.keys_.set(index, value);
            return this;
         }

         public Osmformat.Node.Builder addKeys(int value) {
            this.ensureKeysIsMutable();
            this.keys_.add(value);
            return this;
         }

         public Osmformat.Node.Builder addAllKeys(Iterable<? extends Integer> values) {
            this.ensureKeysIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.keys_);
            return this;
         }

         public Osmformat.Node.Builder clearKeys() {
            this.keys_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
         }

         private void ensureValsIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.vals_ = new ArrayList<>(this.vals_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public List<Integer> getValsList() {
            return Collections.unmodifiableList(this.vals_);
         }

         @Override
         public int getValsCount() {
            return this.vals_.size();
         }

         @Override
         public int getVals(int index) {
            return this.vals_.get(index);
         }

         public Osmformat.Node.Builder setVals(int index, int value) {
            this.ensureValsIsMutable();
            this.vals_.set(index, value);
            return this;
         }

         public Osmformat.Node.Builder addVals(int value) {
            this.ensureValsIsMutable();
            this.vals_.add(value);
            return this;
         }

         public Osmformat.Node.Builder addAllVals(Iterable<? extends Integer> values) {
            this.ensureValsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.vals_);
            return this;
         }

         public Osmformat.Node.Builder clearVals() {
            this.vals_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
         }

         @Override
         public boolean hasInfo() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public Osmformat.Info getInfo() {
            return this.info_;
         }

         public Osmformat.Node.Builder setInfo(Osmformat.Info value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.info_ = value;
               this.bitField0_ |= 8;
               return this;
            }
         }

         public Osmformat.Node.Builder setInfo(Osmformat.Info.Builder builderForValue) {
            this.info_ = builderForValue.build();
            this.bitField0_ |= 8;
            return this;
         }

         public Osmformat.Node.Builder mergeInfo(Osmformat.Info value) {
            if ((this.bitField0_ & 8) == 8 && this.info_ != Osmformat.Info.getDefaultInstance()) {
               this.info_ = Osmformat.Info.newBuilder(this.info_).mergeFrom(value).buildPartial();
            } else {
               this.info_ = value;
            }

            this.bitField0_ |= 8;
            return this;
         }

         public Osmformat.Node.Builder clearInfo() {
            this.info_ = Osmformat.Info.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
         }

         @Override
         public boolean hasLat() {
            return (this.bitField0_ & 16) == 16;
         }

         @Override
         public long getLat() {
            return this.lat_;
         }

         public Osmformat.Node.Builder setLat(long value) {
            this.bitField0_ |= 16;
            this.lat_ = value;
            return this;
         }

         public Osmformat.Node.Builder clearLat() {
            this.bitField0_ &= -17;
            this.lat_ = 0L;
            return this;
         }

         @Override
         public boolean hasLon() {
            return (this.bitField0_ & 32) == 32;
         }

         @Override
         public long getLon() {
            return this.lon_;
         }

         public Osmformat.Node.Builder setLon(long value) {
            this.bitField0_ |= 32;
            this.lon_ = value;
            return this;
         }

         public Osmformat.Node.Builder clearLon() {
            this.bitField0_ &= -33;
            this.lon_ = 0L;
            return this;
         }
      }
   }

   public interface NodeOrBuilder extends MessageLiteOrBuilder {
      boolean hasId();

      long getId();

      List<Integer> getKeysList();

      int getKeysCount();

      int getKeys(int var1);

      List<Integer> getValsList();

      int getValsCount();

      int getVals(int var1);

      boolean hasInfo();

      Osmformat.Info getInfo();

      boolean hasLat();

      long getLat();

      boolean hasLon();

      long getLon();
   }

   public static final class PrimitiveBlock extends GeneratedMessageLite implements Osmformat.PrimitiveBlockOrBuilder {
      private static final Osmformat.PrimitiveBlock defaultInstance = new Osmformat.PrimitiveBlock(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.PrimitiveBlock> PARSER = new AbstractParser<Osmformat.PrimitiveBlock>() {
         public Osmformat.PrimitiveBlock parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.PrimitiveBlock(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int STRINGTABLE_FIELD_NUMBER = 1;
      private Osmformat.StringTable stringtable_;
      public static final int PRIMITIVEGROUP_FIELD_NUMBER = 2;
      private List<Osmformat.PrimitiveGroup> primitivegroup_;
      public static final int GRANULARITY_FIELD_NUMBER = 17;
      private int granularity_;
      public static final int LAT_OFFSET_FIELD_NUMBER = 19;
      private long latOffset_;
      public static final int LON_OFFSET_FIELD_NUMBER = 20;
      private long lonOffset_;
      public static final int DATE_GRANULARITY_FIELD_NUMBER = 18;
      private int dateGranularity_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private PrimitiveBlock(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private PrimitiveBlock(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.PrimitiveBlock getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.PrimitiveBlock getDefaultInstanceForType() {
         return defaultInstance;
      }

      private PrimitiveBlock(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 10:
                     Osmformat.StringTable.Builder subBuilder = null;
                     if ((this.bitField0_ & 1) == 1) {
                        subBuilder = this.stringtable_.toBuilder();
                     }

                     this.stringtable_ = (Osmformat.StringTable)input.readMessage(Osmformat.StringTable.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.stringtable_);
                        this.stringtable_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 1;
                     break;
                  case 18:
                     if ((mutable_bitField0_ & 2) != 2) {
                        this.primitivegroup_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }

                     this.primitivegroup_.add(input.readMessage(Osmformat.PrimitiveGroup.PARSER, extensionRegistry));
                     break;
                  case 136:
                     this.bitField0_ |= 2;
                     this.granularity_ = input.readInt32();
                     break;
                  case 144:
                     this.bitField0_ |= 16;
                     this.dateGranularity_ = input.readInt32();
                     break;
                  case 152:
                     this.bitField0_ |= 4;
                     this.latOffset_ = input.readInt64();
                     break;
                  case 160:
                     this.bitField0_ |= 8;
                     this.lonOffset_ = input.readInt64();
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var36) {
            throw var36.setUnfinishedMessage(this);
         } catch (IOException var37) {
            throw new InvalidProtocolBufferException(var37.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 2) == 2) {
               this.primitivegroup_ = Collections.unmodifiableList(this.primitivegroup_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var34) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.PrimitiveBlock> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasStringtable() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public Osmformat.StringTable getStringtable() {
         return this.stringtable_;
      }

      @Override
      public List<Osmformat.PrimitiveGroup> getPrimitivegroupList() {
         return this.primitivegroup_;
      }

      public List<? extends Osmformat.PrimitiveGroupOrBuilder> getPrimitivegroupOrBuilderList() {
         return this.primitivegroup_;
      }

      @Override
      public int getPrimitivegroupCount() {
         return this.primitivegroup_.size();
      }

      @Override
      public Osmformat.PrimitiveGroup getPrimitivegroup(int index) {
         return this.primitivegroup_.get(index);
      }

      public Osmformat.PrimitiveGroupOrBuilder getPrimitivegroupOrBuilder(int index) {
         return this.primitivegroup_.get(index);
      }

      @Override
      public boolean hasGranularity() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public int getGranularity() {
         return this.granularity_;
      }

      @Override
      public boolean hasLatOffset() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public long getLatOffset() {
         return this.latOffset_;
      }

      @Override
      public boolean hasLonOffset() {
         return (this.bitField0_ & 8) == 8;
      }

      @Override
      public long getLonOffset() {
         return this.lonOffset_;
      }

      @Override
      public boolean hasDateGranularity() {
         return (this.bitField0_ & 16) == 16;
      }

      @Override
      public int getDateGranularity() {
         return this.dateGranularity_;
      }

      private void initFields() {
         this.stringtable_ = Osmformat.StringTable.getDefaultInstance();
         this.primitivegroup_ = Collections.emptyList();
         this.granularity_ = 100;
         this.latOffset_ = 0L;
         this.lonOffset_ = 0L;
         this.dateGranularity_ = 1000;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasStringtable()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            for (int i = 0; i < this.getPrimitivegroupCount(); i++) {
               if (!this.getPrimitivegroup(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.stringtable_);
         }

         for (int i = 0; i < this.primitivegroup_.size(); i++) {
            output.writeMessage(2, (MessageLite)this.primitivegroup_.get(i));
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeInt32(17, this.granularity_);
         }

         if ((this.bitField0_ & 16) == 16) {
            output.writeInt32(18, this.dateGranularity_);
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeInt64(19, this.latOffset_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeInt64(20, this.lonOffset_);
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.stringtable_);
            }

            for (int i = 0; i < this.primitivegroup_.size(); i++) {
               size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.primitivegroup_.get(i));
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeInt32Size(17, this.granularity_);
            }

            if ((this.bitField0_ & 16) == 16) {
               size += CodedOutputStream.computeInt32Size(18, this.dateGranularity_);
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeInt64Size(19, this.latOffset_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeInt64Size(20, this.lonOffset_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.PrimitiveBlock parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(data);
      }

      public static Osmformat.PrimitiveBlock parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.PrimitiveBlock parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(data);
      }

      public static Osmformat.PrimitiveBlock parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.PrimitiveBlock parseFrom(InputStream input) throws IOException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(input);
      }

      public static Osmformat.PrimitiveBlock parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.PrimitiveBlock parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.PrimitiveBlock)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.PrimitiveBlock parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.PrimitiveBlock)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.PrimitiveBlock parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(input);
      }

      public static Osmformat.PrimitiveBlock parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.PrimitiveBlock)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.PrimitiveBlock.Builder newBuilder() {
         return Osmformat.PrimitiveBlock.Builder.create();
      }

      public Osmformat.PrimitiveBlock.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.PrimitiveBlock.Builder newBuilder(Osmformat.PrimitiveBlock prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.PrimitiveBlock.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.PrimitiveBlock, Osmformat.PrimitiveBlock.Builder>
         implements Osmformat.PrimitiveBlockOrBuilder {
         private int bitField0_;
         private Osmformat.StringTable stringtable_ = Osmformat.StringTable.getDefaultInstance();
         private List<Osmformat.PrimitiveGroup> primitivegroup_ = Collections.emptyList();
         private int granularity_ = 100;
         private long latOffset_;
         private long lonOffset_;
         private int dateGranularity_ = 1000;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.PrimitiveBlock.Builder create() {
            return new Osmformat.PrimitiveBlock.Builder();
         }

         public Osmformat.PrimitiveBlock.Builder clear() {
            super.clear();
            this.stringtable_ = Osmformat.StringTable.getDefaultInstance();
            this.bitField0_ &= -2;
            this.primitivegroup_ = Collections.emptyList();
            this.bitField0_ &= -3;
            this.granularity_ = 100;
            this.bitField0_ &= -5;
            this.latOffset_ = 0L;
            this.bitField0_ &= -9;
            this.lonOffset_ = 0L;
            this.bitField0_ &= -17;
            this.dateGranularity_ = 1000;
            this.bitField0_ &= -33;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.PrimitiveBlock getDefaultInstanceForType() {
            return Osmformat.PrimitiveBlock.getDefaultInstance();
         }

         public Osmformat.PrimitiveBlock build() {
            Osmformat.PrimitiveBlock result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.PrimitiveBlock buildPartial() {
            Osmformat.PrimitiveBlock result = new Osmformat.PrimitiveBlock(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.stringtable_ = this.stringtable_;
            if ((this.bitField0_ & 2) == 2) {
               this.primitivegroup_ = Collections.unmodifiableList(this.primitivegroup_);
               this.bitField0_ &= -3;
            }

            result.primitivegroup_ = this.primitivegroup_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 2;
            }

            result.granularity_ = this.granularity_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 4;
            }

            result.latOffset_ = this.latOffset_;
            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 8;
            }

            result.lonOffset_ = this.lonOffset_;
            if ((from_bitField0_ & 32) == 32) {
               to_bitField0_ |= 16;
            }

            result.dateGranularity_ = this.dateGranularity_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.PrimitiveBlock.Builder mergeFrom(Osmformat.PrimitiveBlock other) {
            if (other == Osmformat.PrimitiveBlock.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasStringtable()) {
                  this.mergeStringtable(other.getStringtable());
               }

               if (!other.primitivegroup_.isEmpty()) {
                  if (this.primitivegroup_.isEmpty()) {
                     this.primitivegroup_ = other.primitivegroup_;
                     this.bitField0_ &= -3;
                  } else {
                     this.ensurePrimitivegroupIsMutable();
                     this.primitivegroup_.addAll(other.primitivegroup_);
                  }
               }

               if (other.hasGranularity()) {
                  this.setGranularity(other.getGranularity());
               }

               if (other.hasLatOffset()) {
                  this.setLatOffset(other.getLatOffset());
               }

               if (other.hasLonOffset()) {
                  this.setLonOffset(other.getLonOffset());
               }

               if (other.hasDateGranularity()) {
                  this.setDateGranularity(other.getDateGranularity());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasStringtable()) {
               return false;
            } else {
               for (int i = 0; i < this.getPrimitivegroupCount(); i++) {
                  if (!this.getPrimitivegroup(i).isInitialized()) {
                     return false;
                  }
               }

               return true;
            }
         }

         public Osmformat.PrimitiveBlock.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.PrimitiveBlock parsedMessage = null;

            try {
               parsedMessage = (Osmformat.PrimitiveBlock)Osmformat.PrimitiveBlock.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.PrimitiveBlock)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasStringtable() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public Osmformat.StringTable getStringtable() {
            return this.stringtable_;
         }

         public Osmformat.PrimitiveBlock.Builder setStringtable(Osmformat.StringTable value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.stringtable_ = value;
               this.bitField0_ |= 1;
               return this;
            }
         }

         public Osmformat.PrimitiveBlock.Builder setStringtable(Osmformat.StringTable.Builder builderForValue) {
            this.stringtable_ = builderForValue.build();
            this.bitField0_ |= 1;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder mergeStringtable(Osmformat.StringTable value) {
            if ((this.bitField0_ & 1) == 1 && this.stringtable_ != Osmformat.StringTable.getDefaultInstance()) {
               this.stringtable_ = Osmformat.StringTable.newBuilder(this.stringtable_).mergeFrom(value).buildPartial();
            } else {
               this.stringtable_ = value;
            }

            this.bitField0_ |= 1;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clearStringtable() {
            this.stringtable_ = Osmformat.StringTable.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
         }

         private void ensurePrimitivegroupIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
               this.primitivegroup_ = new ArrayList<>(this.primitivegroup_);
               this.bitField0_ |= 2;
            }
         }

         @Override
         public List<Osmformat.PrimitiveGroup> getPrimitivegroupList() {
            return Collections.unmodifiableList(this.primitivegroup_);
         }

         @Override
         public int getPrimitivegroupCount() {
            return this.primitivegroup_.size();
         }

         @Override
         public Osmformat.PrimitiveGroup getPrimitivegroup(int index) {
            return this.primitivegroup_.get(index);
         }

         public Osmformat.PrimitiveBlock.Builder setPrimitivegroup(int index, Osmformat.PrimitiveGroup value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensurePrimitivegroupIsMutable();
               this.primitivegroup_.set(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveBlock.Builder setPrimitivegroup(int index, Osmformat.PrimitiveGroup.Builder builderForValue) {
            this.ensurePrimitivegroupIsMutable();
            this.primitivegroup_.set(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder addPrimitivegroup(Osmformat.PrimitiveGroup value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensurePrimitivegroupIsMutable();
               this.primitivegroup_.add(value);
               return this;
            }
         }

         public Osmformat.PrimitiveBlock.Builder addPrimitivegroup(int index, Osmformat.PrimitiveGroup value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensurePrimitivegroupIsMutable();
               this.primitivegroup_.add(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveBlock.Builder addPrimitivegroup(Osmformat.PrimitiveGroup.Builder builderForValue) {
            this.ensurePrimitivegroupIsMutable();
            this.primitivegroup_.add(builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder addPrimitivegroup(int index, Osmformat.PrimitiveGroup.Builder builderForValue) {
            this.ensurePrimitivegroupIsMutable();
            this.primitivegroup_.add(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder addAllPrimitivegroup(Iterable<? extends Osmformat.PrimitiveGroup> values) {
            this.ensurePrimitivegroupIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.primitivegroup_);
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clearPrimitivegroup() {
            this.primitivegroup_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder removePrimitivegroup(int index) {
            this.ensurePrimitivegroupIsMutable();
            this.primitivegroup_.remove(index);
            return this;
         }

         @Override
         public boolean hasGranularity() {
            return (this.bitField0_ & 4) == 4;
         }

         @Override
         public int getGranularity() {
            return this.granularity_;
         }

         public Osmformat.PrimitiveBlock.Builder setGranularity(int value) {
            this.bitField0_ |= 4;
            this.granularity_ = value;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clearGranularity() {
            this.bitField0_ &= -5;
            this.granularity_ = 100;
            return this;
         }

         @Override
         public boolean hasLatOffset() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public long getLatOffset() {
            return this.latOffset_;
         }

         public Osmformat.PrimitiveBlock.Builder setLatOffset(long value) {
            this.bitField0_ |= 8;
            this.latOffset_ = value;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clearLatOffset() {
            this.bitField0_ &= -9;
            this.latOffset_ = 0L;
            return this;
         }

         @Override
         public boolean hasLonOffset() {
            return (this.bitField0_ & 16) == 16;
         }

         @Override
         public long getLonOffset() {
            return this.lonOffset_;
         }

         public Osmformat.PrimitiveBlock.Builder setLonOffset(long value) {
            this.bitField0_ |= 16;
            this.lonOffset_ = value;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clearLonOffset() {
            this.bitField0_ &= -17;
            this.lonOffset_ = 0L;
            return this;
         }

         @Override
         public boolean hasDateGranularity() {
            return (this.bitField0_ & 32) == 32;
         }

         @Override
         public int getDateGranularity() {
            return this.dateGranularity_;
         }

         public Osmformat.PrimitiveBlock.Builder setDateGranularity(int value) {
            this.bitField0_ |= 32;
            this.dateGranularity_ = value;
            return this;
         }

         public Osmformat.PrimitiveBlock.Builder clearDateGranularity() {
            this.bitField0_ &= -33;
            this.dateGranularity_ = 1000;
            return this;
         }
      }
   }

   public interface PrimitiveBlockOrBuilder extends MessageLiteOrBuilder {
      boolean hasStringtable();

      Osmformat.StringTable getStringtable();

      List<Osmformat.PrimitiveGroup> getPrimitivegroupList();

      Osmformat.PrimitiveGroup getPrimitivegroup(int var1);

      int getPrimitivegroupCount();

      boolean hasGranularity();

      int getGranularity();

      boolean hasLatOffset();

      long getLatOffset();

      boolean hasLonOffset();

      long getLonOffset();

      boolean hasDateGranularity();

      int getDateGranularity();
   }

   public static final class PrimitiveGroup extends GeneratedMessageLite implements Osmformat.PrimitiveGroupOrBuilder {
      private static final Osmformat.PrimitiveGroup defaultInstance = new Osmformat.PrimitiveGroup(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.PrimitiveGroup> PARSER = new AbstractParser<Osmformat.PrimitiveGroup>() {
         public Osmformat.PrimitiveGroup parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.PrimitiveGroup(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int NODES_FIELD_NUMBER = 1;
      private List<Osmformat.Node> nodes_;
      public static final int DENSE_FIELD_NUMBER = 2;
      private Osmformat.DenseNodes dense_;
      public static final int WAYS_FIELD_NUMBER = 3;
      private List<Osmformat.Way> ways_;
      public static final int RELATIONS_FIELD_NUMBER = 4;
      private List<Osmformat.Relation> relations_;
      public static final int CHANGESETS_FIELD_NUMBER = 5;
      private List<Osmformat.ChangeSet> changesets_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private PrimitiveGroup(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private PrimitiveGroup(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.PrimitiveGroup getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.PrimitiveGroup getDefaultInstanceForType() {
         return defaultInstance;
      }

      private PrimitiveGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 10:
                     if ((mutable_bitField0_ & 1) != 1) {
                        this.nodes_ = new ArrayList<>();
                        mutable_bitField0_ |= 1;
                     }

                     this.nodes_.add(input.readMessage(Osmformat.Node.PARSER, extensionRegistry));
                     break;
                  case 18:
                     Osmformat.DenseNodes.Builder subBuilder = null;
                     if ((this.bitField0_ & 1) == 1) {
                        subBuilder = this.dense_.toBuilder();
                     }

                     this.dense_ = (Osmformat.DenseNodes)input.readMessage(Osmformat.DenseNodes.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.dense_);
                        this.dense_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 1;
                     break;
                  case 26:
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.ways_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     this.ways_.add(input.readMessage(Osmformat.Way.PARSER, extensionRegistry));
                     break;
                  case 34:
                     if ((mutable_bitField0_ & 8) != 8) {
                        this.relations_ = new ArrayList<>();
                        mutable_bitField0_ |= 8;
                     }

                     this.relations_.add(input.readMessage(Osmformat.Relation.PARSER, extensionRegistry));
                     break;
                  case 42:
                     if ((mutable_bitField0_ & 16) != 16) {
                        this.changesets_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     this.changesets_.add(input.readMessage(Osmformat.ChangeSet.PARSER, extensionRegistry));
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var36) {
            throw var36.setUnfinishedMessage(this);
         } catch (IOException var37) {
            throw new InvalidProtocolBufferException(var37.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 1) == 1) {
               this.nodes_ = Collections.unmodifiableList(this.nodes_);
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.ways_ = Collections.unmodifiableList(this.ways_);
            }

            if ((mutable_bitField0_ & 8) == 8) {
               this.relations_ = Collections.unmodifiableList(this.relations_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.changesets_ = Collections.unmodifiableList(this.changesets_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var34) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.PrimitiveGroup> getParserForType() {
         return PARSER;
      }

      @Override
      public List<Osmformat.Node> getNodesList() {
         return this.nodes_;
      }

      public List<? extends Osmformat.NodeOrBuilder> getNodesOrBuilderList() {
         return this.nodes_;
      }

      @Override
      public int getNodesCount() {
         return this.nodes_.size();
      }

      @Override
      public Osmformat.Node getNodes(int index) {
         return this.nodes_.get(index);
      }

      public Osmformat.NodeOrBuilder getNodesOrBuilder(int index) {
         return this.nodes_.get(index);
      }

      @Override
      public boolean hasDense() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public Osmformat.DenseNodes getDense() {
         return this.dense_;
      }

      @Override
      public List<Osmformat.Way> getWaysList() {
         return this.ways_;
      }

      public List<? extends Osmformat.WayOrBuilder> getWaysOrBuilderList() {
         return this.ways_;
      }

      @Override
      public int getWaysCount() {
         return this.ways_.size();
      }

      @Override
      public Osmformat.Way getWays(int index) {
         return this.ways_.get(index);
      }

      public Osmformat.WayOrBuilder getWaysOrBuilder(int index) {
         return this.ways_.get(index);
      }

      @Override
      public List<Osmformat.Relation> getRelationsList() {
         return this.relations_;
      }

      public List<? extends Osmformat.RelationOrBuilder> getRelationsOrBuilderList() {
         return this.relations_;
      }

      @Override
      public int getRelationsCount() {
         return this.relations_.size();
      }

      @Override
      public Osmformat.Relation getRelations(int index) {
         return this.relations_.get(index);
      }

      public Osmformat.RelationOrBuilder getRelationsOrBuilder(int index) {
         return this.relations_.get(index);
      }

      @Override
      public List<Osmformat.ChangeSet> getChangesetsList() {
         return this.changesets_;
      }

      public List<? extends Osmformat.ChangeSetOrBuilder> getChangesetsOrBuilderList() {
         return this.changesets_;
      }

      @Override
      public int getChangesetsCount() {
         return this.changesets_.size();
      }

      @Override
      public Osmformat.ChangeSet getChangesets(int index) {
         return this.changesets_.get(index);
      }

      public Osmformat.ChangeSetOrBuilder getChangesetsOrBuilder(int index) {
         return this.changesets_.get(index);
      }

      private void initFields() {
         this.nodes_ = Collections.emptyList();
         this.dense_ = Osmformat.DenseNodes.getDefaultInstance();
         this.ways_ = Collections.emptyList();
         this.relations_ = Collections.emptyList();
         this.changesets_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else {
            for (int i = 0; i < this.getNodesCount(); i++) {
               if (!this.getNodes(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            for (int ix = 0; ix < this.getWaysCount(); ix++) {
               if (!this.getWays(ix).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            for (int ixx = 0; ixx < this.getRelationsCount(); ixx++) {
               if (!this.getRelations(ixx).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            for (int ixxx = 0; ixxx < this.getChangesetsCount(); ixxx++) {
               if (!this.getChangesets(ixxx).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();

         for (int i = 0; i < this.nodes_.size(); i++) {
            output.writeMessage(1, (MessageLite)this.nodes_.get(i));
         }

         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(2, this.dense_);
         }

         for (int i = 0; i < this.ways_.size(); i++) {
            output.writeMessage(3, (MessageLite)this.ways_.get(i));
         }

         for (int i = 0; i < this.relations_.size(); i++) {
            output.writeMessage(4, (MessageLite)this.relations_.get(i));
         }

         for (int i = 0; i < this.changesets_.size(); i++) {
            output.writeMessage(5, (MessageLite)this.changesets_.get(i));
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;

            for (int i = 0; i < this.nodes_.size(); i++) {
               size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.nodes_.get(i));
            }

            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(2, this.dense_);
            }

            for (int i = 0; i < this.ways_.size(); i++) {
               size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.ways_.get(i));
            }

            for (int i = 0; i < this.relations_.size(); i++) {
               size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.relations_.get(i));
            }

            for (int i = 0; i < this.changesets_.size(); i++) {
               size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.changesets_.get(i));
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.PrimitiveGroup parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(data);
      }

      public static Osmformat.PrimitiveGroup parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.PrimitiveGroup parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(data);
      }

      public static Osmformat.PrimitiveGroup parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.PrimitiveGroup parseFrom(InputStream input) throws IOException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(input);
      }

      public static Osmformat.PrimitiveGroup parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.PrimitiveGroup parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.PrimitiveGroup)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.PrimitiveGroup parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.PrimitiveGroup)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.PrimitiveGroup parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(input);
      }

      public static Osmformat.PrimitiveGroup parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.PrimitiveGroup)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.PrimitiveGroup.Builder newBuilder() {
         return Osmformat.PrimitiveGroup.Builder.create();
      }

      public Osmformat.PrimitiveGroup.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.PrimitiveGroup.Builder newBuilder(Osmformat.PrimitiveGroup prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.PrimitiveGroup.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.PrimitiveGroup, Osmformat.PrimitiveGroup.Builder>
         implements Osmformat.PrimitiveGroupOrBuilder {
         private int bitField0_;
         private List<Osmformat.Node> nodes_ = Collections.emptyList();
         private Osmformat.DenseNodes dense_ = Osmformat.DenseNodes.getDefaultInstance();
         private List<Osmformat.Way> ways_ = Collections.emptyList();
         private List<Osmformat.Relation> relations_ = Collections.emptyList();
         private List<Osmformat.ChangeSet> changesets_ = Collections.emptyList();

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.PrimitiveGroup.Builder create() {
            return new Osmformat.PrimitiveGroup.Builder();
         }

         public Osmformat.PrimitiveGroup.Builder clear() {
            super.clear();
            this.nodes_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.dense_ = Osmformat.DenseNodes.getDefaultInstance();
            this.bitField0_ &= -3;
            this.ways_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.relations_ = Collections.emptyList();
            this.bitField0_ &= -9;
            this.changesets_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.PrimitiveGroup getDefaultInstanceForType() {
            return Osmformat.PrimitiveGroup.getDefaultInstance();
         }

         public Osmformat.PrimitiveGroup build() {
            Osmformat.PrimitiveGroup result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.PrimitiveGroup buildPartial() {
            Osmformat.PrimitiveGroup result = new Osmformat.PrimitiveGroup(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((this.bitField0_ & 1) == 1) {
               this.nodes_ = Collections.unmodifiableList(this.nodes_);
               this.bitField0_ &= -2;
            }

            result.nodes_ = this.nodes_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 1;
            }

            result.dense_ = this.dense_;
            if ((this.bitField0_ & 4) == 4) {
               this.ways_ = Collections.unmodifiableList(this.ways_);
               this.bitField0_ &= -5;
            }

            result.ways_ = this.ways_;
            if ((this.bitField0_ & 8) == 8) {
               this.relations_ = Collections.unmodifiableList(this.relations_);
               this.bitField0_ &= -9;
            }

            result.relations_ = this.relations_;
            if ((this.bitField0_ & 16) == 16) {
               this.changesets_ = Collections.unmodifiableList(this.changesets_);
               this.bitField0_ &= -17;
            }

            result.changesets_ = this.changesets_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.PrimitiveGroup.Builder mergeFrom(Osmformat.PrimitiveGroup other) {
            if (other == Osmformat.PrimitiveGroup.getDefaultInstance()) {
               return this;
            } else {
               if (!other.nodes_.isEmpty()) {
                  if (this.nodes_.isEmpty()) {
                     this.nodes_ = other.nodes_;
                     this.bitField0_ &= -2;
                  } else {
                     this.ensureNodesIsMutable();
                     this.nodes_.addAll(other.nodes_);
                  }
               }

               if (other.hasDense()) {
                  this.mergeDense(other.getDense());
               }

               if (!other.ways_.isEmpty()) {
                  if (this.ways_.isEmpty()) {
                     this.ways_ = other.ways_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureWaysIsMutable();
                     this.ways_.addAll(other.ways_);
                  }
               }

               if (!other.relations_.isEmpty()) {
                  if (this.relations_.isEmpty()) {
                     this.relations_ = other.relations_;
                     this.bitField0_ &= -9;
                  } else {
                     this.ensureRelationsIsMutable();
                     this.relations_.addAll(other.relations_);
                  }
               }

               if (!other.changesets_.isEmpty()) {
                  if (this.changesets_.isEmpty()) {
                     this.changesets_ = other.changesets_;
                     this.bitField0_ &= -17;
                  } else {
                     this.ensureChangesetsIsMutable();
                     this.changesets_.addAll(other.changesets_);
                  }
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            for (int i = 0; i < this.getNodesCount(); i++) {
               if (!this.getNodes(i).isInitialized()) {
                  return false;
               }
            }

            for (int ix = 0; ix < this.getWaysCount(); ix++) {
               if (!this.getWays(ix).isInitialized()) {
                  return false;
               }
            }

            for (int ixx = 0; ixx < this.getRelationsCount(); ixx++) {
               if (!this.getRelations(ixx).isInitialized()) {
                  return false;
               }
            }

            for (int ixxx = 0; ixxx < this.getChangesetsCount(); ixxx++) {
               if (!this.getChangesets(ixxx).isInitialized()) {
                  return false;
               }
            }

            return true;
         }

         public Osmformat.PrimitiveGroup.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.PrimitiveGroup parsedMessage = null;

            try {
               parsedMessage = (Osmformat.PrimitiveGroup)Osmformat.PrimitiveGroup.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.PrimitiveGroup)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         private void ensureNodesIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
               this.nodes_ = new ArrayList<>(this.nodes_);
               this.bitField0_ |= 1;
            }
         }

         @Override
         public List<Osmformat.Node> getNodesList() {
            return Collections.unmodifiableList(this.nodes_);
         }

         @Override
         public int getNodesCount() {
            return this.nodes_.size();
         }

         @Override
         public Osmformat.Node getNodes(int index) {
            return this.nodes_.get(index);
         }

         public Osmformat.PrimitiveGroup.Builder setNodes(int index, Osmformat.Node value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureNodesIsMutable();
               this.nodes_.set(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder setNodes(int index, Osmformat.Node.Builder builderForValue) {
            this.ensureNodesIsMutable();
            this.nodes_.set(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addNodes(Osmformat.Node value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureNodesIsMutable();
               this.nodes_.add(value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addNodes(int index, Osmformat.Node value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureNodesIsMutable();
               this.nodes_.add(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addNodes(Osmformat.Node.Builder builderForValue) {
            this.ensureNodesIsMutable();
            this.nodes_.add(builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addNodes(int index, Osmformat.Node.Builder builderForValue) {
            this.ensureNodesIsMutable();
            this.nodes_.add(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addAllNodes(Iterable<? extends Osmformat.Node> values) {
            this.ensureNodesIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.nodes_);
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder clearNodes() {
            this.nodes_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder removeNodes(int index) {
            this.ensureNodesIsMutable();
            this.nodes_.remove(index);
            return this;
         }

         @Override
         public boolean hasDense() {
            return (this.bitField0_ & 2) == 2;
         }

         @Override
         public Osmformat.DenseNodes getDense() {
            return this.dense_;
         }

         public Osmformat.PrimitiveGroup.Builder setDense(Osmformat.DenseNodes value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.dense_ = value;
               this.bitField0_ |= 2;
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder setDense(Osmformat.DenseNodes.Builder builderForValue) {
            this.dense_ = builderForValue.build();
            this.bitField0_ |= 2;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder mergeDense(Osmformat.DenseNodes value) {
            if ((this.bitField0_ & 2) == 2 && this.dense_ != Osmformat.DenseNodes.getDefaultInstance()) {
               this.dense_ = Osmformat.DenseNodes.newBuilder(this.dense_).mergeFrom(value).buildPartial();
            } else {
               this.dense_ = value;
            }

            this.bitField0_ |= 2;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder clearDense() {
            this.dense_ = Osmformat.DenseNodes.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
         }

         private void ensureWaysIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.ways_ = new ArrayList<>(this.ways_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public List<Osmformat.Way> getWaysList() {
            return Collections.unmodifiableList(this.ways_);
         }

         @Override
         public int getWaysCount() {
            return this.ways_.size();
         }

         @Override
         public Osmformat.Way getWays(int index) {
            return this.ways_.get(index);
         }

         public Osmformat.PrimitiveGroup.Builder setWays(int index, Osmformat.Way value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureWaysIsMutable();
               this.ways_.set(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder setWays(int index, Osmformat.Way.Builder builderForValue) {
            this.ensureWaysIsMutable();
            this.ways_.set(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addWays(Osmformat.Way value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureWaysIsMutable();
               this.ways_.add(value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addWays(int index, Osmformat.Way value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureWaysIsMutable();
               this.ways_.add(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addWays(Osmformat.Way.Builder builderForValue) {
            this.ensureWaysIsMutable();
            this.ways_.add(builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addWays(int index, Osmformat.Way.Builder builderForValue) {
            this.ensureWaysIsMutable();
            this.ways_.add(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addAllWays(Iterable<? extends Osmformat.Way> values) {
            this.ensureWaysIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.ways_);
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder clearWays() {
            this.ways_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder removeWays(int index) {
            this.ensureWaysIsMutable();
            this.ways_.remove(index);
            return this;
         }

         private void ensureRelationsIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
               this.relations_ = new ArrayList<>(this.relations_);
               this.bitField0_ |= 8;
            }
         }

         @Override
         public List<Osmformat.Relation> getRelationsList() {
            return Collections.unmodifiableList(this.relations_);
         }

         @Override
         public int getRelationsCount() {
            return this.relations_.size();
         }

         @Override
         public Osmformat.Relation getRelations(int index) {
            return this.relations_.get(index);
         }

         public Osmformat.PrimitiveGroup.Builder setRelations(int index, Osmformat.Relation value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureRelationsIsMutable();
               this.relations_.set(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder setRelations(int index, Osmformat.Relation.Builder builderForValue) {
            this.ensureRelationsIsMutable();
            this.relations_.set(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addRelations(Osmformat.Relation value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureRelationsIsMutable();
               this.relations_.add(value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addRelations(int index, Osmformat.Relation value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureRelationsIsMutable();
               this.relations_.add(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addRelations(Osmformat.Relation.Builder builderForValue) {
            this.ensureRelationsIsMutable();
            this.relations_.add(builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addRelations(int index, Osmformat.Relation.Builder builderForValue) {
            this.ensureRelationsIsMutable();
            this.relations_.add(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addAllRelations(Iterable<? extends Osmformat.Relation> values) {
            this.ensureRelationsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.relations_);
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder clearRelations() {
            this.relations_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder removeRelations(int index) {
            this.ensureRelationsIsMutable();
            this.relations_.remove(index);
            return this;
         }

         private void ensureChangesetsIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.changesets_ = new ArrayList<>(this.changesets_);
               this.bitField0_ |= 16;
            }
         }

         @Override
         public List<Osmformat.ChangeSet> getChangesetsList() {
            return Collections.unmodifiableList(this.changesets_);
         }

         @Override
         public int getChangesetsCount() {
            return this.changesets_.size();
         }

         @Override
         public Osmformat.ChangeSet getChangesets(int index) {
            return this.changesets_.get(index);
         }

         public Osmformat.PrimitiveGroup.Builder setChangesets(int index, Osmformat.ChangeSet value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureChangesetsIsMutable();
               this.changesets_.set(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder setChangesets(int index, Osmformat.ChangeSet.Builder builderForValue) {
            this.ensureChangesetsIsMutable();
            this.changesets_.set(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addChangesets(Osmformat.ChangeSet value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureChangesetsIsMutable();
               this.changesets_.add(value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addChangesets(int index, Osmformat.ChangeSet value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureChangesetsIsMutable();
               this.changesets_.add(index, value);
               return this;
            }
         }

         public Osmformat.PrimitiveGroup.Builder addChangesets(Osmformat.ChangeSet.Builder builderForValue) {
            this.ensureChangesetsIsMutable();
            this.changesets_.add(builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addChangesets(int index, Osmformat.ChangeSet.Builder builderForValue) {
            this.ensureChangesetsIsMutable();
            this.changesets_.add(index, builderForValue.build());
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder addAllChangesets(Iterable<? extends Osmformat.ChangeSet> values) {
            this.ensureChangesetsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.changesets_);
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder clearChangesets() {
            this.changesets_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }

         public Osmformat.PrimitiveGroup.Builder removeChangesets(int index) {
            this.ensureChangesetsIsMutable();
            this.changesets_.remove(index);
            return this;
         }
      }
   }

   public interface PrimitiveGroupOrBuilder extends MessageLiteOrBuilder {
      List<Osmformat.Node> getNodesList();

      Osmformat.Node getNodes(int var1);

      int getNodesCount();

      boolean hasDense();

      Osmformat.DenseNodes getDense();

      List<Osmformat.Way> getWaysList();

      Osmformat.Way getWays(int var1);

      int getWaysCount();

      List<Osmformat.Relation> getRelationsList();

      Osmformat.Relation getRelations(int var1);

      int getRelationsCount();

      List<Osmformat.ChangeSet> getChangesetsList();

      Osmformat.ChangeSet getChangesets(int var1);

      int getChangesetsCount();
   }

   public static final class Relation extends GeneratedMessageLite implements Osmformat.RelationOrBuilder {
      private static final Osmformat.Relation defaultInstance = new Osmformat.Relation(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.Relation> PARSER = new AbstractParser<Osmformat.Relation>() {
         public Osmformat.Relation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.Relation(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int ID_FIELD_NUMBER = 1;
      private long id_;
      public static final int KEYS_FIELD_NUMBER = 2;
      private List<Integer> keys_;
      private int keysMemoizedSerializedSize = -1;
      public static final int VALS_FIELD_NUMBER = 3;
      private List<Integer> vals_;
      private int valsMemoizedSerializedSize = -1;
      public static final int INFO_FIELD_NUMBER = 4;
      private Osmformat.Info info_;
      public static final int ROLES_SID_FIELD_NUMBER = 8;
      private List<Integer> rolesSid_;
      private int rolesSidMemoizedSerializedSize = -1;
      public static final int MEMIDS_FIELD_NUMBER = 9;
      private List<Long> memids_;
      private int memidsMemoizedSerializedSize = -1;
      public static final int TYPES_FIELD_NUMBER = 10;
      private List<Osmformat.Relation.MemberType> types_;
      private int typesMemoizedSerializedSize;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private Relation(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private Relation(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.Relation getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.Relation getDefaultInstanceForType() {
         return defaultInstance;
      }

      private Relation(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               int limit;
               switch (tag) {
                  case 0:
                     done = true;
                     continue;
                  case 8:
                     this.bitField0_ |= 1;
                     this.id_ = input.readInt64();
                     continue;
                  case 16:
                     if ((mutable_bitField0_ & 2) != 2) {
                        this.keys_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }

                     this.keys_.add(input.readUInt32());
                     continue;
                  case 18:
                     int length = input.readRawVarint32();
                     limit = input.pushLimit(length);
                     if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                        this.keys_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }
                     break;
                  case 24:
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.vals_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     this.vals_.add(input.readUInt32());
                     continue;
                  case 26:
                     int lengthx = input.readRawVarint32();
                     limit = input.pushLimit(lengthx);
                     if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                        this.vals_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.vals_.add(input.readUInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  case 34:
                     Osmformat.Info.Builder subBuilder = null;
                     if ((this.bitField0_ & 2) == 2) {
                        subBuilder = this.info_.toBuilder();
                     }

                     this.info_ = (Osmformat.Info)input.readMessage(Osmformat.Info.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.info_);
                        this.info_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 2;
                     continue;
                  case 64:
                     if ((mutable_bitField0_ & 16) != 16) {
                        this.rolesSid_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     this.rolesSid_.add(input.readInt32());
                     continue;
                  case 66:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 16) != 16 && input.getBytesUntilLimit() > 0) {
                        this.rolesSid_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.rolesSid_.add(input.readInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  case 72:
                     if ((mutable_bitField0_ & 32) != 32) {
                        this.memids_ = new ArrayList<>();
                        mutable_bitField0_ |= 32;
                     }

                     this.memids_.add(input.readSInt64());
                     continue;
                  case 74:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 32) != 32 && input.getBytesUntilLimit() > 0) {
                        this.memids_ = new ArrayList<>();
                        mutable_bitField0_ |= 32;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.memids_.add(input.readSInt64());
                     }

                     input.popLimit(limit);
                     continue;
                  case 80:
                     int rawValue = input.readEnum();
                     Osmformat.Relation.MemberType value = Osmformat.Relation.MemberType.valueOf(rawValue);
                     if (value == null) {
                        unknownFieldsCodedOutput.writeRawVarint32(tag);
                        unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                     } else {
                        if ((mutable_bitField0_ & 64) != 64) {
                           this.types_ = new ArrayList<>();
                           mutable_bitField0_ |= 64;
                        }

                        this.types_.add(value);
                     }
                     continue;
                  case 82:
                     limit = input.pushLimit(input.readRawVarint32());

                     while (input.getBytesUntilLimit() > 0) {
                        int rawValuex = input.readEnum();
                        Osmformat.Relation.MemberType valuex = Osmformat.Relation.MemberType.valueOf(rawValuex);
                        if (valuex == null) {
                           unknownFieldsCodedOutput.writeRawVarint32(tag);
                           unknownFieldsCodedOutput.writeRawVarint32(rawValuex);
                        } else {
                           if ((mutable_bitField0_ & 64) != 64) {
                              this.types_ = new ArrayList<>();
                              mutable_bitField0_ |= 64;
                           }

                           this.types_.add(valuex);
                        }
                     }

                     input.popLimit(limit);
                     continue;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
                     continue;
               }

               while (input.getBytesUntilLimit() > 0) {
                  this.keys_.add(input.readUInt32());
               }

               input.popLimit(limit);
            }
         } catch (InvalidProtocolBufferException var39) {
            throw var39.setUnfinishedMessage(this);
         } catch (IOException var40) {
            throw new InvalidProtocolBufferException(var40.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 2) == 2) {
               this.keys_ = Collections.unmodifiableList(this.keys_);
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.vals_ = Collections.unmodifiableList(this.vals_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.rolesSid_ = Collections.unmodifiableList(this.rolesSid_);
            }

            if ((mutable_bitField0_ & 32) == 32) {
               this.memids_ = Collections.unmodifiableList(this.memids_);
            }

            if ((mutable_bitField0_ & 64) == 64) {
               this.types_ = Collections.unmodifiableList(this.types_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var37) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.Relation> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasId() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public long getId() {
         return this.id_;
      }

      @Override
      public List<Integer> getKeysList() {
         return this.keys_;
      }

      @Override
      public int getKeysCount() {
         return this.keys_.size();
      }

      @Override
      public int getKeys(int index) {
         return this.keys_.get(index);
      }

      @Override
      public List<Integer> getValsList() {
         return this.vals_;
      }

      @Override
      public int getValsCount() {
         return this.vals_.size();
      }

      @Override
      public int getVals(int index) {
         return this.vals_.get(index);
      }

      @Override
      public boolean hasInfo() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public Osmformat.Info getInfo() {
         return this.info_;
      }

      @Override
      public List<Integer> getRolesSidList() {
         return this.rolesSid_;
      }

      @Override
      public int getRolesSidCount() {
         return this.rolesSid_.size();
      }

      @Override
      public int getRolesSid(int index) {
         return this.rolesSid_.get(index);
      }

      @Override
      public List<Long> getMemidsList() {
         return this.memids_;
      }

      @Override
      public int getMemidsCount() {
         return this.memids_.size();
      }

      @Override
      public long getMemids(int index) {
         return this.memids_.get(index);
      }

      @Override
      public List<Osmformat.Relation.MemberType> getTypesList() {
         return this.types_;
      }

      @Override
      public int getTypesCount() {
         return this.types_.size();
      }

      @Override
      public Osmformat.Relation.MemberType getTypes(int index) {
         return this.types_.get(index);
      }

      private void initFields() {
         this.id_ = 0L;
         this.keys_ = Collections.emptyList();
         this.vals_ = Collections.emptyList();
         this.info_ = Osmformat.Info.getDefaultInstance();
         this.rolesSid_ = Collections.emptyList();
         this.memids_ = Collections.emptyList();
         this.types_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasId()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeInt64(1, this.id_);
         }

         if (this.getKeysList().size() > 0) {
            output.writeRawVarint32(18);
            output.writeRawVarint32(this.keysMemoizedSerializedSize);
         }

         for (int i = 0; i < this.keys_.size(); i++) {
            output.writeUInt32NoTag(this.keys_.get(i));
         }

         if (this.getValsList().size() > 0) {
            output.writeRawVarint32(26);
            output.writeRawVarint32(this.valsMemoizedSerializedSize);
         }

         for (int i = 0; i < this.vals_.size(); i++) {
            output.writeUInt32NoTag(this.vals_.get(i));
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeMessage(4, this.info_);
         }

         if (this.getRolesSidList().size() > 0) {
            output.writeRawVarint32(66);
            output.writeRawVarint32(this.rolesSidMemoizedSerializedSize);
         }

         for (int i = 0; i < this.rolesSid_.size(); i++) {
            output.writeInt32NoTag(this.rolesSid_.get(i));
         }

         if (this.getMemidsList().size() > 0) {
            output.writeRawVarint32(74);
            output.writeRawVarint32(this.memidsMemoizedSerializedSize);
         }

         for (int i = 0; i < this.memids_.size(); i++) {
            output.writeSInt64NoTag(this.memids_.get(i));
         }

         if (this.getTypesList().size() > 0) {
            output.writeRawVarint32(82);
            output.writeRawVarint32(this.typesMemoizedSerializedSize);
         }

         for (int i = 0; i < this.types_.size(); i++) {
            output.writeEnumNoTag(this.types_.get(i).getNumber());
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeInt64Size(1, this.id_);
            }

            int dataSize = 0;

            for (int i = 0; i < this.keys_.size(); i++) {
               dataSize += CodedOutputStream.computeUInt32SizeNoTag(this.keys_.get(i));
            }

            size += dataSize;
            if (!this.getKeysList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.keysMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.vals_.size(); i++) {
               dataSize += CodedOutputStream.computeUInt32SizeNoTag(this.vals_.get(i));
            }

            size += dataSize;
            if (!this.getValsList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.valsMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeMessageSize(4, this.info_);
            }

            dataSize = 0;

            for (int i = 0; i < this.rolesSid_.size(); i++) {
               dataSize += CodedOutputStream.computeInt32SizeNoTag(this.rolesSid_.get(i));
            }

            size += dataSize;
            if (!this.getRolesSidList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.rolesSidMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.memids_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.memids_.get(i));
            }

            size += dataSize;
            if (!this.getMemidsList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.memidsMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.types_.size(); i++) {
               dataSize += CodedOutputStream.computeEnumSizeNoTag(this.types_.get(i).getNumber());
            }

            size += dataSize;
            if (!this.getTypesList().isEmpty()) {
               size = ++size + CodedOutputStream.computeRawVarint32Size(dataSize);
            }

            this.typesMemoizedSerializedSize = dataSize;
            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.Relation parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.Relation)PARSER.parseFrom(data);
      }

      public static Osmformat.Relation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Relation)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Relation parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.Relation)PARSER.parseFrom(data);
      }

      public static Osmformat.Relation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Relation)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Relation parseFrom(InputStream input) throws IOException {
         return (Osmformat.Relation)PARSER.parseFrom(input);
      }

      public static Osmformat.Relation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Relation)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Relation parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.Relation)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.Relation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Relation)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.Relation parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.Relation)PARSER.parseFrom(input);
      }

      public static Osmformat.Relation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Relation)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Relation.Builder newBuilder() {
         return Osmformat.Relation.Builder.create();
      }

      public Osmformat.Relation.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.Relation.Builder newBuilder(Osmformat.Relation prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.Relation.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.Relation, Osmformat.Relation.Builder>
         implements Osmformat.RelationOrBuilder {
         private int bitField0_;
         private long id_;
         private List<Integer> keys_ = Collections.emptyList();
         private List<Integer> vals_ = Collections.emptyList();
         private Osmformat.Info info_ = Osmformat.Info.getDefaultInstance();
         private List<Integer> rolesSid_ = Collections.emptyList();
         private List<Long> memids_ = Collections.emptyList();
         private List<Osmformat.Relation.MemberType> types_ = Collections.emptyList();

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.Relation.Builder create() {
            return new Osmformat.Relation.Builder();
         }

         public Osmformat.Relation.Builder clear() {
            super.clear();
            this.id_ = 0L;
            this.bitField0_ &= -2;
            this.keys_ = Collections.emptyList();
            this.bitField0_ &= -3;
            this.vals_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.info_ = Osmformat.Info.getDefaultInstance();
            this.bitField0_ &= -9;
            this.rolesSid_ = Collections.emptyList();
            this.bitField0_ &= -17;
            this.memids_ = Collections.emptyList();
            this.bitField0_ &= -33;
            this.types_ = Collections.emptyList();
            this.bitField0_ &= -65;
            return this;
         }

         public Osmformat.Relation.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.Relation getDefaultInstanceForType() {
            return Osmformat.Relation.getDefaultInstance();
         }

         public Osmformat.Relation build() {
            Osmformat.Relation result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.Relation buildPartial() {
            Osmformat.Relation result = new Osmformat.Relation(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.id_ = this.id_;
            if ((this.bitField0_ & 2) == 2) {
               this.keys_ = Collections.unmodifiableList(this.keys_);
               this.bitField0_ &= -3;
            }

            result.keys_ = this.keys_;
            if ((this.bitField0_ & 4) == 4) {
               this.vals_ = Collections.unmodifiableList(this.vals_);
               this.bitField0_ &= -5;
            }

            result.vals_ = this.vals_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 2;
            }

            result.info_ = this.info_;
            if ((this.bitField0_ & 16) == 16) {
               this.rolesSid_ = Collections.unmodifiableList(this.rolesSid_);
               this.bitField0_ &= -17;
            }

            result.rolesSid_ = this.rolesSid_;
            if ((this.bitField0_ & 32) == 32) {
               this.memids_ = Collections.unmodifiableList(this.memids_);
               this.bitField0_ &= -33;
            }

            result.memids_ = this.memids_;
            if ((this.bitField0_ & 64) == 64) {
               this.types_ = Collections.unmodifiableList(this.types_);
               this.bitField0_ &= -65;
            }

            result.types_ = this.types_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.Relation.Builder mergeFrom(Osmformat.Relation other) {
            if (other == Osmformat.Relation.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasId()) {
                  this.setId(other.getId());
               }

               if (!other.keys_.isEmpty()) {
                  if (this.keys_.isEmpty()) {
                     this.keys_ = other.keys_;
                     this.bitField0_ &= -3;
                  } else {
                     this.ensureKeysIsMutable();
                     this.keys_.addAll(other.keys_);
                  }
               }

               if (!other.vals_.isEmpty()) {
                  if (this.vals_.isEmpty()) {
                     this.vals_ = other.vals_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureValsIsMutable();
                     this.vals_.addAll(other.vals_);
                  }
               }

               if (other.hasInfo()) {
                  this.mergeInfo(other.getInfo());
               }

               if (!other.rolesSid_.isEmpty()) {
                  if (this.rolesSid_.isEmpty()) {
                     this.rolesSid_ = other.rolesSid_;
                     this.bitField0_ &= -17;
                  } else {
                     this.ensureRolesSidIsMutable();
                     this.rolesSid_.addAll(other.rolesSid_);
                  }
               }

               if (!other.memids_.isEmpty()) {
                  if (this.memids_.isEmpty()) {
                     this.memids_ = other.memids_;
                     this.bitField0_ &= -33;
                  } else {
                     this.ensureMemidsIsMutable();
                     this.memids_.addAll(other.memids_);
                  }
               }

               if (!other.types_.isEmpty()) {
                  if (this.types_.isEmpty()) {
                     this.types_ = other.types_;
                     this.bitField0_ &= -65;
                  } else {
                     this.ensureTypesIsMutable();
                     this.types_.addAll(other.types_);
                  }
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return this.hasId();
         }

         public Osmformat.Relation.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.Relation parsedMessage = null;

            try {
               parsedMessage = (Osmformat.Relation)Osmformat.Relation.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.Relation)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public long getId() {
            return this.id_;
         }

         public Osmformat.Relation.Builder setId(long value) {
            this.bitField0_ |= 1;
            this.id_ = value;
            return this;
         }

         public Osmformat.Relation.Builder clearId() {
            this.bitField0_ &= -2;
            this.id_ = 0L;
            return this;
         }

         private void ensureKeysIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
               this.keys_ = new ArrayList<>(this.keys_);
               this.bitField0_ |= 2;
            }
         }

         @Override
         public List<Integer> getKeysList() {
            return Collections.unmodifiableList(this.keys_);
         }

         @Override
         public int getKeysCount() {
            return this.keys_.size();
         }

         @Override
         public int getKeys(int index) {
            return this.keys_.get(index);
         }

         public Osmformat.Relation.Builder setKeys(int index, int value) {
            this.ensureKeysIsMutable();
            this.keys_.set(index, value);
            return this;
         }

         public Osmformat.Relation.Builder addKeys(int value) {
            this.ensureKeysIsMutable();
            this.keys_.add(value);
            return this;
         }

         public Osmformat.Relation.Builder addAllKeys(Iterable<? extends Integer> values) {
            this.ensureKeysIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.keys_);
            return this;
         }

         public Osmformat.Relation.Builder clearKeys() {
            this.keys_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
         }

         private void ensureValsIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.vals_ = new ArrayList<>(this.vals_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public List<Integer> getValsList() {
            return Collections.unmodifiableList(this.vals_);
         }

         @Override
         public int getValsCount() {
            return this.vals_.size();
         }

         @Override
         public int getVals(int index) {
            return this.vals_.get(index);
         }

         public Osmformat.Relation.Builder setVals(int index, int value) {
            this.ensureValsIsMutable();
            this.vals_.set(index, value);
            return this;
         }

         public Osmformat.Relation.Builder addVals(int value) {
            this.ensureValsIsMutable();
            this.vals_.add(value);
            return this;
         }

         public Osmformat.Relation.Builder addAllVals(Iterable<? extends Integer> values) {
            this.ensureValsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.vals_);
            return this;
         }

         public Osmformat.Relation.Builder clearVals() {
            this.vals_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
         }

         @Override
         public boolean hasInfo() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public Osmformat.Info getInfo() {
            return this.info_;
         }

         public Osmformat.Relation.Builder setInfo(Osmformat.Info value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.info_ = value;
               this.bitField0_ |= 8;
               return this;
            }
         }

         public Osmformat.Relation.Builder setInfo(Osmformat.Info.Builder builderForValue) {
            this.info_ = builderForValue.build();
            this.bitField0_ |= 8;
            return this;
         }

         public Osmformat.Relation.Builder mergeInfo(Osmformat.Info value) {
            if ((this.bitField0_ & 8) == 8 && this.info_ != Osmformat.Info.getDefaultInstance()) {
               this.info_ = Osmformat.Info.newBuilder(this.info_).mergeFrom(value).buildPartial();
            } else {
               this.info_ = value;
            }

            this.bitField0_ |= 8;
            return this;
         }

         public Osmformat.Relation.Builder clearInfo() {
            this.info_ = Osmformat.Info.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
         }

         private void ensureRolesSidIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.rolesSid_ = new ArrayList<>(this.rolesSid_);
               this.bitField0_ |= 16;
            }
         }

         @Override
         public List<Integer> getRolesSidList() {
            return Collections.unmodifiableList(this.rolesSid_);
         }

         @Override
         public int getRolesSidCount() {
            return this.rolesSid_.size();
         }

         @Override
         public int getRolesSid(int index) {
            return this.rolesSid_.get(index);
         }

         public Osmformat.Relation.Builder setRolesSid(int index, int value) {
            this.ensureRolesSidIsMutable();
            this.rolesSid_.set(index, value);
            return this;
         }

         public Osmformat.Relation.Builder addRolesSid(int value) {
            this.ensureRolesSidIsMutable();
            this.rolesSid_.add(value);
            return this;
         }

         public Osmformat.Relation.Builder addAllRolesSid(Iterable<? extends Integer> values) {
            this.ensureRolesSidIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.rolesSid_);
            return this;
         }

         public Osmformat.Relation.Builder clearRolesSid() {
            this.rolesSid_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }

         private void ensureMemidsIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
               this.memids_ = new ArrayList<>(this.memids_);
               this.bitField0_ |= 32;
            }
         }

         @Override
         public List<Long> getMemidsList() {
            return Collections.unmodifiableList(this.memids_);
         }

         @Override
         public int getMemidsCount() {
            return this.memids_.size();
         }

         @Override
         public long getMemids(int index) {
            return this.memids_.get(index);
         }

         public Osmformat.Relation.Builder setMemids(int index, long value) {
            this.ensureMemidsIsMutable();
            this.memids_.set(index, value);
            return this;
         }

         public Osmformat.Relation.Builder addMemids(long value) {
            this.ensureMemidsIsMutable();
            this.memids_.add(value);
            return this;
         }

         public Osmformat.Relation.Builder addAllMemids(Iterable<? extends Long> values) {
            this.ensureMemidsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.memids_);
            return this;
         }

         public Osmformat.Relation.Builder clearMemids() {
            this.memids_ = Collections.emptyList();
            this.bitField0_ &= -33;
            return this;
         }

         private void ensureTypesIsMutable() {
            if ((this.bitField0_ & 64) != 64) {
               this.types_ = new ArrayList<>(this.types_);
               this.bitField0_ |= 64;
            }
         }

         @Override
         public List<Osmformat.Relation.MemberType> getTypesList() {
            return Collections.unmodifiableList(this.types_);
         }

         @Override
         public int getTypesCount() {
            return this.types_.size();
         }

         @Override
         public Osmformat.Relation.MemberType getTypes(int index) {
            return this.types_.get(index);
         }

         public Osmformat.Relation.Builder setTypes(int index, Osmformat.Relation.MemberType value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureTypesIsMutable();
               this.types_.set(index, value);
               return this;
            }
         }

         public Osmformat.Relation.Builder addTypes(Osmformat.Relation.MemberType value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureTypesIsMutable();
               this.types_.add(value);
               return this;
            }
         }

         public Osmformat.Relation.Builder addAllTypes(Iterable<? extends Osmformat.Relation.MemberType> values) {
            this.ensureTypesIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.types_);
            return this;
         }

         public Osmformat.Relation.Builder clearTypes() {
            this.types_ = Collections.emptyList();
            this.bitField0_ &= -65;
            return this;
         }
      }

      public static enum MemberType implements EnumLite {
         NODE(0, 0),
         WAY(1, 1),
         RELATION(2, 2);

         public static final int NODE_VALUE = 0;
         public static final int WAY_VALUE = 1;
         public static final int RELATION_VALUE = 2;
         private static EnumLiteMap<Osmformat.Relation.MemberType> internalValueMap = new EnumLiteMap<Osmformat.Relation.MemberType>() {
            public Osmformat.Relation.MemberType findValueByNumber(int number) {
               return Osmformat.Relation.MemberType.valueOf(number);
            }
         };
         private final int value;

         public final int getNumber() {
            return this.value;
         }

         public static Osmformat.Relation.MemberType valueOf(int value) {
            switch (value) {
               case 0:
                  return NODE;
               case 1:
                  return WAY;
               case 2:
                  return RELATION;
               default:
                  return null;
            }
         }

         public static EnumLiteMap<Osmformat.Relation.MemberType> internalGetValueMap() {
            return internalValueMap;
         }

         private MemberType(int index, int value) {
            this.value = value;
         }
      }
   }

   public interface RelationOrBuilder extends MessageLiteOrBuilder {
      boolean hasId();

      long getId();

      List<Integer> getKeysList();

      int getKeysCount();

      int getKeys(int var1);

      List<Integer> getValsList();

      int getValsCount();

      int getVals(int var1);

      boolean hasInfo();

      Osmformat.Info getInfo();

      List<Integer> getRolesSidList();

      int getRolesSidCount();

      int getRolesSid(int var1);

      List<Long> getMemidsList();

      int getMemidsCount();

      long getMemids(int var1);

      List<Osmformat.Relation.MemberType> getTypesList();

      int getTypesCount();

      Osmformat.Relation.MemberType getTypes(int var1);
   }

   public static final class StringTable extends GeneratedMessageLite implements Osmformat.StringTableOrBuilder {
      private static final Osmformat.StringTable defaultInstance = new Osmformat.StringTable(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.StringTable> PARSER = new AbstractParser<Osmformat.StringTable>() {
         public Osmformat.StringTable parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.StringTable(input, extensionRegistry);
         }
      };
      public static final int S_FIELD_NUMBER = 1;
      private List<ByteString> s_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private StringTable(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private StringTable(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.StringTable getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.StringTable getDefaultInstanceForType() {
         return defaultInstance;
      }

      private StringTable(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 10:
                     if ((mutable_bitField0_ & 1) != 1) {
                        this.s_ = new ArrayList<>();
                        mutable_bitField0_ |= 1;
                     }

                     this.s_.add(input.readBytes());
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException var35) {
            throw var35.setUnfinishedMessage(this);
         } catch (IOException var36) {
            throw new InvalidProtocolBufferException(var36.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 1) == 1) {
               this.s_ = Collections.unmodifiableList(this.s_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var33) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.StringTable> getParserForType() {
         return PARSER;
      }

      @Override
      public List<ByteString> getSList() {
         return this.s_;
      }

      @Override
      public int getSCount() {
         return this.s_.size();
      }

      @Override
      public ByteString getS(int index) {
         return this.s_.get(index);
      }

      private void initFields() {
         this.s_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();

         for (int i = 0; i < this.s_.size(); i++) {
            output.writeBytes(1, this.s_.get(i));
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            int var4 = 0;
            int dataSize = 0;

            for (int i = 0; i < this.s_.size(); i++) {
               dataSize += CodedOutputStream.computeBytesSizeNoTag(this.s_.get(i));
            }

            var4 += dataSize;
            var4 += 1 * this.getSList().size();
            var4 += this.unknownFields.size();
            this.memoizedSerializedSize = var4;
            return var4;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.StringTable parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.StringTable)PARSER.parseFrom(data);
      }

      public static Osmformat.StringTable parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.StringTable)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.StringTable parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.StringTable)PARSER.parseFrom(data);
      }

      public static Osmformat.StringTable parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.StringTable)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.StringTable parseFrom(InputStream input) throws IOException {
         return (Osmformat.StringTable)PARSER.parseFrom(input);
      }

      public static Osmformat.StringTable parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.StringTable)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.StringTable parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.StringTable)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.StringTable parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.StringTable)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.StringTable parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.StringTable)PARSER.parseFrom(input);
      }

      public static Osmformat.StringTable parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.StringTable)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.StringTable.Builder newBuilder() {
         return Osmformat.StringTable.Builder.create();
      }

      public Osmformat.StringTable.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.StringTable.Builder newBuilder(Osmformat.StringTable prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.StringTable.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.StringTable, Osmformat.StringTable.Builder>
         implements Osmformat.StringTableOrBuilder {
         private int bitField0_;
         private List<ByteString> s_ = Collections.emptyList();

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.StringTable.Builder create() {
            return new Osmformat.StringTable.Builder();
         }

         public Osmformat.StringTable.Builder clear() {
            super.clear();
            this.s_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
         }

         public Osmformat.StringTable.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.StringTable getDefaultInstanceForType() {
            return Osmformat.StringTable.getDefaultInstance();
         }

         public Osmformat.StringTable build() {
            Osmformat.StringTable result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.StringTable buildPartial() {
            Osmformat.StringTable result = new Osmformat.StringTable(this);
            int from_bitField0_ = this.bitField0_;
            if ((this.bitField0_ & 1) == 1) {
               this.s_ = Collections.unmodifiableList(this.s_);
               this.bitField0_ &= -2;
            }

            result.s_ = this.s_;
            return result;
         }

         public Osmformat.StringTable.Builder mergeFrom(Osmformat.StringTable other) {
            if (other == Osmformat.StringTable.getDefaultInstance()) {
               return this;
            } else {
               if (!other.s_.isEmpty()) {
                  if (this.s_.isEmpty()) {
                     this.s_ = other.s_;
                     this.bitField0_ &= -2;
                  } else {
                     this.ensureSIsMutable();
                     this.s_.addAll(other.s_);
                  }
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return true;
         }

         public Osmformat.StringTable.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.StringTable parsedMessage = null;

            try {
               parsedMessage = (Osmformat.StringTable)Osmformat.StringTable.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.StringTable)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         private void ensureSIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
               this.s_ = new ArrayList<>(this.s_);
               this.bitField0_ |= 1;
            }
         }

         @Override
         public List<ByteString> getSList() {
            return Collections.unmodifiableList(this.s_);
         }

         @Override
         public int getSCount() {
            return this.s_.size();
         }

         @Override
         public ByteString getS(int index) {
            return this.s_.get(index);
         }

         public Osmformat.StringTable.Builder setS(int index, ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureSIsMutable();
               this.s_.set(index, value);
               return this;
            }
         }

         public Osmformat.StringTable.Builder addS(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.ensureSIsMutable();
               this.s_.add(value);
               return this;
            }
         }

         public Osmformat.StringTable.Builder addAllS(Iterable<? extends ByteString> values) {
            this.ensureSIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.s_);
            return this;
         }

         public Osmformat.StringTable.Builder clearS() {
            this.s_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
         }
      }
   }

   public interface StringTableOrBuilder extends MessageLiteOrBuilder {
      List<ByteString> getSList();

      int getSCount();

      ByteString getS(int var1);
   }

   public static final class Way extends GeneratedMessageLite implements Osmformat.WayOrBuilder {
      private static final Osmformat.Way defaultInstance = new Osmformat.Way(true);
      private final ByteString unknownFields;
      public static Parser<Osmformat.Way> PARSER = new AbstractParser<Osmformat.Way>() {
         public Osmformat.Way parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Osmformat.Way(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int ID_FIELD_NUMBER = 1;
      private long id_;
      public static final int KEYS_FIELD_NUMBER = 2;
      private List<Integer> keys_;
      private int keysMemoizedSerializedSize = -1;
      public static final int VALS_FIELD_NUMBER = 3;
      private List<Integer> vals_;
      private int valsMemoizedSerializedSize = -1;
      public static final int INFO_FIELD_NUMBER = 4;
      private Osmformat.Info info_;
      public static final int REFS_FIELD_NUMBER = 8;
      private List<Long> refs_;
      private int refsMemoizedSerializedSize = -1;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private Way(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private Way(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Osmformat.Way getDefaultInstance() {
         return defaultInstance;
      }

      public Osmformat.Way getDefaultInstanceForType() {
         return defaultInstance;
      }

      private Way(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.initFields();
         int mutable_bitField0_ = 0;
         Output unknownFieldsOutput = ByteString.newOutput();
         CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               int limit;
               switch (tag) {
                  case 0:
                     done = true;
                     continue;
                  case 8:
                     this.bitField0_ |= 1;
                     this.id_ = input.readInt64();
                     continue;
                  case 16:
                     if ((mutable_bitField0_ & 2) != 2) {
                        this.keys_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }

                     this.keys_.add(input.readUInt32());
                     continue;
                  case 18:
                     int length = input.readRawVarint32();
                     limit = input.pushLimit(length);
                     if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                        this.keys_ = new ArrayList<>();
                        mutable_bitField0_ |= 2;
                     }
                     break;
                  case 24:
                     if ((mutable_bitField0_ & 4) != 4) {
                        this.vals_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     this.vals_.add(input.readUInt32());
                     continue;
                  case 26:
                     int lengthx = input.readRawVarint32();
                     limit = input.pushLimit(lengthx);
                     if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                        this.vals_ = new ArrayList<>();
                        mutable_bitField0_ |= 4;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.vals_.add(input.readUInt32());
                     }

                     input.popLimit(limit);
                     continue;
                  case 34:
                     Osmformat.Info.Builder subBuilder = null;
                     if ((this.bitField0_ & 2) == 2) {
                        subBuilder = this.info_.toBuilder();
                     }

                     this.info_ = (Osmformat.Info)input.readMessage(Osmformat.Info.PARSER, extensionRegistry);
                     if (subBuilder != null) {
                        subBuilder.mergeFrom(this.info_);
                        this.info_ = subBuilder.buildPartial();
                     }

                     this.bitField0_ |= 2;
                     continue;
                  case 64:
                     if ((mutable_bitField0_ & 16) != 16) {
                        this.refs_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     this.refs_.add(input.readSInt64());
                     continue;
                  case 66:
                     limit = input.pushLimit(input.readRawVarint32());
                     if ((mutable_bitField0_ & 16) != 16 && input.getBytesUntilLimit() > 0) {
                        this.refs_ = new ArrayList<>();
                        mutable_bitField0_ |= 16;
                     }

                     while (input.getBytesUntilLimit() > 0) {
                        this.refs_.add(input.readSInt64());
                     }

                     input.popLimit(limit);
                     continue;
                  default:
                     if (!this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                        done = true;
                     }
                     continue;
               }

               while (input.getBytesUntilLimit() > 0) {
                  this.keys_.add(input.readUInt32());
               }

               input.popLimit(limit);
            }
         } catch (InvalidProtocolBufferException var37) {
            throw var37.setUnfinishedMessage(this);
         } catch (IOException var38) {
            throw new InvalidProtocolBufferException(var38.getMessage()).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 2) == 2) {
               this.keys_ = Collections.unmodifiableList(this.keys_);
            }

            if ((mutable_bitField0_ & 4) == 4) {
               this.vals_ = Collections.unmodifiableList(this.vals_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.refs_ = Collections.unmodifiableList(this.refs_);
            }

            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var35) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Osmformat.Way> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasId() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public long getId() {
         return this.id_;
      }

      @Override
      public List<Integer> getKeysList() {
         return this.keys_;
      }

      @Override
      public int getKeysCount() {
         return this.keys_.size();
      }

      @Override
      public int getKeys(int index) {
         return this.keys_.get(index);
      }

      @Override
      public List<Integer> getValsList() {
         return this.vals_;
      }

      @Override
      public int getValsCount() {
         return this.vals_.size();
      }

      @Override
      public int getVals(int index) {
         return this.vals_.get(index);
      }

      @Override
      public boolean hasInfo() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public Osmformat.Info getInfo() {
         return this.info_;
      }

      @Override
      public List<Long> getRefsList() {
         return this.refs_;
      }

      @Override
      public int getRefsCount() {
         return this.refs_.size();
      }

      @Override
      public long getRefs(int index) {
         return this.refs_.get(index);
      }

      private void initFields() {
         this.id_ = 0L;
         this.keys_ = Collections.emptyList();
         this.vals_ = Collections.emptyList();
         this.info_ = Osmformat.Info.getDefaultInstance();
         this.refs_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasId()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeInt64(1, this.id_);
         }

         if (this.getKeysList().size() > 0) {
            output.writeRawVarint32(18);
            output.writeRawVarint32(this.keysMemoizedSerializedSize);
         }

         for (int i = 0; i < this.keys_.size(); i++) {
            output.writeUInt32NoTag(this.keys_.get(i));
         }

         if (this.getValsList().size() > 0) {
            output.writeRawVarint32(26);
            output.writeRawVarint32(this.valsMemoizedSerializedSize);
         }

         for (int i = 0; i < this.vals_.size(); i++) {
            output.writeUInt32NoTag(this.vals_.get(i));
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeMessage(4, this.info_);
         }

         if (this.getRefsList().size() > 0) {
            output.writeRawVarint32(66);
            output.writeRawVarint32(this.refsMemoizedSerializedSize);
         }

         for (int i = 0; i < this.refs_.size(); i++) {
            output.writeSInt64NoTag(this.refs_.get(i));
         }

         output.writeRawBytes(this.unknownFields);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeInt64Size(1, this.id_);
            }

            int dataSize = 0;

            for (int i = 0; i < this.keys_.size(); i++) {
               dataSize += CodedOutputStream.computeUInt32SizeNoTag(this.keys_.get(i));
            }

            size += dataSize;
            if (!this.getKeysList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.keysMemoizedSerializedSize = dataSize;
            dataSize = 0;

            for (int i = 0; i < this.vals_.size(); i++) {
               dataSize += CodedOutputStream.computeUInt32SizeNoTag(this.vals_.get(i));
            }

            size += dataSize;
            if (!this.getValsList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.valsMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeMessageSize(4, this.info_);
            }

            dataSize = 0;

            for (int i = 0; i < this.refs_.size(); i++) {
               dataSize += CodedOutputStream.computeSInt64SizeNoTag(this.refs_.get(i));
            }

            size += dataSize;
            if (!this.getRefsList().isEmpty()) {
               size = ++size + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }

            this.refsMemoizedSerializedSize = dataSize;
            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Osmformat.Way parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Osmformat.Way)PARSER.parseFrom(data);
      }

      public static Osmformat.Way parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Way)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Way parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Osmformat.Way)PARSER.parseFrom(data);
      }

      public static Osmformat.Way parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Osmformat.Way)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Osmformat.Way parseFrom(InputStream input) throws IOException {
         return (Osmformat.Way)PARSER.parseFrom(input);
      }

      public static Osmformat.Way parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Way)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Way parseDelimitedFrom(InputStream input) throws IOException {
         return (Osmformat.Way)PARSER.parseDelimitedFrom(input);
      }

      public static Osmformat.Way parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Way)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Osmformat.Way parseFrom(CodedInputStream input) throws IOException {
         return (Osmformat.Way)PARSER.parseFrom(input);
      }

      public static Osmformat.Way parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Osmformat.Way)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Osmformat.Way.Builder newBuilder() {
         return Osmformat.Way.Builder.create();
      }

      public Osmformat.Way.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Osmformat.Way.Builder newBuilder(Osmformat.Way prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Osmformat.Way.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Osmformat.Way, Osmformat.Way.Builder>
         implements Osmformat.WayOrBuilder {
         private int bitField0_;
         private long id_;
         private List<Integer> keys_ = Collections.emptyList();
         private List<Integer> vals_ = Collections.emptyList();
         private Osmformat.Info info_ = Osmformat.Info.getDefaultInstance();
         private List<Long> refs_ = Collections.emptyList();

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Osmformat.Way.Builder create() {
            return new Osmformat.Way.Builder();
         }

         public Osmformat.Way.Builder clear() {
            super.clear();
            this.id_ = 0L;
            this.bitField0_ &= -2;
            this.keys_ = Collections.emptyList();
            this.bitField0_ &= -3;
            this.vals_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.info_ = Osmformat.Info.getDefaultInstance();
            this.bitField0_ &= -9;
            this.refs_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }

         public Osmformat.Way.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Osmformat.Way getDefaultInstanceForType() {
            return Osmformat.Way.getDefaultInstance();
         }

         public Osmformat.Way build() {
            Osmformat.Way result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Osmformat.Way buildPartial() {
            Osmformat.Way result = new Osmformat.Way(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.id_ = this.id_;
            if ((this.bitField0_ & 2) == 2) {
               this.keys_ = Collections.unmodifiableList(this.keys_);
               this.bitField0_ &= -3;
            }

            result.keys_ = this.keys_;
            if ((this.bitField0_ & 4) == 4) {
               this.vals_ = Collections.unmodifiableList(this.vals_);
               this.bitField0_ &= -5;
            }

            result.vals_ = this.vals_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 2;
            }

            result.info_ = this.info_;
            if ((this.bitField0_ & 16) == 16) {
               this.refs_ = Collections.unmodifiableList(this.refs_);
               this.bitField0_ &= -17;
            }

            result.refs_ = this.refs_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Osmformat.Way.Builder mergeFrom(Osmformat.Way other) {
            if (other == Osmformat.Way.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasId()) {
                  this.setId(other.getId());
               }

               if (!other.keys_.isEmpty()) {
                  if (this.keys_.isEmpty()) {
                     this.keys_ = other.keys_;
                     this.bitField0_ &= -3;
                  } else {
                     this.ensureKeysIsMutable();
                     this.keys_.addAll(other.keys_);
                  }
               }

               if (!other.vals_.isEmpty()) {
                  if (this.vals_.isEmpty()) {
                     this.vals_ = other.vals_;
                     this.bitField0_ &= -5;
                  } else {
                     this.ensureValsIsMutable();
                     this.vals_.addAll(other.vals_);
                  }
               }

               if (other.hasInfo()) {
                  this.mergeInfo(other.getInfo());
               }

               if (!other.refs_.isEmpty()) {
                  if (this.refs_.isEmpty()) {
                     this.refs_ = other.refs_;
                     this.bitField0_ &= -17;
                  } else {
                     this.ensureRefsIsMutable();
                     this.refs_.addAll(other.refs_);
                  }
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return this.hasId();
         }

         public Osmformat.Way.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Osmformat.Way parsedMessage = null;

            try {
               parsedMessage = (Osmformat.Way)Osmformat.Way.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Osmformat.Way)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public long getId() {
            return this.id_;
         }

         public Osmformat.Way.Builder setId(long value) {
            this.bitField0_ |= 1;
            this.id_ = value;
            return this;
         }

         public Osmformat.Way.Builder clearId() {
            this.bitField0_ &= -2;
            this.id_ = 0L;
            return this;
         }

         private void ensureKeysIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
               this.keys_ = new ArrayList<>(this.keys_);
               this.bitField0_ |= 2;
            }
         }

         @Override
         public List<Integer> getKeysList() {
            return Collections.unmodifiableList(this.keys_);
         }

         @Override
         public int getKeysCount() {
            return this.keys_.size();
         }

         @Override
         public int getKeys(int index) {
            return this.keys_.get(index);
         }

         public Osmformat.Way.Builder setKeys(int index, int value) {
            this.ensureKeysIsMutable();
            this.keys_.set(index, value);
            return this;
         }

         public Osmformat.Way.Builder addKeys(int value) {
            this.ensureKeysIsMutable();
            this.keys_.add(value);
            return this;
         }

         public Osmformat.Way.Builder addAllKeys(Iterable<? extends Integer> values) {
            this.ensureKeysIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.keys_);
            return this;
         }

         public Osmformat.Way.Builder clearKeys() {
            this.keys_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
         }

         private void ensureValsIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.vals_ = new ArrayList<>(this.vals_);
               this.bitField0_ |= 4;
            }
         }

         @Override
         public List<Integer> getValsList() {
            return Collections.unmodifiableList(this.vals_);
         }

         @Override
         public int getValsCount() {
            return this.vals_.size();
         }

         @Override
         public int getVals(int index) {
            return this.vals_.get(index);
         }

         public Osmformat.Way.Builder setVals(int index, int value) {
            this.ensureValsIsMutable();
            this.vals_.set(index, value);
            return this;
         }

         public Osmformat.Way.Builder addVals(int value) {
            this.ensureValsIsMutable();
            this.vals_.add(value);
            return this;
         }

         public Osmformat.Way.Builder addAllVals(Iterable<? extends Integer> values) {
            this.ensureValsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.vals_);
            return this;
         }

         public Osmformat.Way.Builder clearVals() {
            this.vals_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
         }

         @Override
         public boolean hasInfo() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public Osmformat.Info getInfo() {
            return this.info_;
         }

         public Osmformat.Way.Builder setInfo(Osmformat.Info value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.info_ = value;
               this.bitField0_ |= 8;
               return this;
            }
         }

         public Osmformat.Way.Builder setInfo(Osmformat.Info.Builder builderForValue) {
            this.info_ = builderForValue.build();
            this.bitField0_ |= 8;
            return this;
         }

         public Osmformat.Way.Builder mergeInfo(Osmformat.Info value) {
            if ((this.bitField0_ & 8) == 8 && this.info_ != Osmformat.Info.getDefaultInstance()) {
               this.info_ = Osmformat.Info.newBuilder(this.info_).mergeFrom(value).buildPartial();
            } else {
               this.info_ = value;
            }

            this.bitField0_ |= 8;
            return this;
         }

         public Osmformat.Way.Builder clearInfo() {
            this.info_ = Osmformat.Info.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
         }

         private void ensureRefsIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.refs_ = new ArrayList<>(this.refs_);
               this.bitField0_ |= 16;
            }
         }

         @Override
         public List<Long> getRefsList() {
            return Collections.unmodifiableList(this.refs_);
         }

         @Override
         public int getRefsCount() {
            return this.refs_.size();
         }

         @Override
         public long getRefs(int index) {
            return this.refs_.get(index);
         }

         public Osmformat.Way.Builder setRefs(int index, long value) {
            this.ensureRefsIsMutable();
            this.refs_.set(index, value);
            return this;
         }

         public Osmformat.Way.Builder addRefs(long value) {
            this.ensureRefsIsMutable();
            this.refs_.add(value);
            return this;
         }

         public Osmformat.Way.Builder addAllRefs(Iterable<? extends Long> values) {
            this.ensureRefsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.refs_);
            return this;
         }

         public Osmformat.Way.Builder clearRefs() {
            this.refs_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
         }
      }
   }

   public interface WayOrBuilder extends MessageLiteOrBuilder {
      boolean hasId();

      long getId();

      List<Integer> getKeysList();

      int getKeysCount();

      int getKeys(int var1);

      List<Integer> getValsList();

      int getValsCount();

      int getVals(int var1);

      boolean hasInfo();

      Osmformat.Info getInfo();

      List<Long> getRefsList();

      int getRefsCount();

      long getRefs(int var1);
   }
}
