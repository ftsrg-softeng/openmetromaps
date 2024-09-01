package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.utils.config.PbfConfig;
import de.topobyte.osm4j.utils.config.TboConfig;

public class OsmOutputConfig {
   private FileFormat fileFormat;
   private PbfConfig pbfConfig;
   private TboConfig tboConfig;
   private boolean writeMetadata;

   public OsmOutputConfig(FileFormat outputFormat) {
      this(outputFormat, new PbfConfig(), new TboConfig(), true);
   }

   public OsmOutputConfig(FileFormat outputFormat, boolean writeMetadata) {
      this(outputFormat, new PbfConfig(), new TboConfig(), writeMetadata);
   }

   public OsmOutputConfig(FileFormat outputFormat, PbfConfig pbfConfig, TboConfig tboConfig, boolean writeMetadata) {
      this.fileFormat = outputFormat;
      this.pbfConfig = pbfConfig;
      this.tboConfig = tboConfig;
      this.writeMetadata = writeMetadata;
   }

   public FileFormat getFileFormat() {
      return this.fileFormat;
   }

   public void setFileFormat(FileFormat fileFormat) {
      this.fileFormat = fileFormat;
   }

   public PbfConfig getPbfConfig() {
      return this.pbfConfig;
   }

   public void setPbfConfig(PbfConfig pbfConfig) {
      this.pbfConfig = pbfConfig;
   }

   public TboConfig getTboConfig() {
      return this.tboConfig;
   }

   public void setTboConfig(TboConfig tboConfig) {
      this.tboConfig = tboConfig;
   }

   public boolean isWriteMetadata() {
      return this.writeMetadata;
   }

   public void setWriteMetadata(boolean writeMetadata) {
      this.writeMetadata = writeMetadata;
   }
}
