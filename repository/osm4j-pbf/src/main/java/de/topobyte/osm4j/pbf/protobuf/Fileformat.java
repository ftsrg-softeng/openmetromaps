package de.topobyte.osm4j.pbf.protobuf;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ByteString.Output;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;

public final class Fileformat {
   private Fileformat() {
   }

   public static void registerAllExtensions(ExtensionRegistryLite registry) {
   }

   public static final class Blob extends GeneratedMessageLite implements Fileformat.BlobOrBuilder {
      private static final Fileformat.Blob defaultInstance = new Fileformat.Blob(true);
      private final ByteString unknownFields;
      public static Parser<Fileformat.Blob> PARSER = new AbstractParser<Fileformat.Blob>() {
         public Fileformat.Blob parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Fileformat.Blob(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int RAW_FIELD_NUMBER = 1;
      private ByteString raw_;
      public static final int RAW_SIZE_FIELD_NUMBER = 2;
      private int rawSize_;
      public static final int ZLIB_DATA_FIELD_NUMBER = 3;
      private ByteString zlibData_;
      public static final int LZMA_DATA_FIELD_NUMBER = 4;
      private ByteString lzmaData_;
      public static final int OBSOLETE_BZIP2_DATA_FIELD_NUMBER = 5;
      private ByteString oBSOLETEBzip2Data_;
      public static final int LZ4_DATA_FIELD_NUMBER = 6;
      private ByteString lz4Data_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private Blob(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private Blob(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Fileformat.Blob getDefaultInstance() {
         return defaultInstance;
      }

      public Fileformat.Blob getDefaultInstanceForType() {
         return defaultInstance;
      }

      private Blob(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                     this.bitField0_ |= 1;
                     this.raw_ = input.readBytes();
                     break;
                  case 16:
                     this.bitField0_ |= 2;
                     this.rawSize_ = input.readInt32();
                     break;
                  case 26:
                     this.bitField0_ |= 4;
                     this.zlibData_ = input.readBytes();
                     break;
                  case 34:
                     this.bitField0_ |= 8;
                     this.lzmaData_ = input.readBytes();
                     break;
                  case 42:
                     this.bitField0_ |= 16;
                     this.oBSOLETEBzip2Data_ = input.readBytes();
                     break;
                  case 50:
                     this.bitField0_ |= 32;
                     this.lz4Data_ = input.readBytes();
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

      public Parser<Fileformat.Blob> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasRaw() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public ByteString getRaw() {
         return this.raw_;
      }

      @Override
      public boolean hasRawSize() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public int getRawSize() {
         return this.rawSize_;
      }

      @Override
      public boolean hasZlibData() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public ByteString getZlibData() {
         return this.zlibData_;
      }

      @Override
      public boolean hasLzmaData() {
         return (this.bitField0_ & 8) == 8;
      }

      @Override
      public ByteString getLzmaData() {
         return this.lzmaData_;
      }

      @Deprecated
      @Override
      public boolean hasOBSOLETEBzip2Data() {
         return (this.bitField0_ & 16) == 16;
      }

      @Deprecated
      @Override
      public ByteString getOBSOLETEBzip2Data() {
         return this.oBSOLETEBzip2Data_;
      }

      @Override
      public boolean hasLz4Data() {
         return (this.bitField0_ & 32) == 32;
      }

      @Override
      public ByteString getLz4Data() {
         return this.lz4Data_;
      }

      private void initFields() {
         this.raw_ = ByteString.EMPTY;
         this.rawSize_ = 0;
         this.zlibData_ = ByteString.EMPTY;
         this.lzmaData_ = ByteString.EMPTY;
         this.oBSOLETEBzip2Data_ = ByteString.EMPTY;
         this.lz4Data_ = ByteString.EMPTY;
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
            output.writeBytes(1, this.raw_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeInt32(2, this.rawSize_);
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeBytes(3, this.zlibData_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeBytes(4, this.lzmaData_);
         }

         if ((this.bitField0_ & 16) == 16) {
            output.writeBytes(5, this.oBSOLETEBzip2Data_);
         }

         if ((this.bitField0_ & 32) == 32) {
            output.writeBytes(6, this.lz4Data_);
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
               size += CodedOutputStream.computeBytesSize(1, this.raw_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeInt32Size(2, this.rawSize_);
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeBytesSize(3, this.zlibData_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeBytesSize(4, this.lzmaData_);
            }

            if ((this.bitField0_ & 16) == 16) {
               size += CodedOutputStream.computeBytesSize(5, this.oBSOLETEBzip2Data_);
            }

            if ((this.bitField0_ & 32) == 32) {
               size += CodedOutputStream.computeBytesSize(6, this.lz4Data_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Fileformat.Blob parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Fileformat.Blob)PARSER.parseFrom(data);
      }

      public static Fileformat.Blob parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Fileformat.Blob)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Fileformat.Blob parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Fileformat.Blob)PARSER.parseFrom(data);
      }

      public static Fileformat.Blob parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Fileformat.Blob)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Fileformat.Blob parseFrom(InputStream input) throws IOException {
         return (Fileformat.Blob)PARSER.parseFrom(input);
      }

      public static Fileformat.Blob parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Fileformat.Blob)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Fileformat.Blob parseDelimitedFrom(InputStream input) throws IOException {
         return (Fileformat.Blob)PARSER.parseDelimitedFrom(input);
      }

      public static Fileformat.Blob parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Fileformat.Blob)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Fileformat.Blob parseFrom(CodedInputStream input) throws IOException {
         return (Fileformat.Blob)PARSER.parseFrom(input);
      }

      public static Fileformat.Blob parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Fileformat.Blob)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Fileformat.Blob.Builder newBuilder() {
         return Fileformat.Blob.Builder.create();
      }

      public Fileformat.Blob.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Fileformat.Blob.Builder newBuilder(Fileformat.Blob prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Fileformat.Blob.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Fileformat.Blob, Fileformat.Blob.Builder>
         implements Fileformat.BlobOrBuilder {
         private int bitField0_;
         private ByteString raw_ = ByteString.EMPTY;
         private int rawSize_;
         private ByteString zlibData_ = ByteString.EMPTY;
         private ByteString lzmaData_ = ByteString.EMPTY;
         private ByteString oBSOLETEBzip2Data_ = ByteString.EMPTY;
         private ByteString lz4Data_ = ByteString.EMPTY;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Fileformat.Blob.Builder create() {
            return new Fileformat.Blob.Builder();
         }

         public Fileformat.Blob.Builder clear() {
            super.clear();
            this.raw_ = ByteString.EMPTY;
            this.bitField0_ &= -2;
            this.rawSize_ = 0;
            this.bitField0_ &= -3;
            this.zlibData_ = ByteString.EMPTY;
            this.bitField0_ &= -5;
            this.lzmaData_ = ByteString.EMPTY;
            this.bitField0_ &= -9;
            this.oBSOLETEBzip2Data_ = ByteString.EMPTY;
            this.bitField0_ &= -17;
            this.lz4Data_ = ByteString.EMPTY;
            this.bitField0_ &= -33;
            return this;
         }

         public Fileformat.Blob.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Fileformat.Blob getDefaultInstanceForType() {
            return Fileformat.Blob.getDefaultInstance();
         }

         public Fileformat.Blob build() {
            Fileformat.Blob result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Fileformat.Blob buildPartial() {
            Fileformat.Blob result = new Fileformat.Blob(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.raw_ = this.raw_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.rawSize_ = this.rawSize_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            result.zlibData_ = this.zlibData_;
            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 8;
            }

            result.lzmaData_ = this.lzmaData_;
            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 16;
            }

            result.oBSOLETEBzip2Data_ = this.oBSOLETEBzip2Data_;
            if ((from_bitField0_ & 32) == 32) {
               to_bitField0_ |= 32;
            }

            result.lz4Data_ = this.lz4Data_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Fileformat.Blob.Builder mergeFrom(Fileformat.Blob other) {
            if (other == Fileformat.Blob.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasRaw()) {
                  this.setRaw(other.getRaw());
               }

               if (other.hasRawSize()) {
                  this.setRawSize(other.getRawSize());
               }

               if (other.hasZlibData()) {
                  this.setZlibData(other.getZlibData());
               }

               if (other.hasLzmaData()) {
                  this.setLzmaData(other.getLzmaData());
               }

               if (other.hasOBSOLETEBzip2Data()) {
                  this.setOBSOLETEBzip2Data(other.getOBSOLETEBzip2Data());
               }

               if (other.hasLz4Data()) {
                  this.setLz4Data(other.getLz4Data());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return true;
         }

         public Fileformat.Blob.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Fileformat.Blob parsedMessage = null;

            try {
               parsedMessage = (Fileformat.Blob)Fileformat.Blob.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Fileformat.Blob)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasRaw() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public ByteString getRaw() {
            return this.raw_;
         }

         public Fileformat.Blob.Builder setRaw(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.raw_ = value;
               return this;
            }
         }

         public Fileformat.Blob.Builder clearRaw() {
            this.bitField0_ &= -2;
            this.raw_ = Fileformat.Blob.getDefaultInstance().getRaw();
            return this;
         }

         @Override
         public boolean hasRawSize() {
            return (this.bitField0_ & 2) == 2;
         }

         @Override
         public int getRawSize() {
            return this.rawSize_;
         }

         public Fileformat.Blob.Builder setRawSize(int value) {
            this.bitField0_ |= 2;
            this.rawSize_ = value;
            return this;
         }

         public Fileformat.Blob.Builder clearRawSize() {
            this.bitField0_ &= -3;
            this.rawSize_ = 0;
            return this;
         }

         @Override
         public boolean hasZlibData() {
            return (this.bitField0_ & 4) == 4;
         }

         @Override
         public ByteString getZlibData() {
            return this.zlibData_;
         }

         public Fileformat.Blob.Builder setZlibData(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 4;
               this.zlibData_ = value;
               return this;
            }
         }

         public Fileformat.Blob.Builder clearZlibData() {
            this.bitField0_ &= -5;
            this.zlibData_ = Fileformat.Blob.getDefaultInstance().getZlibData();
            return this;
         }

         @Override
         public boolean hasLzmaData() {
            return (this.bitField0_ & 8) == 8;
         }

         @Override
         public ByteString getLzmaData() {
            return this.lzmaData_;
         }

         public Fileformat.Blob.Builder setLzmaData(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 8;
               this.lzmaData_ = value;
               return this;
            }
         }

         public Fileformat.Blob.Builder clearLzmaData() {
            this.bitField0_ &= -9;
            this.lzmaData_ = Fileformat.Blob.getDefaultInstance().getLzmaData();
            return this;
         }

         @Deprecated
         @Override
         public boolean hasOBSOLETEBzip2Data() {
            return (this.bitField0_ & 16) == 16;
         }

         @Deprecated
         @Override
         public ByteString getOBSOLETEBzip2Data() {
            return this.oBSOLETEBzip2Data_;
         }

         @Deprecated
         public Fileformat.Blob.Builder setOBSOLETEBzip2Data(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 16;
               this.oBSOLETEBzip2Data_ = value;
               return this;
            }
         }

         @Deprecated
         public Fileformat.Blob.Builder clearOBSOLETEBzip2Data() {
            this.bitField0_ &= -17;
            this.oBSOLETEBzip2Data_ = Fileformat.Blob.getDefaultInstance().getOBSOLETEBzip2Data();
            return this;
         }

         @Override
         public boolean hasLz4Data() {
            return (this.bitField0_ & 32) == 32;
         }

         @Override
         public ByteString getLz4Data() {
            return this.lz4Data_;
         }

         public Fileformat.Blob.Builder setLz4Data(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 32;
               this.lz4Data_ = value;
               return this;
            }
         }

         public Fileformat.Blob.Builder clearLz4Data() {
            this.bitField0_ &= -33;
            this.lz4Data_ = Fileformat.Blob.getDefaultInstance().getLz4Data();
            return this;
         }
      }
   }

