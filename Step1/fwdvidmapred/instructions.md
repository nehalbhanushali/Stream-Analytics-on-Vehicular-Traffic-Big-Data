### Env
linux

### Pre requisites:
1. Install ffmpeg
2. openalpr
3. hadoop ( we used 2.8.0 here)
4. python ( we are on 2.7.2, hence -> print x )

### Instructions to separately run the python mapreduce
run: `$ shell.sh video.mp4` // whatever the name is


### OUTCOME :
video -> frames(frame names stored in input file) -> txt of license plate names and confidence ( eg: "- FGADG23 Confidence 89")
