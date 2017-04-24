SCRIPT_FILE=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT_FILE")
# above 2 lines is a hack to get current folder
echo "base dir>> $BASEDIR"
HADOOP_HOME="/usr/local/hadoop-2.8.0"
echo "The Hadoop home is set to $HADOOP_HOME"

HADOOP_STRM_JAR=$HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.8.0.jar

VIDEO_FOLDER=$1

for VIDEO_PATH in "$VIDEO_FOLDER"/*
do
  echo "VIDEO HERE >> $VIDEO_PATH"
  VIDEO_FILE=$(basename "$VIDEO_PATH" ".mp4")  
  echo "VIDEO NAME >> $VIDEO_FILE"


  MAPRED_IMG_DIR="$BASEDIR/CITIES/$VIDEO_FILE/video_map_red/images"
  MAPRED_INP_DIR="$BASEDIR/CITIES/$VIDEO_FILE/video_map_red/input"
  echo "The input video image files will be stored in $MAPRED_IMG_DIR"

  MAPRED_OUT_DIR="$BASEDIR/CITIES/$VIDEO_FILE/video_map_red/output"
  echo "The output of the process will be stored in $MAPRED_OUT_DIR"

  echo "Processing video file: $VIDEO_PATH"

  echo "Extracting images from video: $VIDEO_PATH into $MAPRED_IMG_DIR"
  mkdir -p $MAPRED_IMG_DIR
  rm -r $MAPRED_IMG_DIR
  mkdir -p $MAPRED_IMG_DIR
  mkdir -p $MAPRED_INP_DIR
  rm -r $MAPRED_INP_DIR
  mkdir -p $MAPRED_INP_DIR

  ffmpeg -i $VIDEO_PATH -r 1/1 $MAPRED_IMG_DIR/%07d.png

  echo "Extracting image file names into $MAPRED_INP_DIR"
  ls $MAPRED_IMG_DIR/*.png > $MAPRED_INP_DIR/image_names.txt 

  echo "Starting map/reduce..."
  mkdir -p $MAPRED_OUT_DIR
  rm -r $MAPRED_OUT_DIR
  $HADOOP_HOME/bin/hadoop jar $HADOOP_STRM_JAR -input $MAPRED_INP_DIR -output $MAPRED_OUT_DIR -mapper $BASEDIR/mapper.py -reducer $BASEDIR/reducer.py

  echo "******************"
  echo "******************"
  echo "******************"
  echo "******************"

  echo "Finished python map/reduce! The output is available in $MAPRED_OUT_DIR"
  echo "******************"
  echo "******************"
  echo "******************"
  echo "******************"

  JAVAMAPRED_CLASSES_DIR="$BASEDIR/license_classes"

  mkdir -p $JAVAMAPRED_CLASSES_DIR
  rm -r $JAVAMAPRED_CLASSES_DIR
  mkdir -p $JAVAMAPRED_CLASSES_DIR

  echo "%%%%%%%%%%%%%%%%%%%%%%"
  echo "%%%%%%%%%%%%%%%%%%%%%%"

  javac -classpath $HADOOP_HOME/share/hadoop/common/hadoop-common-2.8.0.jar:$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.8.0.jar -d       $JAVAMAPRED_CLASSES_DIR $BASEDIR/LicenseMapper1.java $BASEDIR/LicenseReducer1.java $BASEDIR/LicenseDriver1.java

  jar cvf $BASEDIR/license.jar -C $JAVAMAPRED_CLASSES_DIR/ .
  rm -r $BASEDIR/licenselist_output_mm

  $HADOOP_HOME/bin/hadoop jar $BASEDIR/license.jar U.CC.LicenseDriver1 $MAPRED_OUT_DIR $BASEDIR/java_MR_output/$VIDEO_FILE/licenselist_output_mm

  echo "Finished java map/reduce! The output is available in $BASEDIR/java_MR_output/$VIDEO_FILE/licenselist_output_mm"

mv "$BASEDIR/java_MR_output/$VIDEO_FILE/licenselist_output_mm/part-00000" "$BASEDIR/WatchFolder/$VIDEO_FILE.json" 

done