   public static final class BlobHeader extends GeneratedMessageLite implements Fileformat.BlobHeaderOrBuilder {
      private static final Fileformat.BlobHeader defaultInstance = new Fileformat.BlobHeader(true);
      private final ByteString unknownFields;
      public static Parser<Fileformat.BlobHeader> PARSER = new AbstractParser<Fileformat.BlobHeader>() {
         public Fileformat.BlobHeader parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Fileformat.BlobHeader(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int TYPE_FIELD_NUMBER = 1;
      private Object type_;
      public static final int INDEXDATA_FIELD_NUMBER = 2;
      private ByteString indexdata_;
      public static final int DATASIZE_FIELD_NUMBER = 3;
      private int datasize_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private static final long serialVersionUID = 0L;

      private BlobHeader(com.google.protobuf.GeneratedMessageLite.Builder builder) {
         super(builder);
         this.unknownFields = builder.getUnknownFields();
      }

      private BlobHeader(boolean noInit) {
         this.unknownFields = ByteString.EMPTY;
      }

      public static Fileformat.BlobHeader getDefaultInstance() {
         return defaultInstance;
      }

      public Fileformat.BlobHeader getDefaultInstanceForType() {
         return defaultInstance;
      }

      private BlobHeader(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                     ByteString bs = input.readBytes();
                     this.bitField0_ |= 1;
                     this.type_ = bs;
                     break;
                  case 18:
                     this.bitField0_ |= 2;
                     this.indexdata_ = input.readBytes();
                     break;
                  case 24:
                     this.bitField0_ |= 4;
                     this.datasize_ = input.readInt32();
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
            try {
               unknownFieldsCodedOutput.flush();
            } catch (IOException var34) {
            } finally {
               this.unknownFields = unknownFieldsOutput.toByteString();
            }

            this.makeExtensionsImmutable();
         }
      }

      public Parser<Fileformat.BlobHeader> getParserForType() {
         return PARSER;
      }

      @Override
      public boolean hasType() {
         return (this.bitField0_ & 1) == 1;
      }

      @Override
      public String getType() {
         Object ref = this.type_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.type_ = s;
            }

            return s;
         }
      }

