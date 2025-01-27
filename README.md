[![Master](https://github.com/ThatRedox/ChunkyAnimation/actions/workflows/master.yml/badge.svg?branch=master)](https://github.com/ThatRedox/ChunkyAnimation/actions/workflows/master.yml)

# ChunkAnimate
ChunkyAnimate is a plugin to the Minecraft path-tracer [Chunky](https://github.com/chunky-dev/chunky) which provides tools and modifications to aid in both the creation and the rendering of animations. The plugin removes the need to completely reload Chunky on every frame. The `Keyframe Editor` can be used to created keyframes to then be animated and rendered or you can load json files generated by external tools such as [ChunkyAnimationAutomation](https://github.com/jackjt8/ChunkyAnimationAutomation).

## Download
* [Releases](https://github.com/ThatRedox/ChunkyAnimation/releases)
* [Latest build from master (may be expired)](https://nightly.link/ThatRedox/ChunkyAnimation/workflows/master/master/ChunkyAnimatePlugin.zip)

## Installation
### Note: The latest version requires at least Chunky `2.4.0`
Download the latest plugin build and extract it. In the Chunky Launcher, expand `Advanced Settings` and click on 
`Manage plugins`. In the `Plugin Manager` window click on `Add` and select the `.jar` file in the extracted zip file. 
Click on `Save` and start Chunky as usual.

![image](https://user-images.githubusercontent.com/42661490/131207533-99f55041-5a2b-401f-979d-875d51971be1.png)

## Usage
Upon opening Chunky, there will be two new tabs called `Animation Controls` and `Keyframe Editor`.

To Keyframe an animation natively within Chunky using the `Keyframe Editor` tab:

1. Edit (Keyframe)`Name`: "Keyframe" to something like "Keyframe1"
2. Edit (Keyframe)`Time` under (Keyframe)`Name`
3. Press `Add Keyframe`
4. Select Keyframe and then move camera and edit scene properties as desired and then press `From Scene` on all values to be animated
5. Repeat Steps 1-4 to add all Keyframes
6. Use `Play Preview` to preview animation or drag the `Time` slider.
7. To apply edits to a Keyframe; select the keyframe and then use `From Scene` to apply changes.

Note 1 - Use `Save Keyframes` and `Load keyframes` to resume progress.

Note 2 - Using `From Scene` and then `Add Keyframe` creates blank frames.


To render an animation in the `Animation Controls` tab:

  1a. If using the `Keyframe Editor` make sure to load the keyframes first if none are present.

  1b. If using externally generated json files click on `Load Frames From Folder`.

2. Press `From Keyframes`

3. Choose the Framerate.

4. Click `Start animation` to start automatically rendering the animation frames. Click
`Stop Animation` to stop animating. The render reset button will **NOT** stop animating.
![image](https://user-images.githubusercontent.com/42661490/131207357-d5a5fe74-8bc5-4a95-be53-2922bfa41f81.png)

The resulting animation frames will be saved next to the initial scene under the `animate` folder.
![image](https://user-images.githubusercontent.com/42661490/131207367-c7aabbb2-c0da-4e31-89a4-6de193ecd587.png)

To convert the frames into a video you may use something like ffmpeg. Below is an example command you could use

```
ffmpeg -r 30 -f image2 -s 1920x1080 -i frame%05d.png -vcodec libx264 -crf 25  -pix_fmt yuv420p test.mp4
```

```
-i is the file name with %05d defining the suffix. (ie frame00002, frame00003, etc.)
-r is the framerate (fps)
-crf is the quality, lower means better quality, 15-25 is usually good
-s is the resolution
-pix_fmt yuv420p specifies the pixel format, change this as needed
```

