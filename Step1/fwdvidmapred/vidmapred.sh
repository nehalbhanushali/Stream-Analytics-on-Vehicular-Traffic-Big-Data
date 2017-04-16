SCRIPT_FILE=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT_FILE")
HADOOP_HOME="/usr/local/hadoop-2.8.0"
echo "The Hadoop home is set to $HADOOP_HOME"

HADOOP_STRM_JAR=$HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.8.0.jar

MAPRED_IMG_DIR="$BASEDIR/video_map_red/images"
MAPRED_INP_DIR="$BASEDIR/video_map_red/input"
echo "The input video image files will be stored in $MAPRED_IMG_DIR"

MAPRED_OUT_DIR="$BASEDIR/video_map_red/output"
echo "The output of the process will be stored in $MAPRED_OUT_DIR"

VIDEO_FILE=$1
echo "Processing video file: $VIDEO_FILE"

echo "Extracting images from video: $VIDEO_FILE into $MAPRED_IMG_DIR"
mkdir -p $MAPRED_IMG_DIR
rm -r $MAPRED_IMG_DIR
mkdir -p $MAPRED_IMG_DIR
mkdir -p $MAPRED_INP_DIR
rm -r $MAPRED_INP_DIR
mkdir -p $MAPRED_INP_DIR
ffmpeg -i $VIDEO_FILE -r 1/1 $MAPRED_IMG_DIR/%07d.png

echo "Extracting image file names into $MAPRED_INP_DIR"
ls $MAPRED_IMG_DIR/*.png > $MAPRED_INP_DIR/image_names.txt 

echo "Starting map/reduce..."
mkdir -p $MAPRED_OUT_DIR
rm -r $MAPRED_OUT_DIR
$HADOOP_HOME/bin/hadoop jar $HADOOP_STRM_JAR -input $MAPRED_INP_DIR -output $MAPRED_OUT_DIR -mapper $BASEDIR/mapper.py -reducer $BASEDIR/reducer.py

echo "Finished map/reduce! The output is available in $MAPRED_OUT_DIR"