      @Override
      public ByteString getTypeBytes() {
         Object ref = this.type_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.type_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      @Override
      public boolean hasIndexdata() {
         return (this.bitField0_ & 2) == 2;
      }

      @Override
      public ByteString getIndexdata() {
         return this.indexdata_;
      }

      @Override
      public boolean hasDatasize() {
         return (this.bitField0_ & 4) == 4;
      }

      @Override
      public int getDatasize() {
         return this.datasize_;
      }

      private void initFields() {
         this.type_ = "";
         this.indexdata_ = ByteString.EMPTY;
         this.datasize_ = 0;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasType()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasDatasize()) {
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
            output.writeBytes(1, this.getTypeBytes());
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeBytes(2, this.indexdata_);
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeInt32(3, this.datasize_);
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
               size += CodedOutputStream.computeBytesSize(1, this.getTypeBytes());
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeBytesSize(2, this.indexdata_);
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeInt32Size(3, this.datasize_);
            }

            size += this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static Fileformat.BlobHeader parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(data);
      }

      public static Fileformat.BlobHeader parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Fileformat.BlobHeader parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(data);
      }

      public static Fileformat.BlobHeader parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(data, extensionRegistry);
      }

      public static Fileformat.BlobHeader parseFrom(InputStream input) throws IOException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(input);
      }

      public static Fileformat.BlobHeader parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Fileformat.BlobHeader parseDelimitedFrom(InputStream input) throws IOException {
         return (Fileformat.BlobHeader)PARSER.parseDelimitedFrom(input);
      }

      public static Fileformat.BlobHeader parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Fileformat.BlobHeader)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static Fileformat.BlobHeader parseFrom(CodedInputStream input) throws IOException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(input);
      }

      public static Fileformat.BlobHeader parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (Fileformat.BlobHeader)PARSER.parseFrom(input, extensionRegistry);
      }

      public static Fileformat.BlobHeader.Builder newBuilder() {
         return Fileformat.BlobHeader.Builder.create();
      }

      public Fileformat.BlobHeader.Builder newBuilderForType() {
         return newBuilder();
      }

      public static Fileformat.BlobHeader.Builder newBuilder(Fileformat.BlobHeader prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public Fileformat.BlobHeader.Builder toBuilder() {
         return newBuilder(this);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder
         extends com.google.protobuf.GeneratedMessageLite.Builder<Fileformat.BlobHeader, Fileformat.BlobHeader.Builder>
         implements Fileformat.BlobHeaderOrBuilder {
         private int bitField0_;
         private Object type_ = "";
         private ByteString indexdata_ = ByteString.EMPTY;
         private int datasize_;

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
         }

         private static Fileformat.BlobHeader.Builder create() {
            return new Fileformat.BlobHeader.Builder();
         }

         public Fileformat.BlobHeader.Builder clear() {
            super.clear();
            this.type_ = "";
            this.bitField0_ &= -2;
            this.indexdata_ = ByteString.EMPTY;
            this.bitField0_ &= -3;
            this.datasize_ = 0;
            this.bitField0_ &= -5;
            return this;
         }

         public Fileformat.BlobHeader.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Fileformat.BlobHeader getDefaultInstanceForType() {
            return Fileformat.BlobHeader.getDefaultInstance();
         }

         public Fileformat.BlobHeader build() {
            Fileformat.BlobHeader result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public Fileformat.BlobHeader buildPartial() {
            Fileformat.BlobHeader result = new Fileformat.BlobHeader(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.type_ = this.type_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.indexdata_ = this.indexdata_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            result.datasize_ = this.datasize_;
            result.bitField0_ = to_bitField0_;
            return result;
         }

         public Fileformat.BlobHeader.Builder mergeFrom(Fileformat.BlobHeader other) {
            if (other == Fileformat.BlobHeader.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasType()) {
                  this.bitField0_ |= 1;
                  this.type_ = other.type_;
               }

               if (other.hasIndexdata()) {
                  this.setIndexdata(other.getIndexdata());
               }

               if (other.hasDatasize()) {
                  this.setDatasize(other.getDatasize());
               }

               this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
               return this;
            }
         }

         public final boolean isInitialized() {
            return !this.hasType() ? false : this.hasDatasize();
         }

         public Fileformat.BlobHeader.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Fileformat.BlobHeader parsedMessage = null;

            try {
               parsedMessage = (Fileformat.BlobHeader)Fileformat.BlobHeader.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (Fileformat.BlobHeader)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }
            }

            return this;
         }

         @Override
         public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
         }

         @Override
         public String getType() {
            Object ref = this.type_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.type_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         @Override
         public ByteString getTypeBytes() {
            Object ref = this.type_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.type_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public Fileformat.BlobHeader.Builder setType(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.type_ = value;
               return this;
            }
         }

         public Fileformat.BlobHeader.Builder clearType() {
            this.bitField0_ &= -2;
            this.type_ = Fileformat.BlobHeader.getDefaultInstance().getType();
            return this;
         }

         public Fileformat.BlobHeader.Builder setTypeBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.type_ = value;
               return this;
            }
         }

         @Override
         public boolean hasIndexdata() {
            return (this.bitField0_ & 2) == 2;
         }

         @Override
         public ByteString getIndexdata() {
            return this.indexdata_;
         }

         public Fileformat.BlobHeader.Builder setIndexdata(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.indexdata_ = value;
               return this;
            }
         }

         public Fileformat.BlobHeader.Builder clearIndexdata() {
            this.bitField0_ &= -3;
            this.indexdata_ = Fileformat.BlobHeader.getDefaultInstance().getIndexdata();
            return this;
         }

         @Override
         public boolean hasDatasize() {
            return (this.bitField0_ & 4) == 4;
         }

         @Override
         public int getDatasize() {
            return this.datasize_;
         }

         public Fileformat.BlobHeader.Builder setDatasize(int value) {
            this.bitField0_ |= 4;
            this.datasize_ = value;
            return this;
         }

         public Fileformat.BlobHeader.Builder clearDatasize() {
            this.bitField0_ &= -5;
            this.datasize_ = 0;
            return this;
         }
      }
   }

   public interface BlobHeaderOrBuilder extends MessageLiteOrBuilder {
      boolean hasType();

      String getType();

      ByteString getTypeBytes();

      boolean hasIndexdata();

      ByteString getIndexdata();

      boolean hasDatasize();

      int getDatasize();
   }

   public interface BlobOrBuilder extends MessageLiteOrBuilder {
      boolean hasRaw();

      ByteString getRaw();

      boolean hasRawSize();

      int getRawSize();

      boolean hasZlibData();

      ByteString getZlibData();

      boolean hasLzmaData();

      ByteString getLzmaData();

      @Deprecated
      boolean hasOBSOLETEBzip2Data();

      @Deprecated
      ByteString getOBSOLETEBzip2Data();

      boolean hasLz4Data();

      ByteString getLz4Data();
   }
}
