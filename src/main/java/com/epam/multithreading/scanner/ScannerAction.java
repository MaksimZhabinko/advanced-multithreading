package com.epam.multithreading.scanner;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

public class ScannerAction extends RecursiveTask<ConcurrentHashMap<String, Long>> {
    public static final String FILE_COUNT = "FileCount";
    public static final String FOLDER_COUNT = "FolderCount";
    public static final String FILE_SIZE = "FileSize";
    private final ConcurrentHashMap<String, Long> scanned;
    private final String directoryPath;

    public ScannerAction(String directoryPath) {
        this.directoryPath = directoryPath;
        this.scanned = new ConcurrentHashMap<>();
    }

    @Override
    protected ConcurrentHashMap<String, Long> compute() {
        File file = new File(directoryPath);
        if (file.listFiles() != null) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ScannerAction scannerAction = new ScannerAction(files[i].getPath());
                    scannerAction.fork();
                    ConcurrentHashMap<String, Long> map = scannerAction.join();
                    folderCount(map);
                    fileCount(map);
                    fileSize(map);
                } else {
                    if (scanned.get(FILE_COUNT) == null || scanned.get(FILE_COUNT) == 0) {
                        scanned.put(FILE_COUNT, 1L);
                        scanned.put(FILE_SIZE, files[i].length());
                    } else {
                        scanned.put(FILE_COUNT, scanned.get(FILE_COUNT) + 1L);
                        scanned.put(FILE_SIZE, scanned.get(FILE_SIZE) + files[i].length());
                    }
                }
            }
        }
        return scanned;
    }

    private void folderCount(Map<String, Long> map) {
        long folderCount = map.get(FOLDER_COUNT) == null ? 0L : map.get(FOLDER_COUNT);
        if (scanned.get(FOLDER_COUNT) == null || scanned.get(FOLDER_COUNT) == 0) {
            scanned.put(FOLDER_COUNT, 1L + folderCount);
        } else {
            scanned.put(FOLDER_COUNT, 1L + folderCount + scanned.get(FOLDER_COUNT));
        }
    }

    private void fileCount(Map<String, Long> map) {
        long fileCount = map.get(FILE_COUNT) == null ? 0L : map.get(FILE_COUNT);
        if (scanned.get(FILE_COUNT) == null || scanned.get(FILE_COUNT) == 0) {
            scanned.put(FILE_COUNT, fileCount);
        } else {
            scanned.put(FILE_COUNT, scanned.get(FILE_COUNT) + fileCount);
        }
    }

    private void fileSize(Map<String, Long> map) {
        long fileSize = map.get(FILE_SIZE) == null ? 0L : map.get(FILE_SIZE);
        if (scanned.get(FILE_SIZE) == null) {
            scanned.put(FILE_SIZE, fileSize);
        } else {
            scanned.put(FILE_SIZE, scanned.get(FILE_SIZE) + fileSize);
        }
    }
}