//package com.gluonhq.charm.down.plugins.android;
//
//import android.Manifest;
//import android.media.AudioFormat;
//import android.media.AudioRecord;
//import android.media.MediaRecorder;
//import com.gluonhq.impl.charm.down.plugins.Constants;
//import com.gluonhq.impl.charm.down.plugins.DefaultEsMicrophoneService;
//import com.gluonhq.impl.charm.down.plugins.android.PermissionRequestActivity;
//
//import javafx.concurrent.Task;
//
//import java.io.ByteArrayOutputStream;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.concurrent.*;
//import java.util.function.Function;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//public class AndroidEsMicrophoneService extends DefaultEsMicrophoneService {
//
//    private static final Logger LOG = Logger.getLogger(AndroidEsMicrophoneService.class.getName());
//    private boolean debug;
//
//    // record duration for a chunk, in seconds
//    private long CHUNK_RECORD_TIME = 60;  // 1 minute
//
//    private static final int DEFAULT_BUFFER_INCREASE_FACTOR = 2;
//
//    private AudioRecord audioRecord = null;
//
//    private RecorderTask recorderTask;
//
//    private ExecutorService recordingExecutor;
//    private Function<String, Boolean> addChunk;
//
//    public AndroidEsMicrophoneService() {
//        if ("true".equals(System.getProperty(Constants.DOWN_DEBUG))) {
//            debug = true;
//        }
//    }
//
//    @Override
//    protected void start(float sampleRate, int sampleSizeInBits, int channels, int chunkRecordTime, Function<String, Boolean> addChunk) {
//        boolean audioPermission = PermissionRequestActivity.verifyPermissions(Manifest.permission.RECORD_AUDIO);
//        if (! audioPermission) {
//            LOG.log(Level.WARNING, "Audio recording disabled");
//            return;
//        }
//        CHUNK_RECORD_TIME = chunkRecordTime;
//        recorderTask = new RecorderTask((int) sampleRate, sampleSizeInBits, channels);
//        recordingExecutor = createExecutor("AudioRecording");
//        recordingExecutor.execute(recorderTask);
//        this.addChunk = addChunk;
//    }
//
//    @Override
//    protected void stop() {
//        if (recorderTask != null) {
//            recorderTask.stop();
//        }
//
//        if (recordingExecutor != null) {
//            recordingExecutor.shutdown();
//            try {
//                recordingExecutor.awaitTermination(5, TimeUnit.SECONDS);
//            } catch (InterruptedException ex) { }
//        }
//    }
//
//    private class RecorderTask extends Task<Void> {
//
//        private TimedAudioCapture timedAudioCapture;
//        private ScheduledExecutorService scheduler;
//        private CountDownLatch latch;
//        private final int sampleRate;
//        private final int sampleSizeInBits;
//        private final int channels;
//
//        public RecorderTask(int sampleRate, int sampleSizeInBits, int channels) {
//            this.sampleRate = sampleRate;
//            this.sampleSizeInBits = sampleSizeInBits;
//            this.channels = channels;
//        }
//
//        @Override
//        protected Void call() {
//
//            final int audioFormat = sampleSizeInBits == 16 ? AudioFormat.ENCODING_PCM_16BIT : AudioFormat.ENCODING_PCM_8BIT;
//            final int channelConfig = channels == 2 ? AudioFormat.CHANNEL_IN_STEREO : AudioFormat.CHANNEL_IN_MONO;
//            int bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
//            int increasedBufferSize =  bufferSize * DEFAULT_BUFFER_INCREASE_FACTOR;
//
//            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, increasedBufferSize);
//
//            audioRecord.startRecording();
//            latch = new CountDownLatch(1);
//            timedAudioCapture = new TimedAudioCapture(sampleRate, sampleSizeInBits, channels, bufferSize);
//
//            scheduler = Executors.newScheduledThreadPool(1);
//            scheduler.scheduleAtFixedRate(() -> {
//                if (timedAudioCapture != null) {
//                    timedAudioCapture.update();
//                }
//            }, CHUNK_RECORD_TIME, CHUNK_RECORD_TIME, TimeUnit.SECONDS);
//            timedAudioCapture.start();
//
//            try {
//                latch.await();
//            } catch (InterruptedException ex) {
//                LOG.log(Level.WARNING, "Error count down latch", ex);
//            }
//            return null;
//        }
//
//        @Override
//        protected void cancelled() {
//            super.cancelled();
//            LOG.log(Level.WARNING, "Recording task was cancelled");
//            updateRecordingStatus(false);
//            stop();
//        }
//
//        @Override
//        protected void failed() {
//            super.cancelled();
//            LOG.log(Level.WARNING, "Recording task failed");
//            updateRecordingStatus(false);
//            stop();
//        }
//
//        @Override
//        protected void succeeded() {
//            super.succeeded();
//            LOG.log(Level.INFO, "Recording task was succeeded");
//            closeLine();
//        }
//
//        private void closeLine() {
//            if (audioRecord != null) {
//                audioRecord.stop();
//                audioRecord.release();
//                audioRecord = null;
//            }
//            if (scheduler != null) {
//                scheduler.shutdown();
//                try {
//                    scheduler.awaitTermination(5, TimeUnit.SECONDS);
//                } catch (InterruptedException ex) { }
//            }
//            if (debug) {
//                LOG.log(Level.INFO, "Finished recording");
//            }
//            updateRecordingStatus(false);
//        }
//
//        public void stop() {
//            if (debug) {
//                LOG.log(Level.INFO, "Stop recording");
//            }
//
//            if (timedAudioCapture != null) {
//                timedAudioCapture.stop();
//            }
//            if (latch != null) {
//                latch.countDown();
//            }
//        }
//    }
//
//    /**
//     * Reads data from the input channel and writes to the output stream, with a
//     * maximum time of CHUNK_RECORD_TIME
//     */
//    private class TimedAudioCapture {
//
//        private final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm-ss.SSS");
//        private volatile boolean isRunning;
//
//        private final Thread thread;
//        private final int channels;
//        private final int sampleRate;
//        private final int sampleSizeInBits;
//        private int chunk  = 0;
//
//        int blockSizeVoice = 256;                               // deal with this many samples at a time
//        int sampleRateVoice = 8000;                             // Sample rate in Hz
//        public double frequencyVoice = 0.0;
//
//
//        public TimedAudioCapture(int sampleRate, int sampleSizeInBits, int channels, int bufferSize) {
//            this.sampleRate = sampleRate;
//            this.sampleSizeInBits = sampleSizeInBits;
//            this.channels = channels;
//
//            thread = new Thread(() -> {
//                isRunning = audioRecord != null;
//                if (debug) {
//                    LOG.log(Level.INFO, "Start recording chunk 0");
//                }
//                final byte[] readBuffer = new byte[bufferSize];
//                double[] toTransformVoice = new double[bufferSize];
//                int[] toTransformInt = new int[bufferSize];
//
//                int outputSize = (int) (CHUNK_RECORD_TIME * sampleRate * sampleSizeInBits * channels / 8);
//
//                if (debug) {
//                    LOG.log(Level.INFO, String.format("reserved initial size %d bytes", outputSize));
//                }
//                ByteArrayOutputStream recordBytes = new ByteArrayOutputStream(outputSize);
//
//
//                while (isRunning && audioRecord != null) {
//                    if (isRunning && audioRecord != null) {
//                        int bufferResult = audioRecord.read(readBuffer, 0, bufferSize);
//
//                        for(int i = 0; i < blockSizeVoice && i < bufferResult; i++) {
//                            toTransformVoice[i] = (double) readBuffer[i] / 32768.0; // signed 16 bit
//                           // toTransformInt[i] = (int) (readBuffer[i]); // signed 16 bit
//                            toTransformInt[i] = (int) Math.abs(readBuffer[i]);
//                        }
//                        DefaultEsMicrophoneService.updateStatus("dziala: " + readBuffer[0] + ":" +readBuffer[1] + ":" +readBuffer.length,readBuffer, toTransformInt);
//
////                        DefaultEsMicrophoneService.updateStatus(
////                                "bufferResult: " + bufferResult +
////                                        "\r\nblockSizeVoice: " + blockSizeVoice +
////                                        "\r\nDate: " + new Date()  ,
////                                readBuffer
//////                                short2byte(readBuffer)
////                        );
//
////                        if (bufferResult != AudioRecord.ERROR_INVALID_OPERATION && bufferResult != AudioRecord.ERROR_BAD_VALUE) {
//////                            byte[] buffer = short2byte(readBuffer);
//////                            recordBytes.write(buffer, 0, buffer.length);
////                            recordBytes.write(readBuffer, 0, readBuffer.length);
////                        }
//                    }
//                }
//            });
//
//            thread.setName("TimedAudioCapture");
//        }
//
//        public void update() {
//            if (debug) {
//                LOG.log(Level.INFO, String.format("Start recording chunk %d", chunk));
//            }
//        }
//
//        public void stop() {
//            if (debug) {
//                LOG.log(Level.INFO, "Stop TimedAudioCapture");
//            }
//            isRunning = false;
//        }
//
//        public void start() {
//            thread.start();
//        }
//
//
//    }
//
//    private ExecutorService createExecutor(final String name) {
//        ThreadFactory factory = r -> {
//            Thread t = new Thread(r);
//            t.setName(name);
//            t.setDaemon(true);
//            return t;
//        };
//        return Executors.newSingleThreadExecutor(factory);
//    }
//
//    private byte[] short2byte(short[] sData) {
//        int shortArrsize = sData.length;
//        byte[] bytes = new byte[shortArrsize * 2];
//        for (int i = 0; i < shortArrsize; i++) {
//            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
//            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
//            sData[i] = 0;
//        }
//        return bytes;
//    }
//
//}
