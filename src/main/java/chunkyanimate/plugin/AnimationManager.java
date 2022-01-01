package chunkyanimate.plugin;

import chunkyanimate.animation.AnimationFrame;
import chunkyanimate.animation.AnimationKeyFrame;
import chunkyanimate.animation.AnimationUtils;
import chunkyanimate.util.ObservableValue;
import it.unimi.dsi.fastutil.doubles.Double2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectSortedMap;
import se.llbit.chunky.main.Chunky;
import se.llbit.chunky.renderer.scene.Scene;
import se.llbit.log.Log;
import se.llbit.util.TaskTracker;

import java.io.*;
import java.util.*;

public class AnimationManager {

    private Chunky chunky = null;
    private boolean animating = false;
    public final Double2ObjectSortedMap<AnimationKeyFrame> animationKeyFrames = new Double2ObjectRBTreeMap<>();

    private final ObservableValue<Integer> currentFrameValue = new ObservableValue<>(0);
    public final ObservableValue.ObservableInterface<Integer> currentFrame = currentFrameValue.getObservableInterface();

    private final ArrayList<AnimationFrame> animationFrames = new ArrayList<>();
    private final ObservableValue<Integer> totalFramesValue = new ObservableValue<>(0);
    public final ObservableValue.ObservableInterface<Integer> totalFrames =  totalFramesValue.getObservableInterface();

    public void setChunky(Chunky chunky) {
        this.chunky = chunky;
    }

    public void saveKeyframes(File out) throws IOException {
        AnimationUtils.saveKeyframes(out, animationKeyFrames);
    }

    public void loadKeyframes(File in) throws IOException {
        AnimationUtils.loadKeyframes(in, animationKeyFrames);
    }

    public void saveFrame(int count) {
        Scene scene = chunky.getSceneManager().getScene();

        File baseDir = chunky.getRenderController().getContext().getSceneDirectory();
        String path = baseDir.getPath();
        path += String.format("%sanimate%sframe%05d%s",
                File.separator,
                File.separator,
                count,
                scene.getOutputMode().getExtension());

        File saveFile = new File(path);
        File parentDir = saveFile.getParentFile();
        if (!parentDir.exists() && !saveFile.getParentFile().mkdirs()) {
            Log.error("Failed to create output directory: " + saveFile.getParentFile().getPath());
            return;
        }
        try {
            if (!saveFile.createNewFile()) {
                Log.error("File already exists: " + saveFile.getPath());
                return;
            }
        } catch (IOException e) {
            Log.error("Failed to create output file", e);
            return;
        }

        scene.saveFrame(saveFile, TaskTracker.NONE, chunky.getRenderController().getContext().numRenderThreads());
    }

    public void frameComplete() {
        if (animating) {
            chunky.getSceneManager().getScene().pauseRender();

            int frameNumber = this.currentFrameValue.getValue();
            saveFrame(frameNumber);
            currentFrameValue.setValue(frameNumber+1);

            runUntilRender();
        }
    }

    public void startAnimation() {
        this.animating = true;
        this.currentFrameValue.setValue(0);
        runUntilRender();
    }

    public void stopAnimation() {
        this.animating = false;
    }

    public boolean isAnimating() {
        return this.animating;
    }

    public void runUntilRender() {
        Scene scene = chunky.getSceneManager().getScene();
        synchronized (scene) {
            scene.haltRender();
            scene.forceReset();

            if (currentFrameValue.getValue() < animationFrames.size()) {
                AnimationFrame frame = animationFrames.get(currentFrameValue.getValue());
                if (frame != null) {
                    frame.apply(scene);
                    scene.startRender();
                    return;
                }
            }
        }

        this.animating = false;
    }

    public void fromFolder(File folder) {
        animating = false;
        currentFrameValue.setValue(0);
        animationFrames.clear();
        totalFramesValue.setValue(0);

        AnimationUtils.loadFramesFromFolder(
                folder,
                animationFrames,
                chunky.getSceneManager().getScene()
        );

        totalFramesValue.setValue(animationFrames.size());
    }

    public void fromKeyFrames(double framerate) {
        animating = false;
        currentFrameValue.setValue(0);
        animationFrames.clear();
        totalFramesValue.setValue(0);

        AnimationUtils.loadFramesFromKeyframes(
                animationKeyFrames,
                framerate,
                animationFrames,
                chunky.getSceneManager().getScene()
        );

        totalFramesValue.setValue(animationFrames.size());
    }
}
