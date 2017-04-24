# Big_Data_Final_Project

[Presentation link](http://prezi.com/xusx-vdbosup/?utm_campaign=share&utm_medium=copy)

## PREREQUISITES
1. sudo chmod 755 to all 3 files (*.sh, *.py) (sudo chmod 755 *.sh)
2. install opnalpr and ffmpeg libraries 
   i. openalpr: sudo apt-get update && sudo apt-get install -y openalpr openalpr-daemon openalpr-utils libopenalpr-dev
3. ffmpeg: sudo apt-get install ffmpeg)

## INSTRUCTIONS TO RUN THE CODE

1. Phase 1 needs to be executed in linux. The shell script runs phase 1. Inorder to run the shell script, run the following command in the terminal :

```
~/path-to-shell/vidmapred2.sh ~/path-to-video-folder/input_videos
```
NOTE : Please be careful about the path

2. Phase 2 can be executed in linux using mono and windows using visual studio. Update the `/input-file-path/` in the event listener `EntryStream/Program.cs` code to point at Phase1 output files `../java_MR_output/*`.

NOTE : The event listeners must be started before completion of Phase1 as it watches the files being dumped in `../java_MR_output/*`.

The Event-hub-listener parses input json and sends it to the Stream Analytics Job (NOTE : This is also already running on the cloud)

3. The final output can be seen as storage tables using Azure storage explorer and visualised in PowerBI dashboards.



