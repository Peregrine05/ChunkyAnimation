[![Master](https://github.com/ThatRedox/ChunkyAnimation/actions/workflows/master.yml/badge.svg?branch=master)](https://github.com/ThatRedox/ChunkyAnimation/actions/workflows/master.yml)

# ChunkAnimate
ChunkyAnimate is a plugin to the Minecraft path-tracer [Chunky](https://github.com/chunky-dev/chunky) which helps load
multiple json files to render a scene without having to completely reload Chunky on every frame. It is currently
a work in progress, but it is usable to make animations with json files generated by external tools such as 
[ChunkyAnimationAutomation](https://github.com/jackjt8/ChunkyAnimationAutomation).

## Installation
### Note: The latest version requires at least snapshot `2.4.0-183-gffad4f42`
Download the latest plugin build and extract it. In the Chunky Launcher, expand `Advanced Settings` and click on 
`Manage plugins`. In the `Plugin Manager` window click on `Add` and select the `.jar` file in the extracted zip file. 
Click on `Save` and start Chunky as usual.

![image](https://user-images.githubusercontent.com/42661490/116319916-28ef2580-a76c-11eb-9f93-86d444a349fd.png)

To download the latest Chunky snapshot, open `Advanced Settings` in the Chunky Launcher and enable `Download Snapshots`. Then click on the `Check for update` button.

![image](https://user-images.githubusercontent.com/42661490/116319834-0e1cb100-a76c-11eb-8842-e72dc2b76854.png)

## Usage
Upon opening Chunky, there will be a new tab called `Animation Controls`. Here you can choose a folder with json files
to load as an animation. Afterwards, click `Start Animation` to start automatically rendering animation frames. Click
`Stop Animation` to stop animating. The render reset button will **NOT** stop animating.

The resulting animation frames will be saved next to the initial scene under the `animate` folder.
