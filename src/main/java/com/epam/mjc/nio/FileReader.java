package com.epam.mjc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public Profile getDataFromFile(File file) {
        List<String> rawFields = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel()) {
            ByteBuffer buff = ByteBuffer.allocate(1024);

            fc.read(buff);
            buff.flip();

            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            String rawResult = decoder.decode(buff).toString();

            for(String line : rawResult.split("\\r?\\n")) {
                rawFields.add(line.split(" ")[1]);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        return new Profile(
                rawFields.get(0),
                Integer.valueOf(rawFields.get(1)),
                rawFields.get(2),
                Long.valueOf(rawFields.get(3)));
    }
}
