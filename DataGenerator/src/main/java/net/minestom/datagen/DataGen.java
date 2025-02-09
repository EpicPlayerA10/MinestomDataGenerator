package net.minestom.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DataGen {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(DataGen.class);
    private static final Path OUTPUT = Path.of("../MinestomData/");

    public static void main(String[] args) {
        LOGGER.info("Generation starting...");
        for (var type : DataGenType.values()) {
            generate(type, type.getGenerator());
        }
        LOGGER.info("Generation done!");
    }

    public static void generate(DataGenType type, DataGenerator generator) {
        final var path = OUTPUT.resolve(type.getFileName() + ".json");
        try {
            // Ensure that the directory exists
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                GSON.toJson(generator.generate(), writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
